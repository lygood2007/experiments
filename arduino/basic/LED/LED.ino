/*
 * Primeiro projeto com Arduíno: pressiona a chave e acende um LED.
 */
int ledPin = 13;
int buttonPin = 2;
int buttonState = 0;

void setup () {
  pinMode(ledPin, OUTPUT);
  pinMode(buttonPin, INPUT);
}

void loop () {
  buttonState = digitalRead(buttonPin);
  
  if (buttonState == HIGH) {
    digitalWrite(ledPin, LOW);
    delay(1000);
    digitalWrite(ledPin, HIGH);
    delay(1000);
  }
  else {
    digitalWrite(ledPin, HIGH);
  }
}
