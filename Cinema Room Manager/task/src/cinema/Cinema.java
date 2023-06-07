package cinema;

import java.util.Scanner;

public class Cinema {
    private static int rows = 0;
    private static int seatsPerRow = 0;

    private static final int DISCOUNT_SETPOINT = 60;
    private static final int PRICE = 10;
    private static final int DISCOUNT_PRICE = 8;
    private static String[][] seats;
    private static boolean run = true;
    private static int purchasedTickets = 0;
    private static int currentIncome = 0;

    public static void main(String[] args) {
        // Write your code here

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int userRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int userNumSeats = scanner.nextInt();
        rows = userRows;
        seatsPerRow = userNumSeats;
        seats = new String[seatsPerRow][rows];
        initSeats();

        while (run) {
            printMenu();
            int menuSelection = scanner.nextInt();
            switch (menuSelection) {
                case 1 -> printSeats();
                case 2 -> {

                    while (true) {
                        System.out.println("\nEnter a row number:");
                        int rowNum = scanner.nextInt();
                        System.out.println("Enter a seat number in that row:");
                        int seatNum = scanner.nextInt();
                        try {
                            buyTicket(rowNum, seatNum);
                            break;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                    }

                }
                case 3 -> printStatistics();
                case 0 -> run = false;
            }
        }

    }

    private static void printStatistics() {
        double percent = (double) purchasedTickets / getTotalSeats() * 100;
        System.out.println("Number of purchased tickets: " + purchasedTickets);
        System.out.printf("Percentage: %.2f%%\n", percent);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + getTotalIncome());

    }

    private static int getTotalIncome() {

        if (getTotalSeats() < DISCOUNT_SETPOINT) {
            return getTotalSeats() * PRICE;
        } else {
            int frontRows = rows / 2;
            int backRows = rows - frontRows;
            return frontRows * seatsPerRow * PRICE + backRows * seatsPerRow * DISCOUNT_PRICE;
        }

    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    private static void initSeats() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                seats[j][i] = "S";
            }
        }
    }

    private static void buyTicket(int rowNum, int seatNum) {
        if (rowNum > rows || seatNum > seatsPerRow) {
            throw new IllegalArgumentException("Wrong input!");
        }
        if (seatIsAvailable(rowNum, seatNum)) {
            int seatPrice = getSeatPrice(rowNum, seatNum);
            System.out.println("Ticket price: $" + seatPrice);
            seats[seatNum - 1][rowNum - 1] = "B";
            purchasedTickets++;
            currentIncome += seatPrice;
        } else {
            throw new IllegalArgumentException("That ticket has already been purchased!");
        }

    }

    private static boolean seatIsAvailable(int rowNum, int seatNum) {
        return seats[seatNum - 1][rowNum - 1].equals("S");
    }

    private static int getSeatPrice(int rowNum, int seatNum) {
        int ticketPrice = PRICE;

        if (getTotalSeats() >= DISCOUNT_SETPOINT && rowNum > rows / 2) {
            ticketPrice = DISCOUNT_PRICE;
        }
        return ticketPrice;

    }

    public static int getTotalSeats() {
        return rows * seatsPerRow;
    }

    public static void printSeats() {
        System.out.print("\nCinema:\n ");
        for (int i = 1; i <= seatsPerRow; i++) {
            System.out.print(" " + i);
        }
        System.out.print("\n");
        for (int i = 0; i < rows; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < seatsPerRow; j++) {
                System.out.print(seats[j][i] + " ");
            }
            System.out.print("\n");
        }
    }
}