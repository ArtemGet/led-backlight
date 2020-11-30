package LED;
import java.util.Map;

public class ColorRGB<b> {
    private int R;
    private int G;
    private int B;
    private double brightness;
    private static ColorRGB builtColor = new ColorRGB();

    public ColorRGB() {

    }
    public ColorRGB(double brightness, int r, int g, int b) {
        this.setAll(brightness, r, g, b);
    }

    public int getR() {
        return R;
    }
    public int getG() {
        return G;
    }
    public int getB() {
        return B;
    }
    public double getBrightness() {
        return brightness;
    }
    public Map<String,Integer> getRGB() {
        return Map.of("red", R, "green", G, "blue", B);
    }

    public void setRGB(int r, int g, int b) {
        this.R = r;
        this.G = g;
        this.B = b;
        if (r < 0 || r > 255) {
            throw new IllegalArgumentException("out of range R \n" +
                    "");
        }
        if (g < 0 || g > 255) {
            throw new IllegalArgumentException("out of range G \n");
        }
        if (b < 0 || b > 255) {
            throw new IllegalArgumentException("out of range B \n");
        }
    }
    public void setBrightness(double brightness)  throws IllegalArgumentException{
        if (brightness >= 0 && brightness <= 1) {
            this.brightness = brightness;
        //setRGB((int)(this.R * brightness), (int)(this.G * brightness), (int)(this.B * brightness));
        }
        else if (brightness < 0) {
            this.brightness = 0;
            //setRGB(0,0,0);
            throw new IllegalArgumentException("Attention! brightness is under 0% \n" +
                    "brightness set on 0%\n" +
                    "Attention! this option will turn LEDs off\n");
        }
        else if (brightness > 1) {
            this.brightness = 1;
            throw new IllegalArgumentException("Attention! brightness exceeds 100%\n" +
                    "brightness set on 100%");
        }
    }
    public void setAll(double brightness, int r, int g, int b) {
        setRGB(r, g, b);
        setBrightness(brightness);
    }

    public ColorRGB buildColor() {
        builtColor.setAll(this.brightness, (int)(R * brightness), (int)(G * brightness), (int)(B * brightness));
       return builtColor;
    }

    @Override
    public String toString() {
        return "Red - " + R + "\n" +
                "Green - " + G + "\n" +
                "Red - " + B + "\n" +
                "Brightness - " + brightness;
    }
}
