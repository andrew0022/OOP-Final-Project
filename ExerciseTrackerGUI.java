import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class ExerciseSet {
    private int setNumber;
    private double weight;
    private int reps;

    public ExerciseSet(int setNumber) {
        this.setNumber = setNumber;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(int setNumber) {
        this.setNumber = setNumber;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}

class Exercise {
    private String exerciseName;
    private ArrayList<ExerciseSet> exerciseSets;
    private int nextSetNumber;

    public Exercise(String exerciseName) {
        this.exerciseName = exerciseName;
        this.exerciseSets = new ArrayList<>();
        this.nextSetNumber = 1;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public ArrayList<ExerciseSet> getExerciseSets() {
        return exerciseSets;
    }

    public void addExerciseSet(ExerciseSet exerciseSet) {
        exerciseSets.add(exerciseSet);
        nextSetNumber++;
    }

    public int getNextSetNumber() {
        return nextSetNumber;
    }

    public void displayExerciseSets() {
        for (ExerciseSet exerciseSet : exerciseSets) {
            System.out.println(exerciseName + " - Set " + exerciseSet.getSetNumber() +
                    " - Weight: " + exerciseSet.getWeight() + " - Reps: " + exerciseSet.getReps());
        }
    }
}

public class ExerciseTrackerGUI {
    private JFrame frame;
    private ArrayList<Exercise> exercises;
    private JPanel exercisesPanel;

    public ExerciseTrackerGUI() {
        frame = new JFrame("Exercise Tracker");
        exercises = new ArrayList<>();
        exercisesPanel = new JPanel();
        exercisesPanel.setLayout(new BoxLayout(exercisesPanel, BoxLayout.Y_AXIS));

        String exerciseName = JOptionPane.showInputDialog(frame, "Enter the exercise name:");

        if (exerciseName == null) {
            frame.dispose();
            return;
        }

        if (exerciseName != null && !exerciseName.isEmpty()) {
            Exercise initialExercise = new Exercise(exerciseName);
            exercises.add(initialExercise);
            createExerciseComponents(initialExercise, exercisesPanel);
        }

        JButton addExerciseButton = new JButton("Add New Exercise");
        addExerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newExerciseName = JOptionPane.showInputDialog(frame, "Enter the exercise name:");
                if (newExerciseName != null && !newExerciseName.isEmpty()) {
                    Exercise newExercise = new Exercise(newExerciseName);
                    exercises.add(newExercise);
                    createExerciseComponents(newExercise, exercisesPanel);
                    frame.pack();
                }
            }
        });

        JButton saveButton = new JButton("Save Exercises");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveExercises();
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(addExerciseButton, BorderLayout.NORTH);
        frame.add(new JScrollPane(exercisesPanel), BorderLayout.CENTER);
        frame.add(saveButton, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createExerciseComponents(Exercise exercise, JPanel exercisesPanel) {
        JPanel exercisePanel = new JPanel();
        exercisePanel.setLayout(new BoxLayout(exercisePanel, BoxLayout.Y_AXIS));
        exercisePanel.setBorder(BorderFactory.createTitledBorder(exercise.getExerciseName()));

        JButton addButton = new JButton("Add Set");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExerciseSet exerciseSet = new ExerciseSet(exercise.getNextSetNumber());
                exercise.addExerciseSet(exerciseSet);
                addSetComponents(exercisePanel, exerciseSet);
                frame.pack();
            }
        });

        JButton displayButton = new JButton("Display Exercise Sets");
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exercise.displayExerciseSets();
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(addButton);
        controlPanel.add(displayButton);

        exercisePanel.add(controlPanel);
        exercisesPanel.add(exercisePanel);
    }

    private void addSetComponents(JPanel exercisePanel, ExerciseSet exerciseSet) {
        JPanel setPanel = new JPanel();
        setPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel setLabel = new JLabel("Set " + exerciseSet.getSetNumber());
        JTextField weightField = new JTextField(5);
        JTextField repsField = new JTextField(5);

        // make sure values typed in are auto saved
        weightField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                try {
                    double weight = Double.parseDouble(weightField.getText());
                    exerciseSet.setWeight(weight);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid weight input", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        repsField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                try {
                    int reps = Integer.parseInt(repsField.getText());
                    exerciseSet.setReps(reps);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid reps input", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setPanel.add(setLabel);
        setPanel.add(new JLabel("Weight:"));
        setPanel.add(weightField);
        setPanel.add(new JLabel("Reps:"));
        setPanel.add(repsField);
        exercisePanel.add(setPanel);

    }

    private void saveExercises() {
        try {
            FileWriter writer = new FileWriter("exercises.txt", true);
            writer.write("=== New Workout Session ===\n");
            for (Exercise exercise : exercises) {
                writer.write(exercise.getExerciseName() + "\n");
                for (ExerciseSet set : exercise.getExerciseSets()) {
                    writer.write("\tSet " + set.getSetNumber() +
                            " - Weight: " + set.getWeight() +
                            " - Reps: " + set.getReps() + "\n");
                }
                writer.write("\n");
            }
            writer.close();
            JOptionPane.showMessageDialog(frame, "Exercises saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving exercises", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
