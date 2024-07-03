import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class RegistrationManager extends Frame implements ActionListener {
    // Components for user input
    TextField nameField, emailField, phoneField;
    CheckboxGroup genderGroup;
    Checkbox male, female, other;
    Choice courseChoice;
    TextArea displayArea;
    ArrayList<User> users;
    Button submitButton, displayButton, exportButton;

    // Constructor to set up the GUI
    public RegistrationManager() {
        // Initialize user list
        users = new ArrayList<>();

        // Set up the frame
        setTitle("Registration Manager");
        setSize(500, 600);
        setLayout(new FlowLayout());

        // Name input
        add(new Label("Name:"));
        nameField = new TextField(30);
        add(nameField);

        // Email input
        add(new Label("Email:"));
        emailField = new TextField(30);
        add(emailField);

        // Phone input
        add(new Label("Phone:"));
        phoneField = new TextField(30);
        add(phoneField);

        // Gender selection
        add(new Label("Gender:"));
        genderGroup = new CheckboxGroup();
        male = new Checkbox("Male", genderGroup, false);
        female = new Checkbox("Female", genderGroup, false);
        other = new Checkbox("Other", genderGroup, false);
        add(male);
        add(female);
        add(other);

        // Course selection
        add(new Label("Course:"));
        courseChoice = new Choice();
        courseChoice.add("Computer Science");
        courseChoice.add("Information Technology");
        courseChoice.add("Electronics");
        courseChoice.add("Mechanical");
        add(courseChoice);

        // Submit button
        submitButton = new Button("Submit");
        submitButton.addActionListener(this);
        add(submitButton);

        // Display button
        displayButton = new Button("Display");
        displayButton.addActionListener(this);
        add(displayButton);

        // Export button
        exportButton = new Button("Export to File");
        exportButton.addActionListener(this);
        add(exportButton);

        // Display area for showing user details
        displayArea = new TextArea(10, 40);
        displayArea.setEditable(false);
        add(displayArea);

        // Add window listener to handle window close
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    // Handle button click events
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            submitForm();
        } else if (e.getSource() == displayButton) {
            displayUsers();
        } else if (e.getSource() == exportButton) {
            exportToFile();
        }
    }

    // Submit form and save user data
    private void submitForm() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String gender = genderGroup.getSelectedCheckbox().getLabel();
        String course = courseChoice.getSelectedItem();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || gender.isEmpty() || course.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = new User(name, email, phone, gender, course);
        users.add(user);
        JOptionPane.showMessageDialog(this, "User registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearForm();
    }

    // Display all users in the text area
    private void displayUsers() {
        StringBuilder displayText = new StringBuilder();
        for (User user : users) {
            displayText.append(user).append("\n");
        }
        displayArea.setText(displayText.toString());
    }

    // Export user data to a file
    private void exportToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("users.txt"))) {
            for (User user : users) {
                writer.println(user);
            }
            JOptionPane.showMessageDialog(this, "User data exported to users.txt", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error exporting data to file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Clear form fields
    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        genderGroup.setSelectedCheckbox(null);
        courseChoice.select(0);
    }

    // Main method to run the application
    public static void main(String[] args) {
        RegistrationManager manager = new RegistrationManager();
        manager.setVisible(true);
    }
}

// User class to hold user data
class User implements Serializable {
    private String name;
    private String email;
    private String phone;
    private String gender;
    private String course;

    public User(String name, String email, String phone, String gender, String course) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.course = course;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Email: " + email + ", Phone: " + phone + ", Gender: " + gender + ", Course: " + course;
    }
}
