package ui;

import javax.swing.*;
import java.awt.*;
import utils.FileManager;
import utils.UITheme;

public class Register extends JFrame {

    JTextField nameField;
    JTextField emailField;
    JPasswordField passwordField;

    public Register() {

        setTitle("StreamIt - Register");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(UITheme.BG_DARK);

        JLabel title = new JLabel("Create Account");
        title.setBounds(200, 30, 350, 40);
        title.setFont(UITheme.fontTitle(30));
        title.setForeground(UITheme.TEXT_WHITE);

        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setBounds(120, 100, 150, 25);
        nameLabel.setForeground(UITheme.TEXT_GRAY);
        nameLabel.setFont(UITheme.fontBody(14));

        nameField = UITheme.textField();
        nameField.setBounds(120, 125, 450, 42);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(120, 185, 150, 25);
        emailLabel.setForeground(UITheme.TEXT_GRAY);
        emailLabel.setFont(UITheme.fontBody(14));

        emailField = UITheme.textField();
        emailField.setBounds(120, 210, 450, 42);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(120, 270, 150, 25);
        passwordLabel.setForeground(UITheme.TEXT_GRAY);
        passwordLabel.setFont(UITheme.fontBody(14));

        passwordField = UITheme.passwordField();
        passwordField.setBounds(120, 295, 450, 42);

        JButton registerButton = UITheme.primaryButton("Register");
        registerButton.setBounds(120, 390, 210, 46);

        JButton backButton = UITheme.secondaryButton("Back");
        backButton.setBounds(360, 390, 210, 46);

        panel.add(title);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(registerButton);
        panel.add(backButton);

        add(panel);

        // Register Button
        registerButton.addActionListener(e -> {

            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all the fields.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

                return;
            }

            boolean success = FileManager.registerUser(name, email, password);

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Registration Successful!");

                dispose();
                new Login();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Registration Failed!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        // Back Button
        backButton.addActionListener(e -> {

            dispose();
            new Login();

        });

        setVisible(true);
    }
}