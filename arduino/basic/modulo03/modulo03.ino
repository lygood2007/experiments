const int sensor = 0; // Pino analógico de entrada 0
int sensor_value = 0;

void setup () {
  Serial.begin(9600);
}

void loop () {
  sensor_value = analogRead(sensor);
  Serial.print("Valor do sensor: ");
  Serial.println(sensor_value);
  delay(500);
}
