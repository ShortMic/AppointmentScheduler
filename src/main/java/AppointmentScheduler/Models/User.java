package AppointmentScheduler.Models;

public abstract class User {

    private static int userId;
    private static String userName; //unique
    private static String password;
    private static boolean assigned = false;

    public static void setUserId(int userId) {
        User.userId = userId;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserName(String thisUserName) {
        userName = thisUserName;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setPassword(String thisPassword) {
        password = thisPassword;
    }

    public static String getPassword() {
        return password;
    }

    public static void assign(){
        if(!(userId == 0 && userName == null && password == null)){
            assigned = true;
        }
    }

    public static boolean isAssigned(){
        return assigned;
    }

    public static void clear(){
        userId = 0;
        userName = null;
        password = null;
        assigned = false;
    }

}
