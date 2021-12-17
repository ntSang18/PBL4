package service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import main.MainServer;
import model.BO;
import model.bean.DBFile;
import model.bean.Package_File;
import model.bean.User;

public class Service {
	private static Service _Instance;
	private SocketIOServer server;
	private final int PORT_NUMBER = 9999;
	private final String PATH_FILE = "server_data/";
	private final Map<Integer, ServiceFile> FileUpload;
	
	public static Service Instance() {
		if(_Instance == null) _Instance = new Service();
		return _Instance;
	}
	

	private Service() {
		FileUpload = new HashMap<Integer, ServiceFile>();
	}
	
	public void StartServer() {
		Configuration config = new Configuration();
        config.setPort(PORT_NUMBER);
        server = new SocketIOServer(config);
        server.addConnectListener(new ConnectListener() {
			
			@Override
			public void onConnect(SocketIOClient arg0) {
				MainServer.Settxt("1 user has connected \n");
			}
		});
        server.addEventListener("register", User.class, new DataListener<User>() {

			@Override
			public void onData(SocketIOClient sioc, User data, AckRequest ar) throws Exception {
				User u = BO.Instance().Register(data);
				if(u != null) {
					ar.sendAckData(true);
					sioc.sendEvent("all_file", BO.Instance().GetAllFile().toArray());
				}
				else {
					ar.sendAckData(false);
				}
			}
		});
        server.addEventListener("login", User.class, new DataListener<User>() {

			@Override
			public void onData(SocketIOClient sioc, User data, AckRequest ar) throws Exception {
				User u = BO.Instance().Login(data);
				if(u != null) {
					ar.sendAckData(true);
					sioc.sendEvent("all_file", BO.Instance().GetAllFile().toArray());
				}
				else {
					ar.sendAckData(false);
				}
			}
		});
        server.addEventListener("init_file", DBFile.class, new DataListener<DBFile>() {

			@Override
			public void onData(SocketIOClient sioc, DBFile data, AckRequest ar) throws Exception {
				DBFile DBf = BO.Instance().AddFile(data);
				File file = toFile(DBf); 
				FileUpload.put(DBf.getId(), new ServiceFile(file));
				ar.sendAckData(DBf.getId());
			}
		});
        server.addEventListener("upload_file", Package_File.class, new DataListener<Package_File>() {

			@Override
			public void onData(SocketIOClient sioc, Package_File data, AckRequest ar) throws Exception {
				try {
            		ReceiveFile(data);
                	ar.sendAckData(true);
				} catch (Exception e) {
					e.printStackTrace();
					ar.sendAckData(false);
				}
			}
		});
        server.addEventListener("download_file", DBFile.class, new DataListener<DBFile>() {

			@Override
			public void onData(SocketIOClient sioc, DBFile data, AckRequest ar) throws Exception {	
				new ServiceFile2(data, sioc).start();
			}
		});
        server.addEventListener("delete_file", Integer.class, new DataListener<Integer>() {

			@Override
			public void onData(SocketIOClient sioc, Integer data, AckRequest ar) throws Exception {
				BO.Instance().Delete(data);
				server.getBroadcastOperations().sendEvent("delete_file", data);
			}
		});
        server.addDisconnectListener(new DisconnectListener() {
			
			@Override
			public void onDisconnect(SocketIOClient arg0) {
				MainServer.Settxt("1 user has disconnected \n");
			}
		});
        server.start();
        MainServer.Settxt("Server has Start on port : " + PORT_NUMBER + "\n");
	}
	
	public File toFile(DBFile file) {
		String fileExtention = file.getFilename().substring(file.getFilename().lastIndexOf("."), file.getFilename().length());
		return new File(PATH_FILE + file.getId() + fileExtention);
	}
	
	public void ReceiveFile(Package_File data) throws IOException {
		if(!data.isFinish()) {
			FileUpload.get(data.getId()).writeFile(data.getData());
		}
		else {
			FileUpload.get(data.getId()).Close(data.getId());
			server.getBroadcastOperations().sendEvent("add_file", BO.Instance().GetFileById(data.getId()));
			FileUpload.remove(data.getId());
		}
	}
}
