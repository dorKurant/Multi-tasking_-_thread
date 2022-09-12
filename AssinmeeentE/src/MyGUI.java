import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyGUI extends JFrame { //GUI class

	private JPanel contentPane;
	private double Perror;
	private int numOfEDW; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyGUI frame = new MyGUI();
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
	public MyGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 561, 359);
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Welcome to the SAT");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setFont(new Font("Gisha", Font.BOLD, 22));
		lblNewLabel.setBounds(161, 27, 228, 32);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Input Perror (0.1-0.9):");
		lblNewLabel_1.setFont(new Font("Gisha", Font.BOLD, 18));
		lblNewLabel_1.setBounds(51, 110, 201, 20);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Input num of EDW workers (1-3):");
		lblNewLabel_2.setFont(new Font("Gisha", Font.BOLD, 18));
		lblNewLabel_2.setBounds(51, 180, 295, 20);
		contentPane.add(lblNewLabel_2);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Double(0), null, null, 0.01));
		spinner.setBounds(419, 110, 41, 20);
		contentPane.add(spinner);

		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(419, 180, 41, 20);
		contentPane.add(spinner_1);

		JButton btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Perror = (double) spinner.getValue();
				if(!validNum1(Perror)) {
					JOptionPane.showMessageDialog(null, "illegal input!", "Error", JOptionPane.ERROR_MESSAGE);
					spinner.setValue(0.2);
					Perror = 0.2;
				}
				
				numOfEDW = (int) spinner_1.getValue();
				if(!validNum2(numOfEDW)) {
					JOptionPane.showMessageDialog(null, "illegal input!", "Error", JOptionPane.ERROR_MESSAGE);
					spinner_1.setValue(2);
					numOfEDW = 2;
				}

				new SAT(Perror,numOfEDW); //create a new envelope class
			}	
			
			private boolean validNum1(double Perror) { //invalid Perror
				if(Perror<0.1 || Perror>0.9) {
					return false;
				}
				return true;
			}

			private boolean validNum2(int numOfEDW) { //invalid num of EDW
				if(numOfEDW<1 || numOfEDW>3) {
					return false;
				}
				return true;
			}

		});
		btnNewButton.setFont(new Font("Gisha", Font.BOLD, 16));
		btnNewButton.setBounds(133, 250, 107, 32);
		contentPane.add(btnNewButton);


		JButton btnNewButton_2 = new JButton("Exit");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_2.setFont(new Font("Gisha", Font.BOLD, 16));
		btnNewButton_2.setBounds(293, 250, 107, 32);
		contentPane.add(btnNewButton_2);
	}
}