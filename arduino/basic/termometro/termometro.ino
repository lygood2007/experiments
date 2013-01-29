const int SENSOR = 0;
const int BUZZER = 6;
const int LED1 = 8;
const int LED2 = 9;
const int LED3 = 10;
const int LED4 = 11;
const int LED5 = 12;
const int LED6 = 13;

int sensor_value = 0;

void setup () {
  pinMode(BUZZER, OUTPUT);
  pinMode(LED1, OUTPUT);
  pinMode(LED2, OUTPUT);
  pinMode(LED3, OUTPUT);
  pinMode(LED4, OUTPUT);
  pinMode(LED5, OUTPUT);
  pinMode(LED6, OUTPUT); 
  
  Serial.begin(9600);
}

void loop () {
  sensor_value = analogRead(SENSOR);
  Serial.print("Valor do sensor: ");
  Serial.println(sensor_value);
  
  turnOnOff(LED1, sensor_value, 0);
  turnOnOff(LED2, sensor_value, 540);
  turnOnOff(LED3, sensor_value, 545);
  turnOnOff(LED4, sensor_value, 550);
  turnOnOff(LED5, sensor_value, 555);
  turnOnOff(LED6, sensor_value, 560);
  turnOnOff(BUZZER, sensor_value, 560);
}

void turnOnOff (int led, int value, int threshold) {
  digitalWrite(led, value > threshold ? HIGH : LOW);
}

