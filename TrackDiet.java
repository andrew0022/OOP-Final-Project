import javax.swing.*;
import java.awt.*;

public class TrackDiet extends JFrame {
    public TrackDiet() {
        setTitle("Track Diet");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Track Diet GUI components
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Track Diet:");
        // Add your components for tracking diet here

        panel.add(label);
        // Add your components to the panel

        add(panel);
        setVisible(true);
    }
}
