import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLogin extends Applet {
    private TextField adminNameField;
    private TextField passwordField;
    private Label lblAdmin;
    private Label lblPassword;
    private Canvas adminUnderline;
    private Canvas passwordUnderline;
    
    private final String adminName = "Admin";
    private final String password = "1234";
    
    public void init() {
        setLayout(null);
        setSize(400, 300);
        setBackground(new Color(230, 240, 250));

        // Labels
        lblAdmin = new Label("Admin Name:");
        lblAdmin.setBounds(50, 50, 100, 30);
        add(lblAdmin);

        adminNameField = new TextField();
        adminNameField.setBounds(170, 50, 150, 30);
        add(adminNameField);

        // Underline for Admin Name
        adminUnderline = new Canvas();
        adminUnderline.setBounds(170, 80, 150, 2);
        adminUnderline.setBackground(Color.BLACK);
        add(adminUnderline);

        lblPassword = new Label("Password:");
        lblPassword.setBounds(50, 100, 100, 30);
        add(lblPassword);

        passwordField = new TextField();
        passwordField.setEchoChar('*');
        passwordField.setBounds(170, 100, 150, 30);
        add(passwordField);

        // Underline for Password
        passwordUnderline = new Canvas();
        passwordUnderline.setBounds(170, 130, 150, 2);
        passwordUnderline.setBackground(Color.BLACK);
        add(passwordUnderline);

        // Login Button
        Button btnLogin = new Button("Login");
        btnLogin.setBounds(140, 160, 100, 30);
        add(btnLogin);

        // Button Action
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (adminNameField.getText().equals(adminName) && password.equals(passwordField.getText())) {
                    showStatus("Login Successful!");
                    
                    // Reset underlines to default (black)
                    adminUnderline.setBackground(Color.BLACK);
                    passwordUnderline.setBackground(Color.BLACK);
                } else {
                    showStatus("Incorrect Username or Password!");
                    
                    // Change underlines to red for error indication
                    adminUnderline.setBackground(Color.RED);
                    passwordUnderline.setBackground(Color.RED);
                }
            }
        });
    }
}
