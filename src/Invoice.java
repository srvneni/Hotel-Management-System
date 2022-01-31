import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JEditorPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.ComponentOrientation;
import javax.swing.JButton;
public class Invoice extends JFrame {

	private JPanel contentPane;
	Connection conn = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Invoice frame = new Invoice();
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
	public Invoice() {
		conn = MySqlConnection.getConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1191, 744);
		
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
		
		float prices[] = new float[2];
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.setBackground(SystemColor.textHighlightText);
		panel.setBounds(365, 115, 499, 500);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Invoice");
		lblNewLabel.setFont(new Font("Lucida Sans", Font.BOLD, 25));
		lblNewLabel.setBounds(198, 23, 98, 38);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Room Service Price");
		lblNewLabel_1.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(31, 349, 150, 38);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Room Cost");
		lblNewLabel_1_1.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(31, 290, 150, 38);
		panel.add(lblNewLabel_1_1);
		
		JLabel customerIdLabel = new JLabel("");
		customerIdLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		customerIdLabel.setBounds(338, 229, 78, 38);
		panel.add(customerIdLabel);
		
		JLabel lblNewLabel_3_1 = new JLabel("");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_3_1.setBounds(328, 229, 78, 38);
		panel.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Invoice Id");
		lblNewLabel_1_1_1.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		lblNewLabel_1_1_1.setBounds(31, 169, 150, 38);
		panel.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_4 = new JLabel("Amount");
		lblNewLabel_4.setFont(new Font("Lucida Sans", Font.PLAIN, 18));
		lblNewLabel_4.setBounds(31, 401, 150, 62);
		panel.add(lblNewLabel_4);
		
		JLabel invoiceLabel = new JLabel("");
		invoiceLabel.setBounds(342, 179, 128, 22);
		panel.add(invoiceLabel);
		
		JLabel lblNewLabel_3_2 = new JLabel("");
		lblNewLabel_3_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_3_2.setBounds(328, 229, 78, 38);
		panel.add(lblNewLabel_3_2);
		
		JLabel lblNewLabel_3_3 = new JLabel("");
		lblNewLabel_3_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_3_3.setBounds(328, 246, 78, 38);
		panel.add(lblNewLabel_3_3);
		
		JLabel roomCostLabel = new JLabel("");
		roomCostLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		roomCostLabel.setBounds(338, 290, 78, 38);
		panel.add(roomCostLabel);
		
		JLabel lblNewLabel_6 = new JLabel("Customer Id");
		lblNewLabel_6.setFont(new Font("Lucida Sans Typewriter", Font.PLAIN, 15));
		lblNewLabel_6.setBounds(31, 236, 128, 30);
		panel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_3_5 = new JLabel("");
		lblNewLabel_3_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_3_5.setBounds(328, 197, 78, 38);
		panel.add(lblNewLabel_3_5);
		
		JLabel amountLabel = new JLabel("");
		amountLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		amountLabel.setBounds(328, 414, 78, 38);
		panel.add(amountLabel);
		
		JLabel rspLabel = new JLabel("");
		rspLabel.setBounds(338, 363, 68, 24);
		panel.add(rspLabel);
		
		JLabel lblNewLabel_7 = new JLabel("Date of Issue");
		lblNewLabel_7.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		lblNewLabel_7.setBounds(31, 122, 98, 36);
		panel.add(lblNewLabel_7);
		
		JLabel doilabel = new JLabel("");
		doilabel.setBounds(342, 135, 128, 22);
		panel.add(doilabel);
		
		JButton btnNewButton = new JButton("back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainPage mp = new MainPage();
				mp.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBounds(48, 34, 108, 60);
		contentPane.add(btnNewButton);
		UserInfo user = new UserInfo();
		int customer_id = user.getUserId();
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
			String query7 = "select * from invoice where customer_id = "+customer_id+"";
			Statement stmts = conn.createStatement();
			ResultSet rs = stmts.executeQuery(query7);	
			while(rs.next()) {
				doilabel.setText(String.valueOf(rs.getDate("date_of_issue")));
				invoiceLabel.setText(String.valueOf(rs.getInt("invoice_id")));
				customerIdLabel.setText(String.valueOf(rs.getInt("customer_id")));
				rspLabel.setText(String.valueOf(prices[0]));
				roomCostLabel.setText(String.valueOf(prices[1]));
				amountLabel.setText(String.valueOf(rs.getInt("amount")));
			}
		}
		catch(Exception e6) {
			System.out.println(e6.getMessage());
		}
	}
}
