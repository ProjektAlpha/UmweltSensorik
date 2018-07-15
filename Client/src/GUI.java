import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.mapr.demo.mqtt.simple.Publisher;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SpringLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Panel;
import javax.swing.JSeparator;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridLayout;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.util.Arrays;
import java.awt.Canvas;

public class GUI extends JFrame {

	private JPanel contentPane;
	private final JSeparator separator = new JSeparator();

	/**
	 * Launch the application.
	 * @throws MqttException 
	 */
	public static void main(String[] args) throws MqttException {
				
		Client.startSubscriber();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static String [] getLiveMesswerte() throws MqttException {
		
		String [] LiveMesswerte = Client.setMesswerte();
		
		return LiveMesswerte;
		
	}
	
	
	public double [][] getMesswerte(){
		
		double Messwerte[][] = Client.setDiagrammdaten();
		
		return Messwerte;
		
	}
	
	private void Diagramm() {
		
		double [][]Messwerte = getMesswerte();
		
		DefaultXYDataset dataset = new DefaultXYDataset();
		dataset.addSeries("Temperatur", Messwerte);		
		
		XYLineAndShapeRenderer dot = new XYLineAndShapeRenderer();

		NumberAxis xax = new NumberAxis("Zeit");
		NumberAxis yax = new NumberAxis("Wert");
		
		XYPlot plot = new XYPlot(dataset,xax,yax, dot);
		
		
		JFreeChart chart1 = new JFreeChart(plot);
		JFreeChart chart2 = new JFreeChart(plot);
		JFreeChart chart3 = new JFreeChart(plot);
		JFreeChart chart4 = new JFreeChart(plot);
				
		JFrame window = new JFrame("Diagramme");
		window.setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_01 = new JPanel();
		JPanel panel_02 = new JPanel();
		JPanel panel_03 = new JPanel();
		JPanel panel_04 = new JPanel();
		
		ChartPanel chartPanel1 = new ChartPanel(chart1);
		ChartPanel chartPanel2 = new ChartPanel(chart2);
		ChartPanel chartPanel3 = new ChartPanel(chart3);
		ChartPanel chartPanel4 = new ChartPanel(chart4);
		
		panel_01.add(chartPanel1);
		panel_02.add(chartPanel2);
		panel_03.add(chartPanel3);
		panel_04.add(chartPanel4);
		
		panel.add(panel_01);
		panel.add(panel_02);
		panel.add(panel_03);
		panel.add(panel_04);
		panel.add(Box.createVerticalGlue());		
		window.getContentPane().add(scrollPane);

	
		window.setSize(1000,1000);
		window.setVisible(true);
		
	}


	/**
	 * Create the frame.
	 */
	public GUI() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 338, 248);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblUmweltmonitoringSystem = new JLabel("Umweltmonitoring System");
		lblUmweltmonitoringSystem.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblUmweltmonitoringSystem.setForeground(new Color(0, 0, 0));
		
		JLabel lblZeitraumDerAnzeige = new JLabel("Zeitraum der Anzeige");
		lblZeitraumDerAnzeige.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		
		JLabel lblVon = new JLabel("Von                                  Bis");
		
		JFormattedTextField frmtdtxtfldDdmmyyyy = new JFormattedTextField();
		frmtdtxtfldDdmmyyyy.setText("DD:MM:YYYY");
		
		JFormattedTextField frmtdtxtfldDdmmyyyy_1 = new JFormattedTextField();
		frmtdtxtfldDdmmyyyy_1.setText("DD:MM:YYYY");
		
		JFormattedTextField formattedTextField_2 = new JFormattedTextField();
		formattedTextField_2.setText("00:00");
		
		JFormattedTextField formattedTextField_3 = new JFormattedTextField();
		formattedTextField_3.setText("00:00");
		
		JSeparator separator_2 = new JSeparator();
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Diagramm();
				
			}
		});
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JButton btnLiveabfrage = new JButton("Live-Abfrage");
		btnLiveabfrage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				try {
					Publisher.startPublisher();
					PopUp nw = new PopUp();
					
				} catch (MqttException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				PopUp.NewScreen();
			}
		});
		btnLiveabfrage.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JSeparator separator_1 = new JSeparator();
		SpringLayout sl_contentPane = new SpringLayout();
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnLiveabfrage, 124, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnLiveabfrage, 166, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnStart, 124, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnStart, 27, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, formattedTextField_3, 100, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, formattedTextField_3, 166, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, formattedTextField_3, 271, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, formattedTextField_2, 100, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, formattedTextField_2, 27, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, formattedTextField_2, 108, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, frmtdtxtfldDdmmyyyy_1, 76, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, frmtdtxtfldDdmmyyyy_1, 166, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, frmtdtxtfldDdmmyyyy_1, 271, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, frmtdtxtfldDdmmyyyy, 76, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, frmtdtxtfldDdmmyyyy, 27, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, frmtdtxtfldDdmmyyyy, 108, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblVon, 58, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblVon, 27, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblZeitraumDerAnzeige, 40, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblZeitraumDerAnzeige, 27, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblZeitraumDerAnzeige, 271, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblUmweltmonitoringSystem, 17, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblUmweltmonitoringSystem, 27, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, separator_2, 12, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, separator_2, 22, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, separator_2, 13, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, separator_2, 23, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, separator_1, 12, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, separator_1, 17, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, separator_1, 13, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, separator_1, 18, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, separator, 12, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, separator, 12, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, separator, 13, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, separator, 13, SpringLayout.WEST, contentPane);
		contentPane.setLayout(sl_contentPane);
		contentPane.add(separator);
		contentPane.add(separator_1);
		contentPane.add(separator_2);
		contentPane.add(lblUmweltmonitoringSystem);
		contentPane.add(lblZeitraumDerAnzeige);
		contentPane.add(lblVon);
		contentPane.add(frmtdtxtfldDdmmyyyy);
		contentPane.add(frmtdtxtfldDdmmyyyy_1);
		contentPane.add(formattedTextField_2);
		contentPane.add(formattedTextField_3);
		contentPane.add(btnStart);
		contentPane.add(btnLiveabfrage);
		
		Panel panel = new Panel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel, -6, SpringLayout.SOUTH, lblVon);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel, 64, SpringLayout.EAST, frmtdtxtfldDdmmyyyy_1);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, contentPane);
		contentPane.add(panel);

		
	}
}
