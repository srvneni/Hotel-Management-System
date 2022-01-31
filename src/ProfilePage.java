import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class ProfilePage extends JFrame {
	Connection conn = null;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField ediEmailTextField_1;
	private JTextField ediEmailTextField_2;
	private JTextField textField_3;
	private JTextField editPhoneNumberTextField_4;
	private JTextField editPhoneNumberTextField_5;
	private JLabel lblNewLabel;
	private JLabel lblPrimaryEmailId;
	private JLabel lblSecondaryEmailId;
	private JLabel lblFullName;
	private JLabel lblPrimaryPhoneNumber;
	private JLabel lblSecondaryPhoneNumber;
	private JLabel lblNewLabel_1;
	private JButton btnNewButton;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfilePage frame = new ProfilePage();
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
	public ProfilePage() {
		conn = MySqlConnection.getConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1184, 738);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.textHighlightText);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		UserInfo user = new UserInfo();
		int customer = user.getUserId();
		String[] phone = new String[2];
		String[] email =  new String[2];
		String fullName = "";
		try {
			Statement stmt = conn.createStatement();
			String query = "select * from customer where customer_id ="+customer+"";
			ResultSet rs = stmt.executeQuery(query);
			int i = 0;
			while(rs.next()) {
				fullName = rs.getString("full_name");
				i++;
			}
			System.out.println(fullName);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			Statement stmt = conn.createStatement();
			String query = "select * from customer_phone where customer_id ="+customer+"";
			ResultSet rs = stmt.executeQuery(query);
			int i = 0;
			while(rs.next()) {
				phone[i] = rs.getString("phone_number");
				i++;
			}
			System.out.println(phone[i]);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			Statement stmt = conn.createStatement();
			String query = "select * from customer_email where customer_id ="+customer+"";
			ResultSet rs = stmt.executeQuery(query);
			int i = 0;
			while(rs.next()) {
				email[i] = rs.getString("email");
				i++;
			}
			System.out.println(email[0]);
			System.out.println(email[1]);
		}
		catch(Exception e2) {
			System.out.println(e2.getMessage());
		}
		textField = new JTextField();
		textField.setText(""+customer);
		textField.setEditable(false);
		textField.setText(""+customer);
		textField.setBounds(216, 189, 235, 51);
		contentPane.add(textField);
		textField.setColumns(10);
		
		ediEmailTextField_1 = new JTextField();
		ediEmailTextField_1.setEditable(false);
		ediEmailTextField_1.setBounds(216, 299, 235, 51);
		ediEmailTextField_1.setText(email[0]);
		contentPane.add(ediEmailTextField_1);
		ediEmailTextField_1.setColumns(10);
		
		ediEmailTextField_2 = new JTextField();
		ediEmailTextField_2.setEditable(false);
		ediEmailTextField_2.setBounds(216, 404, 235, 51);
		ediEmailTextField_2.setText(email[1]);
		contentPane.add(ediEmailTextField_2);
		ediEmailTextField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setText((""+fullName));
		textField_3.setEditable(false);
		textField_3.setBounds(672, 189, 235, 51);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		editPhoneNumberTextField_4 = new JTextField();
		editPhoneNumberTextField_4.setEditable(false);
		editPhoneNumberTextField_4.setColumns(10);
		editPhoneNumberTextField_4.setText(phone[0]);
		editPhoneNumberTextField_4.setBounds(672, 299, 235, 51);
		contentPane.add(editPhoneNumberTextField_4);
		
		editPhoneNumberTextField_5 = new JTextField();
		editPhoneNumberTextField_5.setEditable(false);
		editPhoneNumberTextField_5.setColumns(10);
		editPhoneNumberTextField_5.setText(phone[1]);
		editPhoneNumberTextField_5.setBounds(672, 404, 235, 51);
		contentPane.add(editPhoneNumberTextField_5);
		Icon icon = new ImageIcon("C:\\Users\\srvne\\eclipse-workspace\\HotelManagementSystem\\images\\editImage.png");
		JButton editEmail1 = new JButton(icon);
		editEmail1.setBackground(SystemColor.textHighlightText);
		editEmail1.setBounds(471, 299, 53, 51);
		contentPane.add(editEmail1);
		
		JButton btnNewButton_1 = new JButton((Icon) null);
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton_1.setBackground(SystemColor.textHighlight);
		btnNewButton_1.setForeground(SystemColor.textHighlightText);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ediEmailTextField_1.getText()!=email[0]) {
					System.out.println("called1");
					updateProfile("customer_email","email", ediEmailTextField_1.getText(), email[0]);
				}
				if(ediEmailTextField_2.getText()!=email[1]) {
					updateProfile("customer_email","email", ediEmailTextField_2.getText(), email[1]);
				}
				if(editPhoneNumberTextField_4.getText()!=phone[0]) {
					updateProfile("customer_phone","phone_number", editPhoneNumberTextField_4.getText(), phone[0]);
				}
				if(editPhoneNumberTextField_5.getText()!=phone[1]) {
					updateProfile("customer_phone","phone_number", editPhoneNumberTextField_5.getText(), phone[1]);
				}
				ediEmailTextField_1.setEditable(false);
				ediEmailTextField_2.setEditable(false);
				editPhoneNumberTextField_4.setEditable(false);
				editPhoneNumberTextField_5.setEditable(false);
			}
		});
		btnNewButton_1.setText("submit");
		btnNewButton_1.setBounds(495, 501, 175, 51);
		btnNewButton_1.setVisible(false);
		contentPane.add(btnNewButton_1);
		
		JButton editEmail2 = new JButton(icon);
		editEmail2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton_1.setVisible(true);
				ediEmailTextField_2.setEditable(true);
			}
		});
		editEmail2.setBackground(SystemColor.textHighlightText);
		editEmail2.setBounds(471, 404, 53, 51);
		contentPane.add(editEmail2);
		
		JButton editPhone1 = new JButton(icon);
		editPhone1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton_1.setVisible(true);
				editPhoneNumberTextField_4.setEditable(true);
			}
		});
		editPhone1.setBackground(SystemColor.textHighlightText);
		editPhone1.setBounds(929, 299, 53, 51);
		contentPane.add(editPhone1);
		
		JButton editPhone2 = new JButton(icon);
		editPhone2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton_1.setVisible(true);
				editPhoneNumberTextField_5.setEditable(true);
			}
		});
		editPhone2.setBackground(SystemColor.textHighlightText);
		editPhone2.setBounds(929, 404, 53, 51);
		contentPane.add(editPhone2);
		
		lblNewLabel = new JLabel("User Id");
		lblNewLabel.setBounds(216, 164, 61, 14);
		contentPane.add(lblNewLabel);
		
		lblPrimaryEmailId = new JLabel("Primary Email Id");
		lblPrimaryEmailId.setBounds(216, 274, 99, 14);
		contentPane.add(lblPrimaryEmailId);
		
		lblSecondaryEmailId = new JLabel("Secondary Email Id");
		lblSecondaryEmailId.setBounds(216, 384, 99, 9);
		contentPane.add(lblSecondaryEmailId);
		
		lblFullName = new JLabel("Full Name");
		lblFullName.setBounds(672, 164, 61, 14);
		contentPane.add(lblFullName);
		
		lblPrimaryPhoneNumber = new JLabel("Primary Phone Number");
		lblPrimaryPhoneNumber.setBounds(672, 274, 122, 14);
		contentPane.add(lblPrimaryPhoneNumber);
		
		lblSecondaryPhoneNumber = new JLabel("Secondary Phone Number");
		lblSecondaryPhoneNumber.setBounds(672, 379, 141, 14);
		contentPane.add(lblSecondaryPhoneNumber);
		
		lblNewLabel_1 = new JLabel("Customer Profile");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_1.setForeground(SystemColor.textHighlight);
		lblNewLabel_1.setBounds(429, 60, 282, 51);
		contentPane.add(lblNewLabel_1);
		
		btnNewButton = new JButton("back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainPage mp= new MainPage();
				mp.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBackground(SystemColor.textHighlight);
		btnNewButton.setForeground(SystemColor.textHighlightText);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBounds(37, 42, 90, 51);
		contentPane.add(btnNewButton);
		
		editEmail1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton_1.setVisible(true);
				ediEmailTextField_1.setEditable(true);
			}
		});
		
	}
	
	public boolean updateProfile(String tableName, String columnName, String data, String presentData){
		UserInfo user = new UserInfo();
		int customer = user.getUserId();
		System.out.println("called2");
		String query = "update "+tableName+" set "+columnName+" = '"+data+"' where customer_id="+customer+" and "+columnName+"='"+presentData+"'";
		System.out.println(query);
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			if(stmt.execute()) {
				System.out.println(stmt.getGeneratedKeys());
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
}
