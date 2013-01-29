// Calibrar LDR
const int LDR = 0;
int ldr_value = 0;

void setup () {
  Serial.begin(9600);
}

void loop () {
  ldr_value = analogRead(LDR);
  Serial.print("LDR --> ");
  Serial.println(ldr_value);
  delay(500);
}
