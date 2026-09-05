package admin;

import java.io.*;

public class AdminStats {

    public static int countLines(String fileName) {

        int count = 0;

        try {

            File file = new File(fileName);

            if(!file.exists())
                return 0;

            BufferedReader br = new BufferedReader(new FileReader(file));

            while(br.readLine() != null){

                count++;

            }

            br.close();

        } catch(Exception e){

            e.printStackTrace();

        }

        return count;

    }

    public static int totalUsers(){

        return countLines("data/users.txt");

    }

    public static int totalMovies(){

        return countLines("data/movies.txt");

    }

    public static int totalWatchlist(){

        return countLines("data/watchlist.txt");

    }

    public static int totalHistory(){

        return countLines("data/history.txt");

    }

}
