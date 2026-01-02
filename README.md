# Übung 4 - Flight Routing System

Ein Java-basiertes System zur Verwaltung und Berechnung von Flugrouten zwischen Flughäfen.

## Übersicht

Dieses Projekt implementiert ein vollständiges Flugroutenplanungssystem mit folgenden Hauptfunktionen:

- **Routenberechnung**: Findet optimale Flugverbindungen zwischen Flughäfen
- **Sortierung**: Sortiert Routen nach verschiedenen Kriterien mit unterschiedlichen Algorithmen
- **Suche**: Durchsucht Flüge nach verschiedenen Kriterien
- **Interaktive Konsole**: Benutzerfreundliche Menüoberfläche

## Features

### 1. Routenberechnung
Das System kann optimale Flugrouten zwischen zwei Flughäfen berechnen:
- **Günstigste Route**: Minimiert die Gesamtkosten (Dijkstra-Algorithmus)
- **Schnellste Route**: Minimiert die Gesamtflugdauer (Dijkstra-Algorithmus)
- **Wenigste Umstiege**: Minimiert die Anzahl der Flüge (BFS-Algorithmus)
- **Langsamste Route**: Maximiert die Gesamtflugdauer

### 2. Sortierung
Sortiert Routen nach verschiedenen Kriterien:
- **Stabile Sortierung**: MergeSort (erhält relative Reihenfolge)
- **Instabile Sortierung**: QuickSort (optimiert für Performance)
- **Sortierkriterien**: Preis, Dauer, Umstiege, Airline, Flugnummer, kombiniert

### 3. Suche
Durchsucht die Flugdatenbank nach:
- Abflughafen (IATA-Code)
- Zielflughafen (IATA-Code)
- Airline
- Flugnummer

## Projekt ausführen

### Voraussetzungen
- Java 17+
- Maven 3.9+
- (optional) IntelliJ IDEA oder VS Code

### Starten
```bash
./mvnw clean compile exec:java -Dexec.mainClass="at.hochschule.burgenland.bswe.algo.Main"
```

oder innerhalb der IDE:

* Klasse `Main.java` öffnen
* Methode `main()` ausführen

### Verwendung des Konsolenmenüs

Nach dem Start erscheint das Hauptmenü:

```
xxxxx Welcome to our flight-route planner xxxxx
1 - Calculate flightroute
2 - Sort flightroute
3 - Searching
9 - Exit
Please select under following options:
```

#### Option 1: Flugroute berechnen
1. Wählen Sie Option `1`
2. Geben Sie den IATA-Code des Abflughafens ein (z.B. `VIE`)
3. Geben Sie den IATA-Code des Zielflughafens ein (z.B. `JFK`)
4. Wählen Sie ein Optimierungskriterium:
   - `1` - Günstigste Route
   - `2` - Schnellste Route
   - `3` - Wenigste Umstiege
   - `4` - Langsamste Route
5. Die berechnete Route wird angezeigt

#### Option 2: Routen sortieren
1. Wählen Sie Option `2`
2. Geben Sie eine komma-separierte Liste von Routen-IDs ein (z.B. `1,2,3,4`)
3. Wählen Sie einen Sortieralgorithmus:
   - `1` - Stable (MergeSort)
   - `2` - Unstable (QuickSort)
4. Wählen Sie einen Komparator:
   - `1` - Preis (aufsteigend)
   - `2` - Dauer (aufsteigend)
   - `3` - Dauer (absteigend)
   - `4` - Umstiege (aufsteigend)
   - `5` - Kombiniert (Airline, Dauer, Umstiege)
5. Die sortierten Routen werden angezeigt

#### Option 3: Flüge suchen
1. Wählen Sie Option `3`
2. Wählen Sie einen Suchtyp:
   - `1` - Nach Abflughafen
   - `2` - Nach Zielflughafen
   - `3` - Nach Airline
   - `4` - Nach Flugnummer
3. Geben Sie den Suchbegriff ein
4. Alle passenden Flüge werden angezeigt

#### Option 9: Programm beenden
Wählen Sie Option `9`, um das Programm zu beenden.

## Algorithmen

### Pathfinding-Algorithmen

#### Dijkstra-Algorithmus
- **Verwendung**: Günstigste/schnellste Route
- **Komplexität**: O((V + E) log V)
- **Eigenschaften**: Exakte Lösung für positive Kantengewichte

#### Breadth-First Search (BFS)
- **Verwendung**: Route mit wenigsten Umstiegen
- **Komplexität**: O(V + E)
- **Eigenschaften**: Garantiert kürzesten Pfad in Bezug auf Kantenanzahl

### Sortieralgorithmen

#### MergeSort
- **Typ**: Stabil
- **Komplexität**: O(n log n) (best/average/worst)
- **Speicher**: O(n)
- **Verwendung**: Wenn relative Reihenfolge gleichwertiger Elemente wichtig ist

#### QuickSort
- **Typ**: Instabil
- **Komplexität**: O(n log n) average, O(n²) worst case
- **Speicher**: O(log n)
- **Verwendung**: Für maximale Performance bei großen Datenmengen

## Tests ausführen

Die Tests werden mit **JUnit** über Maven ausgeführt:

```bash
./mvnw test
```

Alle Komponenten des Systems sind mit umfassenden Unit-Tests abgedeckt.

## Abhängigkeiten

Das Projekt verwendet folgende Hauptbibliotheken:

- **OpenCSV**: CSV-Dateiverarbeitung
- **Lombok**: Reduzierung von Boilerplate-Code
- **Log4j2**: Logging
- **JUnit 5**: Unit-Testing

## CI / Pull Requests

Bei jedem Pull Request auf den Branch `master` werden automatisch **alle Unit Tests** über Maven ausgeführt.

## Dokumentation

Eine detaillierte Dokumentation befindet sich im `docs/` Verzeichnis:
- `gruppe2_uebung4_bswe_algo_ws2025.tex`: LaTeX-Dokumentation
- `gruppe2_uebung4_bswe_algo_ws2025.pdf`: Kompilierte PDF-Dokumentation

## Lizenz

Dieses Projekt ist unter der **MIT License** veröffentlicht.

---

© 2025 – Hochschule Burgenland, BSWE3B  
Autoren: Raja Abdulhadi, Alexander R. Brenner, Julia Michler
