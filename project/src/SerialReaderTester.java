import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import jssc.SerialPortList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;;

public class SerialReaderTester extends JFrame {

	private JPanel mainPanel;
	private JPanel panel;
	private JPanel p00;
	private JPanel p0;
	private JPanel p1;		
	private JPanel p3;
	private JPanel p4;
	private JPanel p5;
	private JPanel p6;
	private JPanel p7;
	private JPanel p8;	
	private JLabel resultsLabel;
	private JLabel result1Label;
	private JLabel result2Label;	
	private JLabel portLabel;
	private JLabel paramLabel;
	private JLabel rateLabel;
	private JLabel bitsLabel;
	private JLabel parityLabel;
	private JLabel stopBitsLabel;
	private JLabel celsium1;
	private JLabel celsium2;	
	private JLabel pathLabel;
	private JComboBox portBox;
	private JComboBox rateBox;
	private JComboBox bitsBox;
	private JComboBox parityBox;
	private JComboBox stopBitsBox;	
	private static JTextField result1Field;
	private static JTextField result2Field;	
	private JTextField pathField;
	private static JButton paramButton;
	private JButton pathButton;	

	private String[] items0 = SerialPortList.getPortNames();
	private String[] items1 = { "9600", "4800", "1200" };
	private String[] items2 = { "8", "7", "6", "5" };
	private String[] items3 = { "NONE", "ODD", "EVEN", "MARK", "SPACE" };
	private String[] items4 = { "1", "2", "1.5" };

	private String port = SerialPortList.getPortNames()[0];
	private int rate = 9600;
	private int bits = 8;
	private int stopBits = 1;
	private int parity = 0;	

	/*
	 * Chart's variables
	 */
	private TimeSeries series1;
	private TimeSeries series2;
	

	public SerialReaderTester() {
		super("Monitoring");
		mainPanel = new JPanel();
		p00 = new JPanel();
		p0 = new JPanel();
		p1 = new JPanel();				
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		p6 = new JPanel();
		p7 = new JPanel();
		p8 = new JPanel();
		
		panel = new JPanel();

		resultsLabel = new JLabel("Real-time data");
		resultsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		portLabel = new JLabel("Port:");
		result1Label = new JLabel("Temperature:");
		result2Label = new JLabel("Humidity:");		
		paramLabel = new JLabel("Connection settings");
		paramLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		rateLabel = new JLabel("Rate:");
		bitsLabel = new JLabel("Data bits:");
		parityLabel = new JLabel("Parity:");
		stopBitsLabel = new JLabel("Stop bits:");
		pathLabel = new JLabel("Log file:");
		paramButton = new JButton("Set parameters");
		pathButton = new JButton("Change...");		
		pathField = new JTextField(45);
		pathField.setText("log.xml");
		pathField.setEditable(false);
		pathField.setHorizontalAlignment(JTextField.CENTER);
		result1Field = new JTextField(10);
		result1Field.setEditable(false);
		result1Field.setHorizontalAlignment(JTextField.CENTER);
		result2Field = new JTextField(10);
		result2Field.setEditable(false);
		result2Field.setHorizontalAlignment(JTextField.CENTER);		
		celsium1 = new JLabel("\u00B0" + "C");
		celsium2 = new JLabel("%Rh");		

		portBox = new JComboBox(items0);
		rateBox = new JComboBox(items1);
		bitsBox = new JComboBox(items2);
		parityBox = new JComboBox(items3);
		stopBitsBox = new JComboBox(items4);		

		p00.add(resultsLabel);
		p0.add(result1Label);
		p0.add(result1Field);
		p0.add(celsium1);
		p1.add(result2Label);
		p1.add(result2Field);
		p1.add(celsium2);			
		p3.add(paramLabel);
		p4.add(portLabel);
		p4.add(portBox);
		p5.add(rateLabel);
		p5.add(rateBox);
		p5.add(bitsLabel);
		p5.add(bitsBox);
		p6.add(parityLabel);
		p6.add(parityBox);
		p6.add(stopBitsLabel);
		p6.add(stopBitsBox);
		p7.add(paramButton);
		p8.add(pathLabel);
		p8.add(pathField);
		p8.add(pathButton);

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(p00);
		mainPanel.add(p0);
		mainPanel.add(p1);			
		mainPanel.add(p3);
		mainPanel.add(p4);
		mainPanel.add(p5);
		mainPanel.add(p6);
		mainPanel.add(p7);
		
		final JTabbedPane tabs = new JTabbedPane();

		panel.setLayout(new BorderLayout());
		panel.add(tabs);
		//panel.add(chartPanel22);
		panel.add(mainPanel, BorderLayout.WEST);
		panel.add(p8, BorderLayout.SOUTH);

		/*
		 * Start of chart's init code
		 */
		series1 = new TimeSeries("Current value of temperature", Millisecond.class);
		series2 = new TimeSeries("Current value of humidity", Millisecond.class);		
		TimeSeriesCollection dataset1 = new TimeSeriesCollection();
		dataset1.addSeries(series1);			
		TimeSeriesCollection dataset2 = new TimeSeriesCollection();
		dataset2.addSeries(series2);		

		final JFreeChart chart1 = createChart(dataset1, "Temperature monitoring", "Time", "Temperature, \u00b0C");
		final JFreeChart chart2 = createChart(dataset2, "Humidity monitoring", "Time", "Humidity, %Rh");

		final ChartPanel chartPanel1 = new ChartPanel(chart1);
		final ChartPanel chartPanel2 = new ChartPanel(chart2);

		chartPanel1.setPreferredSize(new java.awt.Dimension(500, 350));		
		chartPanel2.setPreferredSize(new java.awt.Dimension(500, 350));
		
		 tabs.add("Temperature", chartPanel1);
	     tabs.add("Humidity", chartPanel2);
		/*
		 * End of chart's init code
		 */

		setContentPane(panel);
		setSize(900, 500);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);		

		paramButton.addActionListener(new ParametersSetter());
		pathButton.addActionListener(new PathButtonListener());
	}	

	public class ParametersSetter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			port = (String) portBox.getSelectedItem();
			rate = Integer.parseInt((String) rateBox.getSelectedItem());
			bits = Integer.parseInt((String) bitsBox.getSelectedItem());
			String parityString = (String) parityBox.getSelectedItem();
			String stopBitsString = (String) stopBitsBox.getSelectedItem();			

			switch (parityString) {
			case "NONE":
				parity = 0;
				break;

			case "ODD":
				parity = 1;
				break;

			case "EVEN":
				parity = 2;
				break;

			case "MARK":
				parity = 3;
				break;

			case "SPACE":
				parity = 4;
				break;
			}

			switch (stopBitsString) {
			case "1":
				parity = 1;
				break;

			case "2":
				parity = 2;
				break;

			case "1.5":
				parity = 3;
				break;
			}

		}		

	}

	public class PathButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			selectPath();
		}

		public void selectPath() {
			String path;
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Select directory");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				path = chooser.getSelectedFile().toString() + "\\log.xml";
				pathField.setText(path);
			}
		}

	}

	private JFreeChart createChart(final XYDataset dataset, String name, String xName, String yName) {
		final JFreeChart result = ChartFactory.createTimeSeriesChart(name, xName, yName, dataset,
				true, true, false);
		final XYPlot plot = result.getXYPlot();
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		axis.setFixedAutoRange(60000.0); // 60 seconds
		axis = plot.getRangeAxis();
		axis.setAutoRange(true);
		return result;
	}
/**
 * Main loop that gets the data from controller and operates it
 */	
	public void monitore() {
		int count = 10;
		while (true) {
			String allResultsString = "";
			String[] splittedResultStrings = {"0.00","0.00","0.00"};
			int splittedStringIndex = 0;			
			
			//Time delay in seconds 
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException ex) {
				// Handle an exception
				System.out.println("Delay problem: " + ex);
			}
			
			SerialReader pp1 = new SerialReader();
									
			pp1.readData((byte) 'A', port, rate, bits, stopBits, parity);
			
			allResultsString = pp1.getStringResult();
			
			StringTokenizer splitter = new StringTokenizer(allResultsString, "\t\n");
			
			
			while (splitter.hasMoreElements()) {
				splittedResultStrings[splittedStringIndex] = splitter.nextToken();
				splittedStringIndex++;
			}
			
			
			result1Field.setText(splittedResultStrings[0]);
			result2Field.setText(splittedResultStrings[1]);			
			
			count--;
			double result1 = 000;
			double result2 = 000;			
			try {
				result1 = Double.parseDouble(splittedResultStrings[0]);
				result2 = Double.parseDouble(splittedResultStrings[1]);				
			} catch (NumberFormatException e) {
				System.out.println("NumberFormatException");
			}
			series1.add(new Millisecond(), result1);
			series2.add(new Millisecond(), result2);			
			if (count == 0) {
				String path = pathField.getText();
				FileIO.writeXML(result1, result2, path);
				count = 10;
			}
		}
	}

	public static void main(String[] args) {
		SerialReaderTester srt = new SerialReaderTester();			
		srt.monitore();
	}

}
