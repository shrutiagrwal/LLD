package designPattern.observerDesignPattern;

import java.util.ArrayList;

public class notificationSystem {
    public static void main(String[] args)
    {
        StockObservable iphone=new Iphone();
        UserObservable user=new EmailAlertUser("emaild",iphone);
        UserObservable phoneUser=new PhoneAlertUser("232321321",iphone);
        iphone.setUsers(user);
        iphone.setUsers(phoneUser);
        iphone.setStockCount(10);
    }
}
interface StockObservable{
    public void setUsers(UserObservable user);
    public void removeUser(UserObservable user);
    public void setStockCount(int data);
    public String getName();
}
interface UserObservable{
    void update();
}
 class EmailAlertUser implements UserObservable{
    String id;
    StockObservable stock;
    public EmailAlertUser(String s,StockObservable stock){id=s;this.stock=stock;};
     public void update()
     {  System.out.println("email to user sent success "+ stock.getName());}
}
class PhoneAlertUser implements UserObservable{
    String id;
    StockObservable stock;
    public PhoneAlertUser(String s,StockObservable stock){id=s;this.stock=stock;};
    public void update()
    {  System.out.println("message to user sent success "+ stock.getName());}
}
class Iphone implements StockObservable{
    String name="iphone";
    ArrayList<UserObservable> users=new ArrayList<>();
    int count=0;
    public void setUsers(UserObservable user)
    {   users.add(user);}
    public String getName(){
        return this.name;}

    public void removeUser(UserObservable user)
    {
        users.remove(user);
    }
    void NotifyAllUsers()
    {
        for(UserObservable user:users)
        {   user.update();}
    }
    public void setStockCount(int data)
    {
        if(data!=0)
        {
            count+=data;
            this.NotifyAllUsers();
        }
    }
}