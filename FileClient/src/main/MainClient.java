package main;

import java.awt.EventQueue;

import form.Login;
import service.Service;

public class MainClient {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Service.Instance().StartServer();
					Login.Instance().getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
