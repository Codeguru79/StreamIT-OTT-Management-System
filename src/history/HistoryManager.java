package history;

import movies.Movie;

import java.io.*;
import java.util.ArrayList;

public class HistoryManager {

    private static final String FILE = "data/history.txt";

    public static void addMovie(Movie movie) {

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE, true));

            bw.write(movie.getTitle());
            bw.newLine();

            bw.close();

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