package uk.ac.aber.dcs.dkc2.ParkingReceipt;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;

/**
 * This class is to set parking receipt which includes code, fee and time
 *
 * @author Dixon Chu
 * @version 1.1 (10/04/2019)
 */
public class ParkingReceipt {
    private String code;
    private Double fee;
    private LocalDateTime checkInTime;
    private int checkInHour;


    /**
     * This is the constructor of the parking receipt
     */
    public ParkingReceipt() {
        LocalDateTime checkInTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        checkInHour = checkInTime.getHour() - 1;

    }

    /**
     * This is the constructor of the parking receipt
     *
     * @param code        Four digits code
     * @param checkInHour Check in hour
     * @param fee         Parking fee
     */
    public ParkingReceipt(String code, int checkInHour, Double fee) {
        this.code = code;
        this.checkInHour = checkInHour;
        this.fee = fee;
    }

    /**
     * This method is to get code
     *
     * @return Code
     */
    public String getCode() {
        return code;
    }

    /**
     * This method is to set code
     *
     * @param code Code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * This method is to get the check in time
     *
     * @return Current date and time
     */
    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    /**
     * This methos is to set the check in time
     *
     * @param checkInTime Current date and time
     */
    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    /**
     * This method is to get the current check in Hour from the check in time
     *
     * @return Current hour
     */
    public int getCheckInHour() {
        return checkInHour;
    }

    /**
     * This method is to set the current check in Hour from the check in time
     *
     * @param checkInHour Current hour
     */
    public void setCheckInHour(int checkInHour) {
        this.checkInHour = checkInHour;
    }

    /**
     * This method is to get the fee
     *
     * @return Fee
     */
    public Double getFee() {
        return fee;
    }

    /**
     * This method is to set fee
     *
     * @param fee Fee
     */
    public void setFee(Double fee) {
        this.fee = fee;
    }


    /**
     * This is a equals method
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingReceipt that = (ParkingReceipt) o;
        return checkInHour == that.checkInHour &&
                Objects.equals(code, that.code) &&
                Objects.equals(fee, that.fee) &&
                Objects.equals(checkInTime, that.checkInTime);
    }


    /**
     * This method is to get all the information of the parking receipt
     *
     * @return Al the information of the parking receipt
     */
    @Override
    public String toString() {
        return "=====ParkingReceipt=====" + '\n' +
                "Code: " + code + '\n' +
                "Check In Hour(24-hour format): " + checkInHour + '\n' +
                "Fee: " + fee;
    }

    public void save(PrintWriter pw) {
        pw.println(code);
        pw.println(checkInHour);
    }

    public void load(Scanner scan) {
//        scan.nextLine();
        code = scan.nextLine();
        checkInHour = Integer.parseInt(scan.nextLine());
    }

}
