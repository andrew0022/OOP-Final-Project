import javax.swing.*;
import java.awt.*;

public class AddExercise extends JFrame {
    public AddExercise() {
        setTitle("Add Exercise");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add Exercise GUI components
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Add Exercise:");
        JTextField exerciseField = new JTextField(20);
        JButton addButton = new JButton("Add");

        addButton.addActionListener(e -> {
            String exercise = exerciseField.getText();
            // Perform logic to add exercise here
            // For example, you can display a message or update data
            JOptionPane.showMessageDialog(null, "Exercise added: " + exercise);
            exerciseField.setText("");
        });

        panel.add(label);
        panel.add(exerciseField);
        panel.add(addButton);

        add(panel);
        setVisible(true);
    }
}
