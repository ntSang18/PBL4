package com.bkzalo.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bkzalo.app.MessageType;
import com.bkzalo.main.MainServer;
import com.bkzalo.model.Model_Client;
import com.bkzalo.model.Model_File;
import com.bkzalo.model.Model_LoadChat;
import com.bkzalo.model.Model_Message;
import com.bkzalo.model.Model_Package_Sender;
import com.bkzalo.model.Model_User;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

public class Service {
	private static Service _Instance;
	private SocketIOServer server;
	private List<Model_Client> ListClient;
    private final int PORT_NUMBER = 9999;
	private final String PATH_FILE = "server_data/";
	private final Map<Integer, ServiceFile> fileReceivers;
	private final Map<Integer, Model_Message> MessageFiles;
	
	public static Service Instance() {
		if(_Instance == null) _Instance = new Service();
		return _Instance;
	}
	
	private Service() {
        ListClient = new ArrayList<>();
        fileReceivers = new HashMap<>();
        MessageFiles = new HashMap<>();
    }
	
	public void StartServer() {
		Configuration config = new Configuration();
        config.setPort(PORT_NUMBER);
        server = new SocketIOServer(config);
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient sioc) {
            }
        });
        
        server.addEventListener("register", Model_User.class, new DataListener<Model_User>() {
            @Override
            public void onData(SocketIOClient sioc, Model_User user, AckRequest ar) throws Exception {
            	MainServer.Settxt("USER REGISTER: " + user.getUsername() + "\n");
                server.getBroadcastOperations().sendEvent("list_user", user);
                addClient(sioc, user);
                }
        });
        
        server.addEventListener("login", Model_User.class, new DataListener<Model_User>() {
            @Override
            public void onData(SocketIOClient sioc, Model_User user, AckRequest ar) throws Exception {
            	MainServer.Settxt("USER LOGIN: " + user.getUsername() + "\n");
                addClient(sioc, user);
                userConnect(user.getId());
            }
        });
        
        server.addEventListener("list_user", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient sioc, Integer userID, AckRequest ar) throws Exception {
                List<Model_User> list = ServiceUser.Instance().ListUser(userID);
				sioc.sendEvent("list_user", list.toArray());
            }
        });
        server.addEventListener("send_to_user", Model_Message.class, new DataListener<Model_Message>() {

			@Override
			public void onData(SocketIOClient sioc, Model_Message data, AckRequest ar) throws Exception {
				sendToClient(data);
				ServiceMessage.Instance().AddMessage(data);
			}
		});
        server.addEventListener("load_chat", Model_LoadChat.class, new DataListener<Model_LoadChat>() {

			@Override
			public void onData(SocketIOClient sioc, Model_LoadChat data, AckRequest ar) throws Exception {
				List<Model_Message> list = ServiceMessage.Instance().ListChat(data.getIdUser1(), data.getIdUser2());
				sioc.sendEvent("list_chat", list.toArray());
			}
        	
		});
        server.addEventListener("init_file", Model_Message.class, new DataListener<Model_Message>() {

			@Override
			public void onData(SocketIOClient arg0, Model_Message data, AckRequest ar) throws Exception {
				int id = ServiceMessage.Instance().AddMessageFile(data);
				data.setId(id);
				File file = toFile(data);
				data.setText("");
				fileReceivers.put(data.getId(), new ServiceFile(file));
				MessageFiles.put(data.getId(), data);
				sendToClient(data);
				ar.sendAckData(id);
			}
		});
        server.addEventListener("send_file", Model_Package_Sender.class, new DataListener<Model_Package_Sender>() {
            @Override
            public void onData(SocketIOClient sioc, Model_Package_Sender data, AckRequest ar) throws Exception {
            	try {
            		SendFile(data);
            		ReceiveFile(data);
                	ar.sendAckData(true);
				} catch (Exception e) {
					e.printStackTrace();
					ar.sendAckData(false);
				}
            }
        });
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient sioc) {
                Model_User user = removeClient(sioc);
                    userDisconnect(user.getId());
                    MainServer.Settxt("USER DISCONNECT: " + user.getUsername() + "\n");
            }
        });
        server.start();
        MainServer.Settxt("Server has Start on port : " + PORT_NUMBER + "\n");
	}
	
    public void userConnect(int userID) {
    	//server.getBroadcastOperations().sendEvent(): gui cho tat ca client
        server.getBroadcastOperations().sendEvent("user_status", userID, true);
    }

    public void userDisconnect(int userID) {
        server.getBroadcastOperations().sendEvent("user_status", userID, false);
    }
    
    public void addClient(SocketIOClient client, Model_User user) {
        ListClient.add(new Model_Client(client, user));
    }
    
    public Model_User removeClient(SocketIOClient client) {
        for (Model_Client d : ListClient) {
            if (d.getClient() == client) {
                ListClient.remove(d);
                return d.getUser();
            }
        }
        return null;
    }
    
    public List<Model_Client> getListClient() {
        return ListClient;
    }
    
    public void sendToClient(Model_Message data) {
    	if(data.getType() == MessageType.FILE.getValue() || data.getType() == MessageType.IMAGE.getValue()) {
    		for (Model_Client c : ListClient) {
                if (c.getUser().getId() == data.getIdUserReceive()) {
                    c.getClient().sendEvent("receive_init_file", data);
                    break;
                }
            }
    	}
    	else {
    		for (Model_Client c : ListClient) {
                if (c.getUser().getId() == data.getIdUserReceive()) {
                    c.getClient().sendEvent("receive_ms", data);
                    break;
                }
            }
    	}
    }
    
    public void SendFile(Model_Package_Sender data) {
    	int idUserReceive = MessageFiles.get(data.getFileID()).getIdUserReceive();
    	for (Model_Client c : ListClient) {
            if (c.getUser().getId() == idUserReceive) {
                c.getClient().sendEvent("receive_file", data);
                break;
            }
        }
    }
    
	public File toFile(Model_Message mgs) {
		return new File(PATH_FILE + mgs.getId() + mgs.GetFileExtention());
	}
	
	public void ReceiveFile(Model_Package_Sender data) throws IOException {
		if(!data.isFinish()) {
			fileReceivers.get(data.getFileID()).writeFile(data.getData());
		}
		else {
			fileReceivers.get(data.getFileID()).Close();
			ServiceMessage.Instance().UpdateMessageFile(data.getFileID(), fileReceivers.get(data.getFileID()).getFile().getAbsolutePath());
			fileReceivers.remove(data.getFileID());
			MessageFiles.remove(data.getFileID());
		}
	}
}
