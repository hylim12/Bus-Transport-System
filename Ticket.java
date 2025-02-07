import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Font;

public class Ticket {

    private JFrame frame;

    public Ticket(String[] data) {
        frame = new JFrame();
        frame.setBounds(100, 100, 611, 386);
        frame.setTitle("Ticket Details");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // Main panel to hold all sections
        JPanel mainPanel = new JPanel(new GridLayout(0, 1, 5, 5));
		// Adds padding around the edges of the panel.
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Section 1: Reservation Details
        JPanel reservationPanel = createSectionPanel("Reservation Details", new String[]{
            "Reservation ID: " + data[0],
            "User ID: " + data[1],
            "Bus ID: " + data[2]
        });
        mainPanel.add(reservationPanel);

        // Section 2: User Details
        JPanel userPanel = createSectionPanel("User Details", new String[]{
            "Name: " + data[6],
            "Email: " + data[7],
            "Phone No.: " + data[8],
            "Address: " + data[9]
        });
        mainPanel.add(userPanel);

        // Section 3: Reservation Info
        JPanel reservationInfoPanel = createSectionPanel("Reservation Info", new String[]{
            "Reservation Date: " + data[5],
            "Reserved Seats: " + data[3],
            "Total Fare: " + data[4]
        });
        mainPanel.add(reservationInfoPanel);

        // Section 4: Bus Details
        JPanel busPanel = createSectionPanel("Bus Details", new String[]{
            "Bus No.: " + data[10],
            "Bus Type: " + data[11],
            "Total Seats: " + data[12],
            "Available Seats: " + data[13]
        });
        mainPanel.add(busPanel);

        // Section 5: Trip Details
        JPanel tripPanel = createSectionPanel("Trip Details", new String[]{
            "Departure City: " + data[14],
            "Arrival City: " + data[15],
            "Departure Time: " + data[16],
            "Arrival Time: " + data[17],
            "Fare: " + data[18]
        });
        mainPanel.add(tripPanel);

        // Add main panel to the frame
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

	// Method used to dynamically generate a section of the user interface 
    private JPanel createSectionPanel(String title, String[] details) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(title));
		
		// Iterate through the details array, which contain the data in each section
        for (String detail : details) {
            JLabel label = new JLabel(detail);
            label.setFont(new Font("Arial", Font.PLAIN, 12));
            panel.add(label);
        }

        return panel;
    }
	
	// Main method to demonstrate the UI of Ticket Details 
    public static void main(String[] args) {
        // Sample data for testing
        String[] data = {
            "12345", // Reservation ID
            "67890", // User ID
            "54321", // Bus ID
            "2",     // Reserved Seats
            "Rs.500", // Total Fare
            "2023-10-15", // Reservation Date
            "John Doe", // Name
            "john.doe@example.com", // Email
            "123-456-7890", // Phone No.
            "123 Main St, City", // Address
            "Bus-101", // Bus No.
            "AC Sleeper", // Bus Type
            "50", // Total Seats
            "48", // Available Seats
            "New York", // Departure City
            "Los Angeles", // Arrival City
            "10:00 AM", // Departure Time
            "10:00 PM", // Arrival Time
            "Rs.250" // Fare
        };

        // Create and display the ticket
        new Ticket(data);
    }
}

