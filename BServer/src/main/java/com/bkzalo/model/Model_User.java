package com.bkzalo.model;

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
	public boolean getGender() {
		return gender;
	}
	public void setGender(Boolean gender) {
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
	
	public Model_User(int id, String username, Boolean gender, String image, boolean status) {
		super();
		this.id = id;
		this.username = username;
		this.gender = gender;
		this.image = image;
		this.status = status;
	}
	
	public Model_User() {
		super();
	}
	
    
    

}
