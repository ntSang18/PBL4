package model.bean;

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
	
}
