import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

public class Main {
    public static void main(String[] args) {

        // Startjahr für die Berechnungen
        int year = 2025;

        // Listen für Jahre, Tage und Labels
        List<Double> jahre = new ArrayList<>();
        List<Double> tage = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        // Formatierer für das Datum (Tag.Monat)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.");

        // Berechnung von Ostern jedes Jahr von 2025 bis 2049
        do {
            int a = year % 19;
            int b = year / 100;
            int c = year % 100;
            int d = b / 4;
            int e = b % 4;
            int f = (b + 8) / 25;
            int g = (b - f + 1) / 3;
            int h = (19 * a + b - d - g + 15) % 30;
            int i = c / 4;
            int k = c % 4;
            int l = (32 + 2 * e + 2 * i - h - k) % 7;
            int m = (a + 11 * h + 22 * l) / 451;
            int month = (h + l - 7 * m + 114) / 31;
            int day = ((h + l - 7 * m + 114) % 31) + 1;

            // Ostersonntag berechnen und in das Jahr und den Tag des Jahres speichern
            LocalDate ostern = LocalDate.of(year, month, day);

            // Jahr, Tag und formatiertes Datum hinzufügen
            jahre.add((double) year);
            tage.add((double) ostern.getDayOfYear());
            labels.add(ostern.format(formatter)); // Formatierte Anzeige von Tag.Monat

            year++;
        } while (year < 2200);

        // Listen in Arrays umwandeln
        double[] jahreArray = jahre.stream().mapToDouble(Double::doubleValue).toArray();
        double[] tageArray = tage.stream().mapToDouble(Double::doubleValue).toArray();

        // XY-Chart erstellen
        XYChart chart = new XYChartBuilder()
                .width(800) // Breite des Charts
                .height(600) // Höhe des Charts
                .title("Ostersonntag über die Jahre") // Titel des Charts
                .xAxisTitle("Jahr") // X-Achse: Jahr
                .yAxisTitle("Datum (Tag im Jahr)") // Y-Achse: Tag des Jahres
                .build();

        // Y-Achse formatieren (mit führenden Nullen)
        chart.getStyler().setYAxisDecimalPattern("000");

        // Legende ausblenden
        chart.getStyler().setLegendVisible(false);

        // Serie zum Chart hinzufügen (Ostern: Jahr vs. Tag im Jahr)
        chart.addSeries("Ostern", jahreArray, tageArray);

        // Chart anzeigen
        new SwingWrapper<>(chart).displayChart();
    }
}
