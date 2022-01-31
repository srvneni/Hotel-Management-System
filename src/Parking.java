import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.JSpinner;
import java.awt.Font;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.ComponentOrientation;
import javax.swing.JLabel;
public class Parking extends JFrame {

	private JPanel contentPane;
	Connection conn = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Parking frame = new Parking();
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
	public Parking() {
		conn = MySqlConnection.getConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1187, 736);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Account");
		menuBar.add(mnNewMenu);
		menuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		JMenuItem mntmNewMenuItem = new JMenuItem("Logout");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame1 frame = new Frame1();
				frame.setVisible(true);
				dispose();
			}
		});
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Profile");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProfilePage pp = new ProfilePage();
				pp.setVisible(true);
				dispose();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		mnNewMenu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.textHighlightText);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JDateChooser pdateChooser = new JDateChooser();
		pdateChooser.setBounds(381, 221, 377, 56);
		contentPane.add(pdateChooser);
		
		JButton bookParkingSlots = new JButton("Book Parking Slots");
		bookParkingSlots.setForeground(SystemColor.textHighlightText);
		bookParkingSlots.setBackground(SystemColor.textHighlight);
		bookParkingSlots.setBounds(492, 535, 144, 56);
		contentPane.add(bookParkingSlots);
		
		JSpinner pSpinner = new JSpinner();
		pSpinner.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		pSpinner.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pSpinner.setBounds(381, 438, 377, 56);
		contentPane.add(pSpinner);
		
		JDateChooser pdateChooser_1 = new JDateChooser();
		pdateChooser_1.setBounds(381, 333, 377, 56);
		contentPane.add(pdateChooser_1);
		
		JButton btnNewButton = new JButton("back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainPage mp = new MainPage();
				mp.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton.setBackground(SystemColor.textHighlight);
		btnNewButton.setForeground(SystemColor.textHighlightText);
		btnNewButton.setBounds(59, 34, 109, 64);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Book Parking");
		lblNewLabel.setForeground(SystemColor.textHighlight);
		lblNewLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 30));
		lblNewLabel.setBounds(431, 63, 327, 73);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Start Date");
		lblNewLabel_1.setBounds(381, 191, 109, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("End Date");
		lblNewLabel_2.setBounds(381, 308, 84, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("No of slots required");
		lblNewLabel_2_1.setBounds(381, 415, 144, 14);
		contentPane.add(lblNewLabel_2_1);
		bookParkingSlots.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String formattedEndDate = "";
				String formattedStartDate = "";
				if(pdateChooser.getDate()==null || pdateChooser_1.getDate()==null) {
					System.out.println("data invalid");
				}
				else {
					formattedStartDate = String.valueOf(sdf.format(pdateChooser.getDate()));
					formattedEndDate = String.valueOf(sdf.format(pdateChooser_1.getDate()));
					System.out.println(formattedStartDate);
					System.out.println(formattedEndDate);
				UserInfo user = new UserInfo();
				int customer = user.getUserId();
				System.out.println(customer);
				System.out.println("customer:"+customer);
				java.util.Date date1 = null;
				java.util.Date date2 = null;
				try {
					date1 = sdf.parse(formattedStartDate);
					date2 = sdf.parse(formattedEndDate);
				}
				catch (ParseException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				java.sql.Date start_date_time = new java.sql.Date(date1.getTime());
				java.sql.Date end_date_time = new java.sql.Date(date2.getTime());
				
				try {
					int[] last_inserted_id = new int[1];
					int count = 0;
					try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					 String query4 = "select count(parking_slot_no) from parking_slots where parking_slot_no not in (select parking_slot_no from book_parking_slots inner join booking where book_parking_slots.booking_id = booking.booking_id and '"+start_date_time+"' between start_date_time and end_date_time or '"+end_date_time+"' between start_date_time and end_date_time or start_date_time between '"+start_date_time+"' and '"+end_date_time+"'or end_date_time between '"+start_date_time+"' and '"+end_date_time+"')";
				  	 String query =  "insert into booking values(?,?,?,?)";
				  	String query3 =  "insert into book_parking_slots values(?,?)";
						Statement stmt6 = conn.createStatement();
						ResultSet rs6 = stmt6.executeQuery(query4);
						if(rs6.next()) {
							count  = rs6.getInt("count(parking_slot_no)");
						}
						try {
						    pSpinner.commitEdit();
						} catch ( java.text.ParseException e8 ) {
						 }
						System.out.println(count);
						if(count>=Integer.parseInt(pSpinner.getValue().toString())) {
						 PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
					  	 stmt.setString(1, null);
					  	 stmt.setInt (2, customer);
					     stmt.setDate(3, start_date_time);
					     stmt.setDate(4, end_date_time);
					     stmt.execute();
					  	 ResultSet rs = stmt.getGeneratedKeys();
						  	 if(rs.next())
			                 {
			                    last_inserted_id[0] = rs.getInt(1);
				                System.out.println(last_inserted_id[0]);
                			 	JOptionPane.showMessageDialog(null, "booking successfully made.");
			                 }
			                 int parking[] = new int[10];
			                 Statement stmt2 = conn.createStatement();
			                	 String query2 = "select parking_slot_no from parking_slots where parking_slot_no not in (select parking_slot_no from book_parking_slots inner join booking where book_parking_slots.booking_id = booking.booking_id and '"+start_date_time+"' between start_date_time and end_date_time or '"+end_date_time+"' between start_date_time and end_date_time or start_date_time between '"+start_date_time+"' and '"+end_date_time+"'or end_date_time between '"+start_date_time+"' and '"+end_date_time+"')";
			                	 try {
			                		 ResultSet rs2 = stmt2.executeQuery(query2);
			                		 int k = 0;
			                		 try {
			 						    pSpinner.commitEdit();
			 						} catch ( java.text.ParseException e9 ) {
			 						 }
			                		 while(rs2.next() && k<Integer.parseInt(pSpinner.getValue().toString())){
			                			parking[k]=rs2.getInt("parking_slot_no");
			                			 	try {
			     		                	 PreparedStatement stmt3 = conn.prepareStatement(query3);
			     		                	 System.out.println(stmt);
			     		                	 stmt3.setInt(1, last_inserted_id[0]);
			     						  	 stmt3.setInt(2, rs2.getInt(1));
			     						  	 stmt3.executeUpdate();
				                			 k+=1;
			                			 	}
			                			 	finally{
			                			 		
			                			 	}
			                	 }
			                	 }
			                	 finally{
			                	 }
		                 }
						else 
						{
							JOptionPane.showMessageDialog(null,"Sorry. We don't have enough parking slots");
						}
						}
		catch(Exception e2) {
					System.out.println(e2.getMessage());
				}
				}
		finally {
			
		}
				}
			}
		});
	}
}
