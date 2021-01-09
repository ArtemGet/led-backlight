package Serial;
import LED.ColorRGB;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class SerialComm {
    public volatile static boolean isRunning = true;
    private static String PORT;
    private static SerialPort serialPort;

    private SerialComm() {
    }
    public static void connect() throws SerialPortException {
        if (serialPort == null) {
            initPort();
        } else if (serialPort.isOpened()) {
            throw new SerialPortException(PORT,"connect()",SerialPortException.TYPE_PORT_ALREADY_OPENED);
        }
    }

    private static void initPort() throws SerialPortException {
        try {
            if (SerialPortList.getPortNames().length == 0) {
                throw new SerialPortException("", "initPort()", SerialPortException.TYPE_PORT_NOT_FOUND);
            }
            PORT = SerialPortList.getPortNames()[0];
            serialPort = new SerialPort(PORT);
            serialPort.openPort();
            Thread.sleep(2000);
            serialPort.setParams(9600, 8, 1, 0, true, true);
        }  catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void close() {
        try {
            serialPort.closePort();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public static void push(ColorRGB colorRGB) {
        try {
            serialPort.writeInt(colorRGB.getR());
            serialPort.writeInt(colorRGB.getG());
            serialPort.writeInt(colorRGB.getB());

        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public static void push() throws IOException {
        Files.lines(Path.of("src/main/resources/CurrentMode"))
                .forEach(line -> {
                    int[] RGB = collectRGB(line);
                    for (int channel : RGB) {
                        try {
                            serialPort.writeInt(channel);
                        } catch (SerialPortException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }

    public static boolean pushIfRunning(ColorRGB colorRGB) {
        SerialComm.push(colorRGB);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!isRunning) {
            SerialComm.push(ColorRGB.getDefaults());
            SerialComm.close();
            return false;
        }
        return true;
    }

    public static void startScanning() {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Type 'false' to stop program");
            while (isRunning) {
                isRunning = scanner.nextBoolean();
            }
        }).start();
    }

    public static int[] collectRGB(String line) {
        String[] inColor = line.split(" ");
        int[] outColor = new int[3];
        for (int i = 0; i < 3; i++) {
            outColor[i] = Integer.parseInt(inColor[i]);
        }
        return outColor;
    }

}
