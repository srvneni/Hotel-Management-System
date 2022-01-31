import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.JDatePanelImpl;
import javax.swing.JFormattedTextField.AbstractFormatter;
import com.jgoodies.common.base.Objects;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.ComponentOrientation;
public class MainPage extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage frame = new MainPage();
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
	public MainPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1164, 748);
		
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
				dispose();
				ProfilePage pp = new ProfilePage();
				pp.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		mnNewMenu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.textHighlightText);
		contentPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 102, 204));
		panel.setForeground(SystemColor.text);
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(98, 96, 266, 190);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Book Room");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookRoom frame= new BookRoom();
				frame.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("Lucida Sans", Font.BOLD, 20));
		btnNewButton.setBackground(new Color(51, 102, 204));
		btnNewButton.setBounds(10, 11, 246, 168);
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(51, 102, 204));
		panel_1.setForeground(SystemColor.textHighlight);
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(394, 96, 266, 190);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnOrderRoomService = new JButton("Order Room Service");
		btnOrderRoomService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				RoomService frame= new RoomService();
				frame.setVisible(true);
			}
		});
		btnOrderRoomService.setForeground(SystemColor.textHighlightText);
		btnOrderRoomService.setFont(new Font("Lucida Sans", Font.BOLD, 20));
		btnOrderRoomService.setBackground(new Color(51, 102, 204));
		btnOrderRoomService.setBounds(10, 11, 246, 168);
		panel_1.add(btnOrderRoomService);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(51, 102, 204));
		panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_2.setBounds(689, 96, 278, 190);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnOrderHouseKeeping = new JButton("Order House Keeping");
		btnOrderHouseKeeping.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				HouseKeeping hk = new HouseKeeping();
				hk.setVisible(true);
			}
			
		});
		btnOrderHouseKeeping.setFont(new Font("Lucida Sans", Font.BOLD, 20));
		btnOrderHouseKeeping.setForeground(SystemColor.textHighlightText);
		btnOrderHouseKeeping.setBackground(new Color(51, 102, 204));
		btnOrderHouseKeeping.setBounds(10, 11, 258, 168);
		panel_2.add(btnOrderHouseKeeping);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(51, 102, 204));
		panel_3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_3.setBounds(257, 357, 266, 171);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JButton btnBookParkingSlots = new JButton("Book Parking Slots");
		btnBookParkingSlots.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Parking frame = new Parking();
				frame.setVisible(true);
			}
		});
		btnBookParkingSlots.setForeground(SystemColor.textHighlightText);
		btnBookParkingSlots.setFont(new Font("Lucida Sans", Font.BOLD, 20));
		btnBookParkingSlots.setBackground(new Color(51, 102, 204));
		btnBookParkingSlots.setBounds(10, 11, 246, 149);
		panel_3.add(btnBookParkingSlots);
		
		JPanel panel_3_1 = new JPanel();
		panel_3_1.setLayout(null);
		panel_3_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_3_1.setBackground(new Color(51, 102, 204));
		panel_3_1.setBounds(578, 357, 266, 171);
		contentPane.add(panel_3_1);
		
		JButton btnInvoice = new JButton("Invoice");
		btnInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Invoice in = new Invoice();
				in.setVisible(true);
				
			}
		});
		btnInvoice.setForeground(Color.WHITE);
		btnInvoice.setFont(new Font("Lucida Sans", Font.BOLD, 20));
		btnInvoice.setBackground(new Color(51, 102, 204));
		btnInvoice.setBounds(10, 11, 246, 149);
		panel_3_1.add(btnInvoice);
	}
}
