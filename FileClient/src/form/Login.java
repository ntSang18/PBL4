package form;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

import model.User;
import service.Service;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frame;
	private JTextField LoginUsername;
	private JPasswordField LoginPassword;
	private JTextField RegisterUsername;
	private JPasswordField RegisterPass;
	private JPasswordField RegisterRepass;

	private static Login _Instance;
	public static Login Instance() {
		if(_Instance == null) _Instance = new Login();
		return _Instance;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	private Login() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 335);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setForeground(Color.BLACK);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setFont(new Font("Arial", Font.BOLD, 11));
		tabbedPane.setBounds(0, 0, 384, 296);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(135, 206, 250));
		tabbedPane.addTab("Login", null, panel, null);
		
		JLabel lbDangnhap = new JLabel("LOGIN");
		lbDangnhap.setFont(new Font("Calibri", Font.BOLD, 21));
		lbDangnhap.setBounds(150, 10, 67, 35);
		panel.add(lbDangnhap);
		
		JLabel lbTendangnhap = new JLabel("User name");
		lbTendangnhap.setHorizontalAlignment(SwingConstants.RIGHT);
		lbTendangnhap.setFont(new Font("Calibri", Font.BOLD, 12));
		lbTendangnhap.setBounds(0, 89, 85, 20);
		panel.add(lbTendangnhap);
		
		JLabel lbMatkhau = new JLabel("Password");
		lbMatkhau.setHorizontalAlignment(SwingConstants.RIGHT);
		lbMatkhau.setFont(new Font("Calibri", Font.BOLD, 12));
		lbMatkhau.setBounds(0, 147, 85, 20);
		panel.add(lbMatkhau);
		
		LoginUsername = new JTextField();
		LoginUsername.setColumns(10);
		LoginUsername.setBounds(95, 82, 246, 30);
		panel.add(LoginUsername);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = LoginUsername.getText().trim();
				String password = LoginPassword.getText().trim();
				User u = new User(username, password);
				Service.Instance().Login(u);
			}
		});
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setFont(new Font("Calibri", Font.BOLD, 15));
		btnLogin.setBorder(null);
		btnLogin.setBackground(new Color(220, 20, 60));
		btnLogin.setBounds(65, 210, 105, 30);
		panel.add(btnLogin);
		
		JButton btnLoginExit = new JButton("Exit");
		btnLoginExit.setForeground(new Color(220, 20, 60));
		btnLoginExit.setFont(new Font("Calibri", Font.BOLD, 15));
		btnLoginExit.setBorder(null);
		btnLoginExit.setBackground(new Color(230, 230, 250));
		btnLoginExit.setBounds(215, 210, 105, 30);
		panel.add(btnLoginExit);
		
		JLabel lbIcon = new JLabel((Icon) null);
		lbIcon.setBounds(173, 11, 67, 30);
		panel.add(lbIcon);
		
		LoginPassword = new JPasswordField();
		LoginPassword.setBounds(95, 141, 246, 29);
		panel.add(LoginPassword);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(135, 206, 250));
		tabbedPane.addTab("Register", null, panel_1, null);
		
		JLabel lblRegister = new JLabel("REGISTER");
		lblRegister.setFont(new Font("Calibri", Font.BOLD, 21));
		lblRegister.setBounds(150, 10, 97, 35);
		panel_1.add(lblRegister);
		
		JLabel lbTendangnhap_1 = new JLabel("User name");
		lbTendangnhap_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lbTendangnhap_1.setFont(new Font("Calibri", Font.BOLD, 12));
		lbTendangnhap_1.setBounds(10, 67, 85, 20);
		panel_1.add(lbTendangnhap_1);
		
		JLabel lbMatkhau_1 = new JLabel("Password");
		lbMatkhau_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lbMatkhau_1.setFont(new Font("Calibri", Font.BOLD, 12));
		lbMatkhau_1.setBounds(10, 116, 85, 20);
		panel_1.add(lbMatkhau_1);
		
		RegisterUsername = new JTextField();
		RegisterUsername.setColumns(10);
		RegisterUsername.setBounds(105, 60, 246, 30);
		panel_1.add(RegisterUsername);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = RegisterUsername.getText().trim();
				String password = RegisterPass.getText().trim();
				String repassword = RegisterRepass.getText().trim();
				if(password.equals(repassword)) {
					User u = new User(username, password);
					Service.Instance().Register(u);
				}
				else {
					JOptionPane.showMessageDialog(frame, "Invalid Confirm Password");
				}
			}
		});
		btnRegister.setForeground(Color.BLACK);
		btnRegister.setFont(new Font("Calibri", Font.BOLD, 15));
		btnRegister.setBorder(null);
		btnRegister.setBackground(new Color(220, 20, 60));
		btnRegister.setBounds(65, 210, 105, 30);
		panel_1.add(btnRegister);
		
		JButton btnExitRegister = new JButton("Exit");
		btnExitRegister.setForeground(new Color(220, 20, 60));
		btnExitRegister.setFont(new Font("Calibri", Font.BOLD, 15));
		btnExitRegister.setBorder(null);
		btnExitRegister.setBackground(new Color(230, 230, 250));
		btnExitRegister.setBounds(215, 210, 105, 30);
		panel_1.add(btnExitRegister);
		
		JLabel lbIcon_1 = new JLabel((Icon) null);
		lbIcon_1.setBounds(173, 11, 67, 80);
		panel_1.add(lbIcon_1);
		
		RegisterPass = new JPasswordField();
		RegisterPass.setBounds(105, 110, 246, 29);
		panel_1.add(RegisterPass);
		
		JLabel lbMatkhau_1_1 = new JLabel("Confirm Password");
		lbMatkhau_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lbMatkhau_1_1.setFont(new Font("Calibri", Font.BOLD, 12));
		lbMatkhau_1_1.setBounds(0, 166, 95, 20);
		panel_1.add(lbMatkhau_1_1);
		
		RegisterRepass = new JPasswordField();
		RegisterRepass.setBounds(105, 160, 246, 29);
		panel_1.add(RegisterRepass);
	}
}
