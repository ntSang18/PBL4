package model;

import org.json.JSONException;
import org.json.JSONObject;

public class DBFile {
	
	private int id;
	private String username;
	private String filename;
	private String filesize;
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
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	public DBFile(int id, String username, String filename, String filesize) {
		super();
		this.id = id;
		this.username = username;
		this.filename = filename;
		this.filesize = filesize;
	}
	public DBFile() {
		super();
	}
	public DBFile(Object json) {
		JSONObject obj = (JSONObject) json;
        try {
            id = obj.getInt("id");
            username = obj.getString("username");
            filename = obj.getString("filename");
            filesize = obj.getString("filesize");
        } catch (JSONException e) {
            System.err.println(e);
        }
	}
	public Object toJsonObject() {
		try {
			JSONObject json = new JSONObject();
			json.put("id", id);
			json.put("username", username);
			json.put("filename", filename);
			json.put("filesize", filesize);
			return json;
		} catch (Exception e) {
			return null;
		}
	}
}