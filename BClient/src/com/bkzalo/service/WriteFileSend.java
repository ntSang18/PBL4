package com.bkzalo.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class WriteFileSend extends Thread{
	private File fileI;
	private File fileW;
	
	public WriteFileSend(File filei, File filew) {
		this.fileI = filei;
		this.fileW = filew;
	}
	
	public void run() {
		try {
			FileInputStream fis = new FileInputStream(fileI);
			BufferedInputStream bis = new BufferedInputStream(fis);
			FileOutputStream fos = new FileOutputStream(fileW);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bos.write(bis.readAllBytes());
			bos.flush();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
