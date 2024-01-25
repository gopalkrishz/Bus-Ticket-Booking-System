package org.example;


class NormalUser extends User{

    Boolean isNormalUser;

    public NormalUser(int passanger_no, String passanger_name, String email, long phone_no,String dateInput ,int no_of_seats,boolean isNormalUser) {
        super(passanger_no, passanger_name, email, phone_no,dateInput,no_of_seats);
        this.isNormalUser=isNormalUser;
    }
    public Boolean getNormalUser() {
        return isNormalUser;
    }


}
class SuperUser extends User{
    Boolean isNormalUser;




    public SuperUser(int passanger_no, String passanger_name, String email, long phone_no,String dateInput,int no_of_seats,boolean isNormalUser) {
        super(passanger_no, passanger_name, email, phone_no,dateInput,no_of_seats);
        this.isNormalUser=isNormalUser;
    }
    public int getDiscount(int price){
        int new_price = (int) Math.round(price/1.5);
        return new_price;
    }
    public Boolean getNormalUser() {
        return isNormalUser;
    }



}
public class User {

    int passanger_no;
    String passanger_name;
    String email;
    long phone_no;
    String dateInput;
    int no_of_seats;
    public User(int passanger_no, String passanger_name,String email,long phone_no,String dateInput,int no_of_seats){
        this.passanger_name=passanger_name;
        this.passanger_no=passanger_no;
        this.email=email;
        this.phone_no=phone_no;
        this.dateInput=dateInput;
        this.no_of_seats=no_of_seats;
    }
    public int getPassanger_no() {
        return passanger_no;
    }

    public String getPassanger_name() {
        return passanger_name;
    }

    public String getEmail() {
        return email;
    }

    public long getPhone_no() {
        return phone_no;
    }


    public void setPassanger_no(int passanger_no) {
        this.passanger_no = passanger_no;
    }

    public void setPassanger_name(String passanger_name) {
        this.passanger_name = passanger_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone_no(long phone_no) {
        this.phone_no = phone_no;
    }




}

