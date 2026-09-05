package movies;
import history.HistoryManager;

import watchlist.WatchListManager;
import reviews.ReviewManager;
import utils.CurrentUser;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import utils.UITheme;

public class MovieDetails extends JFrame {

    private JTextArea reviewsArea;
    private JLabel communityRating;
    private int movieId;

    public MovieDetails(Movie movie) {

        this.movieId = movie.getId();

        setTitle(movie.getTitle());
        setSize(700, 860);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(UITheme.BG_DARK);

        // Poster
        JLabel poster = new JLabel();
        poster.setBounds(30,30,220,300);

        try{
            ImageIcon icon = new ImageIcon("src/assets/posters/" + movie.getPoster());
            Image img = icon.getImage().getScaledInstance(220,300,Image.SCALE_SMOOTH);
            poster.setIcon(new ImageIcon(img));
        }catch(Exception e){
            poster.setOpaque(true);
            poster.setBackground(Color.GRAY);
            poster.setText("No Image");
            poster.setHorizontalAlignment(SwingConstants.CENTER);
        }

        // Title
        JLabel title = new JLabel(movie.getTitle());
        title.setBounds(280,30,350,40);
        title.setForeground(UITheme.TEXT_WHITE);
        title.setFont(UITheme.fontTitle(30));

        // Rating
        JLabel rating = new JLabel("⭐ Rating : " + movie.getRating());
        rating.setBounds(280,90,300,25);
        rating.setForeground(UITheme.TEXT_GRAY);
        rating.setFont(UITheme.fontBody(14));

        // Genre
        JLabel genre = new JLabel("🎭 Genre : " + movie.getGenre());
        genre.setBounds(280,125,300,25);
        genre.setForeground(UITheme.TEXT_GRAY);
        genre.setFont(UITheme.fontBody(14));

        // Language
        JLabel language = new JLabel("🌐 Language : " + movie.getLanguage());
        language.setBounds(280,160,300,25);
        language.setForeground(UITheme.TEXT_GRAY);
        language.setFont(UITheme.fontBody(14));

        // Year
        JLabel year = new JLabel("📅 Year : " + movie.getYear());
        year.setBounds(280,195,300,25);
        year.setForeground(UITheme.TEXT_GRAY);
        year.setFont(UITheme.fontBody(14));

        // Duration
        JLabel duration = new JLabel("⏱ Duration : " + movie.getDuration() + " min");
        duration.setBounds(280,230,300,25);
        duration.setForeground(UITheme.TEXT_GRAY);
        duration.setFont(UITheme.fontBody(14));

        // Community (viewer) rating
        communityRating = new JLabel();
        communityRating.setBounds(280,265,320,25);
        communityRating.setForeground(new Color(255, 199, 0));
        communityRating.setFont(UITheme.fontBody(14));
        refreshCommunityRating();

        JLabel descLabel = new JLabel("Description");
        descLabel.setBounds(30,355,200,25);
        descLabel.setForeground(UITheme.TEXT_WHITE);
        descLabel.setFont(UITheme.fontTitle(18));

        JTextArea description = new JTextArea(movie.getDescription());
        description.setBounds(30,385,620,85);
        description.setEditable(false);
        description.setWrapStyleWord(true);
        description.setLineWrap(true);
        description.setBackground(UITheme.BG_CARD);
        description.setForeground(UITheme.TEXT_WHITE);
        description.setFont(UITheme.fontBody(14));
        description.setBorder(new UITheme.RoundedLineBorder(UITheme.FIELD_BORDER, 10, 14));

        JButton watch = UITheme.primaryButton("▶ Watch Now");
        watch.setBounds(30,485,160,42);

        JButton watchlist = UITheme.secondaryButton("❤ Add To Watchlist");
        watchlist.setBounds(210,485,190,42);

        JButton close = UITheme.secondaryButton("Close");
        close.setBounds(490,485,160,42);

        close.addActionListener(e -> dispose());

       watch.addActionListener(e -> {

    HistoryManager.addMovie(movie);

    JOptionPane.showMessageDialog(
            this,
            "Now Playing\n\n" + movie.getTitle()
    );

});

        watchlist.addActionListener(e -> {

    WatchListManager.addMovie(movie);

    JOptionPane.showMessageDialog(
            this,
            movie.getTitle() + " added to Watchlist!"
    );

});

        // ---------------- Rate & Review ----------------

        JLabel reviewHeading = new JLabel("Rate & Review");
        reviewHeading.setBounds(30,545,300,25);
        reviewHeading.setForeground(UITheme.TEXT_WHITE);
        reviewHeading.setFont(UITheme.fontTitle(18));

        JLabel yourRatingLabel = new JLabel("Your Rating:");
        yourRatingLabel.setBounds(30,580,90,32);
        yourRatingLabel.setForeground(UITheme.TEXT_GRAY);
        yourRatingLabel.setFont(UITheme.fontBody(14));

        JComboBox<String> starBox = new JComboBox<>(new String[]{"5 ⭐","4 ⭐","3 ⭐","2 ⭐","1 ⭐"});
        starBox.setBounds(120,580,90,32);

        JTextField commentField = UITheme.textField();
        commentField.setBounds(220,580,300,32);

        JButton submitReview = UITheme.primaryButton("Submit");
        submitReview.setBounds(530,580,120,32);

        submitReview.addActionListener(e -> {

            if (CurrentUser.getEmail() == null || CurrentUser.getEmail().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please log in to leave a review.");
                return;
            }

            String selected = (String) starBox.getSelectedItem();
            int stars = Integer.parseInt(selected.split(" ")[0]);

            ReviewManager.addReview(
                    movieId,
                    CurrentUser.getEmail(),
                    CurrentUser.getName(),
                    stars,
                    commentField.getText()
            );

            commentField.setText("");
            refreshCommunityRating();
            loadReviews();

            JOptionPane.showMessageDialog(this, "Thanks for your review!");

        });

        JLabel reviewsListHeading = new JLabel("Viewer Reviews");
        reviewsListHeading.setBounds(30,625,300,25);
        reviewsListHeading.setForeground(UITheme.TEXT_WHITE);
        reviewsListHeading.setFont(UITheme.fontTitle(16));

        reviewsArea = new JTextArea();
        reviewsArea.setEditable(false);
        reviewsArea.setLineWrap(true);
        reviewsArea.setWrapStyleWord(true);
        reviewsArea.setBackground(UITheme.BG_CARD);
        reviewsArea.setForeground(UITheme.TEXT_WHITE);
        reviewsArea.setFont(UITheme.fontBody(13));

        JScrollPane reviewsScroll = new JScrollPane(reviewsArea);
        reviewsScroll.setBounds(30,655,620,155);
        reviewsScroll.setBorder(new UITheme.RoundedLineBorder(UITheme.FIELD_BORDER, 10, 10));
        UITheme.styleScrollBar(reviewsScroll);
        reviewsScroll.getViewport().setBackground(UITheme.BG_CARD);

        loadReviews();

        add(poster);
        add(title);
        add(rating);
        add(genre);
        add(language);
        add(year);
        add(duration);
        add(communityRating);
        add(descLabel);
        add(description);
        add(watch);
        add(watchlist);
        add(close);
        add(reviewHeading);
        add(yourRatingLabel);
        add(starBox);
        add(commentField);
        add(submitReview);
        add(reviewsListHeading);
        add(reviewsScroll);

        setVisible(true);
    }

    private void refreshCommunityRating() {

        int count = ReviewManager.getReviewCount(movieId);

        if (count == 0) {
            communityRating.setText("⭐ Viewer Rating : No reviews yet");
        } else {
            double avg = ReviewManager.getAverageRating(movieId);
            communityRating.setText("⭐ Viewer Rating : " + avg + " (" + count + " review" + (count == 1 ? "" : "s") + ")");
        }
    }

    private void loadReviews() {

        ArrayList<ReviewManager.Review> reviews = ReviewManager.getReviews(movieId);

        if (reviews.isEmpty()) {
            reviewsArea.setText("No reviews yet. Be the first to review this movie!");
            return;
        }

        StringBuilder sb = new StringBuilder();

        for (ReviewManager.Review r : reviews) {

            String stars = "★".repeat(Math.max(0, Math.min(5, r.rating)));
            String displayName = (r.name == null || r.name.isEmpty()) ? r.email : r.name;

            sb.append(displayName).append("  ").append(stars).append("\n");

            if (r.comment != null && !r.comment.isEmpty()) {
                sb.append(r.comment).append("\n");
            }

            sb.append("\n");
        }

        reviewsArea.setText(sb.toString());
        reviewsArea.setCaretPosition(0);
    }
}
