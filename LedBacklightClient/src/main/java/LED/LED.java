package LED;
import Serial.SerialComm;

public class LED {
    private ColorRGB color;
    private ColorRGB builtColor = new ColorRGB();
    private double brightness;

    public LED(double brightness, int r, int g, int b) {
        this.color = new ColorRGB(r, g, b);
        setBrightness(brightness);
    }

    private ColorRGB buildColor(double brightness) {
        this.builtColor.setRGB((int) (color.getR() * brightness)
                , (int) (color.getG() * brightness)
                , (int) (color.getB() * brightness));
        return builtColor;
    }

    private ColorRGB buildColor() {
        return buildColor(brightness);
    }
//    Add LEDModes class
    public void setBrightness(double brightness) throws IllegalArgumentException {
        if (brightness >= 0 && brightness <= 1) {
            this.brightness = brightness;
        } else if (brightness < 0) {
            this.brightness = 0;
            throw new IllegalArgumentException("Attention! brightness is under 0% \n" +
                    "brightness set on 0%\n" +
                    "Attention! this option will turn LEDs off\n");
        } else if (brightness > 1) {
            this.brightness = 1;
            throw new IllegalArgumentException("Attention! brightness exceeds 100%\n" +
                    "brightness set on 100%");
        }
    }

    public void fluctuateBrightness(int frequencyPerMin) {
        SerialComm.startScanning();
        while (SerialComm.isRunning) {
            for (double i = 0; i < brightness
                    ; i = i + frequencyPerMin * brightness / 600.0) {
                SerialComm.pushIfRunning(this.buildColor(i));
            }
            for (double i = brightness; i > 0
                    ; i = i - frequencyPerMin * brightness / 600.0) {
                SerialComm.pushIfRunning(this.buildColor(i));
            }
        }
    }

//    public void fluctuateColorWavy() {
//        SerialComm.startScanning();
//        color.setRGB(121,121,0);
//        buildColor();
//        while (SerialComm.isRunning) {
//            for (int i = 1; i <= 3; i++) {
//                if (i == 1) {
//                    for (int j = 0; j < 120; j++) {
//                        builtColor.setR(builtColor.getR() - 1);
//                        builtColor.setB(j);
//                        SerialComm.pushIfRunning(builtColor);
//                    }
//                } else if (i == 2) {
//                    for (int j = 0; j < 120; j++) {
//                        builtColor.setG(builtColor.getB() - 1);
//                        builtColor.setR(j);
//                        SerialComm.pushIfRunning(builtColor);
//                    }
//                }else if (i == 3) {
//                    for (int j = 0; j < 120; j++) {
//                        builtColor.setB(builtColor.getR() - 1);
//                        builtColor.setG(j);
//                        SerialComm.pushIfRunning(builtColor);
//                    }
//                }
//            }
//        }
//    }

    @Override
    public String toString() {
        return "Color settings: \n" + this.color + "\n"
                + "Brightness: " + this.brightness + "\n"
                + "BuitColor would be: \n" + this.buildColor();
    }
}
