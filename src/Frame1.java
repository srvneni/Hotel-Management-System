import java.awt.EventQueue;
import java.awt.*;


import javax.swing.JFrame;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;

import java.awt.Image;
import java.awt.SystemColor; 
public class Frame1 {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 window = new Frame1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 1167, 770);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Employee");
		btnNewButton.setFont(new Font("Lucida Sans", Font.PLAIN, 24));
		btnNewButton.setBackground(SystemColor.textHighlight);
		btnNewButton.setForeground(SystemColor.text);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeeSignIn es= new EmployeeSignIn();
				es.setVisible(true);
				frame.dispose();
			}
		});
		btnNewButton.setBounds(761, 237, 231, 58);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("HOTEL MANAGEMENT SYSTEM");
		lblNewLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 35));
		lblNewLabel.setForeground(SystemColor.desktop);
		lblNewLabel.setBackground(Color.GREEN);
		lblNewLabel.setBounds(622, 104, 523, 99);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("OR");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(845, 333, 77, 30);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnCustomer = new JButton("Customer");
		btnCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame3 frame3 = new Frame3();
				frame3.setVisible(true);
				frame.dispose();
				
			}
		});
		btnCustomer.setForeground(SystemColor.text);
		btnCustomer.setFont(new Font("Lucida Sans", Font.PLAIN, 24));
		btnCustomer.setBackground(SystemColor.textHighlight);
		btnCustomer.setBounds(761, 396, 231, 58);
		frame.getContentPane().add(btnCustomer);
		
		JLabel img_label = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/index-page-image.jpg")).getImage();
		img_label.setIcon(new ImageIcon(img));
		img_label.setBounds(0, 0, 598, 682);
		frame.getContentPane().add(img_label);
	}

	private void setLocation(int i, int j) {
		// TODO Auto-generated method stub
		
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}


}
