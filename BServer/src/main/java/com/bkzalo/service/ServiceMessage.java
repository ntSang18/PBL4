package com.bkzalo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bkzalo.connection.DatabaseConnection;
import com.bkzalo.model.Model_File;
import com.bkzalo.model.Model_Message;

public class ServiceMessage {
	
	private static ServiceMessage _Instance;
	private Connection con;
	public static ServiceMessage Instance() {
		if(_Instance == null) _Instance = new ServiceMessage();
		return _Instance;
	}
	
	private ServiceMessage() {
		con = DatabaseConnection.getInstance().getConnection();
	}
	
	public List<Model_Message> GetAllMessage(){
		List<Model_Message> l = new ArrayList<Model_Message>();
		String sql = "SELECT * FROM `message`";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Model_Message mgs = new Model_Message();
				mgs.setId(rs.getInt("id"));
				mgs.setIdUserSend(rs.getInt("idUserSend"));
				mgs.setIdUserReceive(rs.getInt("idUserReceive"));
				mgs.setType(rs.getInt("type"));
				mgs.setText(rs.getString("text"));
				mgs.setFilename(rs.getString("filename"));
				l.add(mgs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}
	
	public List<Model_Message> ListChat(int idUser1, int idUser2){
		List<Model_Message> l = new ArrayList<Model_Message>();
		for (Model_Message mgs : GetAllMessage()) {
			if(mgs.getIdUserSend() == idUser1 && mgs.getIdUserReceive() == idUser2 || 
					mgs.getIdUserSend() == idUser2 && mgs.getIdUserReceive() == idUser1) {
				l.add(mgs);
			}
		}
		return l;
	}
	
	public boolean AddMessage(Model_Message mgs) {
		String sql = "INSERT INTO `message`(`idUserSend`, `idUserReceive`, `type`, `text`) VALUES (?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, mgs.getIdUserSend());
			ps.setInt(2, mgs.getIdUserReceive());
			ps.setInt(3, mgs.getType());
			ps.setString(4, mgs.getText());
			
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int AddMessageFile(Model_Message mgs) {
		String sql = "INSERT INTO `message`(`idUserSend`, `idUserReceive`, `type`,`filename`) VALUES (?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, mgs.getIdUserSend());
			ps.setInt(2, mgs.getIdUserReceive());
			ps.setInt(3, mgs.getType());
			ps.setString(4, mgs.getFilename());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public boolean UpdateMessageFile(int id, String path) {
		String sql = "UPDATE `message` SET `filepath`=? WHERE `id`=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, path);
			ps.setInt(2, id);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
