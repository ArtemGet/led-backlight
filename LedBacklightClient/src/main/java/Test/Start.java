package Test;

import LED.LED;
import Serial.SerialComm;
import jssc.SerialPortException;

import java.util.*;

public class Start {
    public static void main(String[] args) {
        LED led = new LED(1.0, 0, 100, 0);
        System.out.println(led);
        try {
            SerialComm.connect();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        led.fluctuateBrightness(3);
    }
}
