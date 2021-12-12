package com.bkzalo.service;

import com.bkzalo.connection.DatabaseConnection;
import com.bkzalo.model.Model_Account;
import com.bkzalo.model.Model_Client;
import com.bkzalo.model.Model_Error;
import com.bkzalo.model.Model_User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser {
    //  Instance
    private final Connection con;
    private static ServiceUser _Instance;
    
    public static ServiceUser Instance() {
    	if(_Instance == null) _Instance = new ServiceUser();
    	return _Instance;
    }

    public ServiceUser() {
        this.con = DatabaseConnection.getInstance().getConnection();
    }

    //Sang was fixed
    public Model_Error Register(Model_Account data) {
    	Model_Error message = new Model_Error();
    	String query1 = "SELECT COUNT(*) FROM `account` WHERE username =?";
    	try {
			PreparedStatement ps1 = con.prepareStatement(query1);
			ps1.setString(1, data.getUsername());
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();
			int count = rs1.getInt(1);
			if (count == 1) {
                message.setAction(false);
                message.setMessage("User Already Exit");
            } else {
                message.setAction(true);
            }
			if(message.isAction()) {
				String query2 = "insert into `account` (username, `password`) values (?,?)";
				PreparedStatement ps2 = con.prepareStatement(query2, PreparedStatement.RETURN_GENERATED_KEYS);
                ps2.setString(1, data.getUsername());
                ps2.setString(2, data.getPassword());
                ps2.executeUpdate();
                ResultSet rs2 = ps2.getGeneratedKeys();
                rs2.next();
                int id = rs2.getInt(1);
                System.out.println(id);
                String query3 = "INSERT INTO `user`(`id`, `username`) VALUES (?,?)";
                PreparedStatement ps3 = con.prepareStatement(query3);
                ps3.setInt(1, id);
                ps3.setString(2, data.getUsername());
                ps3.executeUpdate();
                
                message.setAction(true);
                message.setMessage("Ok");
                message.setData(new Model_User(id, data.getUsername(), false, "", true));
                ps2.close();
                rs2.close();
                ps3.close();
			}
			ps1.close();
			rs1.close();
		} catch (Exception e) {
			//e.printStackTrace();
			message.setAction(false);
            message.setMessage("Server Error");
		}
    	return message;
    }

	// Sang was fixed
    public Model_User login(Model_Account login) {
    	String query = "SELECT `id`, user.username, `gender`, `image`, `status` FROM `user` "
    			+ "JOIN account USING (id) WHERE account.username = ? AND account.password = ?";
    	Model_User data = null;
    	try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, login.getUsername());
            ps.setString(2, login.getPassword());
            ResultSet rs = ps.executeQuery();
            rs.next();
            int id = rs.getInt(1);
            String username = rs.getString(2);
            boolean gender = rs.getBoolean(3);
            String image = rs.getString(4);
            data = new Model_User(id, username, gender, image, true);
            rs.close();
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
    	return data;
    }

    public List<Model_User> ListUser(int exitUser) {
        List<Model_User> list = new ArrayList<>();
        String query = "SELECT `id`, `username`, `gender`, `image`, `status` FROM `user` WHERE id<>?";
        try {
        	PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, exitUser);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt(1);
                String username = rs.getString(2);
                boolean gender = rs.getBoolean(3);
                String image = rs.getString(4);
                list.add(new Model_User(userID, username, gender, image, checkUserStatus(userID)));
            }
            rs.close();
            ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return list;
    }

    private boolean checkUserStatus(int userID) {
        List<Model_Client> clients = Service.Instance().getListClient();
        for (Model_Client c : clients) {
            if (c.getUser().getId() == userID) {
                return true;
            }
        }
        return false;
    }
}
