package service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import model.DBFile;
import model.Package_File;
import model.User;

public class Service {
	
	private static Service _Instance;
	private User user;
	private Socket client;
	private final int PORT_NUMBER = 9999;
    private final String IP = "192.168.1.10";
    private List<DBFile> AllFile;
    private List<DBFile> UserFile;
    public final String PATH_FILE = "client_data/";
    private final Map<Integer, ServiceFile> FileDownload;
    private RandomAccessFile accFile;
    private long fileSize;
    
    public List<DBFile> getUserFile() {
		return UserFile;
	}

	public void setUserFile(List<DBFile> userFile) {
		UserFile = userFile;
	}

	public List<DBFile> getAllFile() {
		return AllFile;
	}

	public void setAllFile(List<DBFile> allFile) {
		AllFile = allFile;
	}
    
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}

	public static Service Instance() {
		if(_Instance == null) _Instance = new Service();
		return _Instance;
	}
	
	private Service() {
		AllFile = new ArrayList<DBFile>();
		UserFile = new ArrayList<DBFile>();
		FileDownload = new HashMap<Integer, ServiceFile>();
	}
	
	public void StartServer() {
		try {
			client = IO.socket("http://" + IP + ":" + PORT_NUMBER);
			client.on("all_file", new Emitter.Listener() {
				
				@Override
				public void call(Object... os) {
					for (Object o : os) {
						DBFile dbf = new DBFile(o);
						if(dbf.getUsername().equals(user.getUsername())) {
							UserFile.add(dbf);
						}
						AllFile.add(dbf);
					}
					form.Main.Instance().ShowFile(1, AllFile);
					form.Main.Instance().ShowFile(2, UserFile);
				}
			});
			client.on("add_file", new Emitter.Listener() {
				
				@Override
				public void call(Object... os) {
					DBFile dbf = new DBFile(os[0]);
					if(dbf.getUsername().equals(user.getUsername())) {
						UserFile.add(dbf);
					}
					AllFile.add(dbf);
					form.Main.Instance().ShowFile(1, AllFile);
					form.Main.Instance().ShowFile(2, UserFile);
				}
			});
			client.on("download_file", new Emitter.Listener() {
				
				@Override
				public void call(Object... os) {
					Package_File data = new Package_File(os[0]);
					try {
						WriteFile(data);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			});
			client.on("delete_file", new Emitter.Listener() {
				
				@Override
				public void call(Object... os) {
					int idFileDelete = (int) os[0];
					for (DBFile f : AllFile) {
						if(f.getId() == idFileDelete) {
							AllFile.remove(f);
							break;
						}
					}
					for (DBFile f : UserFile) {
						if(f.getId() == idFileDelete) {
							UserFile.remove(f);
							break;
						}
					}
					form.Main.Instance().ShowFile(1, AllFile);
					form.Main.Instance().ShowFile(2, UserFile);
				}
			});
			client.open();
			System.out.println("client is running");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Register(User u) {
		client.emit("register", u.ToObjectJson(), new Ack() {
			
			@Override
			public void call(Object... os) {
				if(os.length > 0){
					boolean action = (boolean) os[0];
					if(action) {
						user = u;
						form.Login.Instance().getFrame().setVisible(false);
						form.Main.Instance().setVisible(true);
					}
					else {
						user = null;
						JOptionPane.showMessageDialog(form.Login.Instance().getFrame(), "Register Failed");
					}
				}
			}
		});
	}
	
	public void Login(User u) {
		client.emit("login", u.ToObjectJson(), new Ack() {
			
			@Override
			public void call(Object... os) {
				if(os.length > 0){
					boolean action = (boolean) os[0];
					if(action) {
						user = u;
						form.Login.Instance().getFrame().setVisible(false);
						form.Main.Instance().setVisible(true);
					}
					else {
						user = null;
						JOptionPane.showMessageDialog(form.Login.Instance().getFrame(), "Login Failed");
					}
				}
			}
		});
	}
	
	public void InitFile(DBFile DBfile, File f) {
		client.emit("init_file", DBfile.toJsonObject(), new Ack() {
			
			@Override
			public void call(Object... os) {
				if (os.length > 0) {
					DBfile.setId((int)os[0]);
					try {
						accFile = new RandomAccessFile(f, "r");
						fileSize = accFile.length();
						
						SendFile(DBfile.getId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		});
	}
	
	public void SendFile(int id) {	
		Package_File data = new Package_File();
        data.setId(id);
        byte[] bytes = readFile();
        if (bytes != null) {
            data.setData(bytes);
            data.setFinish(false);
        } else {
            data.setFinish(true);
            try {
				accFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        client.emit("upload_file", data.toJsonObject(), new Ack() {
            @Override
            public void call(Object... os) {
                if (os.length > 0) {
                    boolean act = (boolean) os[0];
                    if (act) {
                        if (!data.isFinish()) {
						    SendFile(id);
						}
                    }
                }
            }
        });
	}
	
	public synchronized byte[] readFile(){
        try {
        	long filepointer = accFile.getFilePointer();
            if (filepointer != fileSize) {
                int max = 2000;
                long length = filepointer + max >= fileSize ? fileSize - filepointer : max;
                byte[] data = new byte[(int) length];
                accFile.read(data);
                return data;
            } else {
                return null;
            }
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
	
	public void DownLoad(DBFile f) {
		try {
			File file = toFileDownload(f);
			FileDownload.put(f.getId(), new ServiceFile(file));
			client.emit("download_file", f.toJsonObject());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void WriteFile(Package_File data) throws IOException {
		if(!data.isFinish()) {
			FileDownload.get(data.getId()).writeFile(data.getData());
		}
		else {
			FileDownload.get(data.getId()).Close();
			FileDownload.remove(data.getId());
		}
	}

	public void Sort(int table_mode, int sort_mode) {
		if(table_mode == 1) {
			List<DBFile> l = AllFile;
			SortMode(l, sort_mode);
			form.Main.Instance().ShowFile(1, l);
			
		}
		else {
			List<DBFile> l = UserFile;
			SortMode(l, sort_mode);
			form.Main.Instance().ShowFile(2, l);
		}
		
	}
	
	public void SortMode(List<DBFile> l, int sort_mode) {
		if(sort_mode == 0) {
			Collections.sort(l, new Comparator<DBFile>() {

				@Override
				public int compare(DBFile f1, DBFile f2) {
					return f1.getFilename().compareTo(f2.getFilename());
				}
			});
		}
		else {
			Collections.sort(l, new Comparator<DBFile>() {

				@Override
				public int compare(DBFile f1, DBFile f2) {
					int size1 = Integer.parseInt(f1.getFilesize().split(" ")[0]);
					int size2 = Integer.parseInt(f2.getFilesize().split(" ")[0]);
					if(size1 > size2) {
						return 1;
					}
					else {
						return -1;
					}
				}
			});
		}
	}

	public void Search(int table_mode, int search_mode, String txt) {
		form.Main.Instance().ShowFile(table_mode, SearchMode(table_mode, search_mode, txt));
	}
	
	public List<DBFile> SearchMode(int table_mode, int search_mode, String txt) {
		List<DBFile> l = new ArrayList<DBFile>();
		if(search_mode == 0) {
			try {
				int id = Integer.parseInt(txt);
				for (DBFile f : AllFile) {
					if(f.getId() == id) {
						l.add(f);
					}
				}
			} catch (Exception e) {
				return null;
			}
		}
		else if(table_mode == 1) {
			if(search_mode == 1) {
				for (DBFile f : AllFile) {
					if(f.getUsername().toLowerCase().contains(txt)) {
						l.add(f);
					}
				}
			}
			else {
				for (DBFile f : AllFile) {
					if(f.getFilename().toLowerCase().contains(txt)) {
						l.add(f);
					}
				}
			}
		}
		else {
			for (DBFile f : AllFile) {
				if(f.getFilename().toLowerCase().contains(txt)) {
					l.add(f);
				}
			}
		}
		return l;
	}
	
    public File toFileDownload(DBFile f) {
		return new File(PATH_FILE + f.getFilename());
	}

	public void Delete(int id) {
		client.emit("delete_file", id);
	}
}
