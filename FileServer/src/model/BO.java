package model;

import java.util.ArrayList;
import java.util.List;

import model.bean.DBFile;
import model.bean.User;

public class BO {

	private static BO _Instance;
	public static BO Instance() {
		if(_Instance == null) _Instance = new BO();
		return _Instance;
	}
	
	private BO() {
	}
	
	public User Register(User u) {
		return DAO.Instance().Register(u);
	}
	
	public User Login(User u) {
		return DAO.Instance().Login(u);
	}
	
	public List<DBFile> GetAllFile(){
		return DAO.Instance().GetAllFile();
	}
	
	public DBFile GetFileById(int id) {
		for (DBFile f : GetAllFile()) {
			if(f.getId() == id)
			{
				return f;
			}
		}
		return null;
	}
	
	public DBFile AddFile(DBFile f) {
		return DAO.Instance().AddFile(f);
	}
	
	public boolean UpdateFile(int id, String path) {
		return DAO.Instance().UpdateFile(id, path);
	}
	
	public boolean Delete(int id) {
		return DAO.Instance().DeleteFile(id);
	}
	
	
}
