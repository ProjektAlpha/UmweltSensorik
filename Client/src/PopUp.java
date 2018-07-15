
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eclipse.paho.client.mqttv3.MqttException;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;

public class PopUp extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void NewScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PopUp frame = new PopUp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public String [] getLiveMesswerte() throws MqttException {
			
			String [] LiveMesswerte = Client.setMesswerte();
			
			return LiveMesswerte;
			
		}

	/**
	 * Create the frame.
	 * @throws MqttException 
	 */
	public PopUp() throws MqttException {
		
		setDefaultCloseOperation(PopUp.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("Temperatur");
		label.setBounds(521, 49, 90, 23);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Feinstaub");
		label_1.setBounds(521, 149, 61, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("Luftdruck");
		label_2.setBounds(521, 251, 61, 23);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("Luftfeuchtigkeit");
		label_3.setBounds(521, 359, 90, 23);
		contentPane.add(label_3);
		
		JButton button = new JButton("Aktualisieren");
		button.setBounds(72, 440, 115, 23);
		contentPane.add(button);
		
		JLabel lblLiveabfrage = new JLabel("Live-Abfrage ");
		lblLiveabfrage.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblLiveabfrage.setBounds(35, 24, 321, 36);
		contentPane.add(lblLiveabfrage);
		
		JLabel lblUhrzeit = new JLabel("Uhrzeit:");
		lblUhrzeit.setBounds(35, 106, 46, 14);
		contentPane.add(lblUhrzeit);
		
		textField = new JTextField();
		textField.setBounds(521, 81, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(521, 188, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(525, 301, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(521, 397, 86, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(104, 106, 46, 14);
		contentPane.add(lblNewLabel);
		
		
		String [] LiveMesswerte = getLiveMesswerte();
		
		if(LiveMesswerte.length != 0 ) {
		String Temperatur = LiveMesswerte [0];
		String Feinstaub = LiveMesswerte [1];
		String Luftdruck = LiveMesswerte [2];
			
		textField.setText(Temperatur);
		textField_2.setText(Feinstaub);
		textField_3.setText(Luftdruck);
		}
	
		
	}

}
