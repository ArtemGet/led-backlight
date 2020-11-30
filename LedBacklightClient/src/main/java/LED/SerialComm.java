package LED;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class SerialComm {
    private static volatile SerialComm serialBacklight;
    private SerialComm() {
            initPort();
    }
    public static SerialComm getSerialBacklight() {
        if (serialBacklight == null) {
            serialBacklight = new SerialComm();
        }
        return serialBacklight;
    }

    private static String PORT;
    private static SerialPort serialPort;
    private static void initPort() {
            try {
//works only if only one LEDBacklight is connected sry(
                try {
                    PORT = SerialPortList.getPortNames()[0];
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Port not found");
                }
                serialPort = new SerialPort(PORT);
                serialPort.openPort();
                Thread.sleep(2000);
                serialPort.setParams(9600, 8, 1, 0, true, true);
                System.out.println("Port initialised");
            } catch (SerialPortException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        protected void close() {
            try {
                serialPort.closePort();
                System.out.println("Port closed");
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }

    public void pushRGB(ColorRGB colorRGB) {
        if (SerialPortList.getPortNames().length == 0) {
            System.out.println("Connection lost");
            System.exit(1);
        }
            try {
                serialPort.writeInt(colorRGB.getR());
                serialPort.writeInt(colorRGB.getG());
                serialPort.writeInt(colorRGB.getB());
                System.out.println(colorRGB.getR() + " " + colorRGB.getG() + " " + colorRGB.getB());
                System.out.println("changes pushed");
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }

    @Override
    public String toString() {
        return "Serial port - " + PORT + "\n";
    }
}
