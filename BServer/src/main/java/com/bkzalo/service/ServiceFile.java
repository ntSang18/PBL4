package com.bkzalo.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.bkzalo.model.Model_File;

public class ServiceFile{
	
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
	
//	public void WriteFile() {
//		try {
//			File f = toFile(file);
//			FileOutputStream fos = new FileOutputStream(f);
//			BufferedOutputStream bos = new BufferedOutputStream(fos);
//			bos.write(file.getData());
//			bos.flush();
//			bos.close();
//			String path = f.getAbsolutePath();
//			ServiceMessage.Instance().UpdateMessageFile(file.getId(), path);
//			System.out.println(path);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
