import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class EmployeeSignIn extends JFrame {

	private JPanel contentPane;
	private JTextField empIdField;
	private JPasswordField passwordField;
	Connection conn = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeSignIn frame = new EmployeeSignIn();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EmployeeSignIn() {
		String[] role = new String[1];
		String[] roles = new String[3];
		conn = MySqlConnection.getConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1191, 743);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.textHighlightText);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		empIdField = new JTextField();
		empIdField.setBounds(461, 209, 254, 37);
		contentPane.add(empIdField);
		empIdField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(461, 288, 254, 34);
		contentPane.add(passwordField);
		
		JLabel lblNewLabel = new JLabel("Employee Login");
		lblNewLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 30));
		lblNewLabel.setBounds(446, 127, 282, 46);
		contentPane.add(lblNewLabel);
		JButton signInButton = new JButton("Sign In");
		String query3 = "select * from role"; 
	    Statement stmt3;
		try {
			stmt3 = conn.createStatement();
			ResultSet rs3 = stmt3.executeQuery(query3);
			int i=0;
	  		while(rs3.next()) {
	  			for(int k = 0;i<roles.length;i++) {
	  				if(rs3.getString("role_name")==roles[k]) {
	  					break;
	  				}
	  				else {
	  			//String query2 = "select role_name from role where employee_id="+user_id+"";
	  			roles[i]=rs3.getString("role_name");
	  			i++;
	  				}
	  		}
		}
		}catch (SQLException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		signInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = String.valueOf(passwordField.getPassword());
				if(empIdField.getText()=="" || password == null) {
					JOptionPane.showMessageDialog(null, "Please provide userId and password to login.");
				}
				else {
					int user_Id = Integer.parseInt(empIdField.getText());
					UserInfo user = new UserInfo();
					System.out.println("this:"+user.setUserId(user_Id));
					
			    password = String.valueOf(passwordField.getPassword());
			    String query2 = "select * from role where employee_id="+user_Id+""; 
			    Statement stmt2;
				try {
					stmt2 = conn.createStatement();
					ResultSet rs2 = stmt2.executeQuery(query2);
			  		if(rs2.next()) {
			  			//String query2 = "select role_name from role where employee_id="+user_id+"";
			  			role[0]=rs2.getString("role_name");
			  		}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			  	 String query = "select * from signin where employee_Id='"+user_Id+"' and password='"+password+"'"; 
						  	Statement stmt;
							try {
								stmt = conn.createStatement();
								ResultSet rs = stmt.executeQuery(query);
						  		if(rs.next()) {
						  			System.out.println("here1");
						  			//String query2 = "select role_name from role where employee_id="+user_id+"";
						  			if(role[0].charAt(0)=='r') {
						  				System.out.println("here");
						  				dispose();
						  				CheckInManagement cim = new CheckInManagement();
						  				cim.setVisible(true);
						  			}
						  			else if(role[0].charAt(0)=='h') {
						  				dispose();
						  				HousekeepingManagement hkm = new HousekeepingManagement();
						  				hkm.setVisible(true);
						  			}
						  			else if(role[0].charAt(0)=='c') {
						  				dispose();
						  				RoomServiceManagement rsm = new RoomServiceManagement();
						  				rsm.setVisible(true);
						  			}
						  		}
						  		else {
						  			JOptionPane.showMessageDialog(null, "Invaild user Id or Password. Please provide correct details.");
						  		}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				  }
			}
		});
		signInButton.setBackground(SystemColor.textHighlight);
		signInButton.setForeground(SystemColor.textHighlightText);
		signInButton.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
		signInButton.setBounds(498, 388, 123, 37);
		contentPane.add(signInButton);
		
		JLabel lblNewLabel_1 = new JLabel("");
		Image img2 = new ImageIcon(this.getClass().getResource("/login-header-icon.jpg")).getImage();
		lblNewLabel_1.setIcon(new ImageIcon(img2));
		lblNewLabel_1.setBounds(384, 137, 36, 36);
		lblNewLabel_1.setBounds(384, 137, 36, 36);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("User Id");
		lblNewLabel_1_1.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(327, 209, 108, 34);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Password");
		lblNewLabel_1_1_1.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
		lblNewLabel_1_1_1.setBounds(327, 288, 108, 34);
		contentPane.add(lblNewLabel_1_1_1);
		
		JButton btnNewButton_1 = new JButton("back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame1 frames = new Frame1();
				frames.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(SystemColor.textHighlight);
		btnNewButton_1.setBounds(64, 49, 86, 53);
		contentPane.add(btnNewButton_1);
	}
}
