package Test;

import LED.LEDBuilder;
import Serial.SerialComm;
import jssc.SerialPortException;

public class Start {
    public static void main(String[] args) {
        LEDBuilder led = new LEDBuilder(1.0, 0, 100, 0);
        System.out.println(led);
        SerialComm.connect();
        led.fluctuateBrightness(3);
    }
}
