package gdghackathon.mogakco.model;

/**
 * Created by mk on 2017-02-18.
 */

public class UserInfoStatic {
    static String userName;
    static String userEmail;

    public static void setUserName(String name){
        userName = name;
    }

    public static void setUserEmail(String email){
        userEmail = email;
    }

    public static String getUserName(){
        return userName;
    }
    public static String getUserEmail(){
        return userEmail;
    }
}

