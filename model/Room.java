package model;

import static model.RoomType.SINGLE;

public class Room implements IRoom {
    protected String no;
    protected double price;
    protected RoomType rt;

    public Room( String n, RoomType t, double p ){
        no = n;
        price = p;
        rt = t;
    }

    public Room() {
    }

    public String getRoomNumber() { return no ; }

    public double getRoomPrice(){ return price ; }

    public RoomType getRoomType() { return rt ; }

    public boolean isFree() { return price == 0.0 ; }

    // public String toString(){ return "Room " + no + ", " + ( rt == SINGLE ? "Single" : "Double" ) + ", $" + price ; }
    // no price precision control ( or comma for thousands )

    public String toString(){
        return String.format( "Room %s, %s, $%,.2f", no, ( rt == SINGLE ? "Single" : "Double" ), price );
    }
}
