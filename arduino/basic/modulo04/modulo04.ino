const int POTENTIOMETER = 0; // Entrada analógica no pino 0
const int LED = 13; // Saída digital no pino 13
int resistance = 0;

void setup () {
  pinMode(LED, OUTPUT);
  Serial.begin(9600);
}

void loop () {
  resistance = analogRead(POTENTIOMETER);
  digitalWrite(LED, HIGH);
  delay(resistance);
  digitalWrite(LED, LOW);
  delay(resistance);
  
  Serial.println(resistance);
}
