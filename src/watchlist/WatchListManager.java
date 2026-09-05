package watchlist;

import movies.Movie;

import java.io.*;
import java.util.ArrayList;

public class WatchListManager {

    private static final String FILE = "data/watchlist.txt";

    public static void addMovie(Movie movie) {

    System.out.println("Adding: " + movie.getTitle());

    try {

        BufferedWriter bw = new BufferedWriter(new FileWriter(FILE, true));

        bw.write(movie.getTitle());
        bw.newLine();

        bw.close();

        System.out.println("Movie saved successfully.");

    } catch (Exception e) {
        e.printStackTrace();
    }

}

    public static ArrayList<String> getMovies() {

        ArrayList<String> list = new ArrayList<>();

        try {

            BufferedReader br = new BufferedReader(new FileReader(FILE));

            String line;

            while ((line = br.readLine()) != null) {

                list.add(line);

            }

            br.close();

        } catch (Exception e) {

        }

        return list;

    }

}