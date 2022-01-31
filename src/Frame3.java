import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.events.MouseEvent;

import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;

public class Frame3 extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	Connection conn = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame3 frame = new Frame3();
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
	public Frame3() {
		conn = MySqlConnection.getConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1173, 768);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Customer Login ");
		lblNewLabel.setFont(new Font("Lucida Sans", Font.BOLD, 30));
		lblNewLabel.setBounds(446, 127, 282, 46);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(461, 209, 254, 37);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("User Id");
		lblNewLabel_1.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(334, 212, 108, 34);
		contentPane.add(lblNewLabel_1);
		
		JLabel header_icon = new JLabel("");
		Image img2 = new ImageIcon(this.getClass().getResource("/login-header-icon.jpg")).getImage();
		header_icon.setIcon(new ImageIcon(img2));
		header_icon.setBounds(384, 137, 36, 36);
		contentPane.add(header_icon);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(334, 288, 108, 34);
		contentPane.add(lblNewLabel_1_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(461, 288, 254, 34);
		contentPane.add(passwordField);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = String.valueOf(passwordField.getPassword());
					if(textField.getText()=="" || password == null) {
						JOptionPane.showMessageDialog(null, "Please provide userId and password to login.");
					}
					else {
						int user_Id = Integer.parseInt(textField.getText());
						UserInfo user = new UserInfo();
						System.out.println("this:"+user.setUserId(user_Id));
						
				    password = String.valueOf(passwordField.getPassword());
					
				  	 String query = "select * from login where customer_Id='"+user_Id+"' and password='"+password+"'"; 
							  	Statement stmt;
								try {
									stmt = conn.createStatement();
									ResultSet rs = stmt.executeQuery(query);
							  		if(rs.next()) {
							  			dispose();
							  			MainPage frame = new MainPage();
							  			frame.setVisible(true);
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
		}
		);
		btnNewButton.setForeground(SystemColor.text);
		btnNewButton.setBackground(SystemColor.textHighlight);
		btnNewButton.setFont(new Font("Lucida Sans", Font.BOLD, 20));
		btnNewButton.setBounds(498, 388, 123, 37);
		contentPane.add(btnNewButton);
		
		JLabel hyperlink = new JLabel("Don't have an account? Register Here");
		hyperlink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				Frame3 frame = new Frame3();
				frame.dispose();
				HomePage homePage = new HomePage();
				homePage.setVisible(true);
			}
		});
		
		hyperlink.setForeground(new Color(0, 0, 255));
		hyperlink.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		hyperlink.setBounds(395, 352, 381, 25);
		contentPane.add(hyperlink);
		
		JButton btnNewButton_1 = new JButton("back");
		btnNewButton_1.addActionListener(new ActionListener() {
			//work on this code *******
			public void actionPerformed(ActionEvent e) {
				Frame3 frame = new Frame3();
				frame.dispose();
				Frame1 frame2 = new Frame1();
				setVisible(true);
			}
		});
		btnNewButton_1.setBackground(SystemColor.textHighlight);
		btnNewButton_1.setForeground(SystemColor.textHighlightText);
		btnNewButton_1.setBounds(42, 33, 86, 53);
		contentPane.add(btnNewButton_1);
	}
}

