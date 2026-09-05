package watchlist;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import utils.UITheme;

public class WatchListFrame extends JFrame {

    public WatchListFrame() {

        setTitle("My Watchlist");
        setSize(500,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("  ❤ My Watchlist");
        heading.setFont(UITheme.fontTitle(22));
        heading.setForeground(UITheme.ACCENT);
        heading.setBorder(BorderFactory.createEmptyBorder(18,10,10,10));
        add(heading, BorderLayout.NORTH);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(UITheme.fontBody(17));
        area.setBackground(UITheme.BG_DARK);
        area.setForeground(UITheme.TEXT_WHITE);
        area.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));

        ArrayList<String> movies = WatchListManager.getMovies();
        System.out.println(movies);

        if (movies.isEmpty()) {
            area.setText("Your watchlist is empty.");
            area.setForeground(UITheme.TEXT_DIM);
        } else {
            for(String movie : movies){

                area.append("• " + movie + "\n\n");

            }
        }

        JScrollPane scroll = new JScrollPane(area);
        UITheme.styleScrollBar(scroll);
        scroll.getViewport().setBackground(UITheme.BG_DARK);
        add(scroll, BorderLayout.CENTER);

        setVisible(true);

    }

}