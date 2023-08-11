package ui;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    private  static  final int week = 1000*60*1440*7; // number of milliseconds in a week
    private static final String menu = "\nOptions:\n1. Find and reserve a room\n2. See my reservations\n3. Create an account\n4. Admin\n5. Exit \nChoice: " ;
    private static final Scanner sc = new Scanner(System.in);
    private static Customer c = null;
    protected static HotelResource hotelRes = null;
    protected static final AdminResource adminRes = new AdminResource();
    private static CustomerService custSvc;
    protected static ReservationService resSvc;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("M/d/y"); // to parse dates

    public static void main(String[] args){
        hotelRes = new HotelResource(adminRes);
        resSvc = ReservationService.getInstance(hotelRes);
        adminRes.setHotelRes(hotelRes);
        adminRes.setResSvc(resSvc);
        custSvc = CustomerService.getInstance(adminRes);

        while (true){
            System.out.print(menu);

            switch (getInt()){
                case 1:
                    findReserveRoom();
                    continue;
                case 2:
                    seeMyReservations();
                    continue;
                case 3:
                    createAccount();
                    continue;
                case 4:
                    AdminMenu.menu(sc);
                    continue;
                case 5:
                    return;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Enter Customer First Name, Last Name, Email: ");
        custSvc.addCustomer(sc.next(), sc.next(), sc.next());
    }

    private static void seeMyReservations() {
        if (c == null) {
            System.out.print("Enter email: ");
            c = adminRes.getCustomer(sc.next());
        } else {
            System.out.print("Enter email or Hit Enter for '" + c.getEmail() + "': ");
            String e = sc.nextLine().strip();
            System.out.println(e);

            if ( e.length() != 0 ) {
                c = adminRes.getCustomer(e);
            }
        }

        if ( c == null ){
            System.out.println("No customer with that email");
            return;
        }

        Collection<Reservation> myRes = hotelRes.getCustomersReservations(c.getEmail());

        if ( myRes.size() == 0) {
            System.out.println("No reservations for email '" + c.getEmail() + "'.");
            return;
        }

        for ( Reservation r : myRes) {
            System.out.println(r);
        }
    }

    private static void findReserveRoom() {
        String rawRoom;
        IRoom room = null;
        Date cid, cod;

        System.out.print("Enter Check-In Date m/d/y: ");

        if ( null == (cid = getDate(sc.next()))){
            return;
        }

        System.out.print("Enter Check-Out Date m/d/y: ");

        if ( null == (cod = getDate(sc.next()))){
            return;
        }

        if ( ! cod.after(cid)){
            System.out.println("CheckOut not after CheckIn");
            return;
        }

        Collection<IRoom> irc = hotelRes.findARoom(cid, cod);

        if ( irc.size() == 0 ){
            System.out.print("No rooms available for those dates, try a week later? Y/N:");

            if ( sc.next().toUpperCase().charAt(0) == 'Y' ){
                cid.setTime(cid.getTime() + week);
                cod.setTime(cod.getTime() + week);
                irc = hotelRes.findARoom(cid, cod);

                if ( irc.size() == 0){
                    System.out.println("No rooms that week either.");
                    return;
                }
            }
        }

        for ( IRoom r : irc ){
            System.out.println( r );
        }

        System.out.print("Enter Room Number: ");
        rawRoom = sc.next();

        for (IRoom r : irc) {
            if (rawRoom.equalsIgnoreCase(r.getRoomNumber())) {
                room = r;
                break;
            }
        }

        if (room == null) {
            System.out.println("Unknown Room Number");
            return;
        }

        if (c == null) {
            System.out.print("Enter email: ");
            c = adminRes.getCustomer(sc.next());
        } else {
            System.out.print("Enter email or Hit Enter for '" + c.getEmail() + "': ");
            String e = sc.nextLine().strip();

            if ( e.length() != 0 ) {
                c = adminRes.getCustomer(e);
            }
        }

        if ( c == null ){
            System.out.println("No customer with that email");
            return;
        }

        System.out.println(resSvc.reserveARoom(c, room, cid, cod));
    }

    public static int getInt() {
        try {
            return sc.nextInt();
        } catch (Exception e){
            System.out.print("Invalid, try again: ");
            sc.nextLine();
            return getInt();
        }
    }

    private static Date getDate(String s){
        try {
            return sdf.parse(s);
        } catch (Exception e){
            return null;
        }
    }

    public static ReservationService getResSvc() {
        return resSvc;
    }
}
