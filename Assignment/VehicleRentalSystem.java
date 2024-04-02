package Assignment;

import java.util.List;

public class VehicleRentalSystem {
	private List<VehicleClass> vehicles;

	public VehicleRentalSystem(List<VehicleClass> vehicles) {
		super();
		this.vehicles = vehicles;
	}
	
	public void addVehicle(VehicleClass vehicle) {
		vehicles.add(vehicle);
		System.out.println("Vehicle Added");
    }

    public void processRentalRequest(RentalRequest request) throws InvalidRentalPeriodException, VehicleUnavailableException {
    	try {
			if (request.rentingdays < 1) throw new InvalidRentalPeriodException("Invalid rental period.");
		} catch (InvalidRentalPeriodException e) {
			e.printStackTrace();
		}
        for (VehicleClass vehicle : vehicles) {
            if (vehicle.type.equals(request.getType())&& vehicle.isAvaliable()&& vehicle.getDays() >= request.rentingdays) {
                if(vehicle.isAvaliable()) {
                	int availdays=vehicle.getDays();
                	 availdays-= request.rentingdays;
                }
                
                double totalCharge = request.rentingdays * 30.0; // Assuming a flat rate of $30 per day
                System.out.println("Rental Confirmation:\n- Vehicle ID: " +
 vehicle.getId()
 + "\n- Rental Period: " + request.rentingdays + " days\n- Total Rental Charge: $" + totalCharge);
                return;
            }
        }
    }
    
    
}
	
	
	
	


