package uk.ac.aber.dcs.dkc2.CarParkingSystem;

import uk.ac.aber.dcs.dkc2.ParkingReceipt.*;
import uk.ac.aber.dcs.dkc2.ParkingSpace.*;
import uk.ac.aber.dcs.dkc2.Person.*;
import uk.ac.aber.dcs.dkc2.Vehicle.*;
import uk.ac.aber.dcs.dkc2.Zone.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


/**
 * A collection of a car parking system
 *
 * @author Dixon Chu
 * @version 1.1 (10/04/2019)
 */
public class CarPark {
    private ArrayList<Attendant> attendants;
    private ArrayList<Attendant> availableAttendants;
    private Zone[] zones;


    /**
     * This is the constructor which creates a car park with attendants, available attendants, zone and space in it.
     *
     * @throws IOException
     */
    public CarPark() throws IOException {
        attendants = new ArrayList<>();
        availableAttendants = new ArrayList<>();
        zones = new Zone[5];

//        load("database.txt");


            for (int i = 0; i < zones.length; i++) {
                zones[i] = new Zone(i + 1);
                zones[i].addSpaceToCarPark();
            }

        loadHourlyRate(); // Load hourly rate file

    }

    /**
     * This method is to display the whole car park status
     */
    public void displayCarParkStatus() {
        int i = 0;
        for (Zone zone : zones) {
            System.out.println("===== Zone " + (i + 1) + " =====");
            for (Space space : zone.getSpaces()) {
                System.out.println(space);
            }
            i++;
            System.out.println('\n');
        }
    }

    /**
     * This method is to display a specific zone's space in the car park
     *
     * @param zone The type of the zone
     */
    public void showZone(int zone) {
        int zoneName = zone + 1;
        System.out.println("==================== Zone " + zoneName + " ====================");
        for (Space space : zones[zone].getSpaces()) {
            System.out.println(space);
        }
    }


    // ==========Vehicle==========

    /**
     * This method is to add vehicle into the car park
     *
     * @param vehicle The type of the vehicle
     * @param zone    The type of zone which different type of vehicle have a specific zone
     * @return The vehicle which set to a specific zone
     */
    public boolean addVehicle(Vehicle vehicle, int zone) {
        boolean storeVehicle;
        ParkingReceipt parkingReceipt;

        parkingReceipt = generateCode();
        vehicle.setParkingReceipt(parkingReceipt);

        storeVehicle = zones[zone].setVehicleToZone(vehicle); // Set vehicle to zone and set to space accordingly
        vehicle.setZone(zones[zone]); // Set the zone to vehicle

        return storeVehicle;
    }

    /**
     * This method is to remove vehicle from the car park
     *
     * @param licensePlate Vehicle license plate
     * @return Remove vehicle if true, else could not removed
     */
    public boolean removeVehicle(String licensePlate) {
        licensePlate = licensePlate.toUpperCase();

        for (Zone zone : zones) {
            for (Space space : zone.getSpaces()) {
                if (space.getVehicle() != null) {

                    if (space.getVehicle().getLicensePlate().equals(licensePlate)) {

                        try {
                            attendants.get(0).setAvailable(true); // Set attendant available back to true, only if there is attendant
                        } catch (Exception e) {
                        }
                        space.setVehicle(null); // Set vehicle to null from the space
                        space.setAvailableSpace(true); // Set available space to null
                        return true;
                    }
                }
//                else {
//                    System.out.println("Cannot remove " + licensePlate + " - not registered");
//                    return false;
//                }
            }
        }
        return false;
    }

    /**
     * This method is to search vehicle license plate
     *
     * @param licensePlate Vehicle license plate
     * @return Vehicle
     */
    public Vehicle searchForLicensePlate(String licensePlate) {
        Vehicle result = null;

        for (Zone zone : zones) {
            for (Space space : zone.getSpaces()) {
                if (space.getVehicle() != null) {
                    // Return vehicle information if the license plate user entered is same as the vehicle license plate
                    if (space.getVehicle().getLicensePlate().equals(licensePlate.toUpperCase())) {
                        result = space.getVehicle();
                        return result;
                    }
                }
            }
        }
        return result;
    }


    /**
     * This method is to ask the user whether they want attendant to collect their car.
     *
     * @param vehicle Type of vehicle
     */
    public void attendantCollectCar(Vehicle vehicle) {
        Scanner scan = new Scanner(System.in);
        String requireAttendant;
        String tokenCode;
        boolean validate;

        do {
            validate = false;
            System.out.println("Require attendant to collect vehicle?");
            System.out.println("1 - YES");
            System.out.println("2 - NO");

            requireAttendant = scan.nextLine();

            switch (requireAttendant.toUpperCase()) {
                case "1":
                    System.out.println("Enter token code for attendant");
                    tokenCode = scan.nextLine().toUpperCase();

                    // If token code exist, get attendant to collect vehicle
                    if (tokenCode.equals(vehicle.getToken().getTokenCode())) {
                        /* If there is attendant, get the first available attendant to collect vehicle, else let the
                        user to collect on their own*/
                        try {

                            //Assign attendant to collect vehicle
//                            availableAttendants.get(0).setAvailable(false); // Set the attendant available status to false
//                            availableAttendants.get(0).setVehicle(vehicle); // Set the first available attendant to the vehicle

                            StringBuilder result = new StringBuilder();
                            result.append("Attendant(" + availableAttendants.get(0).getName() + ") please collect ");
                            result.append(vehicle.getLicensePlate()); // Get vehicle license plate
                            result.append(" in zone ");
                            result.append(vehicle.getZone().getId()); // Get vehicle zone id
                            result.append(", ");
                            result.append(vehicle.getSpaceName()); // Get space name
                            System.out.println(result);

                            removeVehicle(vehicle.getLicensePlate()); //Remove vehicle from the car park
                        } catch (Exception o) {
                            System.out.println("Sorry, there is no attendant available right now");
                            System.out.println("Your vehicle is at " + "Zone " + vehicle.getZone().getId() + ", " + vehicle.getSpaceName());
                            removeVehicle(vehicle.getLicensePlate()); //Remove vehicle from the car park
                        }
                    } else {
                        System.out.println("Token code does not exist");
                    }
                    break;
                case "2":
                    System.out.println("Your vehicle is at " + "Zone " + vehicle.getZone().getId() + ", " + vehicle.getSpaceName());
                    removeVehicle(vehicle.getLicensePlate()); // Remove vehicle from the car park
                    break;
                default:
                    System.out.println("Wrong Option");
                    validate = true;
            }
            System.out.println("*Take Note: You only have 15 minutes to leave the car park from now on");
            System.out.println("*Seek for attendant if you could not exit");
        } while (validate);
    }


    /**
     * This method is to pass the vehicle to attendant when the user request for an attendant to park for them
     *
     * @param vehicle Type of vehicle
     * @param zone    Type of zone
     * @return True if vehicle is not null
     */
    public void giveVehicleToAttendant(Vehicle vehicle, int zone) {

            // If there is attendant in the car park and user request for, set the attendant to the vehicle
            try {
                availableAttendants.get(0).setAvailable(false);
                availableAttendants.get(0).setVehicle(vehicle); // Set the first available attendant to the vehicle
                System.out.println("Vehicle give to: " + availableAttendants.get(0).getName());
                availableAttendants.remove(0); // Remove the first available attendant since it is requested by the user
                chooseSpace(vehicle, zone); // Let the attendant choose which space to park
            } catch (Exception o) {
                System.out.println("Attendants are unavailable right now");
                addVehicle(vehicle, zone); // Add the vehicle to car park
            }
    }

    /**
     * This method is to let the attendant to choose a specific space in a specific zone
     *
     * @param vehicle Type of vehicle
     * @param zone    Type of zone
     * @return True if vehicle is successfully added to space
     */
    private boolean chooseSpace(Vehicle vehicle, int zone) {
        String spaceName;
        ParkingReceipt parkingReceipt;

        showZone(zone); // Show the specific zone of the car park

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter space name");
        spaceName = scan.nextLine().toUpperCase();

        for (Space space : zones[zone].getSpaces()) {

            if (space.getSpaceName().equals(spaceName)) {
                // If space is available, then set the vehicle to space
                if (space.isAvailableSpace()) {
                    parkingReceipt = generateCode(); // Set the generated code to parking receipt
                    vehicle.setParkingReceipt(parkingReceipt); // Set parking receipt to vehicle

                    space.setVehicle(vehicle); // Set space to vehicle
                    space.setAvailableSpace(false); // Set the status of the space to not available(false)
                    vehicle.setZone(zones[zone]); // Set zone to vehicle
                    vehicle.setSpaceName(spaceName);

                    System.out.println("*Customer will be charge " + "£" + zones[zone].getHourlyRate() + " per hour*");
                    System.out.println("*If you are a disable, you have the fee halved*");

                } else {
                    System.out.println("Space occupied, choose another space");
                    chooseSpace(vehicle, zone); // Choose another space
                }
                return true;
            }
        }
        return false;
    }


    // ==========Attendant==========

    /**
     * This method is to add new attendant to the car park
     *
     * @param attendant Attendant name
     * @return Attendant
     */
    public Attendant addAttendant(Attendant attendant) {
        attendants.add(attendant); // Add attendant to attendants array list
        availableAttendants.add(attendant); // Add attendant to available attendants array list
        return attendant;
    }

    /**
     * This method is to remove attendant from the car park
     *
     * @param attendantName Attendant name
     * @return True if attendant exist
     */
    public boolean removeAttendant(String attendantName) {
        for (Attendant attendant : attendants) {
            if (attendant.getName() != null) {
                /*If attendant name that is going to be remove is same in the attendant array list,
                 remove attendant, else attendant is not exist*/
                if (attendantName.equals(attendant.getName())) {
                    attendants.remove(attendant); // Remove attendant from the array list
                    System.out.println("Removed " + attendantName);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method is to display all attendant in the car park
     */
    public void showAllAttendant() {
        for (Attendant attendant : attendants) {
            System.out.println(attendant);
        }
    }

    /**
     * This method is to display all available attendant in the car park
     */
    public void showAllAvailableAttendant() {
        for (Attendant attendant : availableAttendants) {
            System.out.println(attendant);
        }
    }

    /**
     * This method is to add the attendant back to the booth
     *
     * @param attendant Attendant name
     */
    public void addAttendantToBooth(Attendant attendant) {
        availableAttendants.add(attendant);
    }


    /**
     * This method is to get the attendant that helped a customer to park the car
     *
     * @param vehicle Type of vehicle
     * @return Attendant
     */
    public Attendant getAttendantWithVehicle(Vehicle vehicle) {
        for (Attendant attendant : attendants) {
            if (attendant.getVehicle() != null) {
                if (attendant.getVehicle().getLicensePlate().equals(vehicle.getLicensePlate())) {
                    System.out.println(attendant);
                    return attendant;
                }
            }
        }
        return null;
    }


    // ==========Generate Code&&Token==========

    /**
     * This method is to generate a four digit number code with a character 'C' added to the front
     *
     * @return Parking receipt
     */
    private ParkingReceipt generateCode() {
        Random rand = new Random();
        ParkingReceipt parkingReceipt = new ParkingReceipt();

        // Generate random integers in range 0 to 10000
        String code = String.format("%04d", rand.nextInt(10000)); // 4 digits
        parkingReceipt.setCode("C" + code); // Example code: C1294

        System.out.println("Here is your code to pay later: " + parkingReceipt.getCode());

        return parkingReceipt;
    }

    /**
     * This method is to generate a four digit number code
     *
     * @param vehicle Type of vehicle
     * @return Token
     */
    public Token generateToken(Vehicle vehicle) {
        Random rand = new Random();
        Token token = new Token();

        // Generate random integers in range 0 to 10000 but only 4 digits
        String tokenCode = String.format("%04d", rand.nextInt(10000));

        if (vehicle != null) {
            vehicle.setToken(token); // Set token to vehicle
            token.setTokenCode(tokenCode); // Set code to token code
            System.out.println("You need this token(" + tokenCode + ") to exit later");
//            token.fifteenMinute(vehicle); // if user is still in the car park after 15 min, they need to seek for attendant
        }

        return token;
    }


// ==========Set Vehicle Size==========

    /**
     * This method is to set the limit size to the standard vehicle
     *
     * @param vehicle Standard vehicle
     */
    public void standardSize(Vehicle vehicle) {
        vehicle.setMaxHeight(2.0);
        vehicle.setMinHeight(0.0);
        vehicle.setMaxLength(5.0);
        vehicle.setMinLength(0.0);
    }

    /**
     * This method is to set the limit size to the higher vehicle
     *
     * @param vehicle Higher vehicle
     */
    public void higherSize(Vehicle vehicle) {
        vehicle.setMaxHeight(3.0);
        vehicle.setMinHeight(2.0);
        vehicle.setMaxLength(5.0);
        vehicle.setMinLength(0.0);
    }

    /**
     * This method is to set the limit size to the longer vehicle
     *
     * @param vehicle Longer vehicle
     */
    public void longerSize(Vehicle vehicle) {
        vehicle.setMaxHeight(3.0);
        vehicle.setMinHeight(0.0);
        vehicle.setMaxLength(6.0);
        vehicle.setMinLength(5.1);
    }

    /**
     * This method is to set the limit size to the coaches vehicle
     *
     * @param vehicle Coaches vehicle
     */
    public void coachesSize(Vehicle vehicle) {
        vehicle.setMaxHeight(0.0);
        vehicle.setMinHeight(0.0);
        vehicle.setMaxLength(15.0);
        vehicle.setMinLength(0.0);
    }

    /**
     * This method is to set the limit size to the motorbike vehicle
     *
     * @param vehicle Motorbike vehicle
     */
    // There is no limit size for the motorbike
    public void motorbike(Vehicle vehicle) {
        vehicle.setMaxHeight(null);
        vehicle.setMinHeight(null);
        vehicle.setMaxLength(null);
        vehicle.setMinLength(null);
    }


    //  ==========HourlyRate==========
    private void loadHourlyRate() throws IOException {
        try {
            Scanner file = new Scanner(new File("HourlyRate.txt"));
            int i = 0;
            while (i < 5) {
                zones[i].setHourlyRate(Float.parseFloat(file.nextLine()));
                i++;
            }
            file.close();
        } catch (FileNotFoundException o) {
            FileWriter fileWriter = new FileWriter("HourlyRate.txt");

            fileWriter.write(1.0 + "\n"); // Standard Size Vehicle
            fileWriter.write(1.5 + "\n"); // Higher Size Vehicle
            fileWriter.write(2.0 + "\n"); // Longer Size Vehicle
            fileWriter.write(1.0 + "\n"); // Coaches
            fileWriter.write(0.5 + "\n"); // Motorbike

            fileWriter.close();
            loadHourlyRate();
        }
    }

    public void saveHourlyRate() throws IOException {
        FileWriter fileWriter = new FileWriter("HourlyRate.txt");

        for (int i = 0; i < zones.length - 1; i++) {
            fileWriter.write(zones[i].getHourlyRate() + "\n");
        }
        fileWriter.write(zones[4].getHourlyRate() + "");
        fileWriter.close();
    }

    public void save(String filename) throws IOException {
        PrintWriter outfile = new PrintWriter(new FileWriter(filename));
        outfile.println("=====Attendants=====");
        outfile.println(attendants.size());
        for (Attendant attendant : attendants) {
            attendant.save(outfile);
        }

        outfile.println("=====Available Attendants=====");
        outfile.println(availableAttendants.size());
        for (Attendant attendant : availableAttendants) {
            attendant.save(outfile);
        }

        outfile.println("=====Zone=====");
        outfile.println(zones.length);
        for (Zone zone : zones) {
            outfile.println(zone.getSpaces().size());
            outfile.println();
            for (Space space : zone.getSpaces()) {
                space.save(outfile);
                if (space.getVehicle() != null) {
                    space.getVehicle().save(outfile);
                }
            }
        }
        outfile.close();
    }


    public void load(String fileName) throws FileNotFoundException {
        try (FileReader fr = new FileReader(fileName);
             BufferedReader br = new BufferedReader(fr);
             Scanner infile = new Scanner(br)) {

            attendants.clear();
            availableAttendants.clear();

            infile.useDelimiter("\r?\n|\r");
            infile.nextLine();
            Attendant attendant;

            int amountOfAttendant = Integer.parseInt(infile.nextLine());
            for (int i = 0; i < amountOfAttendant; i++) {
                attendant = new Attendant(fileName);
                attendant.load(infile);
                attendants.add(attendant);
            }
            infile.nextLine();
            int amountOfAvailableAttendant = Integer.parseInt(infile.nextLine());
            for (int i = 0; i < amountOfAvailableAttendant; i++) {
                attendant = new Attendant(fileName);
                attendant.load(infile);
                availableAttendants.add(attendant);
            }

            infile.nextLine();
            int availableZone = Integer.parseInt(infile.nextLine());
            Zone zone;
            for (int i = 0; i < availableZone; i++) {
                zone = new Zone(i + 1);
                int availableSpaces = Integer.parseInt(infile.nextLine());
                infile.nextLine();

                for (int aS = 0; aS < availableSpaces; aS++) {
                    Space space = new Space();
                    space.load(infile);
                    zone.addSpaces(space);
                    zones[i] = zone;

                }
            }

        } catch (IOException e) {
            System.out.println("Creating a file name 'database.txt'");
        }

    }


}
