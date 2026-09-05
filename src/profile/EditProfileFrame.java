package profile;

import utils.CurrentUser;
import utils.FileManager;

import javax.swing.*;
import java.awt.*;
import utils.UITheme;

public class EditProfileFrame extends JFrame {

    public EditProfileFrame() {

        setTitle("Edit Profile");
        setSize(450,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(UITheme.BG_DARK);

        JLabel heading = new JLabel("Edit Profile");
        heading.setBounds(30,20,300,35);
        heading.setForeground(UITheme.ACCENT);
        heading.setFont(UITheme.fontTitle(24));
        add(heading);

        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setBounds(30,70,300,22);
        nameLabel.setForeground(UITheme.TEXT_GRAY);
        nameLabel.setFont(UITheme.fontBody(14));
        add(nameLabel);

        JTextField nameField = UITheme.textField();
        nameField.setText(CurrentUser.getName());
        nameField.setBounds(30,95,380,38);
        add(nameField);

        JLabel emailLabel = new JLabel("Email (cannot be changed)");
        emailLabel.setBounds(30,145,380,22);
        emailLabel.setForeground(UITheme.TEXT_GRAY);
        emailLabel.setFont(UITheme.fontBody(14));
        add(emailLabel);

        JTextField emailField = UITheme.textField();
        emailField.setText(CurrentUser.getEmail());
        emailField.setEditable(false);
        emailField.setBounds(30,170,380,38);
        add(emailField);

        JLabel passLabel = new JLabel("New Password (leave blank to keep current)");
        passLabel.setBounds(30,220,380,22);
        passLabel.setForeground(UITheme.TEXT_GRAY);
        passLabel.setFont(UITheme.fontBody(14));
        add(passLabel);

        JPasswordField passField = UITheme.passwordField();
        passField.setBounds(30,245,380,38);
        add(passField);

        JLabel confirmLabel = new JLabel("Confirm New Password");
        confirmLabel.setBounds(30,295,380,22);
        confirmLabel.setForeground(UITheme.TEXT_GRAY);
        confirmLabel.setFont(UITheme.fontBody(14));
        add(confirmLabel);

        JPasswordField confirmField = UITheme.passwordField();
        confirmField.setBounds(30,320,380,38);
        add(confirmField);

        JButton save = UITheme.primaryButton("Save Changes");
        save.setBounds(30,390,180,44);
        add(save);

        JButton cancel = UITheme.secondaryButton("Cancel");
        cancel.setBounds(230,390,180,44);
        cancel.addActionListener(e -> dispose());
        add(cancel);

        save.addActionListener(e -> {

            String newName = nameField.getText().trim();
            String newPassword = new String(passField.getPassword());
            String confirmPassword = new String(confirmField.getPassword());

            if (newName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty.");
                return;
            }

            if (!newPassword.isEmpty() || !confirmPassword.isEmpty()) {

                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(this, "Passwords do not match.");
                    return;
                }

                if (newPassword.length() < 3) {
                    JOptionPane.showMessageDialog(this, "Password must be at least 3 characters.");
                    return;
                }
            }

            boolean success = FileManager.updateProfile(CurrentUser.getEmail(), newName, newPassword);

            if (success) {

                CurrentUser.setName(newName);

                JOptionPane.showMessageDialog(this, "Profile updated successfully!");
                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Could not update profile. Please try again.");
            }

        });

        setVisible(true);

    }

}
