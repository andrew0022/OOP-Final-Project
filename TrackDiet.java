import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class TrackDiet extends JFrame {
    private static ArrayList<DietInformation> SubmittedDietInfo = new ArrayList<>();

    public TrackDiet() {
        setTitle("Track Diet");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // crating a button panel to hose all the button I have 
        JPanel buttonsPanel = new JPanel();
        // places the panel at the top of the GUI page
        mainPanel.add(buttonsPanel, BorderLayout.NORTH);

        
        JButton addDietIntakeButton = new JButton("Add Diet Intake");
        buttonsPanel.add(addDietIntakeButton);

        
        JButton closeButton = new JButton("Close");
        buttonsPanel.add(closeButton);

     
        JButton saveButton = new JButton("Save to File");
        buttonsPanel.add(saveButton);

        // now creating the Pnel that will house and show the Diet Entries
        JPanel dietformsPanel = new JPanel();
        // ensures that the layout will be a box format and all arrangment on the y axis allowing each of the diet entries to be stacked on on top of one another 
        dietformsPanel.setLayout(new BoxLayout(dietformsPanel, BoxLayout.Y_AXIS));
        // make the dietFormPanle scorllabe ie allows the dynamic rendering of panels by ensruing if the total entries surpass the scrren size it will make the GUI scrollable allowing users to view all entries. 
        mainPanel.add(new JScrollPane(dietformsPanel), BorderLayout.CENTER);



        addDietIntakeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // this will open a New DietForm page for users to submit a dietintake form 
                DietForm dietForm = new DietForm();
                // callback method within the DietForm class 
                dietForm.onSubmit(DietInformation  -> {
                    // add the info entered into the DietForm into the ArrayList of DietInfo classes 
                    SubmittedDietInfo.add(DietInformation);

                    // cals the render Form method that is repsonible for updating the GUI to now show the added Diet Info 
                    renderForms(dietformsPanel);
                });
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the GUI
                dispose();
            }
        });
        // Writes out the current DietInfo entries to a text file
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    FileWriter w = new FileWriter("diet.txt", true);
                    
                    for (DietInformation  dietInfo : SubmittedDietInfo) {
                        w.write("Date: " + dietInfo.getDate() + "\n"
                        + "Calories: " + dietInfo.getCalories() + "\n"
                        + "Protein: " + dietInfo.getProtein() + "\n"
                        + "Carbs: " + dietInfo.getCarbs() + "\n"
                        + "Fats: " + dietInfo.getFats() + "\n"
                        + "\n");
                    }
                    w.close();
                    JOptionPane.showMessageDialog(dietformsPanel, "Diet info saved to file.", "Success", JOptionPane.INFORMATION_MESSAGE);


                } catch (IOException x){
                    JOptionPane.showMessageDialog(dietformsPanel, "Error trying to save diet info into text file", "Error", JOptionPane.ERROR_MESSAGE);

                }


            }
        });

       

        add(mainPanel);
        setVisible(true);
    }
    

    private void renderForms(JPanel dietformsPanel) {
        // basically we do this so that we can panel in the TrackDiet PAnel show all the diet intakes a user has entered
        dietformsPanel.removeAll();

        // We loop thorugh each DietInfo submitted from the SubmittedDietInfo
        for (DietInformation individualDietInfo : SubmittedDietInfo) {
            // we crate a instannce of the DietFormRenderer class and pass in the current DietInfo we are at which is a pararmeter needed for the DietFormRenderer class.
            DietFormRenderer formRenderer = new DietFormRenderer(individualDietInfo, () -> {
                // this line is connected to the fact that the DietFomrRendere creates a delete button and is charge of handeling that delete 
                SubmittedDietInfo.remove(individualDietInfo);
                renderForms(dietformsPanel);
            });
            // now adds the completed and created component to the dietfroms panel.
            dietformsPanel.add(formRenderer);
        }

        // reformat eveerythign to make sure it looks ok        
        dietformsPanel.revalidate();
        dietformsPanel.repaint();
    }
}


class DietForm extends JFrame {
    // basically this line is declaring a private field that is type consumer. Basically consumer takes in a argument but doesnt't return anything its responsible for handeling the submisison of the Form 
    // i used this source: https://stackoverflow.com/questions/29945627/java-8-lambda-void-argument
    private Consumer<DietInformation > onSubmit; 

    public DietForm() {
        setTitle("Add Diet Intake");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel createformPanel = new JPanel(null);
        // adding all teh fields a user can fill in and it's respective labels as well
        JLabel header = new JLabel("add info/intake amount ");
        header.setBounds(85, 0, 250, 30);

        JLabel dateLabel = new JLabel("Date:");
        JTextField dateField = new JTextField();
        dateLabel.setBounds(50, 30, 80, 25);
        dateField.setBounds(90, 30, 150, 25);

        JLabel caloriesLabel = new JLabel("Calories:");
        JTextField caloriesField = new JTextField();
        caloriesLabel.setBounds(30, 60, 80, 25);
        caloriesField.setBounds(90, 60, 150, 25);

        JLabel proteinLabel = new JLabel("Protein:");
        JTextField proteinField = new JTextField();
        proteinLabel.setBounds(40, 90, 80, 25);
        proteinField.setBounds(90, 90, 150, 25);

        JLabel carbLabel = new JLabel("Carbs:");
        JTextField carbsField = new JTextField();
        carbLabel.setBounds(50, 120, 80, 25);
        carbsField.setBounds(90, 120, 150, 25);

        JLabel fatsLabel = new JLabel("Fats:");
        JTextField fatsField = new JTextField();
        fatsLabel.setBounds(57, 150, 80, 25);
        fatsField.setBounds(90, 150, 150, 25);
            
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(100, 180, 100, 30);


        createformPanel.add(header);

        createformPanel.add(dateLabel);
        createformPanel.add(dateField);

        createformPanel.add(caloriesLabel);
        createformPanel.add(caloriesField);

        createformPanel.add(proteinLabel);
        createformPanel.add(proteinField);

        createformPanel.add(carbLabel);
        createformPanel.add(carbsField);

        createformPanel.add(fatsLabel);
        createformPanel.add(fatsField);

        createformPanel.add(submitButton);


        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // creates a DietfInfo Class instance with the info a user entered in 
               DietInformation dietInfo = new DietInformation(
                        dateField.getText(),
                        caloriesField.getText(),
                        proteinField.getText(),
                        carbsField.getText(),
                        fatsField.getText()
                );

                // here basically we call the accept method for the onSubmit which is part of consumer
                onSubmit.accept(dietInfo);
                dispose();
            }
        });

        add(createformPanel);
        setVisible(true);
    }

    public void onSubmit(Consumer<DietInformation > onSubmit) {
        this.onSubmit = onSubmit;
    }
}


class DietFormRenderer extends JPanel {
    public DietFormRenderer(DietInformation  dietInfo, Runnable onDelete) {
        setLayout(new GridLayout(0, 1)); 
        // creating a panel to display the Date info we do something similar for other fields.
        JPanel DatePanel = new JPanel(new BorderLayout());
        JLabel showDate = new JLabel("Date:");
        showDate .setHorizontalAlignment(SwingConstants.RIGHT);
        DatePanel.add(showDate , BorderLayout.WEST);
        JLabel dateVal = new JLabel(dietInfo.date);
        DatePanel.add(dateVal, BorderLayout.CENTER);
        add(DatePanel);

        JPanel caloriePanel = new JPanel(new BorderLayout());
        JLabel showCal = new JLabel("Calories:");
        showCal.setHorizontalAlignment(SwingConstants.RIGHT);
        caloriePanel.add(showCal , BorderLayout.WEST);
        JLabel calVal = new JLabel(dietInfo.calories);
        caloriePanel.add(calVal, BorderLayout.CENTER);
        add(caloriePanel);

        JPanel proteinPanel = new JPanel(new BorderLayout());
        JLabel showProtein = new JLabel("Protein:");
        showProtein.setHorizontalAlignment(SwingConstants.RIGHT);
        proteinPanel.add(showProtein , BorderLayout.WEST);
        JLabel proteinVal = new JLabel(dietInfo.protein);
        proteinPanel.add(proteinVal, BorderLayout.CENTER);
        add(proteinPanel);

        JPanel carbPanel = new JPanel(new BorderLayout());
        JLabel showCarb= new JLabel("Carbs:");
        showCarb.setHorizontalAlignment(SwingConstants.RIGHT);
        carbPanel.add(showCarb, BorderLayout.WEST);
        JLabel carbVal = new JLabel(dietInfo.carbs);
        carbPanel.add(carbVal, BorderLayout.CENTER);
        add(carbPanel);

        JPanel fatsPanel = new JPanel(new BorderLayout());
        JLabel showFats = new JLabel("Fats:");
        showFats.setHorizontalAlignment(SwingConstants.RIGHT);
        fatsPanel.add(showFats, BorderLayout.WEST);
        JLabel fatsVal = new JLabel(dietInfo.fats);
        fatsPanel.add(fatsVal, BorderLayout.CENTER);
        add(fatsPanel);


        // Add a delete button
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // when a user clicks delete we delete that entry. 
                onDelete.run();
            }
        });

        
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(deleteButton);
    }

}


class DietInformation {
    public String date;
    public String calories;
    public String protein;
    public String carbs;
    public String fats;

        
    public DietInformation(String date, String calories, String protein, String carbs, String fats) {
        this.date = date;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getCalories() {
        return calories;
    }
    public void setCalories(String calories) {
        this.calories = calories;
    }
    public String getProtein() {
        return protein;
    }
    public void setProtein(String protein) {
        this.protein = protein;
    }
    public String getCarbs() {
        return carbs;
    }
    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }
    public String getFats() {
        return fats;
    }
    public void setFats(String fats) {
        this.fats = fats;
    }

}