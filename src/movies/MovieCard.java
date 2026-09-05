package movies;
import movies.MovieDetails;
import utils.UITheme;

import javax.swing.*;
import java.awt.*;

public class MovieCard extends JPanel {

    private static final int RADIUS = 14;
    private Color bg = UITheme.BG_CARD;

    public MovieCard(Movie movie) {

        setPreferredSize(new Dimension(180,320));
        setOpaque(false);
        setLayout(null);

        // Rounded-corner hover highlight
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                bg = new Color(44, 44, 48);
                repaint();
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                bg = UITheme.BG_CARD;
                repaint();
            }
        });

        JLabel poster = new JLabel();
        poster.setBounds(15,15,150,200);

        try {

            ImageIcon icon = new ImageIcon("src/assets/posters/" + movie.getPoster());

            Image img = icon.getImage().getScaledInstance(
                    150,
                    200,
                    Image.SCALE_SMOOTH);

            poster.setIcon(new ImageIcon(img));

        } catch (Exception e) {

            poster.setOpaque(true);
            poster.setBackground(Color.GRAY);
            poster.setText("No Image");
            poster.setHorizontalAlignment(SwingConstants.CENTER);
        }

        JLabel title = new JLabel(movie.getTitle(),SwingConstants.CENTER);
        title.setBounds(10,222,160,22);
        title.setForeground(UITheme.TEXT_WHITE);
        title.setFont(UITheme.fontTitle(14));

        JLabel genre = new JLabel(movie.getGenre(),SwingConstants.CENTER);
        genre.setBounds(10,246,160,18);
        genre.setForeground(UITheme.TEXT_GRAY);
        genre.setFont(UITheme.fontBody(12));

        int reviewCount = reviews.ReviewManager.getReviewCount(movie.getId());
        String ratingText = "⭐ " + movie.getRating() + (reviewCount > 0 ? "  (" + reviewCount + " reviews)" : "");

        JLabel rating = new JLabel(ratingText,SwingConstants.CENTER);
        rating.setBounds(10,266,160,18);
        rating.setForeground(new Color(255, 199, 0));
        rating.setFont(UITheme.fontBody(11));

        JButton watch = UITheme.primaryButton("▶ Watch");
        watch.addActionListener(e -> new MovieDetails(movie));
        watch.setBounds(18,288,145,26);

        add(poster);
        add(title);
        add(genre);
        add(rating);
        add(watch);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(bg);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
        g2.dispose();
        super.paintComponent(g);
    }
}