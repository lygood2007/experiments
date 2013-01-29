const int POTENTIOMETER = 0; // Entrada analógica no pino A0
const int LED = 11; // Saída digital no pino 11
int r = 0;
int pwm = 0;

void setup () {
  pinMode(LED, OUTPUT);
}

void loop () {
  r = analogRead(POTENTIOMETER);
  pwm = map(r, 0, 1023, 0, 255);
  analogWrite(LED, pwm);
}

