package corejava1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class TextFileSeparateFileHandler {
			   public static Map<String, String> loadCsvFileToMap(String filePath) {
			       Map<String, String> countryCapitalMap = new HashMap<>();
			       try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			           String line;
			           while ((line = reader.readLine()) != null) {
			               String[] parts = line.split(",", 2);
			               if (parts.length == 2) {
			                   countryCapitalMap.put(parts[0].trim(), parts[1].trim());
			               }
			           }
			       } catch (IOException e) {
			           System.err.println("Error reading file: " + e.getMessage());
			       }
			       return countryCapitalMap;
			   }
public static void writeFilteredCountries(String filePath, Map<String, String> countryCapitalMap, char startsWith) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        for (Map.Entry<String, String> entry : countryCapitalMap.entrySet()) {
            String country = entry.getKey();
            if (country.charAt(0) == Character.toUpperCase(startsWith)) {
                String line = "Country: " + country + ", Capital: " + entry.getValue();
                writer.write(line);
                writer.newLine();
            }
        }
    } catch (IOException e) {
        System.err.println("Error writing to file: " + e.getMessage());
    }
}
public static void main(String[] args) {
    String filePath = "C:\\\\Users\\\\anantharamar.a\\\\Downloads\\\\country 2.csv\\";
    Map<String, String> countryCapitalMap = loadCsvFileToMap(filePath);
    char startsWith = 'K'; // no country name start with s actually
    String outputFile = "D:\\K.txt";
    writeFilteredCountries(outputFile, countryCapitalMap, startsWith);
    System.out.println("Filtered countries have been written to the file.");
}
}