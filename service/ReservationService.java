package service;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class ReservationService {
    private final Collection<IRoom> rooms; // create empty rooms list
    private final ArrayList<Reservation> res;

    private static ReservationService instance;

    private ReservationService(HotelResource hotelRes) {
        rooms = hotelRes.getRooms();
        res = hotelRes.getRes();
    }

    public static ReservationService getInstance(HotelResource hotelRes){
        instance = new ReservationService(hotelRes);
        return instance;
    }

    public void addRoom(IRoom room) {
        if ( null != getARoom(room.getRoomNumber())){
            System.out.println( "Room already in list." );
            return ;
        }

        rooms.add(room);
    }

    public IRoom getARoom(String roomId){
        for ( IRoom r : rooms ){
            if ( r.getRoomNumber().equalsIgnoreCase( roomId )){
                return r;
            }
        }

        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        if ( ! checkOutDate.after(checkInDate)){
            System.out.println("CheckOut not after CheckIn");
            return null;
        }

        System.out.println(res);

        for ( Reservation r : res){
            if ( r.getRoom().getRoomNumber().equalsIgnoreCase(room.getRoomNumber())
                    && checkInDate.before(r.getCheckOutDate())
                    && checkOutDate.after(r.getCheckInDate())){
                System.out.println("Room Not Available for those dates");
                return null;
            }
        }

        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        res.add(newReservation);

        return newReservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        HashSet<IRoom> ret = new HashSet<>(rooms);

        for (Reservation r : res) {
            if (checkInDate.before(r.getCheckOutDate())
                    && checkOutDate.after(r.getCheckInDate())) {
                ret.remove(r.getRoom());
            }
        }

        return ret;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer){
        ArrayList<Reservation> ret = new ArrayList<>();

        for (Reservation r : res) {
            if(r.getCustomer().equals(customer)){
                ret.add(r);
            }
        }

        return ret;
    }

    public void printAllReservation() {
        for (Reservation r : res) {
            System.out.println(r);
        }
    }
}
