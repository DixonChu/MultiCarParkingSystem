package uk.ac.aber.dcs.dkc2.Zone;

import uk.ac.aber.dcs.dkc2.ParkingSpace.Space;
import uk.ac.aber.dcs.dkc2.Vehicle.Vehicle;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is to set zone
 *
 * @author Dixon Chu
 * @version 1.1 (10/04/2019)
 */
public class Zone {
    private ArrayList<Space> spaces;
    private int id;
    private float hourlyRate;

    public Zone() {

    }

    /**
     * This is the constructor of the zone
     *
     * @param id Zone id
     */
    public Zone(int id) {
        this.id = id;
        spaces = new ArrayList<>();

    }

    public void addSpaceToCarPark() {

        // Create 20 spaces in each zone
        for (int i = 0; i < 20; i++) {
            spaces.add(new Space("SZ" + id + "-" + (i + 1)));
        }
    }

    /**
     * This method is to get spaces
     *
     * @return Space
     */
    public ArrayList<Space> getSpaces() {
        return spaces;
    }

    /**
     * This method is to set spaces
     *
     * @param spaces Space
     */
    public void setSpaces(ArrayList<Space> spaces) {
        this.spaces = spaces;
    }

    /**
     * This method is to get the zone id
     *
     * @return zone id
     */
    public int getId() {
        return id;
    }

    /**
     * This method is to set the zone id
     *
     * @param id Zone id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method is to get the hourly rate
     *
     * @return Hourly rate
     */
    public float getHourlyRate() {
        return hourlyRate;
    }

    /**
     * This method is to set the hourly rate
     *
     * @param hourlyRate Hourly rate
     */
    public void setHourlyRate(float hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    /**
     * This method is to set vehicle to zone
     *
     * @param vehicle Vehicle
     * @return True if set
     */
    public boolean setVehicleToZone(Vehicle vehicle) {
        if (vehicle != null) {
            for (Space space : spaces) {
                if (space.isAvailableSpace()) { // If space is available
                    space.setAvailableSpace(false); // Set available to false
                    space.setVehicle(vehicle); // Set vehicle to space

                    vehicle.setSpaceName(space.spaceForVehicle()); // Set space name to vehicle

                    System.out.println("Here is your space: " + "Zone " + zoneForVehicle() + ", " + space.spaceForVehicle());
                    System.out.println("*You will be charge " + "£" + hourlyRate + " per hour*");
                    System.out.println("*If you are a disable, you have the fee halved*");

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method is to get the zone id for the vehicle
     *
     * @return Zone id
     */
    private int zoneForVehicle() {
        return id;
    }


    public void addSpaces(Space space) {
        spaces.add(space);

    }


    /**
     * This method is to get all the information of the zone
     *
     * @return All the information of the zone
     */
    @Override
    public String toString() {
        return "ID: " + id + '\n' +
                "Hourly Rate: " + hourlyRate;
    }

    public void save(PrintWriter pw) {
        pw.println(id);
        pw.println(hourlyRate);
    }

    public void load(Scanner scan) {
        id = Integer.parseInt(scan.nextLine());
        hourlyRate = Float.parseFloat(scan.nextLine());
    }
}
