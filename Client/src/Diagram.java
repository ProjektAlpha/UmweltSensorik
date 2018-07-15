import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import org.jfree.data.xy.*;

import org.jfree.ui.ApplicationFrame;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class Diagram {
	

	public static void main(String[] args) {
		
		XYSeries series1 = new XYSeries("Punkte1");
		series1.add(0.00, 5);
		series1.add(8.00,8);
		series1.add(10.00, 5);
		series1.add(12.00,6);

		// Hinzufuegen von series1 und series2 zu der Datenmenge dataset
		XYSeriesCollection dataset2 = new XYSeriesCollection();
		dataset2.addSeries(series1);
		
		XYLineAndShapeRenderer dot = new XYLineAndShapeRenderer();

		NumberAxis xax = new NumberAxis("Zeit");
		NumberAxis yax = new NumberAxis("Wert");
		
		XYPlot plot = new XYPlot(dataset2,xax,yax, dot);
		
		JFreeChart chart2 = new JFreeChart(plot);
		
		ApplicationFrame punkteframe = new ApplicationFrame("Punkte"); //"Punkte" entspricht der Ueberschrift des Fensters

		ChartPanel chartPanel2 = new ChartPanel(chart2);
		punkteframe.setContentPane(chartPanel2);
		punkteframe.pack();
		punkteframe.setVisible(true);
		
		

	}

}
