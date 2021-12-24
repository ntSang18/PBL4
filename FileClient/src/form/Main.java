package form;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.DBFile;
import service.Service;

import javax.swing.JTabbedPane;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;

public class Main extends JFrame {
	private JTable table;
	private JTable table_1;
	private JComboBox cbbSortAll;
	private JComboBox cbbSortUser;
	private JComboBox cbbSearchAll;
	private JComboBox cbbSearchUser;
	private JTextPane txtSearchUser;
	private JTextPane txtSearchAll;
	private DefaultTableModel model_1;
	private DefaultTableModel model_2;
	private static Main _Instance;
	public static Main Instance() {
		if(_Instance == null) _Instance = new Main();
		return _Instance;
	}
	
	private Main() {
		initalize();
		model_1 = (DefaultTableModel) table.getModel();
		model_2 = (DefaultTableModel) table_1.getModel();
	}
	
	public void ShowFile(int mode, List<DBFile> l)
	{
		try 
		{
			DefaultTableModel model;
			if(mode == 1) {
				model = model_1;
			}
			else {
				model = model_2;
			}
			model.getDataVector().removeAllElements();
			model.fireTableDataChanged();
			for (DBFile f : l) 
			{
				model.addRow(new Object[] {
						f.getId(), f.getUsername(), f.getFilename(), f.getFilesize() 
				});
			}
			
		} catch (Exception e) {
		}
	}
	
	public void initalize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Arial", Font.BOLD, 11));
		tabbedPane.setToolTipText("");
		tabbedPane.setBounds(0, 0, 984, 461);
		getContentPane().add(tabbedPane);
		
		JPanel Panel1 = new JPanel();
		Panel1.setLayout(null);
		Panel1.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("All file", null, Panel1, null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(70, 130, 180));
		panel.setBounds(10, 11, 964, 46);
		Panel1.add(panel);
		
		JLabel lblNewLabel = new JLabel("DANH S\u00C1CH FILE TR\u00CAN SERVER");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setBounds(10, 11, 232, 24);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(10, 68, 693, 354);
		Panel1.add(panel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 693, 354);
		panel_1.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "User upload", "File name", "File size"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBackground(new Color(70, 130, 180));
		panel_2.setBounds(713, 68, 261, 355);
		Panel1.add(panel_2);
		
		JButton btnSearchAll = new JButton("Search");
		btnSearchAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//0 - id, 1 - user name, 2 - file name;
				int search_mode = cbbSearchAll.getSelectedIndex();
				int table_mode = 1;
				String txt = txtSearchAll.getText().trim();
				Service.Instance().Search(table_mode, search_mode, txt);
			}
		});
		btnSearchAll.setForeground(Color.BLACK);
		btnSearchAll.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSearchAll.setBorder(null);
		btnSearchAll.setBackground(new Color(220, 20, 60));
		btnSearchAll.setBounds(10, 93, 241, 30);
		panel_2.add(btnSearchAll);
		
		JButton btnSortAll = new JButton("Sort");
		btnSortAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int table_mode = 1;
				//0=file name; 1 = file size
				int sort_mode = cbbSortAll.getSelectedIndex();
				Service.Instance().Sort(table_mode, sort_mode);
			}
		});
		btnSortAll.setForeground(Color.BLACK);
		btnSortAll.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSortAll.setBorder(null);
		btnSortAll.setBackground(new Color(220, 20, 60));
		btnSortAll.setBounds(10, 11, 115, 30);
		panel_2.add(btnSortAll);
		
		JButton btnRefreshAll = new JButton("Refresh");
		btnRefreshAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowFile(1, Service.Instance().getAllFile());
				txtSearchAll.setText("");
			}
		});
		btnRefreshAll.setForeground(Color.BLACK);
		btnRefreshAll.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnRefreshAll.setBorder(null);
		btnRefreshAll.setBackground(new Color(220, 20, 60));
		btnRefreshAll.setBounds(10, 133, 241, 30);
		panel_2.add(btnRefreshAll);
		
		JButton btnUpload = new JButton("Upload File");
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser ch = new JFileChooser();
				ch.setMultiSelectionEnabled(false);
				int option = ch.showOpenDialog(Main.getFrames()[0]);
				if(option == JFileChooser.APPROVE_OPTION) {
					File file = ch.getSelectedFile();
					DBFile f = new DBFile(0, Service.Instance().getUser().getUsername(), file.getName(), file.length()+" bytes");
					Service.Instance().InitFile(f, file);
				}
			}
		});
		btnUpload.setForeground(Color.BLACK);
		btnUpload.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnUpload.setBorder(null);
		btnUpload.setBackground(new Color(220, 20, 60));
		btnUpload.setBounds(10, 174, 241, 30);
		panel_2.add(btnUpload);
		
		JButton btnDowload = new JButton("Download File");
		btnDowload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = (int) table.getValueAt(table.getSelectedRow(), 0);
					String username = (String) table.getValueAt(table.getSelectedRow(), 1);
					String filename = (String) table.getValueAt(table.getSelectedRow(), 2);
					String filesize = (String) table.getValueAt(table.getSelectedRow(), 3);
					DBFile f = new DBFile(id, username, filename, filesize);
					Service.Instance().DownLoad(f);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(Panel1, "Choose file to download");
				}
			}
		});
		btnDowload.setForeground(Color.BLACK);
		btnDowload.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDowload.setBorder(null);
		btnDowload.setBackground(new Color(220, 20, 60));
		btnDowload.setBounds(10, 215, 241, 30);
		panel_2.add(btnDowload);
		
		txtSearchAll = new JTextPane();
		txtSearchAll.setBounds(136, 52, 115, 30);
		panel_2.add(txtSearchAll);
		
		cbbSortAll = new JComboBox();
		cbbSortAll.setFont(new Font("Arial", Font.BOLD, 12));
		cbbSortAll.setModel(new DefaultComboBoxModel(new String[] {"File name", "File size"}));
		cbbSortAll.setBounds(135, 11, 116, 30);
		panel_2.add(cbbSortAll);
		
		cbbSearchAll = new JComboBox();
		cbbSearchAll.setModel(new DefaultComboBoxModel(new String[] {"Id", "User upload", "File name"}));
		cbbSearchAll.setFont(new Font("Arial", Font.BOLD, 12));
		cbbSearchAll.setBounds(10, 52, 115, 30);
		panel_2.add(cbbSearchAll);
		
		JPanel Panel1_1 = new JPanel();
		Panel1_1.setLayout(null);
		Panel1_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("User file", null, Panel1_1, null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBackground(new Color(70, 130, 180));
		panel_3.setBounds(10, 11, 964, 46);
		Panel1_1.add(panel_3);
		
		JLabel lblNewLabel_1 = new JLabel("DANH S\u00C1CH FILE NG\u01AF\u1EDCI D\u00D9NG \u0110\u00C3 UPLOAD");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setBounds(10, 11, 944, 24);
		panel_3.add(lblNewLabel_1);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBounds(10, 68, 693, 354);
		Panel1_1.add(panel_1_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 693, 354);
		panel_1_1.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "User upload", "File name", "File size"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_1.setViewportView(table_1);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setLayout(null);
		panel_2_1.setBackground(new Color(70, 130, 180));
		panel_2_1.setBounds(713, 68, 261, 354);
		Panel1_1.add(panel_2_1);
		
		JButton btnSearchUser = new JButton("Search");
		btnSearchUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//0 - id, 1 - user name, 2 - file name;
				int search_mode = cbbSearchUser.getSelectedIndex();
				int table_mode = 2;
				String txt = txtSearchUser.getText().trim();
				Service.Instance().Search(table_mode, search_mode, txt);
			}
		});
		btnSearchUser.setForeground(Color.BLACK);
		btnSearchUser.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSearchUser.setBorder(null);
		btnSearchUser.setBackground(new Color(220, 20, 60));
		btnSearchUser.setBounds(10, 93, 241, 30);
		panel_2_1.add(btnSearchUser);
		
		JButton btnSortUser = new JButton("Sort");
		btnSortUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int table_mode = 2;
				//0=file name; 1 = file size
				int sort_mode = cbbSortUser.getSelectedIndex();
				Service.Instance().Sort(table_mode, sort_mode);
			}
		});
		btnSortUser.setForeground(Color.BLACK);
		btnSortUser.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSortUser.setBorder(null);
		btnSortUser.setBackground(new Color(220, 20, 60));
		btnSortUser.setBounds(10, 11, 116, 30);
		panel_2_1.add(btnSortUser);
		
		JButton btnRefreshUser = new JButton("Refresh");
		btnRefreshUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowFile(2, Service.Instance().getUserFile());
				txtSearchUser.setText("");
			}
		});
		btnRefreshUser.setForeground(Color.BLACK);
		btnRefreshUser.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnRefreshUser.setBorder(null);
		btnRefreshUser.setBackground(new Color(220, 20, 60));
		btnRefreshUser.setBounds(10, 134, 241, 30);
		panel_2_1.add(btnRefreshUser);
		
		JButton btnDeleteUser = new JButton("Delete");
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = (int) table_1.getValueAt(table_1.getSelectedRow(), 0);
					Service.Instance().Delete(id);
					ShowFile(2, Service.Instance().getUserFile());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(Panel1_1, "Choose file to delete");
				}
			}
		});
		btnDeleteUser.setForeground(Color.BLACK);
		btnDeleteUser.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDeleteUser.setBorder(null);
		btnDeleteUser.setBackground(new Color(220, 20, 60));
		btnDeleteUser.setBounds(10, 175, 241, 30);
		panel_2_1.add(btnDeleteUser);
		
		txtSearchUser = new JTextPane();
		txtSearchUser.setBounds(136, 52, 115, 30);
		panel_2_1.add(txtSearchUser);
		
		cbbSortUser = new JComboBox();
		cbbSortUser.setFont(new Font("Arial", Font.BOLD, 12));
		cbbSortUser.setModel(new DefaultComboBoxModel(new String[] {"File name", "File size"}));
		cbbSortUser.setBounds(136, 11, 115, 30);
		panel_2_1.add(cbbSortUser);
		
		cbbSearchUser = new JComboBox();
		cbbSearchUser.setFont(new Font("Arial", Font.BOLD, 12));
		cbbSearchUser.setModel(new DefaultComboBoxModel(new String[] {"Id", "File name"}));
		cbbSearchUser.setBounds(10, 52, 115, 30);
		panel_2_1.add(cbbSearchUser);
	}
}
