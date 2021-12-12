package com.bkzalo.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.bkzalo.app.MessageType;

public class Model_File {
	private int id;
	private int idUserSend;
	private int idUserReceive;
	private byte[] data;
	private MessageType type;
	private String fileExtension;
	
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public int getIdUserSend() {
		return idUserSend;
	}
	public void setIdUserSend(int idUserSend) {
		this.idUserSend = idUserSend;
	}
	public int getIdUserReceive() {
		return idUserReceive;
	}
	public void setIdUserReceive(int idUserReceive) {
		this.idUserReceive = idUserReceive;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public Model_File() {
	}
	
	public Model_File(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
        	id = obj.getInt("id");
        	idUserSend = obj.getInt("idUserSend");
        	idUserReceive = obj.getInt("idUserReceive");
        	data = (byte[]) obj.get("data");
        	type = MessageType.toMessageType(obj.getInt("type"));
            fileExtension = obj.getString("fileExtension");
        } catch (JSONException e) {
            System.err.println(e);
        }
	}
	
	public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("id", id);
            json.put("idUserSend", idUserSend);
            json.put("idUserReceive", idUserReceive);
            json.put("data", data);
            json.put("type", type.getValue());
            json.put("fileExtension", fileExtension);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
