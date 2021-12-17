package service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ServiceFile {

	private File file;
	private RandomAccessFile accFile;
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public RandomAccessFile getAccFile() {
		return accFile;
	}

	public void setAccFile(RandomAccessFile accFile) {
		this.accFile = accFile;
	}

	public ServiceFile(File file) throws IOException {
		this.file = file;
		this.accFile = new RandomAccessFile(file, "rw");
	}
	
	public synchronized long writeFile(byte[] data) throws IOException{
        accFile.seek(accFile.length());
        accFile.write(data);
        return accFile.length();
    }

	public void Close() throws IOException {
		accFile.close();
	}
}
