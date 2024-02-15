package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.example")
public class Booking {

    static ApplicationContext context = new AnnotationConfigApplicationContext(Booking.class);
    @Bean
    public Bus bus1(){
        return new Bus(1,"irishTravels",true,3,1000);
    }
    @Bean
    public Bus bus2(){
        return new Bus(2,"aarthiTravels",true,40,900);
    }
    @Bean
    public Bus bus3(){
        return new Bus(3,"leafTravels",false,20,600);
    }
    @Bean
    public SuperUser su(){
        return new SuperUser(0,null,null,0,null,0,false);
    }
    @Bean
    public NormalUser nu(){
        return new NormalUser(0,null,null,0,null,0,true);
    }



    public static int passanger_id=1;
    private static final Logger logger =  LoggerFactory.getLogger(Booking.class);
    public static void main(String[] args) {
        Connection conn=null;
        ObjectMapper mapper= new ObjectMapper();
        String url="jdbc:postgresql://localhost:5432/bus_booking";
        String username ="postgres";
        String password="postgres";
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

        logger.debug("Bus Reservation System starting up...");
        logger.debug("Bus Reservation System initialization complete.");
        Scanner sc = new Scanner(System.in);
        int input =1;
        int ticket_price=0;
        String title = "Welcome to Temple Travels";
        int width = title.length() + 4; // Including borders and spaces
        System.out.println("+".repeat(width));
        System.out.println("| " + title + " |");
        System.out.println("+".repeat(width));
        logger.debug("Hence the bus object are taken from the spring container bean");
        ArrayList<Bus> buses = new ArrayList<>();
        buses.add((Bus)context.getBean("bus1"));
        buses.add((Bus)context.getBean("bus2"));
        buses.add((Bus)context.getBean("bus3"));

        outerloop:
        while(true) {
            logger.debug("the user booking portion starts successfully!!!");
            System.out.println("Enter 1 for booking and 2 for exit the portal ");
            int choice = sc.nextInt();
            switch (choice){
                case 1:

                    System.out.println("Enter the passanger name :");

                    String pname = sc.next();
                    logger.info("the user has entered his name "+pname);
                    System.out.println("Enter the passanger email :");
                    String email = sc.next();
                    logger.info("the user has entered his email "+email);
                    System.out.println("Enter the phone number of the passanger :");
                    long phoneno = sc.nextLong();
                    logger.info("the user has entered his phone no "+phoneno);
                    System.out.println("Enter the date of journey to the temple dd-mm-yyyy(format) :");
                    String dateInput = sc.next();
                    logger.info("the user has entered the date of journey "+dateInput);
                    System.out.println("Enter the no of seats needed for booking : ");
                    int noseats=sc.nextInt();
                    logger.info("the user has entered no of seats "+noseats);
                    System.out.println("are you a existing user yes-or-no :");
                    String existuser=sc.next();
                    logger.debug("the user bean has been created for normal user and super user");
                  SuperUser u1=(SuperUser)context.getBean("su");
                  NormalUser u01=(NormalUser) context.getBean("nu");
                    if(existuser.equals("yes")){
                        logger.debug("the super user portion starts here-->");
                        logger.info("the user "+pname+" is super user");
                        u1.setPassanger_no(passanger_id++);
                        u1.setPassanger_name(pname);
                        u1.setEmail(email);
                        u1.setPhone_no(phoneno);
                        u1.setDateInput(dateInput);
                        u1.setNo_of_seats(noseats);
                        try {
                            String json =mapper.writeValueAsString(u1);
                            System.out.println(json);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
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
                        if(ticket_price==0){
                            System.out.println("there is no buses with the given name so please try again ");
                            logger.info("not a  correct username may be user "+pname+" is illiterate");
                            break;
                        }
                        if(u1.isNormalUser==false){
                            System.out.println("hence the ticket for the user :"+u1.passanger_name+" by travels name :"+busname+" on the date"+u1.dateInput+" is confirmed");
                            logger.info("hence the ticket for the user :"+u1.passanger_name+" by travels name :"+busname+" on the date"+u1.dateInput+" is confirmed and booked successfully");
                            int price=u1.getDiscount(ticket_price);
                            logger.debug("the super user discount method performs successfully :)");
                            System.out.println("As you're a super user you got a discounted price :)"+ price);
                        }
                        sqlInsert = "INSERT INTO booking (passenger_name, bus_name, booking_date, price) VALUES ('" +
                                u1.passanger_name + "', '" + busname + "', '" + u1.dateInput + "', " + ticket_price + ")";
                        try {
                            st.executeUpdate(sqlInsert);
                            logger.debug("the user booking details successfully inserted into the database");
                            System.out.println("Data inserted successfully.");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    else{
                        logger.debug("the normal user portion starts here-->");
                        logger.info("the user "+pname+" is normal user");
                        u01.setPassanger_no(passanger_id++);
                        u01.setPassanger_name(pname);
                        u01.setEmail(email);
                        u01.setPhone_no(phoneno);
                        u01.setDateInput(dateInput);
                        u01.setNo_of_seats(noseats);
                        try {
                            String json =mapper.writeValueAsString(u01);
                            System.out.println(json);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
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

                        logger.info("hence the ticket for the user :"+u01.passanger_name+" by travels name :"+busname+" on the date"+u01.dateInput+" is confirmed and booked successfully");

                        System.out.println("As you're a normal user you didn't have any discount :(  and the price is :"+ticket_price);

                        sqlInsert = "INSERT INTO booking (passenger_name, bus_name, booking_date, price) VALUES ('" +
                                u01.passanger_name + "', '" + busname + "', '" + u01.dateInput + "', " + ticket_price + ")";
                        try {

                            st.executeUpdate(sqlInsert);
                            logger.debug("the user booking details successfully inserted into the database");

                            System.out.println("Data inserted successfully.");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    System.out.println("---------------------------RESERVATION FINISHED--------------------------------");
                    logger.debug("the user booking is completed");
                    break;
                case 2:
                    logger.debug("the exit button is clicked the application is closed");
                    break outerloop;


            }
        }
    }
}
