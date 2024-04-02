package Assignment;

import java.util.ArrayList;
import java.util.List;

public class VehicleMain {
 	   public static void main(String[] args) {
 	       List<VehicleClass> vehicles = new ArrayList<>();
 	       vehicles.add(new VehicleClass(1, "Car", true, 5));
 	       vehicles.add(new VehicleClass(2, "Bike", true, 2));
 	       VehicleRentalSystem rentalSystem = new VehicleRentalSystem(vehicles);
 	       RentalRequest request = new RentalRequest("Car", 3);
 	       try { rentalSystem.processRentalRequest(request); }
 	       catch (VehicleUnavailableException | InvalidRentalPeriodException e) { System.out.println("Exception: " + e.getMessage()); }
 	   }
 	}


