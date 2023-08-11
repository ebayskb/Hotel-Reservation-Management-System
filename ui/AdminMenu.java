package ui;

import model.*;

import java.util.Scanner;

import static model.RoomType.DOUBLE;
import static model.RoomType.SINGLE;

public class AdminMenu {
    private static final String menu = """

            Options:
            1. See all Customers
            2. See all Rooms
            3. See all Reservations
            4. Add a Room
            5. Back to Main Menu
            Choice:\s""";
    private static Scanner sc;

    public static void menu(Scanner sc) {
        AdminMenu.sc = sc;

        while (true) {
            System.out.print(menu);

            switch (MainMenu.getInt()) {
                case 1:
                    seeAllCustomers();
                    continue;
                case 2:
                    seeAllRooms();
                    continue;
                case 3:
                    MainMenu.resSvc.printAllReservation();
                    continue;
                case 4:
                    addARoom();
                    continue;
                case 5:
                    return;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    private static void addARoom () {
        System.out.print( "Enter room #: " );
        String roomNo ;
        //IRoom r;
        roomNo = sc.next();
        try{
            int roomnumber = Integer.parseInt(roomNo);
        }
        catch ( Exception e){
            System.out.println("Not a valid Room Number. Must be numeric only.");
            return;
        }

        if ( MainMenu.hotelRes.getRoom(roomNo) != null){
            System.out.println( "Room already in list." );
            return ;
        }

        System.out.print( "Single y/n? " );

        String l = sc.next();

        if (l.toUpperCase().charAt(0) != 'N' && l.toUpperCase().charAt(0) != 'Y' ){
            System.out.println( "Invalid, try again." );
            return;
        }

        RoomType rt = l.toUpperCase().charAt(0) == 'Y' ? SINGLE : DOUBLE ;
        System.out.print( "Price: " );
        double price = getDouble();

        if ( (price > 10000.0 || price < 10.0 ) && price != 0.0){
            System.out.println( "Price out of range" );
            return;
        }

        IRoom i = price == 0.0 ? new FreeRoom(roomNo, rt) : new Room( roomNo, rt, price ) ;
        MainMenu.resSvc.addRoom(i);
        System.out.println(i);
    }

    private static double getDouble() {
        try {
            return sc.nextDouble();
        } catch ( Exception e ){
            sc.nextLine(); // empty the stream
            System.out.println( "Invalid, try again: " );
            return getDouble();
        }
    }

    private static void seeAllRooms () {
        for ( IRoom r : MainMenu.hotelRes.getRooms()){
            System.out.println(r);
        }
    }
    private static void seeAllCustomers () {
        for (Customer c : MainMenu.adminRes.getAllCustomers()){
            System.out.println(c);
        }
    }
} // end class AdminMenu
