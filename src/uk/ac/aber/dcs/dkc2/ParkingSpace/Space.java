package uk.ac.aber.dcs.dkc2.ParkingSpace;

import uk.ac.aber.dcs.dkc2.Vehicle.Vehicle;

import java.io.PrintWriter;
import java.util.Scanner;


/**
 * This class is to set vehicle to spaces
 *
 * @author Dixon Chu
 * @version 1.1 (10/04/2019)
 */
public class Space {
    private String spaceName;
    private Vehicle vehicle;
    private boolean availableSpace = true;

    public Space() {

    }

    /**
     * This is the constructor to set the space name
     *
     * @param spaceName Space name
     */
    public Space(String spaceName) {
        this.spaceName = spaceName;
    }

    /**
     * This is the constructor to set the space name, vehicle, and is it available for parking
     *
     * @param spaceName      Space name
     * @param vehicle        Vehicle license plate and information
     * @param availableSpace Is space available
     */
    public Space(String spaceName, Vehicle vehicle, boolean availableSpace) {
        this.spaceName = spaceName;
        this.vehicle = vehicle;
        this.availableSpace = availableSpace;
    }

    /**
     * This method is to get the space name
     *
     * @return Space name
     */
    public String getSpaceName() {
        return spaceName;
    }

    /**
     * This method is to set the space name
     *
     * @param spaceName Space name
     */
    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    /**
     * This method is to get vehicle from the space
     *
     * @return Vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * This method is to set the vehicle to the space
     *
     * @param vehicle Vehicle
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * This method is to get the space availability
     *
     * @return True(available) or false(Occupied)
     */
    public boolean isAvailableSpace() {
        return availableSpace;
    }

    /**
     * This method is to set the space availability
     *
     * @param availableSpace True(available) or false(Occupied)
     */
    public void setAvailableSpace(boolean availableSpace) {
        this.availableSpace = availableSpace;
    }

    /**
     * This method is to get the space name for the vehicle
     *
     * @return Space name
     */
    public String spaceForVehicle() {
        return spaceName;
    }

    /**
     * This method is to get the space name from the vehicle
     *
     * @return Vehicle license plate
     */
    private String vehicleLicensePlateSTR() {
        String result;
        try {
            result = vehicle.strVehicleLicensePlate();
        } catch (Exception o) {
            result = "null";
        }
        return result;
    }

    /**
     * This method is to get all the information of the space
     *
     * @return Information of the space
     */
    @Override
    public String toString() {
        return "Space Name: " + spaceName + '\n' +
                "Vehicle License Plate: " + vehicleLicensePlateSTR() + '\n' +
                "Available Space: " + availableSpace + '\n';
    }

    public void load(Scanner scan) {
        spaceName = scan.nextLine();
        availableSpace = Boolean.parseBoolean(scan.nextLine());
        if (!availableSpace) {
            Vehicle vehicle = new Vehicle();
            vehicle.load(scan);
            this.vehicle = vehicle;
        }

    }

    public void save(PrintWriter pw) {
        pw.println(spaceName);
        pw.println(availableSpace);

    }

}
