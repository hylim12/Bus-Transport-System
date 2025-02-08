import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserLogin extends Applet {
    private TextField idField;
    private TextField passwordField;
    private Label statusLabel;
    private Canvas userUnderline;   // Declare it here
    private Canvas passwordUnderline;  // Declare this too

    public void init() {
        setSize(400, 300);
        setBackground(new Color(230, 240, 250));

        // Initialize the panel
        Panel panel = new Panel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID Label
        Label lblId = new Label("ID:");
        lblId.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblId, gbc);

        // ID TextField
        idField = new TextField();
        idField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(idField, gbc);

        // Underline for ID Field
        userUnderline = new Canvas();  // Now it's recognized!
        userUnderline.setSize(150, 2);
        userUnderline.setBackground(new Color(230, 240, 250));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(userUnderline, gbc);

        // Password Label
        Label lblPassword = new Label("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(lblPassword, gbc);

        // Password TextField
        passwordField = new TextField();
        passwordField.setEchoChar('*');
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(passwordField, gbc);

        // Underline for Password Field
        passwordUnderline = new Canvas();  // Now it's recognized!
        passwordUnderline.setSize(150, 2);
        passwordUnderline.setBackground(new Color(230, 240, 250));
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(passwordUnderline, gbc);

        // Login Button
        Button btnLogin = new Button("Login");
        btnLogin.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(btnLogin, gbc);

        // Register Button
        Button btnRegister = new Button("Register");
        btnRegister.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(btnRegister, gbc);

        // Status Label
        statusLabel = new Label("");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setAlignment(Label.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(statusLabel, gbc);

        // Add panel to the Applet
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        // Event Listeners
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (DatabaseOperations.validatePassword(idField.getText(), passwordField.getText())) {
                        statusLabel.setText("Login Successful!");
                    } else {
                        statusLabel.setText("Invalid ID or Password!");
			// Change underlines to red for error indication
                	userUnderline.setBackground(Color.RED);
                	passwordUnderline.setBackground(Color.RED);


                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    statusLabel.setText("Database Error!");
                }

            }
        });

        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Registration page not available in Applet!");
            }
        });
    }
}
