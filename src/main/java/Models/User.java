package Models;

public class User {

    private int userId;
    private String userName; //unique
    private String password;

    public User(int userId, String userName, String password){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }
}
