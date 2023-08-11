package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class HotelResource {
    private final Collection<IRoom> rooms = new ArrayList<>(); // create empty rooms list
    public final ArrayList<Reservation> res = new ArrayList<>();
    private final AdminResource adminRes;

    public HotelResource(AdminResource adminRes) {
        this.adminRes = adminRes;
    }
    public Collection<IRoom> getRooms() {
        return rooms;
    }

    public Customer getCustomer(String email){
        return adminRes.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName){
        Customer c;

        if ((c = getCustomer(email)) != null){
            System.out.println("Customer already exists: " + c);
            return;
        }

        try {
            c = new Customer(firstName, lastName, email);
            adminRes.getAllCustomers().add(c);
        } catch (Exception e) {
            System.out.println("Invalid Email");
        }
    }

    public IRoom getRoom(String roomNumber){
        for ( IRoom r : rooms ){
            if ( r.getRoomNumber().equalsIgnoreCase( roomNumber )){
                return r;
            }
        }

        return null;
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        Customer cust = getCustomer(customerEmail);

        if (cust == null) {
            System.out.println("Unknown Customer Email Address");
            return null;
        }

        if ( ! checkOutDate.after(checkInDate)){
            System.out.println("CheckOut not after CheckIn");
            return null;
        }

        for ( Reservation r : res){
            if ( r.getRoom().equals(room)
                    && checkInDate.before(r.getCheckOutDate())
                    && checkOutDate.after(r.getCheckInDate())){
                System.out.println("Room Not Available for those dates");
                return null;
            }
        }

        Reservation r = new Reservation(cust, room, checkInDate, checkOutDate);
        res.add(r);
        return r;
    } // end of bookARoom

    public Collection<Reservation> getCustomersReservations(String customerEmail){
        ArrayList<Reservation> ret = new ArrayList<>();
        Customer c = getCustomer(customerEmail);

        if (c == null){
            System.out.println("Unknown Customer: " + customerEmail);
            return ret;
        }

        for ( Reservation r : res){
            if (c.equals(r.getCustomer())){
                ret.add(r);
            }
        }

        return ret;
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut){
        HashSet<IRoom> ret = new HashSet<>(rooms);

        for ( Reservation r : res){
            if ( r.getCheckInDate().before(checkOut)
                && r.getCheckOutDate().after(checkIn)){
                ret.remove(r.getRoom());
            }
        }

        return ret;
    }

    public ArrayList<Reservation> getRes() {
        return res;
    }
}
