import uk.ac.aber.dcs.dkc2.CarParkingSystem.*;
import uk.ac.aber.dcs.dkc2.Person.Attendant;
import uk.ac.aber.dcs.dkc2.Vehicle.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * This is the main application of the program. has a command line menu
 *
 * @author Dixon Chu
 * @version 1.1 (10/04/2019)
 */
public class Application {
    private static final String FILENAME = "database.txt";
    private Scanner scan;
    private CarPark carPark;


    public Application() throws IOException {
        scan = new Scanner(System.in);
        carPark = new CarPark();
        try {
            carPark.load(FILENAME);
        } catch (IOException o) {
            System.out.println("Creating a file name 'database.txt'");
        }
    }


    /*===============MENU===============*/
    private void customerMenu() {
        System.out.println("1 - Register Vehicle And Get A Parking Space");
        System.out.println("2 - Collect Car");
        System.out.println("3 - View Vehicle Parking Receipt");
//        System.out.println("4 - Admin Access");
        System.out.println("Q - Quit");
    }

    private void adminMenu() {
        System.out.println("1 - Show Car Park Status");
        System.out.println("2 - Add New Attendant");
        System.out.println("3 - Remove Attendant");
        System.out.println("4 - View All Attendant");
        System.out.println("5 - View All Available Attendant");
        System.out.println("6 - Search For Vehicle");
        System.out.println("7 - Remove Vehicle");
//        System.out.println("8 - Direct to Customer Menu");
        System.out.println("Q - Quit");
    }

    private void vehicleMenu() {
        System.out.println("1 - Standard Size Vehicle   *(Max Height: 2 meter; Max Length: 5 meter)");
        System.out.println("2 - Higher Size Vehicle     *(Min Height: 2 meter, Max Height: 3 meter; Max Height: 5 meter )");
        System.out.println("3 - Longer Size Vehicle     *(Max Height: 3 meter; Min Length: 5.1 meter, Max Length: 6 meter)");
        System.out.println("4 - Coaches                 *(Max Length: 15 meter)");
        System.out.println("5 - Motorbike               *(Height and Length are not limited)");
    }

    private void zoneMenu() {
        System.out.println("Which zone do you want to display?");
        System.out.println("1 - Zone 1");
        System.out.println("2 - Zone 2");
        System.out.println("3 - Zone 3");
        System.out.println("4 - Zone 4");
        System.out.println("5 - Zone 5");
        System.out.println("A - All zones");
    }
    /*==================================*/

    /**
     * This method is to ask whether the user is customer or admin
     */
    private void run() throws IOException {
        scan = new Scanner(System.in);
        String role;
        boolean validate;

        do {
            validate = false;
            System.out.println("1 - Customer");
            System.out.println("2 - Admin");
            System.out.println("Q - Quit");

            role = scan.nextLine();

            switch (role.toUpperCase()) {
                case "1":
                    runMenuForCustomer();
                    break;
                case "2":
                    runMenuForAdmin();
                    break;
                case "Q":
                    saveToFile();
                    System.out.println("Have A Nice Day");
                    break;
                default:
                    System.out.println("Wrong Option");
                    validate = true;
            }
        } while (validate);
    }


    /**
     * This method is to run menu for customer
     */
    private void runMenuForCustomer() throws IOException {
        String customerResponse;
        scan = new Scanner(System.in);

        do {
            customerMenu();
            customerResponse = scan.nextLine().toUpperCase();

            switch (customerResponse) {
                case "1": // Add Vehicle
                    try {
                        addVehicle();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "2": // Collect Vehicle and Pay Ticket Fee
                    readyToLeave();
                    break;
                case "3": // Parking Receipt
                    viewParkingTicket();
                    break;
//                case "4": // Admin Access
//                    runMenuForAdmin();
//                    break;
                case "Q":
                    System.out.println("********* Have A Nice Day ********");
                    run();
                    break;
                default:
                    System.out.println("Wrong Option");
            }
        } while (!customerResponse.equals("Q"));
    }

    /**
     * This method is to run menu for admin
     */
    private void runMenuForAdmin() throws IOException {
        String adminResponse;
        scan = new Scanner(System.in);
        do {
            adminMenu();
            adminResponse = scan.nextLine().toUpperCase();

            switch (adminResponse) {
                case "1": // Display Car Park Status
                    printCarParkStatus();
                    break;
                case "2": // Add New Attendant
                    addNewAttendant();
                    break;
                case "3": // Fire Attendant
                    removeAttendant();
                    break;
                case "4": // Display All Attendant
                    viewAllAttendant();
                    break;
                case "5": // Display All Free Attendant
                    viewAllAvailableAttendant();
                    break;
                case "6": // Find Vehicle
                    searchVehicle();
                    break;
                case "7": // Remove Vehicle When Customer Use More Then 15minutes to Leave
                    removeVehicleManually();
                    break;
//                case "8": // Switch To Customer Menu
//                    runMenuForCustomer();
//                    break;
                case "Q": // Exit Application
                    System.out.println("********* Have A Nice Day ********");
                    run();
                    break;
                default:
                    System.out.println("Wrong Option");
            }
        } while (!(adminResponse.equals("Q")));
    }

    /**
     * This method is to add vehicle to the car park
     */
    private void addVehicle() throws IOException {
        String userResponse;
        String userInput;
        boolean validate;
        Vehicle vehicle;
        scan = new Scanner(System.in);


        do {
            validate = false;
            System.out.println("Choose Vehicle Type");
            vehicleMenu(); // Print vehicle list menu

            userInput = scan.nextLine();

            switch (userInput.toUpperCase()) {
                case "1":
                    vehicle = new Standard(); // New standard size vehicle
                    carPark.standardSize(vehicle); // Set vehicle size
                    askLicensePlate(vehicle); // Get license plate
                    disable(vehicle); // Ask driver is he/she disabled

                    do {
                        validate = false;

                        System.out.println("Where do you want to park?");
                        System.out.println("1 - Inside");
                        System.out.println("2 - Outside");

                        userResponse = scan.nextLine();

                        switch (userResponse.toUpperCase()) {
                            case "1": // Ask driver does they require attendant
                                requireAttendant(vehicle, 3); // Set vehicle to zone 4
                                break;
                            case "2":
                                requireAttendant(vehicle, 0); // Set vehicle to zone 1
                                break;
                            default:
                                System.out.println("Wrong Option");
                                validate = true;
                        }
                    } while (validate);
                    break;
                case "2":
                    vehicle = new Higher(); // New higher size vehicle
                    carPark.higherSize(vehicle); // Set vehicle size
                    askLicensePlate(vehicle);
                    disable(vehicle);
                    requireAttendant(vehicle, 0); // Set vehicle to zone 1
                    break;
                case "3":
                    vehicle = new Longer(); // New longer size vehicle
                    carPark.longerSize(vehicle);
                    askLicensePlate(vehicle);
                    disable(vehicle);
                    requireAttendant(vehicle, 1); // Set vehicle to zone 2
                    break;
                case "4":
                    vehicle = new Coaches(); // New coaches vehicle
                    carPark.coachesSize(vehicle);
                    askLicensePlate(vehicle);
                    disable(vehicle);
                    // If add vehicle to zone 3 and it is full, print 'sorry, it's fully occupied'
                    if (carPark.addVehicle(vehicle, 2)) {
                    } else {
                        System.out.println("Sorry, spaces are fully occupied");
                    }
                    break;
                case "5":
                    vehicle = new Motorbike(); // New motorbike vehicle
                    carPark.motorbike(vehicle);
                    askLicensePlate(vehicle);
                    disable(vehicle);
                    // If add vehicle to zone 5 and it is full, print 'sorry, it's fully occupied'
                    if (carPark.addVehicle(vehicle, 4)) {
                    } else {
                        System.out.println("Fully Occupied");
                    }
                    break;
                default:
                    System.out.println("Wrong Option");
                    validate = true;
            }

        } while (validate);

    }

    private void saveToFile() throws IOException {
        carPark.save(FILENAME);
    }

    /**
     * This method is to ask customer whether they require for attendant
     *
     * @param vehicle Vehicle type
     * @param zone    Zone id
     * @return
     */
    private boolean requireAttendant(Vehicle vehicle, int zone) {
        Scanner scan = new Scanner(System.in);
        String response;
        boolean validate;

        do {
            validate = false;
            System.out.println("Require Attendant?");
            System.out.println("1 - YES");
            System.out.println("2 - NO");

            response = scan.nextLine();

            switch (response.toUpperCase()) {
                case "1": // Required Attendant
                    carPark.giveVehicleToAttendant(vehicle, zone); // Give the vehicle to attendant and choose space
                    break;
                case "2": // Not Required Attendant
                    carPark.addVehicle(vehicle, zone); // Add vehicle to space
                    break;
                default:
                    System.out.println("Wrong Option");
                    validate = true;
            }
        } while (validate);
        return false;
    }

    /**
     * This methos is to ask customer whether they are disabled
     *
     * @param vehicle Vehicle type
     */
    private void disable(Vehicle vehicle) {
        boolean validate;
        String userResponse;

        do {
            validate = false;

            System.out.println("Are you disable");
            System.out.println("1 - YES");
            System.out.println("2 - NO");

            userResponse = scan.nextLine();

            switch (userResponse.toUpperCase()) {
                case "1": // Disable
                    vehicle.setDisable(true);
                    break;
                case "2": // Not Disable
                    vehicle.setDisable(false);
                    break;
                default:
                    System.out.println("Wrong Option");
                    validate = true;
            }

        } while (validate);

    }

    /**
     * This method is to ask vehicle license plate
     *
     * @param vehicle Vehicle type
     */
    private void askLicensePlate(Vehicle vehicle) {
        boolean validate;
        Vehicle v;
        String licensePlate;

        do {
            validate = false;
            System.out.println("Enter License Plate");
            licensePlate = scan.nextLine().toUpperCase();

            v = carPark.searchForLicensePlate(licensePlate); // Search for license plate

            // If the user enter the license plate that exist, enter license plate again
            if (v != null) {
                System.out.println("Vehicle Exist");
                validate = true;
            }
        } while (validate);

        vehicle.setLicensePlate(licensePlate); // Set license plate to vehicle
    }

    /**
     * This method is to view the parking receipt of the vehicle
     */
    private void viewParkingTicket() {
        String licensePlate;
        System.out.println("Enter License Plate");
        licensePlate = scan.nextLine().toUpperCase();

        Vehicle vehicle = carPark.searchForLicensePlate(licensePlate); // Search for license plate

        // If license plate exist, print the receipt of the vehicle to the driver
        if (vehicle != null) {
            System.out.println(vehicle.getParkingReceipt());
        } else {
            System.out.println("Vehicle Does Not Exist");
        }
    }

    /**
     * This method is to remove vehicle from the car park and process payment
     */
    private void readyToLeave() {
        String licensePlate;
        Vehicle vehicle;
        Attendant attendant;

        String code;
        boolean matchCode;
        boolean paymentSuccess;
        boolean matchVehicle;
        double payment;
        double userPayment;

        DecimalFormat format = new DecimalFormat("0.00");

//        Date date = new Date();
//        int currentExitHour = date.getHours(); // Get the current hour which the user is about to check out
        DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime checkOutTime = LocalDateTime.now(); // Get current date and time
        int checkOutHour = checkOutTime.getHour(); // Get current hour from current date and time

        do {
            matchVehicle = false;
            System.out.println("Enter License Plate");

            licensePlate = scan.nextLine().toUpperCase(); // Get license plate
            vehicle = carPark.searchForLicensePlate(licensePlate); // Search for the license plate

            if (vehicle != null) {
                attendant = carPark.getAttendantWithVehicle(vehicle); // Get vehicle from the attendant if user requested
                if (attendant != null) {
                    attendant.setVehicle(null); // Set the vehicle from attendant back to nothing
                    carPark.addAttendantToBooth(attendant); // Add the attendant back to the booth
                }
                do {
                    matchCode = false;
                    System.out.println("Enter Code");
                    code = scan.nextLine().toUpperCase();

                    // If the code is same as the previous given code for the vehicle, process payment, else enter again
                    if (code.equals(vehicle.getParkingReceipt().getCode())) {
                        payment = Math.abs(checkOutHour - vehicle.getParkingReceipt().getCheckInHour()); // Current exit hour - Current Check in hour

                        // Disable customer get half of the payment
                        if (!vehicle.isDisable()) {
                            System.out.println("Total payment: " + payment);
                        } else {
                            payment = (payment / 2); // Current exit hour   - Current Check in hour
                            System.out.println("Total payment: " + format.format(payment));
                        }

                        do {
                            paymentSuccess = false;

                            System.out.println("Enter amount to pay");
                            userPayment = scan.nextDouble();

                            // If the user entered the amount that is larger than the exact payment, give change.
                            if (userPayment > payment) {
                                System.out.println("Here is your change: " + format.format(userPayment - payment));
                            }
                            // If user enter exact payment, remove vehicle from the car park
                            else if (userPayment == payment) {
                                System.out.println("Thank you for paying the exact amount");
                            }
                            // If user enter amount that is less than the exact payment, request to pay again
                            else {
                                System.out.println("You are " + format.format(payment - userPayment) + " short");
                                paymentSuccess = true;
                            }
                        } while (paymentSuccess);
                    } else {
                        System.out.println("Code does not match");
                        matchCode = true;
                    }
                } while (matchCode);

            } else {
                System.out.println("Vehicle does not exist");
                matchVehicle = true;
            }
        } while (matchVehicle);

        carPark.generateToken(vehicle); // Generate a token for exiting the car park later
        carPark.attendantCollectCar(vehicle); // Require attendant to collect vehicle
        scan.nextLine();
    }


    /**
     * This method is to remove vehicle manually
     */
    private void removeVehicleManually() {
        String removeVehicle;
        System.out.println("Enter License Plate");

        removeVehicle = scan.nextLine();
        carPark.removeVehicle(removeVehicle); // Remove vehicle form car park
    }

    /**
     * This method is to display car park status
     */
    private void printCarParkStatus() {

        String response;
        boolean validate;

        do {
            validate = false;
            zoneMenu();
            response = scan.nextLine().toUpperCase();

            switch (response) {
                case "1":// Zone 1
                    carPark.showZone(0); //Display zone 1 status
                    break;
                case "2": // Zone 2
                    carPark.showZone(1); //Display zone 2 status
                    break;
                case "3": // Zone 3
                    carPark.showZone(2); //Display zone 3 status
                    break;
                case "4": // Zone 4
                    carPark.showZone(3); //Display zone 4 status
                    break;
                case "5": // Zone 5
                    carPark.showZone(4); //Display zone 5 status
                    break;
                case "A": // All zones
                    carPark.displayCarParkStatus(); // Display zone 1 to 5
                    break;
                default:
                    System.out.println("Wrong Option");
                    validate = true;
            }
        } while (validate);
    }

    /**
     * This method is to add new attendant
     */
    private void addNewAttendant() {
        String attendantName;
        scan = new Scanner(System.in);

        System.out.println("Please enter new attendant name: ");
        attendantName = scan.nextLine();
        attendantName = attendantName.replaceAll("[^a-zA-Z]", "").toUpperCase(); // Remove non-alphabet

        Attendant attendant = new Attendant(attendantName);
        carPark.addAttendant(attendant); // Add attendant to the car park
    }

    /**
     * This method is to remove attendant
     */
    private void removeAttendant() {
        String attendantName;
        System.out.println("Enter Attendant Name");

        attendantName = scan.nextLine().toUpperCase();
        carPark.removeAttendant(attendantName); // Remove attendant form the car park
    }

    /**
     * This method is to view all attendant from the car park
     */
    private void viewAllAttendant() {
        carPark.showAllAttendant();
    }

    /**
     * This method is to view all available attendant from the car park
     */
    private void viewAllAvailableAttendant() {
        carPark.showAllAvailableAttendant();
    }

    /**
     * This method is to search vehicle using license plate
     */
    private void searchVehicle() {
        String licensePlate;
        System.out.println("Enter License Plate");
        licensePlate = scan.nextLine().toUpperCase();

        Vehicle vehicle = carPark.searchForLicensePlate(licensePlate); // Search vehicle license plate

        if (vehicle != null) {
            System.out.println(vehicle.toString()); // Print vehicle information
        } else {
            System.out.println("Vehicle does not exist");
        }
    }


    public static void main(String[] args) throws IOException {
        Application application = new Application();
        application.run();
//        application.saveToFile();
    }
}

