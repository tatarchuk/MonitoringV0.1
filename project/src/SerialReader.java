import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Timer;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;

public class SerialReader {

	public SerialReader() {

	}

	// private SerialPort serialPort;
	private String stringResult = "000";

	public void readData(byte command, String port, int rate, int bits,
			int stopBits, int parity) {
		SerialPort serialPort = new SerialPort(port);
		ArrayList<Byte> list = new ArrayList<Byte>();
		byte tmp = 0x00;
		int receivedBytesCounter = 0;
		try {
			serialPort.openPort();// Open serial port
			serialPort.setParams(rate, bits, stopBits, parity);// Set params.
			serialPort.writeByte(command);
			
			//Trying to read serial port until \n arrives
			//or 500 ms passes without any data received
			do {
				byte[] buffer;				
				try {
					buffer = serialPort.readBytes(1,500);
					tmp = buffer[0];
					list.add(tmp);
				} catch (SerialPortTimeoutException e) {
					System.out.println(e);
					break;
				}
			} while (tmp != 0x0A);
			
			serialPort.closePort();// Close serial port
		} catch (SerialPortException ex) {
			System.out.println(ex);
		}
		
		Byte[] newArray = list.toArray(new Byte[list.size()]);
		byte[] anotherArray = new byte[newArray.length];
		for (int i = 0; i < newArray.length; i++) {
			anotherArray[i] = newArray[i];
		}
		String str = "000";
		try {
			str = new String(anotherArray, "ASCII");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stringResult = str;
	}

	public String getStringResult() {
		return stringResult;
	}

}