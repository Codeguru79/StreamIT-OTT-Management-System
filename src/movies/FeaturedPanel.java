package movies;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import utils.UITheme;

public class FeaturedPanel extends JPanel {

    private ArrayList<Movie> movies;
    private Movie currentMovie;
    private int currentIndex = 0;

    private JLabel posterLabel;
    private JLabel titleLabel;
    private JLabel infoLabel;
    private JTextArea descriptionArea;

    private JButton watchButton;
    private JButton listButton;

    public void setMovie(Movie movie) {

    showMovie(movie);

}

    public FeaturedPanel() {

        movies = MovieManager.getAllMovies();

        setLayout(null);
        setPreferredSize(new Dimension(1100,270));
        setBackground(UITheme.BG_CARD);

        posterLabel = new JLabel();
        posterLabel.setBounds(25,15,180,240);

        titleLabel = new JLabel();
        titleLabel.setBounds(240,20,700,45);
        titleLabel.setForeground(UITheme.TEXT_WHITE);
        titleLabel.setFont(UITheme.fontTitle(38));

        infoLabel = new JLabel();
        infoLabel.setBounds(240,75,600,25);
        infoLabel.setForeground(UITheme.TEXT_GRAY);
        infoLabel.setFont(UITheme.fontBody(18));

        descriptionArea = new JTextArea();
        descriptionArea.setBounds(240,115,600,70);
        descriptionArea.setOpaque(false);
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setForeground(UITheme.TEXT_WHITE);
        descriptionArea.setFont(UITheme.fontBody(16));

        watchButton = UITheme.primaryButton("▶ Watch Now");
        watchButton.setBounds(240,205,160,38);
        watchButton.addActionListener(e -> {

        if(currentMovie != null){

            new MovieDetails(currentMovie);

    }

});

        listButton = UITheme.secondaryButton("❤ My List");
        listButton.setBounds(415,205,140,38);
        listButton.addActionListener(e -> {

    if(currentMovie != null){

        JOptionPane.showMessageDialog(
                this,
                currentMovie.getTitle() + " added to Watchlist");

    }

});

        add(posterLabel);
        add(titleLabel);
        add(infoLabel);
        add(descriptionArea);
        add(watchButton);
        add(listButton);

        if(!movies.isEmpty())
            showMovie(movies.get(0));

        Timer timer = new Timer(5000,e->{

            currentIndex++;

            if(currentIndex>=movies.size())
                currentIndex=0;

            showMovie(movies.get(currentIndex));

        });

        timer.start();
    }

    private void showMovie(Movie movie){
        currentMovie=   movie;

        titleLabel.setText(movie.getTitle());

        infoLabel.setText(
                "⭐ " +
                        movie.getRating() +
                        "   |   " +
                        movie.getGenre() +
                        "   |   " +
                        movie.getLanguage() +
                        "   |   " +
                        movie.getYear() +
                        "   |   " +
                        movie.getDuration()+" min"
        );

        descriptionArea.setText(movie.getDescription());

        try{

            ImageIcon icon=new ImageIcon(
                    "src/assets/posters/"+movie.getPoster());

            Image img=icon.getImage().getScaledInstance(
                    180,
                    240,
                    Image.SCALE_SMOOTH);

            posterLabel.setIcon(new ImageIcon(img));

        }
        catch(Exception e){

            posterLabel.setIcon(null);

        }

    }

}