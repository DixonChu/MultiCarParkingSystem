package uk.ac.aber.dcs.dkc2.Person;

import uk.ac.aber.dcs.dkc2.Vehicle.Vehicle;

import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

/**
 * This class is to set attendant
 *
 * @author Dixon Chu
 * @version 1.1 (10/04/2019)
 */
public class Attendant {
    private String name;
    private Vehicle vehicle;
    private boolean available = true;


    /**
     * This is the constructor of the attendant
     *
     * @param name Name
     */
    public Attendant(String name) {
        this.name = name;
    }

    /**
     * This is the constructor of the attendant
     *
     * @param name    Attendant name
     * @param vehicle Vehicle license plate
     */
    public Attendant(String name, Vehicle vehicle) {
        this.name = name;
        this.vehicle = vehicle;
    }

    /**
     * This method is to get the attendant name
     *
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * This method is to set the attendant name
     *
     * @param name Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method is to get vehicle
     *
     * @return Vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * This method is to set the vehicle to attendant
     *
     * @param vehicle Vehicle
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * This method is to get the availability of the attendant
     *
     * @return True(available) or false(Not available)
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * This method is to set the availability of the attendant
     *
     * @param available True(available) or false(Not available)
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * This is the equal method if the attendant
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attendant attendant = (Attendant) o;
        return Objects.equals(name, attendant.name) &&
                Objects.equals(vehicle, attendant.vehicle);
    }

    /**
     * This method is to get vehicle license plate
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
     * This method is to get all the information of the attendant
     *
     * @return All the information of the attendant
     */
    @Override
    public String toString() {
        return "Attendant Name: " + name + '\n' +
                "Vehicle: " + vehicleLicensePlateSTR() + '\n' +
                "Available: " + available + '\n';
    }

    public void load(Scanner scan) {
        name = scan.nextLine();

        Vehicle vehicle = new Vehicle();
        vehicle.loadLicensePlate(scan);
        this.vehicle = vehicle;

        available = Boolean.parseBoolean(scan.nextLine());
    }

    public void save(PrintWriter pw) {
        pw.println(name);
        pw.println(vehicleLicensePlateSTR());
        pw.println(available);
    }


}
