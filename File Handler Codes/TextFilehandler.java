package corejava1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class TextFilehandler {
	
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
	   
public static void main(String[] args) {
	       String filePath = "C:\\Users\\anantharamar.a\\Downloads\\country 2.csv\""; // Change to the path of your CSV file
	       Map<String, String> countryCapitalMap = TextFilehandler.loadCsvFileToMap(filePath);
	       for (Map.Entry<String, String> entry : countryCapitalMap.entrySet()) {
	           System.out.println("Country: " + entry.getKey() + ", Capital: " + entry.getValue());
	       }
	   }
	}

