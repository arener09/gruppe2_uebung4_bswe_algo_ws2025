# Übung 4 - Flight Routing System

Ein Java-basiertes System zur Verwaltung und Berechnung von Flugrouten zwischen Flughäfen.

---

## Projekt ausführen

### Voraussetzungen
- Java 17+
- Maven 3.9+
- (optional) IntelliJ IDEA oder VS Code

### Starten
```bash
./mvnw clean compile exec:java -Dexec.mainClass="at.hochschule.burgenland.bswe.algo.Main"
````

oder innerhalb der IDE:

* Klasse `Main.java` öffnen
* Methode `main()` ausführen

---

## Tests ausführen

Die Tests werden mit **JUnit** über Maven ausgeführt:

```bash
./mvnw test
```

---

## CI / Pull Requests

Bei jedem Pull Request auf den Branch `master` werden automatisch **alle Unit Tests** über Maven ausgeführt-

---

## Lizenz

Dieses Projekt ist unter der **MIT License** veröffentlicht.

---

© 2025 – Hochschule Burgenland, BSWE3B
Autoren: Raja Abdulhadi, Alexander R. Brenner, Julia Michler
