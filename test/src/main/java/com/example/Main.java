package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import com.example.model.Category;
import com.example.model.Produkt;
import com.example.service.ProduktManager;
import com.example.service.ProduktService;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProduktManager manager = new ProduktService();
        Properties properties = new Properties();

        // init wird einmal am Anfang aufgerufen
        System.out.print("Pfad zur Property-Datei für DB-Verbindung eingeben: ");
        String pfad = scanner.nextLine().trim();
        try (FileInputStream fis = new FileInputStream(pfad)) {
            properties.load(fis);
            manager.init(properties);
            System.out.println("Datenbankverbindung erfolgreich initialisiert!");
        } catch (IOException e) {
            System.out.println("Fehler beim Laden der Property-Datei: " + e.getMessage());
            System.out.println("Programm wird beendet.");
            scanner.close();
            return;
        } catch (Exception e) {
            System.out.println("Fehler bei der Initialisierung: " + e.getMessage());
            System.out.println("Programm wird beendet.");
            scanner.close();
            return;
        }

        boolean running = true;

        while (running) {
            clearScreen();
            System.out.println("\n=== 📋 Menü ===");
            System.out.println("1. Produkt mit Produktnummer suchen");
            System.out.println("2. Produkte nach Titel durchsuchen");
            System.out.println("3. Kategoriebaum anzeigen");
            System.out.println("4. Beenden");
            System.out.print("👉 Auswahl (1–4): ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    clearScreen();
                    System.out.println("\n=== Produkt mit Produktnummer suchen ===\n");
                    System.out.print("Produktnummer eingeben: ");
                    String produktnummer = scanner.nextLine().trim();
                    Produkt p = manager.getProduktByProduktNummer(produktnummer);
                    if (p != null) {
                        System.out.println("Gefunden: " + p);
                    } else {
                        System.out.println("Kein Produkt gefunden.");
                    }
                    System.out.print("\n[Drücke Enter um zum Menü zurückzukehren]");
                    scanner.nextLine();
                    clearScreen();

                    break;

                case "2":
                    clearScreen();
                    System.out.println("\n=== Produkte mit Titel suchen ===\n");
                    System.out.print("Titel eingeben: ");
                    String titel = scanner.nextLine().trim();
                    if (!titel.contains("%")) {
                        titel = "%" + titel + "%";
                    }
                    List<Produkt> produkte = manager.getProdukteByTitelPattern(titel);
                    if (produkte.isEmpty()) {
                        System.out.println("Keine Produkte gefunden.");
                    } else {
                        System.out.println("Gefundene Produkte:");
                        for (Produkt prod : produkte) {
                            System.out.println(" - " + prod);
                        }
                    }
                    System.out.print("\n[Drücke Enter um zum Menü zurückzukehren]");
                    scanner.nextLine();
                    clearScreen();


                    
                    break;

                case "3":
                    clearScreen();
                    Category wurzel = manager.getCategoryTree();
                    String output = "\n=== 📂 Kategorienbaum ===\n" + wurzel.toString();
                    zeigeMitPaging(output, 30, scanner);
                    break;

                case "4":
                    manager.finish();
                    running = false;
                    break;

                default:
                    System.out.println("Ungültige Eingabe.");
            }
        }

        scanner.close();
        clearScreen();
        System.out.println("Programm beendet.");
    }


    public static void zeigeMitPaging(String content, int seitenLaenge, Scanner scanner) {
        String[] lines = content.split("\n");


        for (int i = 0; i < lines.length; i++) {
            System.out.println(lines[i]);

            if ((i + 1) % seitenLaenge == 0 && i + 1 < lines.length) {
                System.out.print("▶ Weiter mit [Enter], beenden mit [q]: ");
                if (!scanner.hasNextLine()) break;
                String input = scanner.nextLine().trim();

                // „Prompt-Zeile“ nach Eingabe löschen (durch Überschreiben)
                System.out.print("\033[1A"); // Cursor eine Zeile hoch
                System.out.print("\033[2K"); // Ganze Zeile löschen

                if (input.equalsIgnoreCase("q")) {
                    System.out.println("⏹ Ausgabe beendet.");
                    break;
                }
            }
        }
    }

    public static void clearScreen() {
        // ANSI-Clear für moderne Terminals (VS Code, Windows Terminal, etc.)
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }



}
