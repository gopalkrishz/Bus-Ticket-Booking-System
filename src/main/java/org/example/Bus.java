package org.example;

import java.util.HashMap;

public class Bus {
    int bus_no;
    String bus_name;
    boolean isAc;
    int capacity;
    int priceFair;
    HashMap<String,Integer> hm = new HashMap<String,Integer>();
    public void addDates(){

        hm.put("10-01-2023",capacity);
        hm.put("11-01-2023",capacity);
        hm.put("12-01-2023",capacity);
        hm.put("13-01-2023",capacity);
        hm.put("14-01-2023",capacity);
        hm.put("15-01-2023",capacity);



    }
    public void performBooking(String dateInput,int noofseats){
        if(hm.containsKey(dateInput)){
            int capacityNow = hm.get(dateInput);
            if(capacityNow>noofseats){
                capacityNow=capacityNow-noofseats;
                System.out.println("seat booked successfully on the particular date :) "+ dateInput);
            }else{
                System.out.println("seat booking is not successful all seats are booked :( ");
            }
        }
    }
    public Bus(int bus_no,String bus_name,boolean isAc,int capacity,int priceFair){
        this.bus_name=bus_name;
        this.bus_no=bus_no;
        this.isAc=isAc;
        this.capacity=capacity;
        this.priceFair=priceFair;
        addDates();
    }
    public  void diplayBusDetails(){
        System.out.println("the bus no is :"+bus_no+" bus name: "+bus_name+" and is AC :"+isAc+" is available.");
    }



}
