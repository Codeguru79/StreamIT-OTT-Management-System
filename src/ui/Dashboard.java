package ui;

import movies.FeaturedPanel;
import watchlist.WatchListFrame;
import history.HistoryFrame;
import profile.ProfileFrame;
import utils.AppState;
import movies.Movie;
import movies.MovieCard;
import movies.MovieManager;
import utils.CurrentUser;
import utils.UITheme;
import admin.AdminDashboard;



import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Dashboard extends JFrame {
    private JPanel moviePanel;
    private JTextField searchField;
    private JComboBox<String> genreBox;
    private JComboBox<String> sortBox;
    private ArrayList<Movie> movies;
    private FeaturedPanel featuredPanel;
    
private JScrollPane scrollPane;
private JPanel centerPanel;

    public Dashboard() {

        setTitle("StreamIt");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        moviePanel = new JPanel(new FlowLayout(FlowLayout.LEFT,20,20));
        JPanel mainPanel = new JPanel(new BorderLayout());
mainPanel.setBackground(UITheme.BG_DARK);

        //---------------- TOP PANEL ----------------//

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(UITheme.BG_TOPBAR);
        topPanel.setPreferredSize(new Dimension(1200,70));
        topPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0, UITheme.FIELD_BORDER));

        JLabel logo = new JLabel("  StreamIt");
        logo.setForeground(UITheme.ACCENT);
        logo.setFont(UITheme.fontTitle(28));

        searchField = UITheme.textField();
        searchField.setPreferredSize(new Dimension(250,36));
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

    public void insertUpdate(javax.swing.event.DocumentEvent e) {
        applyFilters();
    }

    public void removeUpdate(javax.swing.event.DocumentEvent e) {
        applyFilters();
    }

    public void changedUpdate(javax.swing.event.DocumentEvent e) {
        applyFilters();
    }

});

        JButton profileButton = UITheme.secondaryButton("Profile");
        profileButton.setPreferredSize(new Dimension(100,36));
        profileButton.addActionListener(e -> new ProfileFrame());
        JButton historyButton = UITheme.secondaryButton("History");
        historyButton.setPreferredSize(new Dimension(100,36));
        historyButton.addActionListener(e -> new HistoryFrame());
        JButton adminButton = UITheme.secondaryButton("Admin");
        adminButton.setPreferredSize(new Dimension(90,36));

if(CurrentUser.isAdmin()){

    adminButton.addActionListener(e -> new AdminDashboard());

    
}

        genreBox = new JComboBox<>();
        genreBox.setPreferredSize(new Dimension(140,36));
        genreBox.addItem("All Genres");
        genreBox.addActionListener(e -> applyFilters());

        sortBox = new JComboBox<>(new String[]{
                "Sort: Default",
                "Rating: High to Low",
                "Year: Newest First",
                "Title: A-Z"
        });
        sortBox.setPreferredSize(new Dimension(170,36));
        sortBox.addActionListener(e -> applyFilters());

JPanel rightPanel = new JPanel();
rightPanel.setBackground(UITheme.BG_TOPBAR);
rightPanel.add(searchField);
rightPanel.add(genreBox);
rightPanel.add(sortBox);
rightPanel.add(profileButton);
rightPanel.add(historyButton);
rightPanel.add(adminButton);

        topPanel.add(logo, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        //---------------- MOVIES ----------------//

        moviePanel = new JPanel(new FlowLayout(FlowLayout.LEFT,20,20));
        moviePanel.setBackground(UITheme.BG_DARK);

        movies = MovieManager.getAllMovies();

        populateGenres();
        loadMovies(movies);

       scrollPane = new JScrollPane(moviePanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(UITheme.BG_DARK);
        UITheme.styleScrollBar(scrollPane);

        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(UITheme.BG_DARK);

        featuredPanel = new FeaturedPanel();
        centerPanel.add(featuredPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);
        addWindowFocusListener(new java.awt.event.WindowAdapter() {

    @Override
    public void windowGainedFocus(java.awt.event.WindowEvent e) {

        refreshMovies();

    }

});
        AppState.setDashboard(this);
        System.out.println("Dashboard Registered");

    }
    private void loadMovies(ArrayList<Movie> list){

    moviePanel.removeAll();

    for(Movie movie : list){

        moviePanel.add(new MovieCard(movie));

    }

    moviePanel.revalidate();
    moviePanel.repaint();

}

private void populateGenres() {

    Object previousSelection = genreBox.getItemCount() > 0 ? genreBox.getSelectedItem() : "All Genres";

    genreBox.removeAllItems();
    genreBox.addItem("All Genres");

    java.util.TreeSet<String> genres = new java.util.TreeSet<>();

    for (Movie m : movies) {
        if (m.getGenre() != null && !m.getGenre().isEmpty()) {
            genres.add(m.getGenre());
        }
    }

    for (String g : genres) {
        genreBox.addItem(g);
    }

    if (previousSelection != null) {
        genreBox.setSelectedItem(previousSelection);
    }

}

private void applyFilters() {

    if (moviePanel == null || movies == null) return;

    String text = searchField.getText().toLowerCase();
    String genre = (String) genreBox.getSelectedItem();
    String sort = (String) sortBox.getSelectedItem();

    ArrayList<Movie> filtered = new ArrayList<>();

    for (Movie movie : movies) {

        boolean matchesText = movie.getTitle().toLowerCase().contains(text);
        boolean matchesGenre = genre == null || genre.equals("All Genres")
                || movie.getGenre().equalsIgnoreCase(genre);

        if (matchesText && matchesGenre) {
            filtered.add(movie);
        }

    }

    if (sort != null) {

        switch (sort) {
            case "Rating: High to Low":
                filtered.sort((a,b) -> Double.compare(b.getRating(), a.getRating()));
                break;
            case "Year: Newest First":
                filtered.sort((a,b) -> Integer.compare(b.getYear(), a.getYear()));
                break;
            case "Title: A-Z":
                filtered.sort((a,b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));
                break;
            default:
                break;
        }
    }

    loadMovies(filtered);

    // Update featured banner
    if (featuredPanel != null && !filtered.isEmpty()) {
        featuredPanel.setMovie(filtered.get(0));
    }

}
public void refreshMovies() {

    movies = MovieManager.getAllMovies();

    populateGenres();
    applyFilters();

}

}