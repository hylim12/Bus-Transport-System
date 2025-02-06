import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.*;
import java.sql.SQLException;

public class AdminPanel {

    private JFrame frame; // Main window frame
    private JTextField busIdfield, busNoField, bustypeField, totalSeatsfield, availableSeatsField, depCityField, arrCityField, depTimeField, arrTimeField, farefield, deletionIDfield;
    private JTable busTable, userTable, reservationTable; // Tables for displaying data

    AdminPanel() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel()); // Set modern UI Look
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("Admin Panel");
        frame.setSize(900, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10)); // Set layout with spacing

        // Header Panel with gradient background
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 102, 204), getWidth(), getHeight(), new Color(0, 153, 255));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 80));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel headerLabel = new JLabel("Admin Control Panel");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);

        ImageIcon adminIcon = new ImageIcon("icons/admin.png");
        Image scaledAdminIcon = adminIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        JLabel adminAvatar = new JLabel(new ImageIcon(scaledAdminIcon));
        headerPanel.add(adminAvatar);
        headerPanel.add(headerLabel);

        frame.add(headerPanel, BorderLayout.NORTH);

        // Panel for deleting entries
        JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        deletePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Delete Entry", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16)));

        JComboBox<String> tablesComboBox = new JComboBox<>(new String[]{"Bus", "User", "Reservation"}); // Dropdown for table selection
        deletionIDfield = new JTextField(10);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBackground(new Color(255, 99, 71)); // Red color
        deleteButton.setForeground(Color.WHITE);

        // Delete button action listener
        deleteButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this entry?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                try {
                    DatabaseOperations.delete((String) tablesComboBox.getSelectedItem(), Integer.parseInt(deletionIDfield.getText()));
                    JOptionPane.showMessageDialog(deleteButton, "Deleted Successfully");
                    refreshTables(); // Refresh the tables after deletion
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(deleteButton, "Invalid ID or error in deletion");
                }
            }
        });

        deletePanel.add(tablesComboBox);
        deletePanel.add(deletionIDfield);
        deletePanel.add(deleteButton);
        frame.add(deletePanel, BorderLayout.SOUTH);

        // Tabbed pane for displaying tables
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 18));

        // Create bus, user, and reservation tables
        busTable = createStyledTable(new String[]{"ID", "No", "Type", "Total Seats", "Available", "Departure", "Arrival", "Dep Time", "Arr Time", "Fare"});
        JScrollPane busScrollPane = new JScrollPane(busTable);
        tabbedPane.addTab("Buses", resizeIcon("icons/bus-icon.png"), busScrollPane);

        userTable = createStyledTable(new String[]{"ID", "Name", "E-Mail", "Password", "Phone No", "Address"});
        JScrollPane userScrollPane = new JScrollPane(userTable);
        tabbedPane.addTab("Users", resizeIcon("icons/user-icon.png"), userScrollPane);

        reservationTable = createStyledTable(new String[]{"ID", "User ID", "Bus ID", "Seats", "Total Fare", "Date"});
        JScrollPane reservationScrollPane = new JScrollPane(reservationTable);
        tabbedPane.addTab("Reservations", resizeIcon("icons/reservation-icon.png"), reservationScrollPane);

        frame.add(tabbedPane, BorderLayout.CENTER);

        // Panel for adding a bus
        JPanel busPanel = new JPanel(new GridBagLayout());
        busPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Add Bus", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        String[] labels = {"Bus ID:", "Bus No:", "Bus Type:", "Total Seats:", "Available Seats:", "Departure City:", "Arrival City:", "Departure Time:", "Arrival Time:", "Fare:"};
        JTextField[] fields = {busIdfield, busNoField, bustypeField, totalSeatsfield, availableSeatsField, depCityField, arrCityField, depTimeField, arrTimeField, farefield};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            busPanel.add(label, gbc);

            gbc.gridx = 1;
            fields[i] = new JTextField(12);
            fields[i].setFont(new Font("Arial", Font.PLAIN, 16));
            busPanel.add(fields[i], gbc);
        }

        JButton addBusButton = new JButton("Add Bus");
        addBusButton.setBackground(new Color(34, 139, 34)); // Green color
        addBusButton.setForeground(Color.WHITE);
        addBusButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Bus Added Successfully!"));

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        busPanel.add(addBusButton, gbc);

        tabbedPane.addTab("Add Bus", resizeIcon("icons/add-bus-icon.png"), busPanel);

        frame.setVisible(true);
    }

    private JTable createStyledTable(String[] columnNames) {
        JTable table = new JTable(new DefaultTableModel(new Object[][]{}, columnNames));
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        return table;
    }

    private void refreshTables() {
        try {
            DatabaseOperations.loadData((DefaultTableModel) busTable.getModel(), "buses");
            DatabaseOperations.loadData((DefaultTableModel) reservationTable.getModel(), "reservations");
            DatabaseOperations.loadData((DefaultTableModel) userTable.getModel(), "users");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private ImageIcon resizeIcon(String path) {
        ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
    
}
