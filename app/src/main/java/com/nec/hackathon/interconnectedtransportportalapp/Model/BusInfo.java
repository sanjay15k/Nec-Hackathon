package com.nec.hackathon.interconnectedtransportportalapp.Model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

public class BusInfo implements Serializable {

    private String busNo;
    private LatLng srcL;
    private LatLng destL;
    private String dest;
    private ArrayList<LatLng> midStops;
    private String arrivalTime;
    private String label;
    private int seatAvailability;

    public BusInfo(String busNo, LatLng srcL, LatLng destL, String dest, ArrayList<LatLng> midStops, String arrivalTime, String label, int seatAvailability){
        this.busNo = busNo;
        this.srcL = srcL;
        this.destL = destL;
        this.dest = dest;
        this.midStops = midStops;
        this.arrivalTime = arrivalTime;
        this.label = label;
        this.seatAvailability = seatAvailability;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public LatLng getSrcL() {
        return srcL;
    }

    public void setSrcL(LatLng srcL) {
        this.srcL = srcL;
    }

    public LatLng getDestL() {
        return destL;
    }

    public void setDestL(LatLng destL) {
        this.destL = destL;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public ArrayList<LatLng> getMidStops() {
        return midStops;
    }

    public void setMidStops(ArrayList<LatLng> midStops) {
        this.midStops = midStops;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getSeatAvailability() {
        return seatAvailability;
    }

    public void setSeatAvailability(int seatAvailability) {
        this.seatAvailability = seatAvailability;
    }
}
