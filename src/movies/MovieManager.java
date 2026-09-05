package movies;

import java.io.*;
import java.util.ArrayList;

public class MovieManager {

    private static final String MOVIE_FILE = "data/movies.txt";

    public static ArrayList<Movie> getAllMovies() {

        ArrayList<Movie> movieList = new ArrayList<>();

        try {

            File file = new File(MOVIE_FILE);

            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] data = line.split("\\|");

                if (data.length == 9) {

                    try {

                        Movie movie = new Movie(
                                Integer.parseInt(data[0]),
                                data[1],
                                data[2],
                                data[3],
                                Integer.parseInt(data[4]),
                                Double.parseDouble(data[5]),
                                Integer.parseInt(data[6]),
                                data[7],
                                data[8]
                        );

                        movieList.add(movie);

                    } catch (NumberFormatException badRow) {
                        // Skip a corrupted row instead of aborting the whole list
                        System.out.println("Skipping corrupted movie row: " + line);
                    }
                }
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieList;
    }

}