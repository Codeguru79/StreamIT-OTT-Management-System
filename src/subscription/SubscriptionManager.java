package subscription;

import java.io.*;
import java.util.*;

public class SubscriptionManager {

    private static final String FILE = "data/subscription.txt";

    // Save or update a user's plan (per-user record - does not wipe other users)
    public static void savePlan(String email, String plan) {

        try {

            File file = new File(FILE);
            ArrayList<String> lines = new ArrayList<>();
            boolean updated = false;

            if (file.exists()) {

                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {

                    if (line.trim().isEmpty()) continue;

                    String[] data = line.split("\\|");

                    if (data.length >= 1 && data[0].equalsIgnoreCase(email)) {
                        lines.add(email + "|" + plan);
                        updated = true;
                    } else {
                        lines.add(line);
                    }
                }

                br.close();
            }

            if (!updated) {
                lines.add(email + "|" + plan);
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));

            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }

            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getPlan(String email) {

        try {

            File file = new File(FILE);

            if(!file.exists())
                return "Free";

            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;

            while((line = br.readLine()) != null){

                if (line.trim().isEmpty()) continue;

                String[] data = line.split("\\|");

                if(data.length >= 2 && data[0].equalsIgnoreCase(email)){

                    br.close();
                    return data[1];

                }

            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Free";

    }

    public static int getPlanPrice(String plan) {

        if (plan == null) return 0;

        switch (plan) {
            case "Basic": return 99;
            case "Standard": return 199;
            case "Premium": return 299;
            default: return 0;
        }
    }

    // Every subscription record as {email, plan}
    public static ArrayList<String[]> getAllSubscriptions() {

        ArrayList<String[]> list = new ArrayList<>();

        try {

            File file = new File(FILE);

            if (!file.exists()) return list;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] data = line.split("\\|");

                if (data.length >= 2) {
                    list.add(new String[]{ data[0], data[1] });
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static int getTotalRevenue() {

        int total = 0;

        for (String[] sub : getAllSubscriptions()) {
            total += getPlanPrice(sub[1]);
        }

        return total;
    }

    // Plan -> subscriber count (Basic/Standard/Premium always present, even if zero)
    public static LinkedHashMap<String,Integer> getPlanCounts() {

        LinkedHashMap<String,Integer> counts = new LinkedHashMap<>();
        counts.put("Basic", 0);
        counts.put("Standard", 0);
        counts.put("Premium", 0);

        for (String[] sub : getAllSubscriptions()) {
            counts.merge(sub[1], 1, Integer::sum);
        }

        return counts;
    }

}
