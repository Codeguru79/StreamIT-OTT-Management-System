package utils;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    private static final String USER_FILE = "data/users.txt";
    private static String loggedInUserName = "";

    // Register User
    public static boolean registerUser(String name, String email, String password) {

        try {

            File file = new File(USER_FILE);

            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

            writer.write(name + "|" + email + "|" + password);
            writer.newLine();

            writer.close();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Login User
    public static boolean loginUser(String email, String password) {

        try {

            File file = new File(USER_FILE);

            if (!file.exists()) {
                return false;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\\|");

                if (data.length == 3) {

                    if (data[1].equalsIgnoreCase(email)
                            && data[2].equals(password)) {
                        loggedInUserName = data[0];

                        reader.close();
                        return true;
                    }
                }
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    // Check Admin Login
public static boolean isAdmin(String email, String password) {

    return email.equals("admin@streamit.com")
            && password.equals("12345");
}
public static String getLoggedInUserName() {
    return loggedInUserName;
}

// Update a user's name and (optionally) password. Email is the lookup key and stays fixed.
// Pass an empty newPassword to keep the existing password unchanged.
public static boolean updateProfile(String email, String newName, String newPassword) {

    try {

        File file = new File(USER_FILE);

        if (!file.exists()) return false;

        ArrayList<String> lines = new ArrayList<>();
        boolean found = false;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        while ((line = reader.readLine()) != null) {

            String[] data = line.split("\\|");

            if (data.length == 3 && data[1].equalsIgnoreCase(email)) {

                String passwordToUse = (newPassword == null || newPassword.isEmpty())
                        ? data[2] : newPassword;

                lines.add(newName + "|" + data[1] + "|" + passwordToUse);
                found = true;

            } else {
                lines.add(line);
            }
        }

        reader.close();

        if (!found) return false;

        BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));

        for (String l : lines) {
            writer.write(l);
            writer.newLine();
        }

        writer.close();

        loggedInUserName = newName;

        return true;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}
}