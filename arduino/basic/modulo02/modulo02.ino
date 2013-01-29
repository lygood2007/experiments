// Outputs
const int buzzer = 10;
const int greenLED = 11;
const int yellowLED = 12;
const int redLED = 13;

// Inputs
const int greenLEDSwitch = 2;
const int yellowLEDSwitch = 3;
const int redLEDSwitch = 4;

// Estados dos botoes
int greenLEDSwitchState = 0;
int yellowLEDSwitchState = 0;
int redLEDSwitchState = 0;

int note_delay = 0;
int litLED = 0;

void setup () {
  pinMode(buzzer, OUTPUT);
  pinMode(greenLED, OUTPUT);
  pinMode(yellowLED, OUTPUT);
  pinMode(redLED, OUTPUT);
  pinMode(greenLEDSwitch, INPUT);
  pinMode(yellowLEDSwitch, INPUT);
  pinMode(redLEDSwitch, INPUT);
}

void loop () {
  greenLEDSwitchState = digitalRead(greenLEDSwitch);
  yellowLEDSwitchState = digitalRead(yellowLEDSwitch);
  redLEDSwitchState = digitalRead(redLEDSwitch);
  
  note_delay = 0;
  
  if (greenLEDSwitchState) {
    note_delay += 50;
    litLED = greenLED;
  }
  
  if (yellowLEDSwitchState) {
    note_delay += 200;
    litLED = yellowLED;
  }
  
  if (redLEDSwitchState) {
    note_delay += 1000;
    litLED = redLED;
  }

  if (note_delay > 0 && litLED > 0) {
    
    turnOn(litLED);    
    turnOn(buzzer);
    delayMicroseconds(note_delay);
    turnOff(litLED);
    delayMicroseconds(note_delay);    
    turnOff(buzzer);
  }  
}

void strobe (int pin) {
  turnOn(pin);
  delay(100); // ms
  turnOff(pin);
  delay(100); // ms
}

void turnOn (int pin) {
  digitalWrite(pin, HIGH);
}

void turnOff (int pin) {
  digitalWrite(pin, LOW);
}
