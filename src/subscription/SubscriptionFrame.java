package subscription;

import javax.swing.*;
import java.awt.*;
import subscription.SubscriptionManager;
import utils.CurrentUser;
import utils.UITheme;

public class SubscriptionFrame extends JFrame {

    public SubscriptionFrame() {

        setTitle("Subscription Plans");
        setSize(500,470);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("💎 Choose Your Plan", SwingConstants.CENTER);
        heading.setFont(UITheme.fontTitle(22));
        heading.setForeground(UITheme.ACCENT);
        heading.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
        add(heading, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,1,15,15));
        panel.setBackground(UITheme.BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(10,30,30,30));

        JButton basic = UITheme.secondaryButton("Basic - ₹99 / Month");
        JButton standard = UITheme.secondaryButton("Standard - ₹199 / Month");
        JButton premium = UITheme.primaryButton("Premium - ₹299 / Month");

        basic.addActionListener(e -> {

    SubscriptionManager.savePlan(CurrentUser.getEmail(),"Basic");

    JOptionPane.showMessageDialog(this,
            "Basic Plan Activated!");

});

standard.addActionListener(e -> {

    SubscriptionManager.savePlan(CurrentUser.getEmail(),"Standard");

    JOptionPane.showMessageDialog(this,
            "Standard Plan Activated!");

});

premium.addActionListener(e -> {

    SubscriptionManager.savePlan(CurrentUser.getEmail(),"Premium");

    JOptionPane.showMessageDialog(this,
            "Premium Plan Activated!");

});

        panel.add(basic);
        panel.add(standard);
        panel.add(premium);

        add(panel);

        setVisible(true);

    }

}