package admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
import subscription.SubscriptionManager;
import utils.UITheme;

public class AdminRevenueFrame extends JFrame {

    public AdminRevenueFrame() {

        setTitle("Revenue Dashboard");
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(UITheme.BG_DARK);

        JLabel heading = new JLabel("💰 Revenue Dashboard");
        heading.setBounds(30,20,400,40);
        heading.setForeground(UITheme.ACCENT);
        heading.setFont(UITheme.fontTitle(24));
        add(heading);

        int total = SubscriptionManager.getTotalRevenue();
        Map<String,Integer> counts = SubscriptionManager.getPlanCounts();

        // ---- Total revenue card ----
        JPanel totalCard = UITheme.roundedPanel(UITheme.BG_CARD, 14);
        totalCard.setLayout(null);
        totalCard.setBounds(30,80,530,70);

        JLabel totalLabel = new JLabel("Total Monthly Revenue :  ₹" + total);
        totalLabel.setBounds(20,20,480,30);
        totalLabel.setForeground(UITheme.TEXT_WHITE);
        totalLabel.setFont(UITheme.fontTitle(18));
        totalCard.add(totalLabel);
        add(totalCard);

        // ---- Plan breakdown card ----
        JPanel breakdownCard = UITheme.roundedPanel(UITheme.BG_CARD, 14);
        breakdownCard.setLayout(null);
        breakdownCard.setBounds(30,165,530,140);

        int y = 15;

        for (Map.Entry<String,Integer> entry : counts.entrySet()) {

            int price = SubscriptionManager.getPlanPrice(entry.getKey());
            int subs = entry.getValue();
            int revenue = price * subs;

            JLabel row = new JLabel(
                    entry.getKey() + " Plan   —   " + subs + " subscriber(s)   —   ₹" + price
                            + " each   —   ₹" + revenue + " total"
            );
            row.setBounds(20,y,490,25);
            row.setForeground(UITheme.TEXT_GRAY);
            row.setFont(UITheme.fontBody(14));
            breakdownCard.add(row);

            y += 38;
        }

        add(breakdownCard);

        // ---- Subscribers table ----
        JLabel subsHeading = new JLabel("Subscribers");
        subsHeading.setBounds(30,320,300,25);
        subsHeading.setForeground(UITheme.TEXT_WHITE);
        subsHeading.setFont(UITheme.fontTitle(16));
        add(subsHeading);

        String[] columns = {"Email","Plan","Price (₹)"};
        DefaultTableModel model = new DefaultTableModel(columns,0);

        for (String[] sub : SubscriptionManager.getAllSubscriptions()) {
            model.addRow(new Object[]{ sub[0], sub[1], SubscriptionManager.getPlanPrice(sub[1]) });
        }

        JTable table = new JTable(model);
        table.setBackground(UITheme.BG_CARD);
        table.setForeground(UITheme.TEXT_WHITE);
        table.setGridColor(UITheme.FIELD_BORDER);
        table.setRowHeight(26);
        table.setFont(UITheme.fontBody(13));
        table.setSelectionBackground(UITheme.ACCENT);
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setBackground(UITheme.BG_TOPBAR);
        table.getTableHeader().setForeground(UITheme.TEXT_WHITE);
        table.getTableHeader().setFont(UITheme.fontTitle(13));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(30,350,530,150);
        UITheme.styleScrollBar(scroll);
        scroll.getViewport().setBackground(UITheme.BG_CARD);
        add(scroll);

        JButton close = UITheme.secondaryButton("Close");
        close.setBounds(220,520,150,40);
        close.addActionListener(e -> dispose());
        add(close);

        setVisible(true);

    }

}
