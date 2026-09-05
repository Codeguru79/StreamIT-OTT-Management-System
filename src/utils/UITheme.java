package utils;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 * Central visual theme for StreamIt.
 * Gives every screen the same dark, Netflix-style look:
 * flat rounded buttons, flat rounded fields, one color palette,
 * one font family. Drop-in replacements for plain Swing components -
 * no screen logic changes needed.
 */
public class UITheme {

    // ---------------- Palette ----------------
    public static final Color BG_DARK      = new Color(15, 15, 17);
    public static final Color BG_PANEL     = new Color(24, 24, 27);
    public static final Color BG_CARD      = new Color(32, 32, 36);
    public static final Color BG_TOPBAR    = new Color(20, 20, 23);

    public static final Color ACCENT       = new Color(229, 9, 20);
    public static final Color ACCENT_HOVER = new Color(255, 45, 58);
    public static final Color ACCENT_PRESS = new Color(180, 6, 16);

    public static final Color GRAY_BTN        = new Color(52, 52, 58);
    public static final Color GRAY_BTN_HOVER  = new Color(72, 72, 80);
    public static final Color GRAY_BTN_PRESS  = new Color(40, 40, 45);

    public static final Color TEXT_WHITE   = new Color(240, 240, 242);
    public static final Color TEXT_GRAY    = new Color(165, 165, 170);
    public static final Color TEXT_DIM     = new Color(120, 120, 126);

    public static final Color FIELD_BG     = new Color(38, 38, 42);
    public static final Color FIELD_BORDER = new Color(64, 64, 70);
    public static final Color FIELD_FOCUS  = ACCENT;

    // ---------------- Fonts ----------------
    private static final String FAMILY = "Segoe UI";

    public static Font fontTitle(int size)  { return new Font(FAMILY, Font.BOLD, size); }
    public static Font fontBody(int size)   { return new Font(FAMILY, Font.PLAIN, size); }
    public static Font fontButton(int size) { return new Font(FAMILY, Font.BOLD, size); }

    // ---------------- Buttons ----------------

    /** Solid red accent button - use for the primary action on a screen. */
    public static JButton primaryButton(String text) {
        return build(text, ACCENT, ACCENT_HOVER, ACCENT_PRESS, Color.WHITE, 14);
    }

    /** Flat dark-gray button - use for secondary / back / cancel actions. */
    public static JButton secondaryButton(String text) {
        return build(text, GRAY_BTN, GRAY_BTN_HOVER, GRAY_BTN_PRESS, Color.WHITE, 14);
    }

    private static JButton build(String text, Color base, Color hover, Color press, Color fg, int fontSize) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color fill;
                if (getModel().isPressed()) {
                    fill = press;
                } else if (getModel().isRollover()) {
                    fill = hover;
                } else {
                    fill = base;
                }

                g2.setColor(fill);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();

                super.paintComponent(g);
            }
        };
        btn.setFont(fontButton(fontSize));
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addChangeListener(e -> btn.repaint());
        return btn;
    }

    // ---------------- Text fields ----------------

    public static JTextField textField() {
        JTextField field = new JTextField();
        styleField(field);
        return field;
    }

    public static JPasswordField passwordField() {
        JPasswordField field = new JPasswordField();
        styleField(field);
        return field;
    }

    private static void styleField(JTextField field) {
        field.setBackground(FIELD_BG);
        field.setForeground(TEXT_WHITE);
        field.setCaretColor(TEXT_WHITE);
        field.setFont(fontBody(15));
        field.setBorder(new RoundedLineBorder(FIELD_BORDER, 10, 12));
        field.setOpaque(true);
    }

    /** Rounded border with padding, painted flat (no default Swing bevel). */
    public static class RoundedLineBorder extends AbstractBorder {
        private final Color color;
        private final int radius;
        private final int pad;

        public RoundedLineBorder(Color color, int radius, int pad) {
            this.color = color;
            this.radius = radius;
            this.pad = pad;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(pad / 2, pad, pad / 2, pad);
        }
    }

    // ---------------- Panels ----------------

    /** A JPanel painted with a rounded, filled background (for cards / sections). */
    public static JPanel roundedPanel(Color bg, int radius) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        return panel;
    }

    // ---------------- Scrollbar ----------------

    /** Applies a thin, dark, Netflix-style scrollbar to a JScrollPane. */
    public static void styleScrollBar(JScrollPane sp) {
        sp.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = GRAY_BTN_HOVER;
                this.trackColor = BG_DARK;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return zeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return zeroButton();
            }

            private JButton zeroButton() {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(0, 0));
                return b;
            }
        });
        sp.getVerticalScrollBar().setBackground(BG_DARK);
        sp.setBorder(BorderFactory.createEmptyBorder());
    }

    // ---------------- Misc helpers ----------------

    public static JLabel heading(String text, int size) {
        JLabel l = new JLabel(text);
        l.setForeground(TEXT_WHITE);
        l.setFont(fontTitle(size));
        return l;
    }

    public static JLabel bodyLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(TEXT_GRAY);
        l.setFont(fontBody(14));
        return l;
    }
}
