package com.bkzalo.model;

public class Model_File {
	private int id;
	private int idUserSend;
	private int idUserReceive;
	private byte[] data;
	private int type;
	private String fileExtension;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
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
}
