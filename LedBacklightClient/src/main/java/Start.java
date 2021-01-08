import LED.LED;
import Serial.SerialComm;

public class Start {
    public static void main(String[] args) {
        LED led = new LED(1.0, 0, 100, 0);
        System.out.println(led);
        SerialComm.connect();
        led.fluctuateBrightness(3);
    }
}
