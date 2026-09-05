package utils;

public class CurrentUser {

    private static String name = "";
    private static String email = "";
    private static boolean admin = false;

    public static void setUser(String n, String e) {
        name = n;
        email = e;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String n) {
        name = n;
    }

    public static String getEmail() {
        return email;
    }
    public static void setAdmin(boolean value){
    admin = value;
}

public static boolean isAdmin(){
    return admin;
}
}