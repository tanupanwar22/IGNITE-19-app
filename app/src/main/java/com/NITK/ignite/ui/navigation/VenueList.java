package com.NITK.ignite.ui.navigation;

public class VenueList {
    String venue_name;
    double venue_latitude;
    double venue_longitude;

    VenueList(){

    }

    VenueList(String venue_name,double venue_latitude,double venue_longitude){
        this.venue_name = venue_name;
        this.venue_latitude = venue_latitude;
        this.venue_longitude = venue_longitude;
    }


    public double getVenue_latitude() {
        return venue_latitude;
    }

    public double getVenue_longitude() {
        return venue_longitude;
    }

    public String getVenue_name() {
        return venue_name;
    }
}
