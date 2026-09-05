package app;

import javax.swing.SwingUtilities;
import ui.SplashScreen;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new SplashScreen();
        });

    }
}