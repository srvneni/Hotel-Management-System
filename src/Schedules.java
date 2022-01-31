import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import com.toedter.components.JSpinField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Schedules extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	Connection conn = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Schedules frame = new Schedules();
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
	public Schedules() {
		conn = MySqlConnection.getConnection();
		Time cinTime;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1183, 740);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = dtf.format(LocalDateTime.now());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date1 = null;
        try {
			date1 = format.parse(today);
        }
        catch (ParseException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
        java.sql.Date todayDate = new java.sql.Date(date1.getTime());
        
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm");
        String todayTime = dtf2.format(LocalDateTime.now());
        SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
        Date date = null;

        try {
            date = format2.parse(todayTime);
        }
        catch(Exception e) {
        	System.out.println(e.getMessage());
        }
        java.sql.Time cinTime2 = new java.sql.Time(date.getTime());
        System.out.println("here:"+cinTime2);
        UserInfo user = new UserInfo();
        int employee= user.getUserId();
		textField = new JTextField();
		textField.setText(""+employee);
		textField.setEditable(false);
		textField.setBounds(476, 192, 261, 53);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setText(""+today);
		textField_1.setBounds(476, 283, 261, 53);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		JButton btnNewButton = new JButton("Clock-In");
		String query4 = "Select * from schedules where employee_id="+employee+" and working_date='"+todayDate+"'";
		try {
			PreparedStatement stmt4 = conn.prepareStatement(query4);
			ResultSet rs = stmt4.executeQuery();
			if(rs.next()==false) {
				btnNewButton.setText("Clock-In");
			}
			else {
				if(rs.getTime("start_time")!=null && rs.getTime("end_time")==null) {
					btnNewButton.setText("Clock-Out");
				}
				else if(rs.getTime("start_time")!=null && rs.getTime("end_time")==null){
					btnNewButton.setText("Clock-In");
				}
			}
		}
		catch(Exception e6) {
			System.out.println(e6.getMessage());
		}
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					String query1 = "Select * from schedules where employee_id="+employee+" and working_date='"+todayDate+"'";
					String query2 = "insert into schedules(employee_id, working_date, start_time, end_time) values(?,?,?,?)";
					String query3 = "update schedules set end_time = ? where employee_id = "+employee+" and working_date = '"+todayDate+"'";
					try {
						System.out.println(query1);
					PreparedStatement stmt = conn.prepareStatement(query1);
					ResultSet rs = stmt.executeQuery();
					System.out.println(rs.getFetchSize());
					if(rs.next()==false) {
							try {
								PreparedStatement stmt2 = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
								stmt2.setInt(1,employee);
								stmt2.setDate(2, todayDate);
								stmt2.setTime(3, cinTime2);
								stmt2.setTimestamp(4, null);
								stmt2.executeUpdate();
								ResultSet rs1 = stmt2.getGeneratedKeys();
								if(stmt2.getUpdateCount()==1) {
									System.out.println(rs1);
									btnNewButton.setText("Clock-Out");
									JOptionPane.showMessageDialog(null, "You have clocked in at "+todayTime+" on "+today+"");
								}
							}
							catch(Exception e3) {
								System.out.println(e3.getMessage());
							}
					}
					else{
						System.out.println("entered");
						if(rs.getString("start_time")!=null && rs.getString("end_time")==null) {
							try {
								PreparedStatement stmt3 = conn.prepareStatement(query3, Statement.RETURN_GENERATED_KEYS);
								stmt3.setTime(1, cinTime2);
								stmt3.executeUpdate();
								ResultSet rs2 = stmt3.getGeneratedKeys();
								if(stmt3.getUpdateCount()==1) {
									btnNewButton.setText("Clock-In");
									JOptionPane.showMessageDialog(null, "You have clocked out at "+todayTime+" on "+today+"");
								}
							}
							catch(Exception e3) {
								System.out.println(e3.getMessage());
							}
						}
						else if(rs.getString("start_time")!=null && rs.getString("end_time")!=null) {
							JOptionPane.showMessageDialog(null, "You have clocked in and out today. You cannot clock in again.");
						}
					}
					}
				catch(Exception e2) {
					System.out.println(e2.getMessage());
				}
			}
		});
		btnNewButton.setBackground(SystemColor.textHighlight);
		btnNewButton.setForeground(SystemColor.textHighlightText);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(437, 466, 163, 63);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("USER ID");
		lblNewLabel.setForeground(SystemColor.textHighlight);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(301, 192, 93, 53);
		contentPane.add(lblNewLabel);
		
		JLabel lblDate = new JLabel("DATE");
		lblDate.setForeground(SystemColor.textHighlight);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDate.setBounds(301, 283, 93, 53);
		contentPane.add(lblDate);
		
		textField_2 = new JTextField();
		textField_2.setText(""+todayTime);
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(476, 369, 261, 53);
		contentPane.add(textField_2);
		
		JLabel lblTime = new JLabel("TIME");
		lblTime.setForeground(SystemColor.textHighlight);
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTime.setBounds(301, 369, 93, 53);
		contentPane.add(lblTime);
		
		JLabel lblNewLabel_1 = new JLabel("EMPLOYEE SCHEDULE MANAGEMENT");
		lblNewLabel_1.setForeground(SystemColor.textHighlight);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_1.setBounds(252, 69, 571, 63);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton_1 = new JButton("back");
		btnNewButton_1.addActionListener(new ActionListener() {
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
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_1.setBackground(SystemColor.textHighlight);
		btnNewButton_1.setForeground(SystemColor.textHighlightText);
		btnNewButton_1.setBounds(57, 42, 93, 53);
		contentPane.add(btnNewButton_1);
	}
}
