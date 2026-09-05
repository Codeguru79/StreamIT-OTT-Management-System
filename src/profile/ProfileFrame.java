package profile;

import history.HistoryFrame;
import subscription.SubscriptionFrame;
import ui.Login;
import utils.CurrentUser;
import watchlist.WatchListFrame;
import subscription.SubscriptionManager;

import javax.swing.*;
import java.awt.*;
import utils.UITheme;

public class ProfileFrame extends JFrame {

    public ProfileFrame() {

        setTitle("My Profile");
        setSize(500,550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(UITheme.BG_DARK);

        JLabel heading = new JLabel("MY PROFILE");
        heading.setBounds(130,20,250,40);
        heading.setForeground(UITheme.ACCENT);
        heading.setFont(UITheme.fontTitle(28));

        JPanel infoCard = UITheme.roundedPanel(UITheme.BG_CARD, 14);
        infoCard.setLayout(null);
        infoCard.setBounds(40,85,420,120);

        JLabel name = new JLabel("Name : " + CurrentUser.getName());
        name.setBounds(20,10,380,30);
        name.setForeground(UITheme.TEXT_WHITE);
        name.setFont(UITheme.fontBody(16));

        JLabel email = new JLabel("Email : " + CurrentUser.getEmail());
        email.setBounds(20,45,380,30);
        email.setForeground(UITheme.TEXT_WHITE);
        email.setFont(UITheme.fontBody(16));

        JLabel plan = new JLabel(
        "Subscription : " +
        SubscriptionManager.getPlan(CurrentUser.getEmail())
);
        plan.setBounds(20,80,380,30);
        plan.setForeground(UITheme.TEXT_WHITE);
        plan.setFont(UITheme.fontBody(16));

        infoCard.add(name);
        infoCard.add(email);
        infoCard.add(plan);

        JButton watchlist = UITheme.secondaryButton("❤ My Watchlist");
        watchlist.setBounds(120,240,240,42);

        JButton history = UITheme.secondaryButton("🕒 Watch History");
        history.setBounds(120,296,240,42);

        JButton subscription = UITheme.secondaryButton("💎 Subscription");
        subscription.setBounds(120,352,240,42);

        JButton editProfile = UITheme.secondaryButton("✏ Edit Profile");
        editProfile.setBounds(120,408,240,42);

        JButton logout = UITheme.primaryButton("🚪 Logout");
        logout.setBounds(120,464,240,42);

        watchlist.addActionListener(e -> new WatchListFrame());

        history.addActionListener(e -> new HistoryFrame());

        subscription.addActionListener(e -> new SubscriptionFrame());

        editProfile.addActionListener(e -> new EditProfileFrame());

        logout.addActionListener(e -> {

            dispose();

            new Login();

        });

        panel.add(heading);
        panel.add(infoCard);
        panel.add(watchlist);
        panel.add(history);
        panel.add(subscription);
        panel.add(editProfile);
        panel.add(logout);

        add(panel);

        setSize(500,600);
        setVisible(true);

        addWindowFocusListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowGainedFocus(java.awt.event.WindowEvent e) {

                name.setText("Name : " + CurrentUser.getName());
                plan.setText("Subscription : " + SubscriptionManager.getPlan(CurrentUser.getEmail()));

            }

        });

    }
}