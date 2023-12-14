import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame {
    public MainGUI() {
        setTitle("Main Menu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton addExerciseButton = new JButton("Add Exercise");
        JButton trackProgressButton = new JButton("Track Progress");
        JButton trackDietButton = new JButton("Track Diet");

        addExerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ExerciseTrackerGUI();
            }
        });

        trackProgressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TrackProgress();
            }
        });

        trackDietButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TrackDiet();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(addExerciseButton);
        panel.add(trackProgressButton);
        panel.add(trackDietButton);

        add(panel);
    }
}
