package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.bean.DBFile;
import model.bean.User;

public class DAO {
	private Connection conn;
	private static DAO _Instance;
	public static DAO Instance() {
		if(_Instance == null) _Instance = new DAO();
		return _Instance;
	}
	
	private DAO() {
		try {
    		Class.forName("com.mysql.jdbc.Driver");
            String server = "localhost";
            String port = "3306";
            String database = "pbl4_file";
            String userName = "root";
            String password = "";
            conn = java.sql.DriverManager.getConnection("jdbc:mysql://" + server + ":" + port + "/" + database, userName, password);
            System.out.println("Connection Success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public User Register(User u) {
		String sql = "INSERT INTO `user`(`username`, `password`) VALUES (?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			if(ps.executeUpdate() > 0) {
				return u;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public User Login(User u) {
		String sql = "SELECT COUNT(*) FROM `user` WHERE username = ? AND password = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ResultSet rs = ps.executeQuery();
			rs.next();
			if(rs.getInt(1) != 0) {
				return u;
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	public List<DBFile> GetAllFile() {
		String sql = "SELECT * FROM `file` WHERE 1";
		List<DBFile> l = new ArrayList<DBFile>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				l.add(new DBFile(rs.getInt("id"), rs.getString("username"), rs.getString("filename"), rs.getString("filesize")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}
	
	public DBFile AddFile(DBFile f) {
		String sql = "INSERT INTO `file`(`username`, `filename`, `filesize`) VALUES (?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, f.getUsername());
			ps.setString(2, f.getFilename());
			ps.setString(3, f.getFilesize());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			f.setId(rs.getInt(1));
			return f;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean UpdateFile(int id, String path) {
		String sql = "UPDATE `file` SET `filepath`=?,`status`=? WHERE id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, path);
			ps.setBoolean(2, true);
			ps.setInt(3, id);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean DeleteFile(int id) {
		String sql = "DELETE FROM `file` WHERE id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
