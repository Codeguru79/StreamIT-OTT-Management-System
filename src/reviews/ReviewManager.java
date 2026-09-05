package reviews;

import java.io.*;
import java.util.ArrayList;

public class ReviewManager {

    private static final String FILE = "data/reviews.txt";

    public static class Review {
        public int movieId;
        public String email;
        public String name;
        public int rating;
        public String comment;

        public Review(int movieId, String email, String name, int rating, String comment) {
            this.movieId = movieId;
            this.email = email;
            this.name = name;
            this.rating = rating;
            this.comment = comment;
        }
    }

    // Add a review, replacing any existing review by the same user for the same movie
    public static void addReview(int movieId, String email, String name, int rating, String comment) {

        // File format uses | as a separator - strip it (and newlines) out of free text
        String safeComment = comment == null ? "" : comment.replace("|", " ").replace("\n", " ").trim();
        String safeName = name == null ? "" : name.replace("|", " ").trim();

        try {

            File file = new File(FILE);
            ArrayList<String> lines = new ArrayList<>();
            boolean updated = false;

            if (file.exists()) {

                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {

                    if (line.trim().isEmpty()) continue;

                    String[] data = line.split("\\|", 5);

                    if (data.length >= 2 && data[0].equals(String.valueOf(movieId))
                            && data[1].equalsIgnoreCase(email)) {

                        lines.add(movieId + "|" + email + "|" + safeName + "|" + rating + "|" + safeComment);
                        updated = true;

                    } else {
                        lines.add(line);
                    }
                }

                br.close();
            }

            if (!updated) {
                lines.add(movieId + "|" + email + "|" + safeName + "|" + rating + "|" + safeComment);
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

    public static ArrayList<Review> getReviews(int movieId) {

        ArrayList<Review> list = new ArrayList<>();

        try {

            File file = new File(FILE);

            if (!file.exists()) return list;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] data = line.split("\\|", 5);

                if (data.length >= 5 && data[0].equals(String.valueOf(movieId))) {

                    try {
                        list.add(new Review(
                                movieId,
                                data[1],
                                data[2],
                                Integer.parseInt(data[3]),
                                data[4]
                        ));
                    } catch (NumberFormatException ignored) {}

                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static double getAverageRating(int movieId) {

        ArrayList<Review> reviews = getReviews(movieId);

        if (reviews.isEmpty()) return 0;

        int sum = 0;

        for (Review r : reviews) {
            sum += r.rating;
        }

        return Math.round((sum / (double) reviews.size()) * 10.0) / 10.0;
    }

    public static int getReviewCount(int movieId) {
        return getReviews(movieId).size();
    }

}
