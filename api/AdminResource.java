package api;

import model.Customer;
import model.IRoom;
import service.ReservationService;
import ui.MainMenu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdminResource {
    private final ArrayList<Customer> customers = new ArrayList<>(); // create empty customers list
    private HotelResource hotelRes = null;
    private ReservationService resSvc;


    public Customer getCustomer(String email){
        for (Customer c : customers) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                return c;
            }
        }

        return null;
    }

    public void setHotelRes(HotelResource hotelRes) {
        this.hotelRes = hotelRes;
    }

    public void addRoom(List<IRoom> rooms){
        for ( IRoom r : rooms ){
            resSvc.addRoom(r);
        }
    }

    public Collection<IRoom> getAllRooms(){
        return hotelRes.getRooms();
    }

    public Collection<Customer> getAllCustomers(){
        return customers;
    }

    public void displayAllReservations(){
        MainMenu.getResSvc().printAllReservation();
    }

    public void setResSvc(ReservationService resSvc) {
        this.resSvc = resSvc;
    }
}
