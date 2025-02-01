package loadpso;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login {
    private static final String USERNAME = "admin"; // Default username
    private static final String PASSWORD = "password123"; // Default password

    public boolean authenticate(String username, String password) {
        return USERNAME.equals(username) && PASSWORD.equals(password);
    }

    public void showLoginFrame() {
        JFrame frame = new JFrame("Login Form in Windows Form");
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); // Set layout to null for custom positioning
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setResizable(false);

        // Create components with the specified design
        JLabel l1 = new JLabel("LOGIN:");
        l1.setForeground(Color.decode("#4B0082"));
        l1.setFont(new Font("Serif", Font.BOLD, 20));

        JLabel l2 = new JLabel("Enter User name:");
        l2.setFont(new Font("Arial", Font.PLAIN, 16));
        l2.setForeground(Color.decode("#333333"));

        JLabel l3 = new JLabel("Enter Password:");
        l3.setFont(new Font("Arial", Font.PLAIN, 16));
        l3.setForeground(Color.decode("#333333"));

        JTextField tf1 = new JTextField();
        tf1.setFont(new Font("Arial", Font.PLAIN, 14));
        tf1.setBackground(Color.decode("#FAFAD2"));
        tf1.setBorder(BorderFactory.createLineBorder(Color.decode("#4B0082"), 1));

        JPasswordField p1 = new JPasswordField();
        p1.setFont(new Font("Arial", Font.PLAIN, 14));
        p1.setBackground(Color.decode("#FAFAD2"));
        p1.setBorder(BorderFactory.createLineBorder(Color.decode("#4B0082"), 1));

        JButton btn1 = new JButton("Login");
        btn1.setFont(new Font("Arial", Font.BOLD, 16));
        btn1.setForeground(Color.WHITE);
        btn1.setBackground(Color.decode("#4B0082"));
        btn1.setFocusPainted(false);
        btn1.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Position components on the frame
        l1.setBounds(80, 20, 400, 40);
        l2.setBounds(80, 70, 200, 30);
        l3.setBounds(80, 110, 200, 30);
        tf1.setBounds(300, 70, 200, 30);
        p1.setBounds(300, 110, 200, 30);
        btn1.setBounds(300, 160, 100, 30);

        // Add components to the frame
        frame.add(l1);
        frame.add(l2);
        frame.add(tf1);
        frame.add(l3);
        frame.add(p1);
        frame.add(btn1);

        // Add action listener for login button
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tf1.getText();
                String password = new String(p1.getPassword());
                if (authenticate(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose(); // Close login frame
                    Main.launchMainFrame(); // Launch the main application
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}
