import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class TrackProgress {

    private double previousMaxBench = 0;
    private double previousMaxSquat = 0;
    private double previousMaxDeadlift = 0;
    private boolean benchDone;
    private boolean squatDone;
    private boolean deadliftDone;

    public TrackProgress() {
        String inputFilePath = "exercises.txt";
        String outputFilePath = "trackedworkouts.txt";
        readFile(inputFilePath, outputFilePath);
        showPopup();
    }

    private void readFile(String inputFilePath, String outputFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
                BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            int workoutNumber = 0;
            double maxBench = 0;
            double maxSquat = 0;
            double maxDeadlift = 0;
            benchDone = false;
            squatDone = false;
            deadliftDone = false;
            String currentExercise = "";

            while ((line = br.readLine()) != null) {
                String lowerCaseLine = line.toLowerCase();
                if (lowerCaseLine.contains("=== new workout session ===")) {
                    if (workoutNumber > 0) {
                        printMaxWeights(workoutNumber, maxBench, maxSquat, maxDeadlift, bw);
                        printAnalysis(maxBench, maxSquat, maxDeadlift, bw);
                        updatePreviousMaxWeights(maxBench, maxSquat, maxDeadlift);
                    }
                    workoutNumber++;
                    maxBench = 0;
                    maxSquat = 0;
                    maxDeadlift = 0;
                    benchDone = squatDone = deadliftDone = false;
                } else if (lowerCaseLine.contains("bench press")) {
                    currentExercise = "Bench Press";
                    benchDone = true;
                } else if (lowerCaseLine.contains("squat")) {
                    currentExercise = "Squat";
                    squatDone = true;
                } else if (lowerCaseLine.contains("deadlift")) {
                    currentExercise = "Deadlift";
                    deadliftDone = true;
                } else if (line.startsWith("\tSet")) {
                    double weight = extractWeight(line);
                    if (currentExercise.equals("Bench Press")) {
                        maxBench = Math.max(maxBench, weight);
                    } else if (currentExercise.equals("Squat")) {
                        maxSquat = Math.max(maxSquat, weight);
                    } else if (currentExercise.equals("Deadlift")) {
                        maxDeadlift = Math.max(maxDeadlift, weight);
                    }
                }
            }
            printMaxWeights(workoutNumber, maxBench, maxSquat, maxDeadlift, bw);
            printAnalysis(maxBench, maxSquat, maxDeadlift, bw);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double extractWeight(String line) {
        String[] parts = line.split(" - ");
        return Double.parseDouble(parts[1].split(": ")[1].trim());
    }

    private void printMaxWeights(int workoutNumber, double maxBench, double maxSquat, double maxDeadlift,
            BufferedWriter bw) throws IOException {
        bw.write("--------------------------------------------------------------------------\n");
        bw.write("Workout " + workoutNumber + " Max Lifts" + ": Bench Press: " + maxBench
                + ", Squat: " + maxSquat + ", Deadlift: " + maxDeadlift + "\n");
    }

    private void printAnalysis(double maxBench, double maxSquat, double maxDeadlift, BufferedWriter bw)
            throws IOException {
        if (previousMaxBench > 0) {
            bw.write("\n");
            bw.write("Analysis against previous workout:\n");
            bw.write("Bench Press: " + (benchDone ? compareLifts(previousMaxBench, maxBench) : "Unchanged") + "\n");
            bw.write("Squat: " + (squatDone ? compareLifts(previousMaxSquat, maxSquat) : "Unchanged") + "\n");
            bw.write("Deadlift: " + (deadliftDone ? compareLifts(previousMaxDeadlift, maxDeadlift) : "Unchanged")
                    + "\n");
        }
    }

    private String compareLifts(double previousMax, double currentMax) {
        if (currentMax > previousMax) {
            return "Increased";
        } else if (currentMax < previousMax) {
            return "Decreased";
        } else {
            return "Unchanged";
        }
    }

    private void updatePreviousMaxWeights(double maxBench, double maxSquat, double maxDeadlift) {
        previousMaxBench = maxBench;
        previousMaxSquat = maxSquat;
        previousMaxDeadlift = maxDeadlift;
    }

    private static void showPopup() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                JFrame frame = new JFrame("Progress Report");
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.setSize(300, 100);

                JLabel label = new JLabel("View progress report in trackedworkouts.txt", JLabel.CENTER);
                frame.getContentPane().add(label);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
