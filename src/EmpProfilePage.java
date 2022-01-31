import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.*
;public class EmpProfilePage extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField primaryEmailIdText;
	private JTextField secondaryEmailText;
	private JTextField textField_3;
	private JTextField primaryPhoneText;
	private JTextField secondaryPhoneText;
	private JButton btnNewButton;
	private JButton editEmail2;
	private JButton editEmail1;
	private JLabel lblNewLabel;
	private JButton editPhone_1;
	private JButton editPhone_2;
	private JButton btnNewButton_1;
	private JLabel lblNewLabel_1;
	private JLabel lblPrimaryEmailId;
	private JLabel lblSecondaryEmailId;
	private JLabel lblFullName;
	private JLabel lblPrimaryPhoneNumber;
	private JLabel lblSecondaryPhoneNumber;
	Connection conn = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmpProfilePage frame = new EmpProfilePage();
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
	public EmpProfilePage() {
		conn = MySqlConnection.getConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1186, 739);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		UserInfo user = new UserInfo();
		int employee = user.getUserId();
		String[] phone = new String[2];
		String[] email =  new String[2];
		String fullName = "";
		try {
			Statement stmt = conn.createStatement();
			String query = "select * from employee where employee_id ="+employee+"";
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
			String query = "select * from employee_phone where employee_id ="+employee+"";
			ResultSet rs = stmt.executeQuery(query);
			int i = 0;
			while(rs.next()) {
				phone[i] = rs.getString("employee_phone_number");
				i++;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			Statement stmt = conn.createStatement();
			String query = "select * from employee_email where employee_id ="+employee+"";
			ResultSet rs = stmt.executeQuery(query);
			int i = 0;
			while(rs.next()) {
				email[i] = rs.getString("employee_email");
				i++;
			}
		}
		catch(Exception e2) {
			System.out.println(e2.getMessage());
		}
		
		
		textField = new JTextField();
		textField.setText("0");
		textField.setText(""+employee);
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(189, 174, 235, 51);
		contentPane.add(textField);
		
		primaryEmailIdText = new JTextField();
		primaryEmailIdText.setText(email[0]);
		primaryEmailIdText.setEditable(false);
		primaryEmailIdText.setColumns(10);
		primaryEmailIdText.setBounds(189, 284, 235, 51);
		contentPane.add(primaryEmailIdText);
		
		secondaryEmailText = new JTextField();
		secondaryEmailText.setText(email[1]);
		secondaryEmailText.setEditable(false);
		secondaryEmailText.setColumns(10);
		secondaryEmailText.setBounds(189, 389, 235, 51);
		contentPane.add(secondaryEmailText);
		
		textField_3 = new JTextField();
		textField_3.setText(""+fullName);
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(645, 174, 235, 51);
		contentPane.add(textField_3);
		
		primaryPhoneText = new JTextField();
		primaryPhoneText.setText(phone[0]);
		primaryPhoneText.setEditable(false);
		primaryPhoneText.setColumns(10);
		primaryPhoneText.setBounds(645, 284, 235, 51);
		contentPane.add(primaryPhoneText);
		
		secondaryPhoneText = new JTextField();
		secondaryPhoneText.setText(phone[1]);
		secondaryPhoneText.setEditable(false);
		secondaryPhoneText.setColumns(10);
		secondaryPhoneText.setBounds(645, 389, 235, 51);
		contentPane.add(secondaryPhoneText);
		
		btnNewButton = new JButton("back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String role = "";
				try {
					String query4 = "select role_name from role where employee_id = "+employee+"";
					PreparedStatement stmt = conn.prepareStatement(query4);
					ResultSet rs = stmt.executeQuery();
					while(rs.next()) {
						role = rs.getString("role_name");
					}
					if(role.charAt(0)=='r') {
						CheckInManagement cim = new CheckInManagement();
						cim.setVisible(true);
						dispose();
					}
					else if(role.charAt(0)=='h') {
						HousekeepingManagement hkm = new HousekeepingManagement();
						hkm.setVisible(true);
						dispose();
					}
					else if(role.charAt(0)=='c') {
						RoomServiceManagement rsm = new RoomServiceManagement();
						rsm.setVisible(true);
						dispose();
					}
				}
				catch(Exception e4) {
					System.out.println(e4.getMessage());
				}
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBackground(SystemColor.textHighlight);
		btnNewButton.setBounds(27, 21, 99, 58);
		contentPane.add(btnNewButton);
		Icon icon = new ImageIcon("C:\\Users\\srvne\\eclipse-workspace\\HotelManagementSystem\\images\\editImage.png");
		editEmail2 = new JButton(icon);
		editEmail2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton_1.setVisible(true);
				secondaryEmailText.setEditable(true);
			}
		});
		editEmail2.setBackground(Color.WHITE);
		editEmail2.setBounds(445, 389, 53, 51);
		contentPane.add(editEmail2);
		
		editEmail1 = new JButton((icon));
		editEmail1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton_1.setVisible(true);
				primaryEmailIdText.setEditable(true);
			}
		});
		editEmail1.setBackground(Color.WHITE);
		editEmail1.setBounds(445, 284, 53, 51);
		contentPane.add(editEmail1);
		
		lblNewLabel = new JLabel("Employee Profile");
		lblNewLabel.setForeground(SystemColor.textHighlight);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(439, 67, 282, 51);
		contentPane.add(lblNewLabel);
		
		editPhone_1 = new JButton((icon));
		editPhone_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton_1.setVisible(true);
				primaryPhoneText.setEditable(true);
			}
		});
		editPhone_1.setBackground(Color.WHITE);
		editPhone_1.setBounds(898, 284, 53, 51);
		contentPane.add(editPhone_1);
		
		editPhone_2 = new JButton((icon));
		editPhone_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton_1.setVisible(true);
				secondaryPhoneText.setEditable(true);
			}
		});
		editPhone_2.setBackground(Color.WHITE);
		editPhone_2.setBounds(898, 389, 53, 51);
		contentPane.add(editPhone_2);
		
		btnNewButton_1 = new JButton("Submit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(editEmail1.getText()!=email[0]) {
					System.out.println(editEmail1.getText());
					System.out.println(email[0]);
					updateProfile("employee_email","employee_email", primaryEmailIdText.getText(), email[0]);
				}
				if(editEmail2.getText()!=email[1]) {
					System.out.println(editEmail2.getText());
					System.out.println(email[1]);
					updateProfile("employee_email","employee_email", secondaryEmailText.getText(), email[1]);
				}
				if(editPhone_1.getText()!=phone[0]) {
					System.out.println(editPhone_1.getText());
					System.out.println(phone[0]);
					updateProfile("employee_phone","employee_phone_number", primaryPhoneText.getText(), phone[0]);
				}
				if(editPhone_2.getText()!=phone[1]) {
					System.out.println(""+editPhone_2.getText());
					System.out.println(phone[1]);
					updateProfile("employee_phone","employee_phone_number", secondaryPhoneText.getText(), phone[1]);
				}
				primaryEmailIdText.setEditable(false);
				secondaryEmailText.setEditable(false);
				primaryPhoneText.setEditable(false);
				secondaryPhoneText.setEditable(false);
			}
		});
		btnNewButton_1.setBounds(507, 519, 149, 51);
		contentPane.add(btnNewButton_1);
		
		lblNewLabel_1 = new JLabel("User Id");
		lblNewLabel_1.setBounds(189, 150, 61, 14);
		contentPane.add(lblNewLabel_1);
		
		lblPrimaryEmailId = new JLabel("Primary Email Id");
		lblPrimaryEmailId.setBounds(189, 260, 99, 14);
		contentPane.add(lblPrimaryEmailId);
		
		lblSecondaryEmailId = new JLabel("Secondary Email Id");
		lblSecondaryEmailId.setBounds(189, 369, 141, 9);
		contentPane.add(lblSecondaryEmailId);
		
		lblFullName = new JLabel("Full Name");
		lblFullName.setBounds(645, 150, 61, 14);
		contentPane.add(lblFullName);
		
		lblPrimaryPhoneNumber = new JLabel("Primary Phone Number");
		lblPrimaryPhoneNumber.setBounds(645, 260, 164, 14);
		contentPane.add(lblPrimaryPhoneNumber);
		
		lblSecondaryPhoneNumber = new JLabel("Secondary Phone Number");
		lblSecondaryPhoneNumber.setBounds(645, 366, 183, 14);
		contentPane.add(lblSecondaryPhoneNumber);
		
		
	}
	
	public boolean updateProfile(String tableName, String columnName, String data, String presentData){
		UserInfo user = new UserInfo();
		int employee = user.getUserId();
		System.out.println("called2");
		String query = "update "+tableName+" set "+columnName+" = '"+data+"' where employee_id="+employee+" and "+columnName+"='"+presentData+"'";
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
