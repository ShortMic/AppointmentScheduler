package Application.Models;

public class User {

    //Current User fields
    private static int currentUserId;
    private static String currentUserName; //unique
    private static String currentPassword;
    private static boolean assigned = false;
    //Generic User fields
    private int userId;
    private String userName; //unique
    private String password;

    //Current User Accessor methods
    public static void setCurrentUserId(int userId) {
        User.currentUserId = userId;
    }

    public static int getCurrentUserId() {
        return User.currentUserId;
    }

    public static void setCurrentUserName(String thisUserName) {
        User.currentUserName = thisUserName;
    }

    public static String getCurrentUserName() {
        return User.currentUserName;
    }

    public static void setCurrentPassword(String thisPassword) {
        User.currentPassword = thisPassword;
    }

    public static String getCurrentPassword() {
        return User.currentPassword;
    }

    public static void assign(){
        if(!(currentUserId == 0 && currentUserName == null && currentPassword == null)){
            assigned = true;
        }
    }

    public static boolean isAssigned(){
        return assigned;
    }

    /**
     * A static method meant to clear the current instanced user's fields when disconnecting or logging out.
     */
    public static void clear(){
        currentUserId = 0;
        currentUserName = null;
        currentPassword = null;
        assigned = false;
    }

    //Generic User constructor

    /**
     *
     * @param userId The associated and unique user id
     * @param userName The associated and unique user name
     * @param password The associated user's password
     */
    public User(int userId, String userName, String password){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }
    public void setUserId(int userId) {
        userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserName(String thisUserName) {
        userName = thisUserName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String thisPassword) {
        password = thisPassword;
    }

    public String getPassword() {
        return password;
    }

}
