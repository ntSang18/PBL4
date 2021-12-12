package com.bkzalo.model;

import com.bkzalo.app.MessageType;

public class Model_Message {

	private int id;
	private int idUserSend;
	private int idUserReceive;
	private int type;
	private String text;
	private String filename;
	
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Model_Message() {
		super();
	}
	public String GetFileExtention() {
		return filename.substring(filename.lastIndexOf("."), filename.length());
	}
}
