package com.bkzalo.model;

import org.json.JSONObject;

public class Model_LoadChat {
	
	private int idUser1;
	private int idUser2;
	
	public int getIdUser1() {
		return idUser1;
	}
	public void setIdUser1(int idUser1) {
		this.idUser1 = idUser1;
	}
	public int getIdUser2() {
		return idUser2;
	}
	public void setIdUser2(int idUser2) {
		this.idUser2 = idUser2;
	}
	
	public Model_LoadChat() {
	}
	
	public Object toJsonObject() {
		try {
			JSONObject json = new JSONObject();
			json.put("idUser1", idUser1);
			json.put("idUser2", idUser2);
			return json;
		} catch (Exception e) {
			return null;
		}
	}
	
}
