// obs.: no projeto-tutorial o nome deste projeto é "frequencímetro", mas isto obviamente não mede frequência alguam.

#include <LiquidCrystal.h>

const int BUZZER = 9; // Digital output at pin 9
const int POTENTIOMETER = 0; // Analog input at pin 0

int resistance, frequency = 0;
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);

void setup () {
  lcd.begin(16, 2);
  lcd.setCursor(0, 0);
  lcd.print("Frequencia:");
  lcd.setCursor(6, 1);
  lcd.print("Hz");
  
  pinMode(BUZZER, OUTPUT);
}

void loop () {
  resistance = analogRead(POTENTIOMETER);
  frequency = map(resistance, 0, 1023, 10, 25000);
  
  lcd.setCursor(0, 1);
  
  if (frequency < 100) {
    lcd.print("   ");
  }
  else if (frequency >= 100 && frequency < 1000) {
    lcd.print("  ");
  }
  else if (frequency >= 1000 && frequency < 10000) {
    lcd.print(" ");
  }
  else { // frequency >= 10000
    // Nada
  }
  
  lcd.print(frequency);
  tone(BUZZER, frequency);
  
  delay(100);
}

