package uk.ac.aber.dcs.dkc2.ParkingReceipt;

import uk.ac.aber.dcs.dkc2.Vehicle.Vehicle;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

/**
 * This class is to get and set the token
 *
 * @author Dixon Chu
 * @version 1.1 (10/04/2019)
 */
public class Token {
    private String tokenCode;


    public Token() {

    }

    /**
     * This is the constructor of the token class
     */
    public Token(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    /**
     * This method is to create a fifteen minutes timer
     *
     * @param vehicle vehicle
     */
    public void fifteenMinute(Vehicle vehicle) {
        if (vehicle != null) {
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0L;

            while (elapsedTime < 15 * 60 * 1000) { // 15min convert to seconds, seconds convert to millis
                elapsedTime = (new Date()).getTime() - startTime;

            }
        }
    }

    /**
     * This method is to get the token code
     *
     * @return Token code
     */
    public String getTokenCode() {
        return tokenCode;
    }

    /**
     * This method is to set the token code
     *
     * @param tokenCode Token code
     */
    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    /**
     * This method is to get all the information of the token
     *
     * @return All the information of the token
     */
    @Override
    public String toString() {
        return "Token{" +
                "tokenCode='" + tokenCode + '\'' +
                '}';
    }

    public void save(PrintWriter pw) {
        pw.println(tokenCode);
    }

    public void load(Scanner scan) {
        tokenCode = scan.nextLine();
    }
}
