const int SENSOR = 0;
const int BUZZER = 6;

int sensor_value = 0;

void setup () {
  pinMode(BUZZER, OUTPUT);
  Serial.begin(9600);
}

void loop () {
   sensor_value = analogRead(SENSOR);
   digitalWrite(BUZZER, (sensor_value > 600 ? HIGH : LOW));
   
   Serial.print("Valor do sensor: ");
   Serial.println(sensor_value);
}
