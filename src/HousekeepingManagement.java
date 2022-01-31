import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;

public class HousekeepingManagement extends JFrame {

	private JPanel contentPane;
	private JTable table;
	Connection conn;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem mntmNewMenuItem;
	private JLabel lblNewLabel;
	private JMenuItem mntmNewMenuItem_1;
	private JMenuItem mntmNewMenuItem_2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HousekeepingManagement frame = new HousekeepingManagement();
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
	public HousekeepingManagement() {
		int n;
		conn = MySqlConnection.getConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1177, 749);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		mnNewMenu = new JMenu("Account");
		menuBar.add(mnNewMenu);
		
		mntmNewMenuItem = new JMenuItem("Logout");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame1 frame = new Frame1();
				frame.setVisible(true);
				dispose();
			}
		});
		
		mntmNewMenuItem_1 = new JMenuItem("Clock-In/Clock-Out");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Schedules sc = new Schedules();
				sc.setVisible(true);
				dispose();
			}
		});
		
		mntmNewMenuItem_2 = new JMenuItem("Profile");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmpProfilePage emp = new EmpProfilePage();
				emp.setVisible(true);
				dispose();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		mnNewMenu.add(mntmNewMenuItem_1);
		mnNewMenu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		DefaultTableModel model = new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"Order Id", "Room No", "Request Date", "Request Time", "Button"
			}
		);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(112, 134, 938, 478);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("fun called");
				int row = table.getSelectedRow();
		        int col = table.getSelectedColumn();
		        System.out.println(row);
				int order_id = Integer.parseInt(table.getModel().getValueAt(row,0).toString());
				System.out.println(table.getModel().getValueAt(row,0).toString());
				UserInfo user = new UserInfo();
				int emp_id = user.getUserId();
				System.out.println(emp_id);
				try {
					System.out.println(""+order_id+" "+emp_id);
					String query = "update views_and_completes set employee_id = "+emp_id+" where order_id = "+order_id+"";
					System.out.println(query);
					PreparedStatement stmt2 = conn.prepareStatement(query);
					int count = stmt2.executeUpdate();
					String query2 = "update views_and_completes set completion_status = 'completed' where order_id = "+order_id+"";
					System.out.println(query2);
					PreparedStatement stmt3 = conn.prepareStatement(query2);
					count = stmt3.executeUpdate();
					if(count>0) {
						JOptionPane.showMessageDialog(null, "completed housekeeping for order_id "+order_id);
					}
				}
				catch(Exception e2) {
					System.out.println(e2.getMessage());
				}
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(model);
		table.setRowHeight(30);
		try {
			Statement stmt = conn.createStatement();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String today = dtf.format(LocalDateTime.now());
			java.util.Date date1 = null;
			try {
				date1 = sdf.parse(today);
				//System.out.println(todayDate);
				java.sql.Date todayDate = new java.sql.Date(date1.getTime());
				System.out.println(todayDate);
			String query = "select * from house_keeping inner join views_and_completes on house_keeping.order_id = views_and_completes.order_id where req_date= '"+todayDate+"' and completion_status = 'incomplete' ";
			ResultSet rs = stmt.executeQuery(query);
			int i = 0;
			System.out.println(rs.getFetchSize());
			while(rs.next()) {
				table.getModel().setValueAt(rs.getInt("order_id"), i, 0);
				table.getModel().setValueAt(rs.getInt("room_no"), i, 1);
				table.getModel().setValueAt(rs.getDate("req_date"), i, 2);
				table.getModel().setValueAt(rs.getTime("req_time"), i, 3);
				table.getModel().setValueAt("Mark Complete", i, 4);
				i++;
			}
			}
			catch(ParseException e) {
				System.out.println(e.getMessage());
		}
		}
		catch(Exception e3) {
			System.out.println(e3.getMessage());
		}
			Action delete = new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JTable table = (JTable)e.getSource();
			        int modelRow = Integer.valueOf( e.getActionCommand() );
			        ((DefaultTableModel)table.getModel()).removeRow(modelRow);
					}
			};
			for(n =0;n<model.getRowCount();n++) {
				if(model.getValueAt(n, 0)==null) {
					break;
				}
			}
			model.setRowCount(n);
			ButtonColumn buttonColumn = new ButtonColumn(table, delete, 4);
			
			lblNewLabel = new JLabel("HOUSE KEEPING MANAGEMENT");
			lblNewLabel.setForeground(SystemColor.textHighlight);
			lblNewLabel.setFont(new Font("Lucida Sans", Font.BOLD, 25));
			lblNewLabel.setBounds(359, 37, 457, 68);
			contentPane.add(lblNewLabel);
			buttonColumn.setMnemonic(KeyEvent.VK_D);
	}

}
