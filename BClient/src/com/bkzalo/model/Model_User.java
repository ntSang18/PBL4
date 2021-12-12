package com.bkzalo.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Model_User {
	
	private int id;
    private String username;
    private boolean gender;
    private String image;
    private boolean status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public Model_User(int id, String username, boolean gender, String image, boolean status) {
		super();
		this.id = id;
		this.username = username;
		this.gender = gender;
		this.image = image;
		this.status = status;
	}
	
	public Model_User(Object json) {
		JSONObject obj = (JSONObject) json;
        try {
            id = obj.getInt("id");
            username = obj.getString("username");
            gender = obj.getBoolean("gender");
            image = obj.getString("image");
            status = obj.getBoolean("status");
        } catch (JSONException e) {
            System.err.println(e);
        }
	}
	
	 public JSONObject toJsonObject() {
	        try {
	            JSONObject obj = new JSONObject();
	            obj.put("id", id);
	            obj.put("username", username);
	            obj.put("gender", gender);
	            obj.put("image", image);
	            obj.put("status", status);
	            return obj;
	        } catch (JSONException e) {
	            return null;
	        }
	    }
	
	
}
