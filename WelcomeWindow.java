import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class WelcomeWindow extends Applet {

    private Panel panel;

    public void init() {
        initialize();
    }

    private void initialize() {
        setSize(500, 250);
        panel = new Panel();
        panel.setBackground(new Color(173, 216, 230)); // Light blue background
        panel.setLayout(new GridBagLayout());
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Label lblWelcome = new Label("Welcome to Bus Reservation System", Label.CENTER);
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setFont(new Font("Serif", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblWelcome, gbc);

        Label lblInstruction = new Label("Please Select Login Profile:", Label.CENTER);
        lblInstruction.setForeground(Color.DARK_GRAY);
        lblInstruction.setFont(new Font("Serif", Font.PLAIN, 16));
        gbc.gridy = 1;
        panel.add(lblInstruction, gbc);

        Button btnAdmin = new Button("Admin");
        styleButton(btnAdmin);
        btnAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AdminLogin();
            }
        });
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        panel.add(btnAdmin, gbc);

        Button btnUser = new Button("User");
        styleButton(btnUser);
        btnUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UserLogin();
            }
        });
        gbc.gridx = 1;
        panel.add(btnUser, gbc);
    }

    private void styleButton(Button button) {
        button.setFont(new Font("Serif", Font.BOLD, 16)); // Adjusted font size
        button.setBackground(new Color(30, 144, 255)); // Deep sky blue
        button.setForeground(Color.WHITE);
    }
}
