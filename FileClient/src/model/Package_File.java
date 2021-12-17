package model;

import org.json.JSONException;
import org.json.JSONObject;

public class Package_File {
	private int id;
	private byte[] data;
	private boolean finish;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Package_File(int id, byte[] data, boolean finish) {
		super();
		this.id = id;
		this.data = data;
		this.finish = finish;
	}
	public Package_File() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Package_File(Object json) {
    	JSONObject obj = (JSONObject) json;
        try {
        	id = obj.getInt("id");
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
            json.put("id", id);
            json.put("data", data);
            json.put("finish", finish);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
