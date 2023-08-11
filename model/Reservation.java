package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("M/d/y"); //
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public String toString(){
        return String.format(String.format(customer + ", " + room + ": " + sdf.format(checkInDate) + " - " + sdf.format(checkOutDate)));
    }
}

