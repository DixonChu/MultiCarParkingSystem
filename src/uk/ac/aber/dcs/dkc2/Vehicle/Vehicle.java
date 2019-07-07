package uk.ac.aber.dcs.dkc2.Vehicle;

import uk.ac.aber.dcs.dkc2.ParkingReceipt.*;
import uk.ac.aber.dcs.dkc2.ParkingSpace.Space;
import uk.ac.aber.dcs.dkc2.Zone.Zone;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This is the super class of standard, higher, longer, coaches and motorbike vehicle
 *
 * @author Dixon Chu
 * @version 1.1 (10/04/2019)
 */
public class Vehicle {
    private String licensePlate;
    private Zone zone;
    private String spaceName;
    private ParkingReceipt parkingReceipt;
    private Token token;
    private boolean disable;
    private Double maxHeight;
    private Double maxLength;
    private Double minHeight;
    private Double minLength;


    public Vehicle() {

    }

    /**
     * This is the constructor of vehicle
     */
    public Vehicle(String licensePlate, Zone zone, String spaceName, ParkingReceipt parkingReceipt, boolean disable) {
        this.licensePlate = licensePlate;
        this.zone = zone;
        this.parkingReceipt = parkingReceipt;
        this.disable = disable;
        this.spaceName = spaceName;
    }

    /**
     * This is the method to get vehicle license plate
     *
     * @return Vehicle license plate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * This method is to set vehicle license plate
     *
     * @param licensePlate Vehicle license plate
     */
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    /**
     * This method is to get zone of the vehicle
     *
     * @return Zone
     */
    public Zone getZone() {
        return zone;
    }

    /**
     * This method is to set zone to the vehicle
     *
     * @param zone Zone id
     */
    public void setZone(Zone zone) {
        this.zone = zone;
    }

    /**
     * This method is to get the parking receipt
     *
     * @return Parking receipt information
     */
    public ParkingReceipt getParkingReceipt() {
        return parkingReceipt;
    }

    /**
     * This method is to set the parking receipt
     *
     * @param parkingReceipt Parking receipt
     */
    public void setParkingReceipt(ParkingReceipt parkingReceipt) {
        this.parkingReceipt = parkingReceipt;
    }

    /**
     * This method is to get is the driver a disabled
     *
     * @return True(disabled) or false(not disabled)
     */
    public boolean isDisable() {
        return disable;
    }

    /**
     * This method is to set the driver is a disabled or not
     *
     * @param disable True(disabled) or false(not disabled)
     */
    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    /**
     * This method is to set the token to the vehicle
     *
     * @param token Token code
     */
    public void setToken(Token token) {
        this.token = token;
    }

    /**
     * This method is to get the token
     *
     * @return Token code
     */
    public Token getToken() {
        return token;
    }

    /**
     * This method is to get the vehicle minimum height
     *
     * @return Minimum height
     */
    public Double getMinHeight() {
        return minHeight;
    }

    /**
     * This method is to set the vehicle minimum height
     *
     * @return Minimum height
     */
    public void setMinHeight(Double minHeight) {
        this.minHeight = minHeight;
    }

    /**
     * This method is to get the vehicle minimum length
     *
     * @return Minimum length
     */
    public Double getMinLength() {
        return minLength;
    }

    /**
     * This method is to set the vehicle minimum length
     *
     * @return Minimum length
     */
    public void setMinLength(Double minLength) {
        this.minLength = minLength;
    }

    /**
     * This method is to get the vehicle maximum height
     *
     * @return Maximum height
     */
    public Double getMaxHeight() {
        return maxHeight;
    }

    /**
     * This method is to set the vehicle maximum height
     *
     * @return Maximum height
     */
    public void setMaxHeight(Double maxHeight) {
        this.maxHeight = maxHeight;
    }

    /**
     * This method is to get the vehicle maximum length
     *
     * @return Maximum length
     */
    public Double getMaxLength() {
        return maxLength;
    }

    /**
     * This method is to set the vehicle maximum length
     *
     * @return Maximum length
     */
    public void setMaxLength(Double maxLength) {
        this.maxLength = maxLength;
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
     * @return Space name
     */
    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    /**
     * This method is to get vehicle license plate
     *
     * @return Vehicle license plate
     */
    public String strVehicleLicensePlate() {
        return licensePlate;
    }

    /**
     * This method is to get all the information of the vehicle
     *
     * @return Information of the vehicle
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(">>>Vehicle Information<<<" + '\n');
        result.append("License Plate: " + licensePlate + '\n');
        result.append("Zone: " + zone + '\n');
        result.append("Space: " + spaceName + '\n');
        result.append(parkingReceipt);
        result.append("Token: " + token + '\n');
        result.append("Disable: " + disable + '\n');
        result.append("Min Height: " + minHeight + '\n');
        result.append("Max Height: " + maxHeight + '\n');
        result.append("Min Length: " + minLength + '\n');
        result.append("Max Length: " + maxLength + '\n');

        return String.valueOf(result);
    }

    public void save(PrintWriter pw) {
        pw.println(licensePlate);
        pw.println(spaceName);
        zone.save(pw);
        parkingReceipt.save(pw);
        if (token != null) {
            token.save(pw);
        } else {
            pw.println("null");
        }
        pw.println(disable);
        pw.println(minHeight);
        pw.println(maxHeight);
        pw.println(minLength);
        pw.println(maxLength);


    }

    public void load(Scanner scan) {
        licensePlate = scan.nextLine();
        spaceName = scan.nextLine();

        Zone zone = new Zone();
        zone.load(scan);
        this.zone = zone;

        ParkingReceipt parkingReceipt = new ParkingReceipt();
        parkingReceipt.load(scan);
        this.parkingReceipt = parkingReceipt;

        Token token = new Token();
        token.load(scan);

        if (!token.equals("null")) {
            this.token = token;
        }

        disable = Boolean.parseBoolean(scan.nextLine());
        minHeight = Double.valueOf(scan.nextLine());
        maxHeight = Double.valueOf(scan.nextLine());
        minLength = Double.valueOf(scan.nextLine());
        maxLength = Double.valueOf(scan.nextLine());
    }

    public void saveLicensePlate(PrintWriter pw) {
        pw.println(licensePlate);
    }

    public void loadLicensePlate(Scanner scan) {
        licensePlate = scan.nextLine();
    }

}
