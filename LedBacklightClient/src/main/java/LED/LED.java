package LED;

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

    @Override
    public String toString() {
        return "Color settings: \n" + this.LEDColor.toString() +
                "Serial.settings: \n" + this.LEDComm.toString();
    }

    public static void main(String[] args) {
        LED led3 = new LED(0.5,200,0,10);
        System.out.println(led3.LEDColor.toString());
        //led3.pushChanges();
        led3.fluctuateBrightness(1);

    }
}
