import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Hashtable;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import javax.swing.JFormattedTextField;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.beans.PropertyChangeEvent;
import javax.swing.JToggleButton;
import javax.swing.SpinnerDateModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import java.awt.TextField;
import java.awt.Button;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.ToolTipManager;
import javax.swing.JLabel;
import java.awt.SystemColor;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JScrollPane;
import java.awt.Insets;
import javax.swing.JMenuItem;
import java.awt.ComponentOrientation;
import javax.swing.JMenu;
public class BookRoom extends JFrame {

	private JPanel contentPane;
	private JTable table;
	Timestamp start_date_time;
	Timestamp end_date_time;
	Connection conn = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookRoom frame = new BookRoom();
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
	public BookRoom() {
		Connection conn = MySqlConnection.getConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int bookedRooms[] = new int[100];
		setBounds(100, 100, 1139, 742);
		contentPane = new JPanel();
		String[] dates = new String[2];
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Account");
		setJMenuBar(menuBar);
		menuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Profile");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProfilePage pp = new ProfilePage();
				pp.setVisible(true);
				dispose();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Logout");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame1 frame = new Frame1();
				frame.setVisible(true);
				dispose();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		contentPane.setBackground(SystemColor.text);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		int l = 0;
		Hashtable<String, Integer> combos = new Hashtable<String, Integer>();
		String data2[][] = new String[7][2];
		String data[][] = new String[7][2]; 
		String data3[][] = new String[7][2]; 
		JComboBox comboBox = new JComboBox();
		comboBox.setToolTipText("");
		comboBox.setBounds(43, 414, 180, 22);
		contentPane.add(comboBox);
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(265, 414, 62, 22);
		contentPane.add(comboBox_1);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 0);
		SpinnerDateModel timeModel = new SpinnerDateModel();
		timeModel.setValue(calendar.getTime());
		JSpinner spinner = new JSpinner(timeModel);
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "hh:mm a");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false); // this makes what you want
        formatter.setOverwriteMode(true);

        spinner.setEditor(editor);
		spinner.setBounds(354, 142, 151, 44);
		contentPane.add(spinner);
		
		JSpinner spinner_1 = new JSpinner(timeModel);
		JSpinner.DateEditor editor1 = new JSpinner.DateEditor(spinner_1, "hh:mm a");
        DateFormatter formatter_1 = (DateFormatter)editor1.getTextField().getFormatter();
        formatter_1.setAllowsInvalid(false); // this makes what you want
        formatter_1.setOverwriteMode(true);
        spinner_1.setEditor(editor1);
		spinner_1.setBounds(354, 234, 151, 44);
		contentPane.add(spinner_1);
		
		
		

		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				String formattedEndDate = "";
				if(dateChooser_1.getDate()==null) {
					System.out.println("data invalid");
				}
				else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					formattedEndDate = String.valueOf(sdf.format(dateChooser_1.getDate()));
				}
				dates[0]= formattedEndDate+" ";
			}
		});
		dateChooser_1.setBounds(45, 234, 299, 44);
		contentPane.add(dateChooser_1);
		
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(43, 142, 299, 44);
		contentPane.add(dateChooser);
		
		String column[]={"Room Type","Available Quantity"};  
		data2[0][0]= "Room_Type";
		data2[0][1]="Required Quantity";
		DefaultTableModel model = new DefaultTableModel(data2, column);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(540, 125, 549, 180);
		contentPane.add(scrollPane);
		table = new JTable(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"room_type", "Available Quantity"
			}
		));
		scrollPane.setViewportView(table);
		table.setFont(new Font("Tahoma", Font.PLAIN, 20));
		table.setRowHeight(30);
		
		Button addbutton = new Button("+");
		addbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			}
		});
		addbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("function called");
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				for(int m=0;m<7;m++) {
					if(m==6 && model.getValueAt(m, 0)!=null && model.getValueAt(m, 1)!=null) { 
						System.out.println(model.getValueAt(m, 0));
						JOptionPane.showMessageDialog(null, "Cannot add More Items into your booking.");
					}
					else if(model.getValueAt(m, 0)==null && model.getValueAt(m, 1)==null){
							if(checkIfElementInTable(data2, model, comboBox.getSelectedItem())==-1) {
							data2[m][0] = comboBox.getSelectedItem().toString();
							data2[m][1] = comboBox_1.getSelectedItem().toString();
							System.out.println("for"+m+": value is "+data2[m][0]+" and "+data2[m][1]+".");
							model.setValueAt(data2[m][0], m, 0);
							model.setValueAt(data2[m][1], m, 1);
							break;
							}
							else {
								int index = checkIfElementInTable(data2, model, comboBox.getSelectedItem());
								data2[index][0] = comboBox.getSelectedItem().toString();
								data2[index][1] = comboBox_1.getSelectedItem().toString();
								System.out.println("for"+m+": value is "+data2[index][0]+" and "+data2[index][1]+".");
								model.setValueAt(data2[index][0], index, 0);
								model.setValueAt(data2[index][1], index, 1);
								break;
							}
						}
					}
				
			}
		});
		addbutton.setBackground(Color.LIGHT_GRAY);
		addbutton.setForeground(Color.BLACK);
		addbutton.setBounds(362, 407, 41, 29);
		contentPane.add(addbutton);
		
		
		JLabel lblNewLabel = new JLabel("Make a Booking Today!");
		lblNewLabel.setForeground(SystemColor.textHighlightText);
		lblNewLabel.setBackground(SystemColor.textHighlight);
		lblNewLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
		lblNewLabel.setBounds(416, 44, 410, 56);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Check Available Rooms");
		btnNewButton.setToolTipText("Click to see available rooms.");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				combos.clear();
				comboBox.removeAllItems();
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		        String today = dtf.format(LocalDateTime.now());
		        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String formattedStartDate = sdf.format(dateChooser.getDate());
				System.out.println(formattedStartDate);
				
				String formattedEndDate = sdf.format(dateChooser_1.getDate());
				System.out.println(formattedEndDate);
				
				SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
				String startTime = sdf2.format(spinner.getValue());
				System.out.println(startTime);
				String endTime = sdf2.format(spinner.getValue());
				System.out.println(startTime);
				String start_date = formattedStartDate+ " "+startTime;
				String end_date = formattedEndDate+ " "+endTime;
		        java.util.Date date1 = null;
		        java.util.Date date2 = null;
				java.util.Date date3 = null;
				try {
					date1 = format.parse(today);
					date2 = format.parse(start_date);
					date3 = format.parse(end_date);
					System.out.println(date3);
				} catch (ParseException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				System.out.println("here2"+date2);
				start_date_time = new java.sql.Timestamp(date2.getTime());
				end_date_time = new java.sql.Timestamp(date3.getTime());
				System.out.println("here this"+start_date_time);
				System.out.println("here this"+end_date_time);
				if (date2.compareTo(date1) <= 0 || date3.compareTo(date2)<=0) {
		            System.out.println("earlier");
		            JOptionPane.showMessageDialog(null, "Error in providing date.");
		        }
				else {
				comboBox.setVisible(true);
				comboBox_1.setVisible(true);
				addbutton.setVisible(true);
				try {
					int i = 1;
				  	 String query =  "select room_type, count(room_type) as CountOf from room where room_no not in (select room_no from room_reservation where booking_id in (select booking_id from reservation where '"+start_date_time+"' between start_date_time and end_date_time or '"+end_date_time+"' between start_date_time and end_date_time or start_date_time between '"+start_date_time+"' and '"+end_date_time+"' or end_date_time between '"+start_date_time+"' and '"+end_date_time+"'))GROUP BY room_type";		
				  	 System.out.println(query);
							  	Statement stmt = conn.createStatement();
							  	ResultSet rs = stmt.executeQuery(query);
						  		while(rs.next()){ 
						  			combos.put(String.valueOf(rs.getString("room_type")),rs.getInt("CountOf"));
						  			comboBox.addItem(String.valueOf(rs.getString("room_type")));
						  		}
						  		System.out.println(combos);
						  		System.out.println(comboBox.getItemCount());

					  
				}
				catch(Exception e2) {
							System.out.println(e2.getMessage());
				}
			}
			}
			});
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox_1.removeAllItems();
				String required = comboBox.getSelectedItem().toString();
		  		Integer available = combos.get(required);
		  		for (int k = 1; k <= available; k++) {
		  			comboBox_1.addItem(k);
		  		}
			}
		});
		JButton bookButton = new JButton("Book Room(s)");
		bookButton.setToolTipText("Click to check for available rooms for selected dates.");
		bookButton.setBackground(SystemColor.textHighlight);
		bookButton.setForeground(SystemColor.textHighlightText);
		bookButton.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
		bookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new Frame3();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				UserInfo user = new UserInfo();
				int customer = user.getUserId();
				System.out.println("customer:"+customer);
				java.util.Date date1 = null;
				java.util.Date date2 = null;
				try {
					int last_inserted_id = 0;
				  	 String query =  "insert into reservation values(?,?,?,?)";
				  	String query3 =  "insert into room_reservation values(?,?)";
						 PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
					  	 stmt.setString(1, null);
					  	 stmt.setInt(2, customer);
					     stmt.setTimestamp(3, start_date_time);
					     stmt.setTimestamp(4, end_date_time);
					     stmt.execute();
					  	 ResultSet rs = stmt.getGeneratedKeys();
					  	if(rs.next())
		                 {
		                    last_inserted_id = rs.getInt(1);
			                System.out.println(last_inserted_id);
		                 }
		                 int rooms = 0;
		                 try {
		                 Statement stmt2 = conn.createStatement();
		                 for(int j=0;j<=7;j++) {
		                	 System.out.println(table.getModel().getValueAt(j,0).toString());
		                	 if(table.getModel().getValueAt(j,0).toString()!=null) {
		                	 String query2 = "select * from room where room_type = '"+table.getModel().getValueAt(j,0).toString()+"' and room_no not in (select room_no from room_reservation where booking_id in (select booking_id from reservation where '"+start_date_time+"' between start_date_time and end_date_time or '"+end_date_time+"' between start_date_time and end_date_time))";
		                	 try {
		                		 ResultSet rs2 = stmt2.executeQuery(query2);
		                		 int k = 0;
		                		 while(rs2.next()&& k<Integer.parseInt(table.getModel().getValueAt(j,1).toString())){
		                			 rooms=rs2.getInt("room_no");
		                			 bookedRooms[k] = rooms;
		                			 	try {
		     		                	 PreparedStatement stmt3 = conn.prepareStatement(query3);
		     		                	 System.out.println(stmt);
		     		                	 stmt3.setInt(1, last_inserted_id);
		     						  	 stmt3.setInt(2, rooms);
		     						  	 stmt3.executeUpdate();
			                			 k+=1;
		                			 	}
		                			 	finally{
		                			 		}
		                			 		
		                		 }
		                		 JOptionPane.showMessageDialog(null,"Booking Successfully made");
		                	 }
		                	 catch(Exception mainex) {
		                		 System.out.println(mainex.getMessage());
		                	 }
		                	 }
		                	 }
		                 }
		                	 finally{
		                		System.out.println("executed well");
		                	 }
		               
		                }
		catch(Exception e2) {
					System.out.println(e2.getMessage());
				}
			}
		});
		bookButton.setBounds(440, 486, 221, 44);
		contentPane.add(bookButton);
		btnNewButton.setForeground(SystemColor.textHighlightText);
		btnNewButton.setBackground(SystemColor.textHighlight);
		btnNewButton.setBounds(104, 333, 173, 29);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainPage mp = new MainPage();
				mp.setVisible(true);
			}
		});
		btnNewButton_1.setFont(new Font("Lucida Sans", Font.BOLD, 20));
		btnNewButton_1.setForeground(SystemColor.textHighlightText);
		btnNewButton_1.setBackground(SystemColor.textHighlight);
		btnNewButton_1.setBounds(28, 29, 100, 56);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_1 = new JLabel("Start Date");
		lblNewLabel_1.setBounds(43, 117, 110, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("End Date");
		lblNewLabel_1_1.setBounds(43, 209, 110, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("Check-In time");
		lblNewLabel_2.setBounds(358, 117, 85, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Check-Out time");
		lblNewLabel_2_1.setBounds(358, 209, 85, 14);
		contentPane.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_3 = new JLabel("Add Selection");
		lblNewLabel_3.setBounds(364, 388, 110, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Rooms Available");
		lblNewLabel_4.setBounds(43, 389, 121, 14);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Quantity");
		lblNewLabel_5.setBounds(265, 388, 47, 14);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("BOOK ROOMS");
		lblNewLabel_6.setForeground(SystemColor.textHighlight);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_6.setBounds(427, 11, 192, 65);
		contentPane.add(lblNewLabel_6);
		}
	public int checkIfElementInTable(String[][] data2,DefaultTableModel model, Object obj) {
		int index = -1;
		for(int j = 0;j<data2.length; j++) {
			if(data2[j][0]==obj.toString()) {
				index = j;
				break;
			}
		}
		return index;
	}
}

