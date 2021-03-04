package DatabaseTest;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User Accounts")
public class Account /*extends user*/{

    /* --------- Attributes -------------*/

    private String userName;

    @Indexed(unique = true)
    private int userID;

    private String email;

    //private Posts[] wishlish;
    private String password;

    private double userRating;
    //private Notification[] notifications;

    /* --------- Methods -------------*/


    public Account(String name, int id, String email, String password){
        this.userName = name;
        this.userID = id;
        this.email = email;
        this.password = password;
        this.userRating = 0;
    }

    /* --------- Getters -------------*/


    public double getUserRating() {
        return userRating;
    }

    public String getEmail() {
        return email;
    }

    public int getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    /* --------- Setters -------------*/

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }
}
