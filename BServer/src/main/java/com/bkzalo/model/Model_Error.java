package com.bkzalo.model;

public class Model_Error {

	private boolean action;
    private String message;
    private Object data;
    
	public boolean isAction() {
		return action;
	}
	public void setAction(boolean action) {
		this.action = action;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public Model_Error(boolean action, String message, Object data) {
		super();
		this.action = action;
		this.message = message;
		this.data = data;
	}
	public Model_Error() {
	}
}
