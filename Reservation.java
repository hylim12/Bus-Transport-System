import java.awt.GridLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class Reservation {

    private JFrame frame;
    private JTextField dateField;
    private JComboBox uidcomboBox;
    private JComboBox bidcomboBox;
    private JSpinner seatSpinner;
    private JTextArea totalFareArea;

	// Constructor to initialize the reservation window
    public Reservation() {
        initialize();
    }

	// Initialize the frame and UI components
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 611, 386);
        frame.setTitle("Reservation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(0, 2, 5, 5));

		// Add User ID label and dropdown
        JLabel lblUserID = new JLabel("User ID:");
        frame.getContentPane().add(lblUserID);
        uidcomboBox = new JComboBox();
        frame.getContentPane().add(uidcomboBox);

		// Add Bus ID label and dropdown
        JLabel lblBusID = new JLabel("Bus ID/Route:");
        frame.getContentPane().add(lblBusID);
        bidcomboBox = new JComboBox();
        frame.getContentPane().add(bidcomboBox);

		// Add Seat Count label and spinner (0-5 seats can be selected)
        JLabel lblSeatCount = new JLabel("No. of Seats to Reserve:");
        frame.getContentPane().add(lblSeatCount);
        seatSpinner = new JSpinner();
        seatSpinner.setModel(new SpinnerNumberModel(0, 0, 5, 1));
        frame.getContentPane().add(seatSpinner);

		 // Add Total Fare label and text area (non-editable)
        JLabel lblTotalFare = new JLabel("Total Fare:");
        frame.getContentPane().add(lblTotalFare);
        totalFareArea = new JTextArea();
        totalFareArea.setText("Rs.0");
        totalFareArea.setEditable(false);
		
		// Event listener for seat selection change to calculate total fare dynamically
        seatSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                String id = (String) bidcomboBox.getSelectedItem();
                id = id.substring(0, id.indexOf("|"));
                try {
                    int totalFare = DatabaseOperations.getBusFare(Integer.valueOf(id)) * (Integer) seatSpinner.getValue();
                    totalFareArea.setText("Rs." + totalFare);
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        frame.getContentPane().add(totalFareArea);

		// Add Date label and input field
        JLabel lbldate = new JLabel("Date (dd/MM/yyyy):");
        frame.getContentPane().add(lbldate);
		
        dateField = new JTextField();
        dateField.setColumns(10);
        frame.getContentPane().add(dateField);

		// Button to check bus details
        JButton btnCheckBusDetails = new JButton("Check Bus Details");
        btnCheckBusDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bid = (String) bidcomboBox.getSelectedItem();
                bid = bid.substring(0, bid.indexOf("|"));
                try {
                    String[] busDetails = DatabaseOperations.getReservation(Integer.valueOf(bid));
                    if (busDetails != null) {
                        JOptionPane.showMessageDialog(frame, "Bus Number: " + busDetails[10] + "\n" +
                                "Bus Type: " + busDetails[11] + "\n" +
								"Total Seats: " + busDetails[12] + "\n" +
								"Available Seats: " + busDetails[13] + "\n" +
                                "Departure City: " + busDetails[14] + "\n" +
                                "Arrival City: " + busDetails[15] + "\n" +
                                "Departure Time: " + busDetails[16] + "\n" +
                                "Arrival Time: " + busDetails[17] + "\n" +
                                "Fare: " + busDetails[18], "Bus Details", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(frame, "Invalid Bus ID", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        frame.getContentPane().add(btnCheckBusDetails);

		// Button to book a reservation
        JButton btnBook = new JButton("Book Reservation");
        btnBook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String uid = (String) uidcomboBox.getSelectedItem();
                uid = uid.substring(0, uid.indexOf("|"));
                String bid = (String) bidcomboBox.getSelectedItem();
                bid = bid.substring(0, bid.indexOf("|"));
                String date = dateField.getText();

                if (!isValidDate(date)) {
                    JOptionPane.showMessageDialog(frame, "Invalid date format. Please use dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    if ((Integer) seatSpinner.getValue() > 0) {
                        int reservationId = DatabaseOperations.getNextReservationId();
                        DatabaseOperations.addReservation(reservationId,
                                Integer.valueOf(uid),
                                Integer.valueOf(bid),
                                (Integer) seatSpinner.getValue(),
                                Integer.valueOf(totalFareArea.getText().substring(3)),
                                date);
                        JOptionPane.showMessageDialog(frame, "Booked Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        new Ticket(DatabaseOperations.getReservation(reservationId));
                    } else {
                        JOptionPane.showMessageDialog(frame, "At least select one seat", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid ID", "Error", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        frame.getContentPane().add(btnBook);
        frame.setVisible(true);

		// Load user and bus data into combo boxes
        try {
            DatabaseOperations.updateCombox("users", uidcomboBox);
            DatabaseOperations.updateCombox("buses", bidcomboBox);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

	// Validate date format
    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
	
	public static void main(String[] args) {
        // Start the application
        new Reservation();
    }
}