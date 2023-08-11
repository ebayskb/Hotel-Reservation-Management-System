package model;// Main.java file
import java.text.SimpleDateFormat;
import java.util.*;
import static model.RoomType.*;


class Driver {
    private static final String menu = "Options:\n1 Add customer\n2 Add room\n3 Add Reservation\n4 Exit\nChoice: " ;
    private static Scanner sc;
    private static final ArrayList<IRoom> rooms = new ArrayList<>(); // create empty rooms list
    private static final ArrayList<Customer> customers = new ArrayList<>(); // create empty customers list
    public static ArrayList<Reservation> res = new ArrayList<>();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("M/d/y"); // to parse dates


    public static void main(String[] args){
        sc = new Scanner( System.in ); // wrap keyboard in a scanner

        while ( true ){
            int choice ;

            System.out.print( menu );

            try {
                choice = sc.nextInt() ;
            } catch ( Exception e ){
                System.out.println( "Invalid, try again." );
                sc.nextLine(); // clear buffer
                continue ;
            }

            switch ( choice ){
                case 1:
                    addCust();
                    continue ;
                case 2:
                    addRoom();
                    continue;
                case 3:
                    addRes();
                    continue;
                case 4:
                    return;
                default:
                    System.out.println( "Invalid choice." );
            } // end switch
        } // end while
    } // end main()

    private static void addRoom(){
        System.out.print( "Enter room #: " );
        String roomNo ;

        roomNo = sc.next();

        // is room # a duplicate ?
        for ( IRoom r : rooms ){
            if ( r.getRoomNumber().equalsIgnoreCase( roomNo )){
                System.out.println( "Room already in list." );
                return ;
            }
        }

        System.out.print( "Single y/n? " );

        String l = sc.next();

        if (l.toUpperCase().charAt(0) != 'N' && l.toUpperCase().charAt(0) != 'Y' ){
            System.out.println( "Invalid, try again." );
            return;
        }

        RoomType rt = l.toUpperCase().charAt(0) == 'Y' ? SINGLE : DOUBLE ;
        double price;

        System.out.print( "Price: " );

        try {
            price = sc.nextDouble();
        } catch ( Exception e ){
            sc.nextLine(); // empty the stream
            System.out.println( "Invalid, try again." );
            return;
        }

        if ( (price > 10000.0 || price < 10.0 ) && price != 0.0){
            System.out.println( "Price out of range" );
            return;
        }

        IRoom i = price == 0.0 ? new FreeRoom(roomNo, rt) : new Room( roomNo, rt, price ) ;
        rooms.add( i );
        System.out.println(i);
    } // end of add room

    private static void addCust() {
        System.out.print("Enter Customer First Name, Last Name, Email: ");
        String first = sc.next();
        String last = sc.next();
        String email = sc.next();

        for (Customer c : customers) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Customer already exists: " + c);
                return;
            }
        }

        Customer c;

        try {
            c = new Customer(first, last, email);
            customers.add(c);
        } catch (Exception e) {
            System.out.println("Invalid Email");
            return;
        }

        System.out.println(c);
    } // end add customer

    private static void addRes() {
        String rawRoom, custEmail;
        IRoom room = null;
        Customer cust = null;
        Date cid, cod;

        System.out.print("Enter Room Number: ");
        rawRoom = sc.next();

        for (IRoom r : rooms) {
            if (rawRoom.equalsIgnoreCase(r.getRoomNumber())) {
                room = r;
                break;
            }
        }

        if (room == null) {
            System.out.println("Unknown Room Number");
            return;
        }

        System.out.print("Enter Customer Email Address: ");
        custEmail = sc.next();

        for (Customer c : customers) {
            if (custEmail.equalsIgnoreCase(c.getEmail())) {
                cust = c;
                break;
            }
        }

        if (cust == null) {
            System.out.println("Unknown Customer Email Address");
            return;
        }

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

        for ( Reservation r : res){
            if ( r.getRoom().getRoomNumber().equalsIgnoreCase(rawRoom)
                    && cid.before(r.getCheckOutDate())
                    && cod.after(r.getCheckInDate())){
                System.out.println("Room Not Available for those dates");
                return;
            }
        }

        Reservation r = new Reservation(cust, room, cid, cod);
        res.add(r);
        System.out.println(r);
    } // end addRes

    private static Date getDate(String s){
        try {
            return sdf.parse(s);
        } catch (Exception e){
            System.out.println("Invalid Date");
            return null;
        }
    }

} // end class Main file
