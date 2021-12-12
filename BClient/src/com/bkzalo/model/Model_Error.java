package com.bkzalo.model;

public class Model_Error {
	
    private boolean action;
    private String message;
    
    public Model_Error(boolean action, String message) {
        this.action = action;
        this.message = message;
    }

    public Model_Error() {
    }
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


}
