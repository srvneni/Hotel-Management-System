import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JCheckBoxMenuItem;
import java.awt.Choice;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.border.MatteBorder;
import com.toedter.components.JSpinField;
import javax.swing.JSpinner;
import java.awt.Font;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.SpinnerNumberModel;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.DefaultComboBoxModel;
public class RoomService extends JFrame {
	Connection conn = null;
	protected static final int CONSTANT = 20;
	private JPanel contentPane;
	private JTable foodTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RoomService frame = new RoomService();
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
	public RoomService() {
		conn = MySqlConnection.getConnection();
		int rooms[] = new int[35];
		String data2[][] = new String[11][3];
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1188, 743);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		Hashtable<String, Float> combos = new Hashtable<String, Float>();
		Hashtable<String, Integer> combos2 = new Hashtable<String, Integer>();
		
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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		int i =0;
		JComboBox foodListBox = new JComboBox();
		foodListBox.setSize(278, 52);
		foodListBox.setLocation(30, 275);
		foodListBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		foodListBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String item = foodListBox.getSelectedItem().toString();
		        foodListBox.setBounds(30, 275, 278, 52);
			}
		});

			UserInfo user = new UserInfo();
			int customer = user.getUserId();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String today = dtf.format(LocalDateTime.now());
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        java.util.Date date1 = null;
			try {
				date1 = format.parse(today);
			} catch (ParseException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			java.sql.Date today_date_time = new java.sql.Date(date1.getTime());
		System.out.println(date1);
		Statement stmt;
		String query = "select * from room_service";
		String query2 = "select room_no from room_reservation inner join reservation on room_reservation.booking_id = reservation.booking_id and '"+today_date_time+"' between reservation.start_date_time and end_date_time and customer_id = "+customer+"";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
	  			System.out.println(rs.getString("food_name"));
	  			combos2.put(rs.getString("food_name"), rs.getInt("item_id"));
	  			System.out.println(combos2.keySet());
	  			combos.put(rs.getString("food_name"),rs.getFloat("food_price"));
	  			data2[i][2]=String.valueOf(rs.getFloat("food_price"));
	  			foodListBox.addItem(String.valueOf(rs.getString("food_name")));
	  			i++;
	  		}
		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		contentPane.add(foodListBox);
		
		JButton addButton = new JButton("Add to List");
		addButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addButton.setForeground(SystemColor.textHighlightText);
		addButton.setBackground(SystemColor.textHighlight);
		addButton.setBounds(409, 411, 130, 46);
		contentPane.add(addButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(583, 100, 538, 469);
		contentPane.add(scrollPane);
		
		foodTable = new JTable();
		scrollPane.setViewportView(foodTable);
		foodTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		foodTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Food Name", "Quantity", "Price"
			}
		));
		foodTable.setRowHeight(40);
		
	
		
		JSpinner quantitySpinner = new JSpinner();
		quantitySpinner.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		quantitySpinner.setFont(new Font("Tahoma", Font.BOLD, 15));
		quantitySpinner.setBounds(449, 276, 45, 52);
		contentPane.add(quantitySpinner);
		
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DefaultTableModel model = (DefaultTableModel)foodTable.getModel();
				for(int m=0;m<foodTable.getModel().getRowCount();m++) {
					if(m==foodTable.getModel().getRowCount() && model.getValueAt(m, 0)!=null && model.getValueAt(m, 1)!=null) { 
						System.out.println(model.getValueAt(m, 0));
						JOptionPane.showMessageDialog(null, "Cannot add More Items into your Cart.");
					}
					else if(model.getValueAt(m, 0)==null && model.getValueAt(m, 1)==null){
							if(checkIfElementInTable(data2, model, foodListBox.getSelectedItem())==-1) {
							data2[m][0] = foodListBox.getSelectedItem().toString();
							data2[m][1] = quantitySpinner.getModel().getValue().toString();
							data2[m][2] = String.valueOf((combos.get(data2[m][0]))*Integer.parseInt(data2[m][1]));
							System.out.println("for"+m+": value is "+data2[m][0]+" and "+data2[m][1]+".");
							model.setValueAt(data2[m][0], m, 0);
							model.setValueAt(data2[m][1], m, 1);
							model.setValueAt(data2[m][2], m, 2);
							break;
							}
							else {
								int index = checkIfElementInTable(data2, model, foodListBox.getSelectedItem());
								data2[index][0] = foodListBox.getSelectedItem().toString();
								data2[index][1] = quantitySpinner.getModel().getValue().toString();
								data2[index][2] = String.valueOf((combos.get(data2[m][0]))*Integer.parseInt(data2[m][1]));
								System.out.println("for"+m+": value is "+data2[index][0]+" and "+data2[index][1]+".");
								model.setValueAt(data2[index][0], index, 0);
								model.setValueAt(data2[index][1], index, 1);
								model.setValueAt(data2[index][2], index, 2);
								break;
							}
						}
					}
			}
		});
			
		JLabel lblNewLabel = new JLabel("Order Room Service");
		lblNewLabel.setForeground(SystemColor.textHighlight);
		lblNewLabel.setFont(new Font("Lucida Sans", Font.BOLD, 35));
		lblNewLabel.setBounds(102, 67, 392, 101);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Quantity");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(449, 246, 90, 19);
		contentPane.add(lblNewLabel_2);
		
		JComboBox roomBox = new JComboBox();
		roomBox.setBounds(30, 405, 278, 52);
		Statement stmt2;
			try {
				System.out.println("entered");
				stmt2 = conn.createStatement();
				System.out.println(stmt2);
				ResultSet rs2 = stmt2.executeQuery(query2);
				System.out.println(rs2);
					while(rs2.next()) {
			  			roomBox.addItem(rs2.getInt("room_no"));
		  		}
			}
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		contentPane.add(roomBox);
		
		JButton placeOrderButton = new JButton("Place Order");
		placeOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query2 = "";
				int last_inserted_id = 0;
				try {
					if(roomBox.getItemCount()==0) {
						JOptionPane.showMessageDialog(null, "You need to have a reservation at hotel to order roomservice. Please make a reservation before you order room service.");
						MainPage mp = new MainPage();
						mp.setVisible(true);
						dispose();
					}
					else {
					int room_no=Integer.parseInt(roomBox.getSelectedItem().toString());
				  	 String query = "insert into orders values(null, "+customer+", "+room_no+")";
							  	 PreparedStatement stmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
							  	 stmt.execute();
							  	 ResultSet rs = stmt.getGeneratedKeys();
						  		if(rs.next()) {
						  			last_inserted_id = rs.getInt(1);
						  		}
						  		for(int i=0;i<foodTable.getModel().getRowCount();i++) {
						  			if(foodTable.getModel().getValueAt(i, 0)!=null) {
						  				System.out.println(combos2.get(foodTable.getModel().getValueAt(i, 0).toString()));
						  		 query2 = "insert into room_service_order values("+last_inserted_id+", '"+combos2.get(foodTable.getModel().getValueAt(i, 0).toString())+"', "+foodTable.getModel().getValueAt(i, 1)+")";
						  		PreparedStatement stmt2 = conn.prepareStatement(query2);
						  		stmt2.execute(query2);
						  			}
						  		}
						  		JOptionPane.showMessageDialog(null, "Booking Successfully made.");
						  		conn.close();
				}
				}
				catch(Exception e2) {
							System.out.println(e2.getMessage());
				}
			
			}
		});
		placeOrderButton.setForeground(SystemColor.textHighlightText);
		placeOrderButton.setBackground(SystemColor.textHighlight);
		placeOrderButton.setBounds(743, 580, 149, 46);
		contentPane.add(placeOrderButton);
		JLabel lblNewLabel_3 = new JLabel("Choose a room no");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(30, 375, 278, 19);
		contentPane.add(lblNewLabel_3);
		
		JButton btnNewButton = new JButton("back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainPage mp = new MainPage();
				mp.setVisible(true);
			}
		});
		btnNewButton.setBackground(SystemColor.textHighlight);
		btnNewButton.setForeground(SystemColor.textHighlightText);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.setBounds(30, 24, 83, 46);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Choose Food from below");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(30, 245, 196, 19);
		contentPane.add(lblNewLabel_1);
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
