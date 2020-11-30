const int B = 3;
const int G = 5;
const int R = 6;
int RIntens;
int GIntens;
int BIntens;
void setup() {
// D3 - 980 Hz
TCCR2B = 0b00000100;  // x64
TCCR2A = 0b00000011;  // fast pwm
  Serial.begin(9600);
  pinMode(R, OUTPUT);
  pinMode(G, OUTPUT);
  pinMode(B, OUTPUT);
}

void loop() {
  if (Serial.available() > 0) { 
    RIntens = Serial.read();
    delay(5);
    GIntens = Serial.read();
    delay(5);
    BIntens = Serial.read();
    delay(5);
    }             
  analogWrite(R, RIntens);
  analogWrite(G, GIntens);
  analogWrite(B, BIntens);
  }
