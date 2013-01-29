// Projeto iluminação automatizada
const int LDR = 0;
const int LED = 6;
int ldr_value = 0;
int pwm = 0;

void setup () {
  pinMode(LED, OUTPUT);
  //Serial.begin(9600);
}

void loop () {
  ldr_value = analogRead(LDR);  
  
  if (ldr_value < 600) {
    pwm = 255;
    analogWrite(LED, pwm);
    delay(1000);    
  }
  else {
    digitalWrite(LED, LOW);
    pwm = 0;
  }
  
  //if (pwm > 255) pwm = 255;
  
  //Serial.println(ldr_value);
}
