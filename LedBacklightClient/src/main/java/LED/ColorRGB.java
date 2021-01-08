package LED;

import java.util.Map;

public class ColorRGB {
    private int R;
    private int G;
    private int B;

    public ColorRGB() {
    }

    public ColorRGB(int r, int g, int b) {
        setRGB(r, g, b);
    }

    public void setR(int r) {
        if (r < 0 || r > 255) {
            throw new IllegalArgumentException("out of range R \n" +
                    "");
        }
        this.R = r;
    }

    public void setG(int g) {
        if (g < 0 || g > 255) {
            throw new IllegalArgumentException("out of range G \n");
        }
        this.G = g;
    }

    public void setB(int b) {
        if (b < 0 || b > 255) {
            throw new IllegalArgumentException("out of range B \n");
        }
        this.B = b;
    }

    public void setRGB(int r, int g, int b) {
        this.setR(r);
        this.setG(g);
        this.setB(b);
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

    public static ColorRGB getDefaults() {
        return new ColorRGB(0,0,0);
    }

    @Override
    public String toString() {
        return "Red - " + R + "\n" +
                "Green - " + G + "\n" +
                "Blue - " + B;
    }
}
