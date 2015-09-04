import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * 
 * @author Tatarchuk Oleksandr
 * 
 */

public class FileIO {

	public static void writeXML(double data1, double data2,
			String path) {
		File file = new File(path);
		Document document = null;
		Element rootElement = null;
		if (!file.exists()) {
			document = new Document();
			rootElement = new Element("result");

		} else {
			try {
				FileInputStream fis = new FileInputStream(path);
				SAXBuilder sb = new SAXBuilder();
				document = sb.build(fis);
				rootElement = document.getRootElement();
				fis.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		DateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
		DateFormat format2 = new SimpleDateFormat("HH:mm:ss");
		Date d = new Date();
		String dateValue = format1.format(d);
		String timeValue = format2.format(d);
		Element date = new Element("data");
		date.setAttribute("date", dateValue);
		rootElement.addContent(date);
		Element time = new Element("time");
		time.setAttribute("time", timeValue);
		date.addContent(time);
		Element param1 = new Element("Temperature");
		time.addContent(param1);
		Element param2 = new Element("Humidity");
		time.addContent(param2);		
		param1.addContent(Double.toString(data1));
		param2.addContent(Double.toString(data2));		
		document.setContent(rootElement);
		try {
			FileWriter writer = new FileWriter(path);
			XMLOutputter outputter = new XMLOutputter();
			outputter.setFormat(Format.getPrettyFormat());
			outputter.output(document, writer);
			writer.close(); // close writer
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
