package com.bkzalo.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.bkzalo.app.MessageType;

public class Model_Package_Sender {

	private int fileID;
    private byte[] data;
    private boolean finish;
    
    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public Model_Package_Sender(int fileID, byte[] data, boolean finish) {
        this.fileID = fileID;
        this.data = data;
        this.finish = finish;
    }

    public Model_Package_Sender() {
    }
    
    public Model_Package_Sender(Object json) {
    	JSONObject obj = (JSONObject) json;
        try {
        	fileID = obj.getInt("fileID");
        	data = (byte[]) obj.get("data");
        	finish = obj.getBoolean("finish");
        } catch (JSONException e) {
            data = null;
            finish = true;
        }
    }

    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("fileID", fileID);
            json.put("data", data);
            json.put("finish", finish);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
