package LED;

import java.util.HashMap;
import java.util.Map;

public class LED {
    ColorRGB LEDColor;
    private SerialComm LEDComm;
    public LED() {
        new LED(0,0,0);
    }
    public LED(int r, int g, int b) {
        new LED(1.0,r ,g ,b);
    }
    public LED(double brightness, int r, int g, int b) {
        this.LEDComm = SerialComm.getSerialBacklight();
        this.LEDColor = new ColorRGB(brightness,r ,g ,b);
    }
    protected void finalize() {
        LEDComm.close();
    }

    public void pushChanges() {
        LEDComm.pushRGB(LEDColor.buildColor());
    }
    public void fluctuateBrightness(int frequencyPerMin) {
        double start = this.LEDColor.getBrightness();
        while (true) {
            for (double i = 0; i < start; i = i + frequencyPerMin * start/600.0) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.LEDColor.setBrightness(i);
                //System.out.println(led3.LEDColor.toString());
                this.pushChanges();
            }
            for (double i = start; i > 0; i = i - frequencyPerMin * start/600.0 ) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.LEDColor.setBrightness(i);
                //System.out.println(this.LEDColor.toString());
                this.pushChanges();
            }
        }
    }
    public void fluctuateColorRandomly() {
        Map<Integer,Integer> startRGB = LEDColor.getRGB();
        int[] changedRGB = new int[3];
        int changedColor = 0;
        int currentColor;
        while (true) {
            for (int i = 0; i < 3; i++) {
                currentColor = (int) LEDColor.getRGB().get(i) ;
                if(((int) (Math.random() * 2)) == 1) {
                    changedColor = 1 + currentColor;
                }
                else {
                    changedColor = -1 + currentColor;
                }
                if ((changedColor <= startRGB.get(i)) && changedColor >= 0) {
                    changedRGB[i] = changedColor;
                }
                else if (changedColor >= startRGB.get(i)) {
                    changedRGB[i] = startRGB.get(i);
                }
            }
            LEDColor.setRGB(changedRGB[0], changedRGB[1], changedRGB[2]);
            this.pushChanges();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void fluctuateColorWavy() {
        while (true) {
            for (int i = 0; i < 3; i++) {
                int lowingColor =(int) LEDColor.getRGB().get(i);
                int uppingColor = 0;

                    while (lowingColor != 0 && uppingColor < 255) {
                        lowingColor -= 1;
                        uppingColor += 1;
                        LEDColor.setByChannel(i, lowingColor);
                        if (i == 2) {
                            LEDColor.setByChannel(0, uppingColor);
                        } else {
                            LEDColor.setByChannel(i + 1, uppingColor);
                        }
                        pushChanges();
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    @Override
    public String toString() {
        return "Color settings: \n" + this.LEDColor.toString() +
                "Serial.settings: \n" + this.LEDComm.toString();
    }

    public static void main(String[] args) {
        LED led3 = new LED(1.0,200,0,0);
        System.out.println(led3.LEDColor.toString());
        //led3.pushChanges();
        //led3.fluctuateBrightness(1);
        //led3.fluctuateColorRandomly();
        led3.fluctuateColorWavy();

    }
}
