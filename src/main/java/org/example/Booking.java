package org.example;

import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Booking {

    public static int passanger_id=1;
    private static final Logger logger =  LoggerFactory.getLogger(Booking.class);
    public static void main(String[] args) {
        Connection conn=null;

        String url="jdbc:postgresql://localhost:5432/--your database name---";
        String username ="--username--";
        String password="--password--";
        String sqlInsert;
        ResultSet rs;
        Statement st;
        try {
            conn = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            try {
                throw new RuntimeException(e);
            } catch (RuntimeException ex) {
                throw new RuntimeException(ex);
            }
        }
        try {
            st=conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        logger.info("Bus Reservation System starting up...");
        logger.info("Bus Reservation System initialization complete.");
        Scanner sc = new Scanner(System.in);
        int input =1;
        int ticket_price=0;
        String title = "Welcome to Temple Travels";
        int width = title.length() + 4; // Including borders and spaces
        System.out.println("+".repeat(width));
        System.out.println("| " + title + " |");
        System.out.println("+".repeat(width));
        ArrayList<Bus> buses = new ArrayList<>();
        buses.add(new Bus(1,"irishTravels",true,3,1000));
        buses.add(new Bus(2,"aarthiTravels",true,40,900));
        buses.add(new Bus(3,"leafTravels",false,20,600));

        outerloop:
        while(true) {
            System.out.println("Enter 1 for booking and 2 for exit the portal ");
            int choice = sc.nextInt();
            switch (choice){
                case 1:

                    System.out.println("Enter the passanger name :");
                    String pname = sc.next();
                    System.out.println("Enter the passanger email :");
                    String email = sc.next();
                    System.out.println("Enter the phone number of the passanger :");
                    long phoneno = sc.nextLong();
                    System.out.println("Enter the date of journey to the temple dd-mm-yyyy(format) :");
                    String dateInput = sc.next();
                    System.out.println("Enter the no of seats needed for booking : ");
                    int noseats=sc.nextInt();
                    System.out.println("are you a existing user yes-or-no :");
                    String existuser=sc.next();
                    SuperUser u1 = null;
                    NormalUser u01=null;
                    if(existuser.equals("yes")){
                         u1 = new SuperUser(passanger_id++,pname,email,phoneno,dateInput,noseats,false);
                        System.out.println("you're now logging with a user as name : "+u1.getPassanger_name()+" and user_id :"+u1.getPassanger_no());
                        System.out.print("-----------------------BUS BOOKING PORTAL--------------------------------------");
                        System.out.println();
                        System.out.print("-------------------------------------------------------------------------------");
                        System.out.println();
                        System.out.println("-AVAILABLE BUSES-");
                        String sqlSelect = "SELECT * FROM bus";

                        // Execute the query
                        try {
                            rs = st.executeQuery(sqlSelect);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        // Process the result set
                        while (true) {
                            try {
                                if (!rs.next()) break;
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            int busNo = 0;
                            try {
                                busNo = rs.getInt("busno");

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            String busName = null;
                            try {
                                busName = rs.getString("busname");
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            String isAc = null;
                            try {
                                isAc = rs.getString("isac");
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            int  price = 0;
                            try {
                                price = rs.getInt("price");
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                            System.out.println("Bus No: " + busNo + ", Bus Name: " + busName + ", Is AC: " + isAc + ", Price: " + price);
                        }

                        System.out.print("-------------------------------------------------------------------------------");
                        System.out.println();
                        System.out.println("Enter the bus name");
                        String busname= sc.next();
                        for(Bus b : buses){
                            if(b.bus_name.equals(busname)){
                                b.performBooking(dateInput,noseats);
                                ticket_price=b.priceFair;
                            }
                        }
                        if(u1.isNormalUser==false){
                            System.out.println("hence the ticket for the user :"+u1.passanger_name+" by travels name :"+busname+" on the date"+u1.dateInput+" is confirmed");
                            int price=u1.getDiscount(ticket_price);
                            System.out.println("As you're a super user you got a discounted price :)"+ price);
                        }
                        sqlInsert = "INSERT INTO booking (passenger_name, bus_name, booking_date, price) VALUES ('" +
                                u1.passanger_name + "', '" + busname + "', '" + u1.dateInput + "', " + ticket_price + ")";
                        try {
                            st.executeUpdate(sqlInsert);
                            System.out.println("Data inserted successfully.");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    else{
                         u01 = new NormalUser(passanger_id++,pname,email,phoneno,dateInput,noseats,true);
                        System.out.println("you're now logging with a user as name : "+u01.getPassanger_name()+" and user_id :"+u01.getPassanger_no());
                        System.out.print("-----------------------BUS BOOKING PORTAL--------------------------------------");
                        System.out.println();
                        System.out.print("-------------------------------------------------------------------------------");
                        System.out.println();
                        System.out.println("-AVAILABLE BUSES-");
                        String sqlSelect = "SELECT * FROM bus";

                        // Execute the query
                        try {
                            rs = st.executeQuery(sqlSelect);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        // Process the result set
                        while (true) {
                            try {
                                if (!rs.next()) break;
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            int busNo = 0;
                            try {
                                busNo = rs.getInt("busno");

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            String busName = null;
                            try {
                                busName = rs.getString("busname");
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            String isAc = null;
                            try {
                                isAc = rs.getString("isac");
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            int  price = 0;
                            try {
                                price = rs.getInt("price");
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                            System.out.println("Bus No: " + busNo + ", Bus Name: " + busName + ", Is AC: " + isAc + ", Price: " + price);
                        }
                        System.out.print("-------------------------------------------------------------------------------");
                        System.out.println();
                        System.out.println("Enter the bus name");
                        String busname= sc.next();
                        for(Bus b : buses){
                            if(b.bus_name.equals(busname)){
                                b.performBooking(dateInput,noseats);
                                ticket_price=b.priceFair;
                            }
                        }
                        System.out.println("hence the ticket for the user :"+u01.passanger_name+" by travels name :"+busname+" on the date"+u01.dateInput+" is confirmed");
                        System.out.println("As you're a normal user you didn't have any discount :(  and the price is :"+ticket_price);

                        sqlInsert = "INSERT INTO booking (passenger_name, bus_name, booking_date, price) VALUES ('" +
                                u01.passanger_name + "', '" + busname + "', '" + u01.dateInput + "', " + ticket_price + ")";
                        try {

                            st.executeUpdate(sqlInsert);
                            System.out.println("Data inserted successfully.");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    System.out.println("---------------------------REGISTRATION FINISHED--------------------------------");
                    break;
                case 2:
                    break outerloop;

            }
        }
    }
}
