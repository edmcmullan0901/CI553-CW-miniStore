package clients;

import javax.swing.*;
import java.awt.*;



public class UIStyler {

    // Apply global Look and Feel and UI configurations
    public static void applyStyling() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("Failed to set Look and Feel: " + e.getMessage());
        }

        // Customize UI properties for all components
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));
        UIManager.put("Button.background", Color.WHITE);
        UIManager.put("Button.foreground", Color.BLACK);

        UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("Label.foreground", Color.BLACK);

        UIManager.put("Panel.background", Color.LIGHT_GRAY);

        UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("TextField.border", BorderFactory.createLineBorder(Color.GRAY, 2));

        UIManager.put("PasswordField.font", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("PasswordField.border", BorderFactory.createLineBorder(Color.GRAY, 2));
    }

    // Create styled panels for consistency
    public static JPanel createStyledPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panel;
    }

    // Create styled buttons for consistency
    public static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);
        return button;
    }

    // Create styled labels for consistency
    public static JLabel createStyledLabel(String text, int alignment) {
        JLabel label = new JLabel(text, alignment);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.DARK_GRAY);
        return label;
    }
}
