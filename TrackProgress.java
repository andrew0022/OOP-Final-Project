import javax.swing.*;
import java.awt.*;

public class TrackProgress extends JFrame {
    public TrackProgress() {
        setTitle("Track Progress");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Track Progress GUI components
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Track Progress:");
        // Add your components for tracking progress here

        panel.add(label);
        // Add your components to the panel

        add(panel);
        setVisible(true);
    }
}
