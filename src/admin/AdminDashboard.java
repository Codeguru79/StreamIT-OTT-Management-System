package admin;

import javax.swing.*;
import java.awt.*;
import admin.ViewUsersFrame;
import admin.AddMovieFrame;
import admin.DeleteMovieFrame;
import admin.EditMovieFrame;
import admin.AdminStats;
import utils.UITheme;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {

        setTitle("StreamIt Admin Panel");
        setSize(600,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(UITheme.BG_DARK);

        JLabel title = new JLabel("ADMIN PANEL");
        title.setBounds(180,30,300,40);
        title.setForeground(UITheme.ACCENT);
        title.setFont(UITheme.fontTitle(32));

        JPanel statsCard = UITheme.roundedPanel(UITheme.BG_CARD, 14);
        statsCard.setLayout(null);
        statsCard.setBounds(40,90,400,165);

        JLabel users = new JLabel("👥 Total Users : " + AdminStats.totalUsers());
users.setBounds(20,10,360,25);

JLabel movies = new JLabel("🎬 Total Movies : " + AdminStats.totalMovies());
movies.setBounds(20,40,360,25);

JLabel watchlist = new JLabel("❤️ Watchlist Entries : " + AdminStats.totalWatchlist());
watchlist.setBounds(20,70,360,25);

JLabel history = new JLabel("🕒 Watch History : " + AdminStats.totalHistory());
history.setBounds(20,100,360,25);

JLabel revenueStat = new JLabel("💰 Monthly Revenue : ₹" + subscription.SubscriptionManager.getTotalRevenue());
revenueStat.setBounds(20,130,360,25);

users.setForeground(UITheme.TEXT_WHITE);
movies.setForeground(UITheme.TEXT_WHITE);
watchlist.setForeground(UITheme.TEXT_WHITE);
history.setForeground(UITheme.TEXT_WHITE);
revenueStat.setForeground(UITheme.TEXT_WHITE);
users.setFont(UITheme.fontBody(14));
movies.setFont(UITheme.fontBody(14));
watchlist.setFont(UITheme.fontBody(14));
history.setFont(UITheme.fontBody(14));
revenueStat.setFont(UITheme.fontBody(14));

statsCard.add(users);
statsCard.add(movies);
statsCard.add(watchlist);
statsCard.add(history);
statsCard.add(revenueStat);

        JButton addMovie = UITheme.secondaryButton("➕ Add Movie");
        addMovie.addActionListener(e -> new AddMovieFrame());

        JButton deleteMovie = UITheme.secondaryButton("❌ Delete Movie");
        deleteMovie.addActionListener(e -> new DeleteMovieFrame());

        JButton viewUsers = UITheme.secondaryButton("👥 View Users");
        viewUsers.addActionListener(e -> new ViewUsersFrame());

        JButton editMovie = UITheme.secondaryButton("✏ Edit Movie");
        editMovie.addActionListener(e -> new EditMovieFrame());

        JButton revenue = UITheme.secondaryButton("💰 Revenue");
        revenue.addActionListener(e -> new AdminRevenueFrame());

        JButton logout = UITheme.primaryButton("Logout");
        logout.addActionListener(e -> dispose());

        int colLeft = 60, colRight = 300, btnW = 220, btnH = 45;

        addMovie.setBounds(colLeft,275,btnW,btnH);
        editMovie.setBounds(colRight,275,btnW,btnH);

        deleteMovie.setBounds(colLeft,330,btnW,btnH);
        viewUsers.setBounds(colRight,330,btnW,btnH);

        revenue.setBounds(colLeft,385,btnW,btnH);
        logout.setBounds(colRight,385,btnW,btnH);

        setSize(600,500);

        panel.add(title);
        panel.add(statsCard);
        panel.add(addMovie);
        panel.add(editMovie);
        panel.add(deleteMovie);
        panel.add(viewUsers);
        panel.add(revenue);
        panel.add(logout);
        

        add(panel);

        setVisible(true);
    }
}