package service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.corundumstudio.socketio.SocketIOClient;

import model.bean.DBFile;
import model.bean.Package_File;

public class ServiceFile2 extends Thread{
	private SocketIOClient client;
    private RandomAccessFile accFile;
    private long fileSize;
    private int id;
    
    public ServiceFile2(DBFile f, SocketIOClient sioc) {
    	try {
    		client = sioc;
    		File file = Service.Instance().toFile(f);
        	accFile = new RandomAccessFile(file, "r");
        	fileSize = accFile.length();
        	id = f.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
    
    public void SendFile() {	
		Package_File data = new Package_File();
        data.setId(id);
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
        client.sendEvent("download_file", data);
        if (!data.isFinish()) {
		    SendFile();
		}
	}
    
    public void run() {
    	SendFile();
    }
}
