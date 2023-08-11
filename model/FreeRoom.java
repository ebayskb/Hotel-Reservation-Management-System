package model;

import static model.RoomType.SINGLE;

public class FreeRoom extends Room {
    public FreeRoom( String n, RoomType t ){
        price = 0.0 ;
        no = n;
        rt = t;
    }

    public String toString(){
        return "Room " + no + ", " + ( rt == SINGLE ? "Single" : "Double" ) + ", FREE" ;
    }
}
