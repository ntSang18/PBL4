package com.bkzalo.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Model_Account {
	
	private String username;
	private String password;
	
	public Model_Account(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public JSONObject ToObjectJson() {
		try {
            JSONObject obj = new JSONObject();
            obj.put("username", username);
            obj.put("password", password);
            return obj;
        } catch (JSONException e) {
            return null;
        }
	}
	
}
