package LED;

public class ColorRGB {
    private int r;
    private int g;
    private int b;

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
        this.r = r;
    }

    public void setG(int g) {
        if (g < 0 || g > 255) {
            throw new IllegalArgumentException("out of range G \n");
        }
        this.g = g;
    }

    public void setB(int b) {
        if (b < 0 || b > 255) {
            throw new IllegalArgumentException("out of range B \n");
        }
        this.b = b;
    }

    public void setRGB(int r, int g, int b) {
        setR(r);
        setG(g);
        setB(b);
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public static ColorRGB getDefaults() {
        return new ColorRGB(0,0,0);
    }

    @Override
    public String toString() {
        return "Red - " + r + "\n" +
                "Green - " + g + "\n" +
                "Blue - " + b;
    }
}
