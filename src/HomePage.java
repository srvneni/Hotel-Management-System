import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.SystemColor;
import javax.swing.JPasswordField;

public class HomePage extends JFrame {
	Connection conn = null;
	private JPanel contentPane;
	private JTextField fullNameTextField;
	private JTextField primaryEmailIdTextField;
	private JTextField secondaryEmailIdTextField;
	private JTextField primaryPhoneNoTextField;
	private JTextField secondaryPhoneNoTextField;
	private JTextField flatNoTextField;
	private JTextField streetNameTextField;
	private JTextField countyTextField;
	private JTextField stateTextField;
	private JTextField zipCodeTextField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomePage frame = new HomePage();
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
		//use the code
	public HomePage() {
		conn = MySqlConnection.getConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1171, 765);
		contentPane = new JPanel();
		//JScrollPane contentPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);mypanel.AutoScroll = false;
		contentPane.setPreferredSize(new Dimension( 2000,2000));
		JScrollPane scrollFrame = new JScrollPane(contentPane);
		contentPane.setAutoscrolls(true);
		scrollFrame.setPreferredSize(new Dimension( 500,300));
		getContentPane().add(scrollFrame);
		
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		JLabel lblNewLabel = new JLabel("Registration ");
		lblNewLabel.setFont(new Font("Lucida Sans", Font.BOLD, 30));
		lblNewLabel.setBounds(494, 24, 240, 56);
		contentPane.add(lblNewLabel);
		
		fullNameTextField = new JTextField();
		fullNameTextField.setBounds(552, 103, 253, 44);
		contentPane.add(fullNameTextField);
		fullNameTextField.setColumns(10);
		
		primaryEmailIdTextField = new JTextField();
		primaryEmailIdTextField.setColumns(10);
		primaryEmailIdTextField.setBounds(552, 158, 253, 44);
		contentPane.add(primaryEmailIdTextField);
		
		secondaryEmailIdTextField = new JTextField();
		secondaryEmailIdTextField.setColumns(10);
		secondaryEmailIdTextField.setBounds(552, 218, 253, 44);
		contentPane.add(secondaryEmailIdTextField);
		
		primaryPhoneNoTextField = new JTextField();
		primaryPhoneNoTextField.setColumns(10);
		primaryPhoneNoTextField.setBounds(552, 274, 253, 44);
		contentPane.add(primaryPhoneNoTextField);
		
		secondaryPhoneNoTextField = new JTextField();
		secondaryPhoneNoTextField.setColumns(10);
		secondaryPhoneNoTextField.setBounds(552, 329, 253, 44);
		contentPane.add(secondaryPhoneNoTextField);
		
		flatNoTextField = new JTextField();
		flatNoTextField.setColumns(10);
		flatNoTextField.setBounds(552, 384, 253, 44);
		contentPane.add(flatNoTextField);
		
		streetNameTextField = new JTextField();
		streetNameTextField.setColumns(10);
		streetNameTextField.setBounds(552, 439, 253, 44);
		contentPane.add(streetNameTextField);
		
		countyTextField = new JTextField();
		countyTextField.setColumns(10);
		countyTextField.setBounds(552, 494, 253, 44);
		contentPane.add(countyTextField);
		
		stateTextField = new JTextField();
		stateTextField.setColumns(10);
		stateTextField.setBounds(552, 549, 253, 44);
		contentPane.add(stateTextField);
		
		zipCodeTextField = new JTextField();
		zipCodeTextField.setColumns(10);
		zipCodeTextField.setBounds(552, 604, 253, 44);
		contentPane.add(zipCodeTextField);
		
		JButton signUpButton = new JButton("Sign Up");
		signUpButton.setForeground(SystemColor.text);
		signUpButton.setBackground(SystemColor.textHighlight);
		signUpButton.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fullNameTextField.getText()==null||primaryEmailIdTextField.getText()==null||primaryPhoneNoTextField.getText()==null||streetNameTextField.getText()==null||countyTextField.getText()==null||stateTextField.getText()==null||zipCodeTextField.getText()==null) {
					JOptionPane.showMessageDialog(null,"Please provide all mandatory details to register.");
				}
				else {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					int last_inserted_id = 0;
					String fullName = fullNameTextField.getText();
					String primaryEmailId = primaryEmailIdTextField.getText();
					String secondaryEmailId = secondaryEmailIdTextField.getText();
					String primaryPhoneNo = primaryPhoneNoTextField.getText();
					String secondaryPhoneNo = secondaryPhoneNoTextField.getText();
					int flatNo = Integer.parseInt(flatNoTextField.getText());
					String streetName = streetNameTextField.getText();
					String county = countyTextField.getText();
					String state = stateTextField.getText();
					String zipCode = zipCodeTextField.getText();
				  	String query = "insert into customer(customer_id, full_name, street_name, flat_no, county, state, zip_code)"+"values(?, ?, ?, ?, ?, ?, ?)";
				  	String query2 = "insert into login(customer_id, password)"+"values(?, ?)";
				  	String query3 = "insert into customer_email(customer_id, email)"+"values(?,?)";
				  	String query4 = "insert into customer_phone(customer_id, phone_number)"+"values(?,?)";
							  	 PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
							  	 stmt.setString(1, null);
							  	 stmt.setString (2, fullName);
							     stmt.setString (3, streetName);
							     stmt.setInt(4, flatNo);
							     stmt.setString(5, county);
							     stmt.setString(6, state);
							     stmt.setString(7, zipCode);
							  	 stmt.execute();
							  	 ResultSet rs = stmt.getGeneratedKeys();
				                 if(rs.next())
				                 {
				                    last_inserted_id = rs.getInt(1);
					                System.out.println(last_inserted_id);
				                 }
				                 stmt = conn.prepareStatement(query2);
				                 stmt.setInt(1, last_inserted_id);
				                 stmt.setString(2, String.valueOf(passwordField.getPassword()));
				                 stmt.execute();
				                 stmt = conn.prepareStatement(query3);
				                 stmt.setInt(1, last_inserted_id);
				                 stmt.setString(2, primaryEmailId);
				                 stmt.execute();
				                 stmt.setInt(1, last_inserted_id);
				                 stmt.setString(2, secondaryEmailId);
				                 stmt.execute();
				                 stmt = conn.prepareStatement(query4);
				                 stmt.setInt(1, last_inserted_id);
				                 stmt.setString(2, primaryPhoneNo);
				                 stmt.execute();
				                 stmt.setInt(1, last_inserted_id);
				                 stmt.setString(2, secondaryPhoneNo);
				                 stmt.execute();
						  		 conn.close();
						  		 JOptionPane.showMessageDialog(null, "Your user id is "+last_inserted_id+". Please use this user id to login to your account.");
					  }
				catch(Exception e2) {
					System.out.println(e2.getMessage());
				}
				}
				
			}
		});
		signUpButton.setBounds(482, 735, 165, 44);
		contentPane.add(signUpButton);
		
		JLabel lblNewLabel_1 = new JLabel("Full Name *");
		lblNewLabel_1.setFont(new Font("Lucida Sans", Font.PLAIN, 22));
		lblNewLabel_1.setBounds(308, 103, 153, 44);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Primary Email Id *");
		lblNewLabel_1_1.setFont(new Font("Lucida Sans", Font.PLAIN, 22));
		lblNewLabel_1_1.setBounds(308, 158, 206, 44);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Secondary Email Id");
		lblNewLabel_1_2.setFont(new Font("Lucida Sans", Font.PLAIN, 22));
		lblNewLabel_1_2.setBounds(308, 218, 206, 44);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Primary Phone No. *");
		lblNewLabel_1_3.setFont(new Font("Lucida Sans", Font.PLAIN, 22));
		lblNewLabel_1_3.setBounds(308, 274, 234, 44);
		contentPane.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("Secondary Phone No.");
		lblNewLabel_1_4.setFont(new Font("Lucida Sans", Font.PLAIN, 22));
		lblNewLabel_1_4.setBounds(308, 329, 234, 44);
		contentPane.add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_5 = new JLabel("Flat No. *");
		lblNewLabel_1_5.setFont(new Font("Lucida Sans", Font.PLAIN, 22));
		lblNewLabel_1_5.setBounds(308, 384, 153, 44);
		contentPane.add(lblNewLabel_1_5);
		
		JLabel lblNewLabel_1_6 = new JLabel("Street Name *");
		lblNewLabel_1_6.setFont(new Font("Lucida Sans", Font.PLAIN, 22));
		lblNewLabel_1_6.setBounds(308, 439, 153, 44);
		contentPane.add(lblNewLabel_1_6);
		
		JLabel lblNewLabel_1_7 = new JLabel("County *");
		lblNewLabel_1_7.setFont(new Font("Lucida Sans", Font.PLAIN, 22));
		lblNewLabel_1_7.setBounds(308, 494, 153, 44);
		contentPane.add(lblNewLabel_1_7);
		
		JLabel lblNewLabel_1_8 = new JLabel("State *");
		lblNewLabel_1_8.setFont(new Font("Lucida Sans", Font.PLAIN, 22));
		lblNewLabel_1_8.setBounds(308, 549, 153, 44);
		contentPane.add(lblNewLabel_1_8);
		
		JLabel lblNewLabel_1_9 = new JLabel("Zipcode *");
		lblNewLabel_1_9.setFont(new Font("Lucida Sans", Font.PLAIN, 22));
		lblNewLabel_1_9.setBounds(308, 604, 153, 44);
		contentPane.add(lblNewLabel_1_9);
		
		
		JLabel lblNewLabel_2 = new JLabel("");
		Image img2 = new ImageIcon(this.getClass().getResource("/RegisterIcon2.png")).getImage();
		lblNewLabel_2.setIcon(new ImageIcon(img2));
		lblNewLabel_2.setBounds(437, 35, 47, 45);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1_9_1 = new JLabel("Password *");
		lblNewLabel_1_9_1.setFont(new Font("Lucida Sans", Font.PLAIN, 22));
		lblNewLabel_1_9_1.setBounds(308, 659, 153, 44);
		contentPane.add(lblNewLabel_1_9_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(552, 665, 253, 44);
		contentPane.add(passwordField);
		
		JButton btnNewButton = new JButton("back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 Frame3 frame = new Frame3();
				 frame.setVisible(true);
			}
		});
		btnNewButton.setBackground(SystemColor.textHighlight);
		btnNewButton.setForeground(SystemColor.textHighlightText);
		btnNewButton.setBounds(24, 24, 86, 56);
		contentPane.add(btnNewButton);
		
		
	}
}
