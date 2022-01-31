import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;

public class RoomServiceManagement extends JFrame {

	private JPanel contentPane;
	private JTable table;
	Connection conn = null;
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
					RoomServiceManagement frame = new RoomServiceManagement();
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
	public RoomServiceManagement() {
		int n;
		conn = MySqlConnection.getConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1178, 743);
		
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
		contentPane.setBackground(SystemColor.textHighlightText);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(67, 119, 1042, 534);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
		        int col = table.getSelectedColumn();
		        String query4 = "delete from room_service_order where order_id = "+Integer.parseInt(table.getModel().getValueAt(row, 0).toString())+"";
		        String query5 = "delete from orders where order_id="+Integer.parseInt(table.getModel().getValueAt(row, 0).toString())+"";
		        try {
		        	PreparedStatement stmt = conn.prepareStatement(query4);
		        	stmt.executeUpdate(query4);
		        }
		        catch(Exception e3) {
		        	System.out.println(e3.getMessage());
		        }
		        try {
		        	PreparedStatement stmt = conn.prepareStatement(query4);
		        	stmt.executeUpdate(query4);
		        }
		        catch(Exception e4) {
		        	System.out.println(e4.getMessage());
		        }
		        
			}
		});
		scrollPane.setViewportView(table);
		DefaultTableModel model = (new DefaultTableModel(
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
				"Order Id", "Food Name", "Room No", "Quantity", "Button"
			}
		));
		table.setModel(model);
		table.setRowHeight(35);
			try {
				Statement stmt;
				stmt = conn.createStatement();
			String query = "select orders.order_id, food_name, room_no, quantity from room_service_order inner join room_service on room_service.item_id = room_service_order.item_id inner join orders on room_service_order.order_id = orders.order_id";
			ResultSet rs = stmt.executeQuery(query);
			int i = 0;
			while(rs.next()) {
				table.getModel().setValueAt(rs.getInt("orders.order_id"),i,0);
				table.getModel().setValueAt(rs.getString("food_name"), i, 1);
				table.getModel().setValueAt(rs.getInt("room_no"), i, 2);
				table.getModel().setValueAt(rs.getInt("quantity"),i,3);
				table.getModel().setValueAt("Mark Complete",i,4);
				i++;
			}
			
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
			lblNewLabel = new JLabel("ROOM SERVICE MANAGEMENT");
			lblNewLabel.setForeground(SystemColor.textHighlight);
			lblNewLabel.setFont(new Font("Lucida Sans", Font.BOLD, 30));
			lblNewLabel.setBounds(315, 33, 558, 66);
			contentPane.add(lblNewLabel);
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
			buttonColumn.setMnemonic(KeyEvent.VK_D);
	}

}
