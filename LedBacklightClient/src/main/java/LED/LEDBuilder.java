package LED;

import Serial.SerialComm;

public class LEDBuilder {
    private ColorRGB color;
    private ColorRGB builtColor = new ColorRGB();
    private double brightness;

    public LEDBuilder(double brightness, int r, int g, int b) {
        this.color = new ColorRGB(r, g, b);
        setBrightness(brightness);
    }

    private ColorRGB buildColor(double brightness) {
        builtColor.setRGB((int) (color.getR() * brightness)
                , (int) (color.getG() * brightness)
                , (int) (color.getB() * brightness));
        return builtColor;
    }

    private ColorRGB buildColor() {
        return buildColor(brightness);
    }

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
        while (SerialComm.isRunning()) {
            for (double i = 0; i < brightness
                    ; i = i + frequencyPerMin * brightness / 600.0) {
                SerialComm.pushIfRunning(buildColor(i));
            }
            for (double i = brightness; i > 0
                    ; i = i - frequencyPerMin * brightness / 600.0) {
                SerialComm.pushIfRunning(buildColor(i));
            }
        }
    }

    @Override
    public String toString() {
        return "Color settings: \n" + color + "\n"
                + "Brightness: " + brightness + "\n"
                + "BuitColor would be: \n" + buildColor();
    }
}
