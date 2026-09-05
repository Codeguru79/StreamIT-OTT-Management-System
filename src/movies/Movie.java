package movies;

public class Movie {

    private int id;
    private String title;
    private String genre;
    private String language;
    private int year;
    private double rating;
    private int duration;
    private String description;
    private String poster;

    public Movie(int id, String title, String genre, String language,
                 int year, double rating, int duration,
                 String description, String poster) {

        this.id = id;
        this.title = title;
        this.genre = genre;
        this.language = language;
        this.year = year;
        this.rating = rating;
        this.duration = duration;
        this.description = description;
        this.poster = poster;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getLanguage() {
        return language;
    }

    public int getYear() {
        return year;
    }

    public double getRating() {
        return rating;
    }

    public int getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public String getPoster() {
        return poster;
    }
}