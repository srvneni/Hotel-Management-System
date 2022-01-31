import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DateFormatter;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.sql.Timestamp;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.JLabel;
public class HouseKeeping extends JFrame {

	private static String getQuery;
	private JPanel contentPane;
	Connection conn = null;
	int last_inserted_id;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HouseKeeping frame = new HouseKeeping();
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
	public HouseKeeping() {
	    java.sql.Timestamp date;
	    String getQuery;
		conn = MySqlConnection.getConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1189, 743);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		JMenu mnNewMenu = new JMenu("Account");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Logout");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame1 frame = new Frame1();
				frame.setVisible(true);
				dispose();
			}
		});
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Profile");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProfilePage pp = new ProfilePage();
				pp.setVisible(true);
				dispose();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		mnNewMenu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(332, 318, 420, 46);
		contentPane.add(comboBox);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(332, 206, 217, 46);
		contentPane.add(dateChooser);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 0);
		SpinnerDateModel timeModel = new SpinnerDateModel();
		timeModel.setValue(calendar.getTime());
		JSpinner spinner = new JSpinner(timeModel);
		spinner.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				comboBox.removeAllItems();
				getData(spinner, dateChooser, comboBox);
			}
		});
		Date start_date, end_date;
		try {
			UserInfo user = new UserInfo();
			int customer  = user.getUserId();
			String checkQuery = "select start_date_time, end_date_time from reservation where customer = "+customer+"";
			PreparedStatement pstmt = conn.prepareStatement(checkQuery);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				start_date = rs.getDate("start_date_time");
				end_date = rs.getDate("end_date_time");
			}
		}
		catch(Exception e6) {
			System.out.println(e6.getMessage());
		}
		spinner.setBounds(602, 206, 150, 46);
		contentPane.add(spinner);
		timeModel.setValue(calendar.getTime());		
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "hh:mm a");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false); // this makes what you want
        formatter.setOverwriteMode(true);
        spinner.setEditor(editor);
		dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				comboBox.removeAllItems();
				getData(spinner, dateChooser, comboBox);
			}
		});
		String query;
        JButton btnNewButton = new JButton("Request House Keeping");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
    			UserInfo user = new UserInfo();
    			int customer = user.getUserId();
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		//SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        		String formattedDate = "";
        		java.util.Date date1;
        		java.util.Date time;
        		java.sql.Timestamp date3;
        		if(dateChooser.getDate()==null) {
        			System.out.println("data invalid");
        		}
        		else {
        		

	        			Date spinnerValue = (Date) spinner.getValue();
	        			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
	        			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy/MMM/dd");
	        			SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss");
	        			SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM-dd");
						time = sdf2.parse(sdf2.format(sdf4.parse(sdf3.format(new Date()) + " " + sdf2.format(spinnerValue), new ParsePosition(0)))+" ", new ParsePosition(0));
	        			formattedDate = String.valueOf(sdf5.format(dateChooser.getDate()));
	        			date1 = sdf5.parse(formattedDate+" ", new ParsePosition(0));
	        			System.out.println("expected:"+date1);
	        			java.sql.Time reqtime = new java.sql.Time(time.getHours(),time.getMinutes(),time.getSeconds());
	        			java.sql.Date date2 = new java.sql.Date(date1.getTime());
	        			System.out.println("get"+reqtime);
	        			String query = "insert into house_keeping values(?,?,?,?,?)";
	        			String query2 = "insert into views_and_completes values(?,?,?)";
	        			if(comboBox.getItemCount()==0) {
	        				JOptionPane.showMessageDialog(null, "You don't have a reservation to make house keeping request. Please make a booking before you request house keeping.");
	        				MainPage mp = new MainPage();
	        				mp.setVisible(true);
	        				dispose();
	        			}
	        			else {
		        		try {
		        			PreparedStatement stmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
		            		stmt.setString(1, null);
							stmt.setInt(2, customer);
							stmt.setInt(3,Integer.parseInt(comboBox.getSelectedItem().toString()));
							stmt.setDate(4, date2);
							stmt.setTime(5, reqtime);
							stmt.executeUpdate();
							ResultSet rs = stmt.getGeneratedKeys();
						  	if(rs.next())
			                 {
			                    last_inserted_id = rs.getInt(1);
				                System.out.println(last_inserted_id);
			                 }
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		        		try {
		        			PreparedStatement stmt2 = conn.prepareStatement(query2,Statement.RETURN_GENERATED_KEYS);
		            		stmt2.setString(1, null);
							stmt2.setInt(2, last_inserted_id);
							stmt2.setString(3,"incomplete");
							int count = stmt2.executeUpdate();
							ResultSet rs2 = stmt2.getGeneratedKeys();
						  	if(count>0)
			                 {
				                JOptionPane.showMessageDialog(null, "Request Successfully made.");
		        		}	
		        		}
		        		catch(Exception e3) {
		        			System.out.println(e3.getMessage());
		        		}
		        		}
        		}
        	}
        });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNewButton.setForeground(SystemColor.textHighlightText);
        btnNewButton.setBackground(SystemColor.textHighlight);
        btnNewButton.setBounds(418, 415, 267, 74);
        contentPane.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("back");
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		MainPage mp = new MainPage();
        		mp.setVisible(true);
        	}
        });
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnNewButton_1.setBackground(SystemColor.textHighlight);
        btnNewButton_1.setForeground(Color.WHITE);
        btnNewButton_1.setBounds(45, 37, 99, 64);
        contentPane.add(btnNewButton_1);
        
        JLabel lblNewLabel = new JLabel("REQUEST HOUSE KEEPING");
        lblNewLabel.setForeground(new Color(0, 120, 215));
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        lblNewLabel.setBounds(369, 55, 383, 64);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Request Date");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1.setBounds(332, 174, 217, 21);
        contentPane.add(lblNewLabel_1);
        
        JLabel lblNewLabel_1_1 = new JLabel("Request Time");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1_1.setBounds(603, 174, 217, 21);
        contentPane.add(lblNewLabel_1_1);
        
        JLabel lblNewLabel_1_2 = new JLabel("Choose a room no.");
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1_2.setBounds(332, 286, 217, 21);
        contentPane.add(lblNewLabel_1_2);
   
	}
	void getData(JSpinner spinner, JDateChooser dateChooser, JComboBox comboBox) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		String formattedDate = "";
		java.util.Date date1;
		java.sql.Timestamp date3;
		if(dateChooser.getDate()==null) {
			System.out.println("data invalid");
		}
		else {
			Date spinnerValue = (Date) spinner.getValue();
			System.out.println("this1:"+spinnerValue);
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy/MMM/dd");
			SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss");
			SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM-dd");
			String actualDate = sdf2.format(sdf4.parse(sdf3.format(new Date()) + " " + sdf2.format(spinnerValue), new ParsePosition(0)));
			formattedDate = String.valueOf(sdf5.format(dateChooser.getDate()));
			UserInfo user = new UserInfo();
			int customer = user.getUserId();
		System.out.println(customer);
		System.out.println("customer:"+customer);
			date1 = sdf.parse(formattedDate+" "+actualDate, new ParsePosition(0));
			System.out.println("expected:"+date1);
			java.sql.Timestamp date2 = new java.sql.Timestamp(date1.getTime());
			System.out.println("get"+date2);
			date3 = date2;
			String query = "select room_no from room_reservation inner join reservation on room_reservation.booking_id = reservation.booking_id and '"+date3+"' between reservation.start_date_time and end_date_time and customer_id = "+customer+"";
			Statement stmt2;
			try {
				System.out.println("this:"+query);
				stmt2 = conn.createStatement();
				System.out.println(stmt2);
				ResultSet rs2 = stmt2.executeQuery(query);
				System.out.println(rs2);
					while(rs2.next()) {
						System.out.println(rs2.getInt("room_no"));
			  			comboBox.addItem(rs2.getInt("room_no"));
		  		}
			}
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//date = date.valueOf(formattedDate+" "+actualDate);
	}
	}
}
