import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.ComponentOrientation;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.ActionListener;


public class CheckInManagement extends JFrame {
	Connection conn = null;
	private JPanel contentPane;
	private JTable table;
	private JButton button;
	private JLabel lblNewLabel;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem_1;
	private JMenuItem mntmNewMenuItem_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckInManagement frame = new CheckInManagement();
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
	public CheckInManagement() {
		conn = MySqlConnection.getConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1179, 742);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnNewMenu = new JMenu("Account");
		menuBar.add(mnNewMenu);
		menuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		mntmNewMenuItem = new JMenuItem("Logout");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame1 frame = new Frame1();
				frame.setVisible(true);
				dispose();
			}
		});
		
		mntmNewMenuItem_1 = new JMenuItem("Clock-In");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Schedules s = new Schedules();
				s.setVisible(true);
				dispose();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		mntmNewMenuItem_2 = new JMenuItem("Profile");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmpProfilePage emp = new EmpProfilePage();
				emp.setVisible(true);
				dispose();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		mnNewMenu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.textHighlightText);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(72, 129, 1059, 559);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
		        int col = table.getSelectedColumn();
		        	float prices[]=new float[2];
		        	int customer_id = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					String today = dtf.format(LocalDateTime.now());
			        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        java.util.Date date1 = null;
					try {
						date1 = format.parse(today);
						java.sql.Timestamp todayDate = new java.sql.Timestamp(date1.getTime());
						System.out.println(""+todayDate);
						if(table.getModel().getValueAt(row, 6)!="checked-in") {
						String query4 = "insert into check_in_out values(?,?,null)";
						PreparedStatement stmt = conn.prepareStatement(query4, Statement.RETURN_GENERATED_KEYS);
						stmt.setInt(1,customer_id);
						stmt.setTimestamp(2, todayDate);
						int count = stmt.executeUpdate();
						ResultSet rs = stmt.getGeneratedKeys();
						System.out.println(rs.getFetchSize());
						if(count>0) {
							table.getModel().setValueAt("checked-in",row,6);
							table.getModel().setValueAt("check-out", row, 7);
							JOptionPane.showMessageDialog(null, "Customer"+customer_id+" checked in");
						}
						}
						else {
							System.out.println("entered");
							String query4 = "update check_in_out set check_out_date_time = ?";
							PreparedStatement stmt = conn.prepareStatement(query4, Statement.RETURN_GENERATED_KEYS);
							stmt.setTimestamp(1, todayDate);
							int count = stmt.executeUpdate();
							ResultSet rs = stmt.getGeneratedKeys();
							if(count>0) {
								table.getModel().setValueAt("checked-out",row,6);
								table.getModel().setValueAt("check-in", row, 7);
								JOptionPane.showMessageDialog(null, "Customer"+customer_id+" checked out");
							}
							String query5 = "select sum(quantity*food_price) from room_service_order inner join room_service on room_service.item_id = room_service_order.item_id inner join orders on orders.order_id = room_service_order.order_id where customer_id = "+customer_id+"";
							try {
								Statement stmt3 = conn.createStatement();
								ResultSet rs2 = stmt3.executeQuery(query5);
								if(rs2.next()) {
									prices[0] = (float) rs2.getDouble("sum(quantity*food_price)");
								}
								String query6 = "select sum(price) from room_reservation inner join room on room_reservation.room_no = room.room_no inner join reservation on reservation.booking_id = room_reservation.booking_id where customer_id = "+customer_id+"";
								ResultSet rs3 = stmt3.executeQuery(query6);
								if(rs3.next()) {
									prices[1] = (float) rs3.getDouble("sum(price)");
								}
								String query7 = "insert into invoice values(?,?,?,?)";
								PreparedStatement stmts = conn.prepareStatement(query7);
								stmts.setString(1, null);
								stmts.setInt(2, customer_id);
								stmts.setDouble(4, prices[0]+prices[1]);
								stmts.setTimestamp(3,todayDate);
								stmts.execute();		
							}
							catch(Exception e6) {
								System.out.println(e6.getMessage());
							}
						}
					}
				catch(ParseException e5) {
					System.out.println(e5.getMessage());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(table);
		DefaultTableModel model = new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
			},
			new String[] {
				"Customer Id", "Customer Name", "Customer Phone", "Rooms Booked", "Start Date", "End Date", "Check In Status", "Buttons"
			}
		);
		table.setModel(model);
		table.getColumnModel().getColumn(1).setPreferredWidth(92);
		table.getColumnModel().getColumn(2).setPreferredWidth(97);
		table.getColumnModel().getColumn(6).setPreferredWidth(87);
		Statement stmt;
		int n;
			try {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String today = dtf.format(LocalDateTime.now());
		        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        java.util.Date date1 = null;
				try {
					date1 = format.parse(today);
					//System.out.println(todayDate);
					java.sql.Date todayDate = new java.sql.Date(date1.getTime());
					stmt = conn.createStatement();
					String query = "select customer.customer_id, full_name, start_date_time, end_date_time, room_no from customer inner join reservation on customer.customer_id = reservation.customer_id inner join room_reservation on reservation.booking_id = room_reservation.booking_id where CAST(start_date_time as DATE)='"+todayDate.toLocalDate()+"'";
					ResultSet rs = stmt.executeQuery(query);
					int k = 0;
					while(rs.next()) {
						table.getModel().setValueAt(rs.getInt("customer.customer_id"), k, 0);
						table.getModel().setValueAt(rs.getString("full_name"), k, 1);
						table.getModel().setValueAt(rs.getDate("start_date_time"), k, 4);
						table.getModel().setValueAt(rs.getDate("end_date_time"), k, 5);
						table.getModel().setValueAt(rs.getInt("room_no"), k, 3);
						k++;
					}
					for(int i = 0;i<table.getModel().getRowCount();i++) {
						if(table.getModel().getValueAt(i, 0)!=null) {
								System.out.println("Hey Im here");
								System.out.println(table.getModel().getValueAt(i, 0));
								String query2 = "select phone_number from customer_phone where customer_id = "+Integer.parseInt(table.getModel().getValueAt(i, 0).toString())+"";
								ResultSet rs2 = stmt.executeQuery(query2);
								while(rs2.next()) {
									System.out.println(rs2.getString("phone_number"));
									table.getModel().setValueAt(rs2.getString("phone_number"), i, 2);
								}
								String query3 = "select check_in_date_time, check_out_date_time from check_in_out where customer_id = "+Integer.parseInt(table.getModel().getValueAt(i, 0).toString())+"";
								ResultSet rs3 = stmt.executeQuery(query3);
								if(rs3.getFetchSize()==0) {
									table.getModel().setValueAt("not checked-in",i,6);
									table.getModel().setValueAt("check-in", i, 7);
								}
								else {
								while(rs3.next()) {
									System.out.println(rs3.getDate("check_in_date_time"));
									System.out.println(rs3.getDate("check_out_date_time"));
									if(rs3.getDate("check_in_date_time") != null && rs3.getDate("check_out_date_time") == null) {
										table.getModel().setValueAt("checked-in",i,6);
										table.getModel().setValueAt("check-out", i, 7);
									}
									else if(rs3.getDate("check_in_date_time") != null && rs3.getDate("check_out_date_time") != null) {
										table.getModel().setValueAt("checked-out",i,6);
										table.getModel().setValueAt("check-in", i, 7);
									}
								}
								}		
						}
					}
					Action checkin = new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							}
					};
					for(n =0;n<model.getRowCount();n++) {
						if(model.getValueAt(n, 0)==null) {
							break;
						}
					}
					model.setRowCount(n);
					ButtonColumn buttonColumn = new ButtonColumn(table, checkin, 7);
					buttonColumn.setMnemonic(KeyEvent.VK_D);
				}	
			catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					}

				catch (ParseException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
		table.setRowHeight(35);
		
		button = new JButton("New button");
		scrollPane.setColumnHeaderView(button);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.textHighlight);
		panel.setBounds(345, 51, 573, 55);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("CUSTOMER CHECK-IN MANAGEMENT");
		lblNewLabel_1.setForeground(SystemColor.text);
		lblNewLabel_1.setFont(new Font("Lucida Sans", Font.BOLD, 28));
		lblNewLabel_1.setBounds(10, 11, 553, 33);
		panel.add(lblNewLabel_1);
		
		lblNewLabel = new JLabel("CUSTOMER CHECK IN MANAGEMENT");
		lblNewLabel.setBounds(482, 65, 474, 41);
		contentPane.add(lblNewLabel);
		lblNewLabel.setForeground(SystemColor.textHighlightText);
		lblNewLabel.setBackground(new Color(0, 120, 215));
		lblNewLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 25));
	}
}
