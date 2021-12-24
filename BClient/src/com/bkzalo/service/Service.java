package com.bkzalo.service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.http.HttpRequest.BodyPublishers;

import org.json.JSONObject;

import com.bkzalo.event.EventErorMessage;
import com.bkzalo.event.PublicEvent;
import com.bkzalo.model.Model_Account;
import com.bkzalo.model.Model_Error;
import com.bkzalo.model.Model_LoadChat;
import com.bkzalo.model.Model_Message;
import com.bkzalo.model.Model_Package_Sender;
import com.bkzalo.model.Model_User;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Service {
	private static Service _Instance;
	private Model_User user;
    private Socket client;
    public final String PATH_FILE_RECEIVE = "client_data_receive/";
    public final String PATH_FILE_SEND = "client_data_send/";
    private final int PORT_NUMBER = 9999;
    private final String IP = "localhost";
	
    private RandomAccessFile accFile;
    private long fileSize;
    private final Map<Integer, ServiceFile> fileReceivers;
    private final Map<Integer, Model_Message> MessageFiles;
	
	public static void main(String[] args) {
		Model_Account data = new Model_Account("thanhsang", "123");	
	}
	
	public static Service Instance() {
		if(_Instance == null) _Instance = new Service();
		return _Instance;
	}
	
	private Service() {
		fileReceivers = new HashMap<>();
		MessageFiles = new HashMap<>();
	}
	
	public void StartServer() {
		try {
			client = IO.socket("http://" + IP + ":" + PORT_NUMBER);
			client.on("list_user", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    //  list user
                    List<Model_User> users = new ArrayList<Model_User>();
                    for (Object o : os) {
                    	Model_User u = new Model_User(o);
                        if (u.getId() != user.getId()) {
                            users.add(u);
                        }
                    }
                    PublicEvent.getInstance().getEventMenuLeft().listUser(users);
                }
            });
			client.on("user_status", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    int userID = (Integer) os[0];
                    boolean status = (Boolean) os[1];
                    if (status) {
                        //  connect
                        PublicEvent.getInstance().getEventMenuLeft().userConnect(userID);
                    } else {
                        //  disconnect
                        PublicEvent.getInstance().getEventMenuLeft().userDisconnect(userID);
                    }
                }
            });
			client.on("receive_ms", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    Model_Message message = new Model_Message(os[0]);
                    System.out.println(message.getText());
                    PublicEvent.getInstance().getEventChat().receiveMessage(message);
                }
            });
			client.on("list_chat", new Emitter.Listener() {
				
				@Override
				public void call(Object... os) {
					List<Model_Message> chats = new ArrayList<Model_Message>();
	                for (Object o : os) {
	                	Model_Message mgs = new Model_Message(o);
	                	chats.add(mgs);
	                }
	                Collections.sort(chats);
	                PublicEvent.getInstance().getEventChat().loadChat(chats);
				}
			});
			client.on("receive_init_file", new Emitter.Listener() {
				
				@Override
				public void call(Object... os) {
					System.out.println("init_file");
					Model_Message mgs = new Model_Message(os[0]);
					File file = toFileReceive(mgs);
					try {
						fileReceivers.put(mgs.getId(), new ServiceFile(file));
						MessageFiles.put(mgs.getId(), mgs);
						System.out.println("end init file");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			client.on("receive_file", new Emitter.Listener() {
				
				@Override
				public void call(Object... os) {
					Model_Package_Sender data = new Model_Package_Sender(os[0]);
					try {
						ReceiveFile(data);
					} catch (IOException e) {
						e.printStackTrace();
					}
//					try {
//						FileOutputStream fos = new FileOutputStream(toFileReceive(file));
//						BufferedOutputStream bos = new BufferedOutputStream(fos);
//						bos.write(file.getData());
//						bos.flush();
//						bos.close();
//						Model_Message mgs = new Model_Message();
//						mgs.setId(file.getId());
//						mgs.setIdUserSend(file.getIdUserSend());
//						mgs.setIdUserReceive(file.getIdUserReceive());
//						mgs.setType(file.getType());
//						mgs.setFile(toFileReceive(file));
//						
//						PublicEvent.getInstance().getEventChat().receiveMessage(mgs);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
				}
			});
			client.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Register(Model_Account data, EventErorMessage error) {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://"+ IP +":8081/Register"))
														.header("Content-Type", "application/json; charset=UTF-8")
														.POST(BodyPublishers.ofString(data.ToObjectJson().toString()))
														.build();
			HttpResponse<String> respone = client.send(request, BodyHandlers.ofString());
			JSONObject obj = new JSONObject(respone.body());
			Model_Error message = new Model_Error(obj.getBoolean("action"), obj.getString("message"));
			if(message.isAction()) {
				Model_User user = new Model_User(obj.getJSONObject("data"));
				this.user = user;
				this.client.emit("login", user.toJsonObject());
			}
			error.callErorMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Login(Model_Account data) {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://"+IP+":8081/Login"))
														.header("Content-Type", "application/json; charset=UTF-8")
														.POST(BodyPublishers.ofString(data.ToObjectJson().toString()))
														.build();
			HttpResponse<String> respone = client.send(request, BodyHandlers.ofString());
			if(respone.statusCode() == 200) {
				JSONObject obj = new JSONObject(respone.body());
				Model_User user = new Model_User(obj);
				this.user = user;
				this.client.emit("login", user.toJsonObject());
				PublicEvent.getInstance().getEventMain().showLoading(false);
				PublicEvent.getInstance().getEventMain().initChat();
			}
			else {
				PublicEvent.getInstance().getEventMain().showLoading(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void LoadChat(int idUser2) {
		Model_LoadChat lc = new Model_LoadChat();
		lc.setIdUser1(user.getId());
		lc.setIdUser2(idUser2);
		client.emit("load_chat", lc.toJsonObject());
	}
	
	public void InitFile(Model_Message mgs) {
		client.emit("init_file", mgs.toJsonObject(), new Ack() {
			
			@Override
			public void call(Object... os) {
				if (os.length > 0) {
					mgs.setId((int)os[0]);
					try {
						new WriteFileSend(mgs.getFile(), toFileSend(mgs)).start();
			
						accFile = new RandomAccessFile(mgs.getFile(), "r");
						fileSize = accFile.length();
						
						SendFile(mgs.getId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	public void SendFile(int id) {	
		Model_Package_Sender data = new Model_Package_Sender();
        data.setFileID(id);
        byte[] bytes = readFile();
        if (bytes != null) {
            data.setData(bytes);
            data.setFinish(false);
        } else {
            data.setFinish(true);
            try {
				accFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        client.emit("send_file", data.toJsonObject(), new Ack() {
            @Override
            public void call(Object... os) {
                if (os.length > 0) {
                    boolean act = (boolean) os[0];
                    if (act) {
                        if (!data.isFinish()) {
						    SendFile(id);
						}
                    }
                }
            }
        });
	}
	
	public synchronized byte[] readFile(){
        try {
        	long filepointer = accFile.getFilePointer();
            if (filepointer != fileSize) {
                int max = 2000;
                long length = filepointer + max >= fileSize ? fileSize - filepointer : max;
                byte[] data = new byte[(int) length];
                accFile.read(data);
                return data;
            } else {
                return null;
            }
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
	
	public Model_User getUser() {
		return user;
	}

	public void setUser(Model_User user) {
		this.user = user;
	}
	
    public Socket getClient() {
        return client;
    }
    
    public File toFileReceive(Model_Message mgs) {
		return new File(PATH_FILE_RECEIVE + mgs.getId() + mgs.GetFileExtention());
	}
    
    public File toFileSend(Model_Message mgs) {
		return new File(PATH_FILE_SEND + mgs.getId() + mgs.GetFileExtention());
	}
    
    public void ReceiveFile(Model_Package_Sender data) throws IOException {
		if(!data.isFinish()) {
			fileReceivers.get(data.getFileID()).writeFile(data.getData());
		}
		else {
			fileReceivers.get(data.getFileID()).Close();
			fileReceivers.remove(data.getFileID());
			Model_Message mgs = MessageFiles.get(data.getFileID());
			PublicEvent.getInstance().getEventChat().receiveMessage(mgs);
			MessageFiles.remove(data.getFileID());
		}
	}
}
