package ui;

import javax.swing.*;
import java.awt.*;
import utils.UITheme;

public class SplashScreen extends JFrame {

    public SplashScreen() {

        setTitle("StreamIt");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBackground(UITheme.BG_DARK);
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(UITheme.FIELD_BORDER, 1));

        JLabel title = new JLabel("StreamIt", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 52));
        title.setForeground(UITheme.ACCENT);
        title.setBounds(200,150,500,65);

        JLabel subtitle = new JLabel("Your Desktop OTT Platform", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN,18));
        subtitle.setForeground(UITheme.TEXT_GRAY);
        subtitle.setBounds(200,218,500,30);

        JProgressBar progress = new JProgressBar();
        progress.setBounds(200,330,500,14);
        progress.setValue(0);
        progress.setStringPainted(false);
        progress.setBorderPainted(false);
        progress.setBackground(UITheme.BG_CARD);
        progress.setForeground(UITheme.ACCENT);

        panel.add(title);
        panel.add(subtitle);
        panel.add(progress);

        add(panel);

        setVisible(true);

        Timer timer = new Timer(20, null);

        timer.addActionListener(e -> {

            int value = progress.getValue();

            if(value < 100){
                progress.setValue(value + 1);
            }else{
                timer.stop();
                dispose();
                new Login();
            }

        });

        timer.start();
    }

}