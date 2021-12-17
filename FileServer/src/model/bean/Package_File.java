package model.bean;

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
    
}
