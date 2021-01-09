package Test;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SerialTest {
    static String PORT;
    static SerialPort serialPort;
    static Scanner request = new Scanner(System.in);
    static int a;
    static int R, G, B;
    static final Map<String, Double> brightness = Map.ofEntries(
            Map.entry("darkest", 0.2),
            Map.entry("dark", 0.4),
            Map.entry("mid", 0.6),
            Map.entry("bright", 0.8),
            Map.entry("brightest", 1.0)
    );

    private static void initPort() {
        try {
            PORT = SerialPortList.getPortNames()[0];
            serialPort = new SerialPort(PORT);
            serialPort.openPort();
            Thread.sleep(2000);
            serialPort.setParams(9600, 8, 1, 0, true, true);
        } catch (ArrayIndexOutOfBoundsException | SerialPortException e) {
            System.out.println("connect LedBackLighter");
            System.exit(1);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private static void pushColorChanges() {
        try {
            serialPort.writeInt(R);
            serialPort.writeInt(G);
            serialPort.writeInt(B);

            System.out.println(R + " " + G + " " + B);
            System.out.println("changes pushed");
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        initPort();

        while (a != 4) {
            writeActions();
            if (a == 1) {
                writeBrightnessActions();
                String brightness = request.next();
                writeColorsActions();
                setColor(brightness, request.nextInt(), request.nextInt(), request.nextInt());
            } else if (a == 2) {

            } else if (a == 3) {
                pushColorChanges();
            }
        }
    }

    private static void writeActions() {
        System.out.println("Enter num:");
        System.out.println("1)Set color");
        System.out.println("2)Set mode");
        System.out.println("3)Push changes");
        System.out.println("4)Exit");
        a = request.nextInt();
    }

    private static void writeColorsActions() {
        System.out.println("Select colors from 0 to 255 (RGB)");
    }

    private static void writeBrightnessActions() {
        System.out.println("Enter brightness level:");
        System.out.println("darkest/dark/mid/bright/brightest");
    }

    private static void setColor(String intensivety, int R, int G, int B) {
        try {
            double coefficient = brightness.get(intensivety);
            SerialTest.R = (int) (R * coefficient);
            SerialTest.G = (int) (G * coefficient);
            SerialTest.B = (int) (B * coefficient);
        } catch (NullPointerException n) {
            System.out.println("----------------------------------");
            System.out.println("Wrong brightness settings");
            System.out.println("----------------------------------");
        }
    }

    private static void setColor(int R, int G, int B) {
        setColor("mid", R, G, B);
    }
}
