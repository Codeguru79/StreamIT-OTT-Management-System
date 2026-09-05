package ui;

import javax.swing.*;


import admin.AdminDashboard;

import java.awt.*;
import utils.FileManager;
import utils.CurrentUser;
import utils.UITheme;


public class Login extends JFrame {

    JTextField emailField;
    JPasswordField passwordField;


    public Login() {


        setTitle("StreamIt - Login");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(UITheme.BG_DARK);

        JLabel title = new JLabel("StreamIt", SwingConstants.CENTER);
        title.setFont(UITheme.fontTitle(40));
        title.setForeground(UITheme.ACCENT);
        title.setBounds(300,40,300,50);

        JLabel subtitle = new JLabel("Entertainment Redefined", SwingConstants.CENTER);
        subtitle.setFont(UITheme.fontBody(16));
        subtitle.setForeground(UITheme.TEXT_GRAY);
        subtitle.setBounds(300,88,300,30);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(UITheme.TEXT_GRAY);
        emailLabel.setFont(UITheme.fontBody(14));
        emailLabel.setBounds(250,170,200,25);

        emailField = UITheme.textField();
        emailField.setBounds(250,198,400,42);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(UITheme.TEXT_GRAY);
        passwordLabel.setFont(UITheme.fontBody(14));
        passwordLabel.setBounds(250,258,200,25);

        passwordField = UITheme.passwordField();
        passwordField.setBounds(250,286,400,42);

        JButton loginButton = UITheme.primaryButton("Login");
        loginButton.setBounds(250,368,190,46);

        JButton registerButton = UITheme.secondaryButton("Register");
        registerButton.setBounds(460,368,190,46);

        panel.add(title);
        panel.add(subtitle);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);

        loginButton.addActionListener(e -> {

    String email = emailField.getText().trim();
    String password = new String(passwordField.getPassword());

    if(email.isEmpty() || password.isEmpty()){

        JOptionPane.showMessageDialog(
                this,
                "Please enter Email and Password.");

        return;
    }

    // Admin Login
    // Admin Login
    System.out.println("Email = " + email);
System.out.println("Admin = " + FileManager.isAdmin(email, password));
if(FileManager.isAdmin(email,password)){

    CurrentUser.setUser("Admin", email);
    CurrentUser.setAdmin(true);

    JOptionPane.showMessageDialog(
            this,
            "Welcome Admin!");

    dispose();

    new Dashboard();

    return;
}

    // User Login
    if(FileManager.loginUser(email,password)){

    CurrentUser.setUser(
        
            FileManager.getLoggedInUserName(),
            email
    );
    CurrentUser.setAdmin(false);

    JOptionPane.showMessageDialog(
            this,
            "Login Successful!");

    dispose();

    new Dashboard();

}else{

        JOptionPane.showMessageDialog(
                this,
                "Invalid Email or Password.",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);

    }
    

});

        registerButton.addActionListener(e -> {
            dispose();
            new Register();
        });

        setVisible(true);
    }

}