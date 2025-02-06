import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Register {
    private JFrame frame;
    private JTextField userIdField, nameField, emailField, phoneNumField, addressField;
    private JPasswordField passwordField;
    private JButton btnBack, btnRegister;
    
    public Register() {
        initialize();
    }
    
    private void initialize() {
        // Setting up the main frame
        frame = new JFrame("User Registration");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        // Creating the main panel with a light background
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 250, 255));
        
        // Creating a card-style panel to contain the form elements
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(500, 600));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 230), 2),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        // Adding header label
        JLabel header = new JLabel("User Registration", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 26));
        header.setForeground(new Color(50, 70, 150));
        gbc.gridwidth = 2;
        card.add(header, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;
        
        // Adding input fields to the form
        userIdField = addLabelAndField("User ID:", card, gbc);
        nameField = addLabelAndField("Name:", card, gbc);
        emailField = addLabelAndField("Email:", card, gbc);
        passwordField = new JPasswordField();
        formatField(passwordField);
        addLabel("Password:", card, gbc);
        gbc.gridx = 1;
        card.add(passwordField, gbc);
        gbc.gridy++;
        phoneNumField = addLabelAndField("Phone:", card, gbc);
        addressField = addLabelAndField("Address:", card, gbc);
        
        // Creating a panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        // Creating buttons
        btnBack = createButton("Back", new Color(200, 50, 50));
        btnRegister = createButton("Register", new Color(50, 150, 50));
        
        // Adding action listeners for buttons
        btnBack.addActionListener(e -> {
            frame.dispose();
            new UserLogin();
        });
        
        btnRegister.addActionListener(e -> {
            try {
                DatabaseOperations.addUser(
                        Integer.parseInt(userIdField.getText()),
                        nameField.getText(),
                        emailField.getText(),
                        phoneNumField.getText(),
                        new String(passwordField.getPassword()),
                        addressField.getText()
                );
                JOptionPane.showMessageDialog(frame, "User Registered Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Registration Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Adding buttons to panel and form
        buttonPanel.add(btnBack);
        buttonPanel.add(btnRegister);
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        card.add(buttonPanel, gbc);
        
        panel.add(card);
        frame.add(panel);
        frame.setVisible(true);
    }
    
    // Method to create labeled input fields
    private JTextField addLabelAndField(String labelText, JPanel panel, GridBagConstraints gbc) {
        addLabel(labelText, panel, gbc);
        gbc.gridx = 1;
        JTextField textField = new JTextField();
        formatField(textField);
        panel.add(textField, gbc);
        gbc.gridy++;
        return textField;
    }
    
    // Method to create labels
    private void addLabel(String text, JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(label, gbc);
    }
    
    // Method to style input fields
    private void formatField(JTextField field) {
        field.setPreferredSize(new Dimension(250, 35));
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 180, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }
    
    // Method to create styled buttons with hover effect
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(160, 50));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        return button;
    }
}