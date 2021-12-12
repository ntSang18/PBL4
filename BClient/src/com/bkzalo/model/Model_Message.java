package com.bkzalo.model;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import com.bkzalo.app.MessageType;

public class Model_Message implements Comparable<Model_Message>{

	private int id;
	private int idUserSend;
	private int idUserReceive;
	private MessageType type;
	private String text;
	private File file;
	private String filename;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public Model_Message() {
	}
	
	public Model_Message(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
        	id = obj.getInt("id");
        	idUserSend = obj.getInt("idUserSend");
        	idUserReceive = obj.getInt("idUserReceive");
        	type = MessageType.toMessageType(obj.getInt("type"));
            text = obj.getString("text");
            filename = obj.getString("filename");
        } catch (JSONException e) {
            filename = "";
        }
    }
	
	public Object toJsonObject() {
		try {
			JSONObject json = new JSONObject();
			json.put("id", id);
			json.put("idUserSend", idUserSend);
			json.put("idUserReceive", getIdUserReceive());
			json.put("type", type.getValue());
			json.put("text", text);
			json.put("filename", filename);
			return json;
		} catch (Exception e) {
			return null;
		}
	}
	
	public String GetFileExtention() {
		return filename.substring(filename.lastIndexOf("."), filename.length());
	}
	@Override
	public int compareTo(Model_Message o) {
		if(id == o.getId()) {
			return 0;
		}
		else if(id > o.getId()) {
			return 1;
		}
		else {
			return -1;
		}
	}
}
