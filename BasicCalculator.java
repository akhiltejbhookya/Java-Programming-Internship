import java.awt.*;
import java.awt.event.*;

public class BasicCalculator extends Frame implements ActionListener {
    // Declare the components
    TextField display;
    Panel panel;
    String operator;
    double num1, num2, result;

    // Constructor to set up the GUI
    public BasicCalculator() {
        // Set up the frame
        setTitle("Basic Calculator");
        setSize(300, 400);
        setLayout(new BorderLayout());

        // Set up the display
        display = new TextField();
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        // Set up the panel for buttons
        panel = new Panel();
        panel.setLayout(new GridLayout(4, 4));

        // Add buttons to the panel
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String button : buttons) {
            Button btn = new Button(button);
            btn.addActionListener(this);
            panel.add(btn);
        }

        add(panel, BorderLayout.CENTER);

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
        String command = e.getActionCommand();

        if (command.charAt(0) >= '0' && command.charAt(0) <= '9') {
            // If a number is pressed
            display.setText(display.getText() + command);
        } else if (command.equals("C")) {
            // Clear the display
            display.setText("");
        } else if (command.equals("=")) {
            // Perform the calculation
            try {
                num2 = Double.parseDouble(display.getText());
                switch (operator) {
                    case "+":
                        result = num1 + num2;
                        break;
                    case "-":
                        result = num1 - num2;
                        break;
                    case "*":
                        result = num1 * num2;
                        break;
                    case "/":
                        if (num2 != 0) {
                            result = num1 / num2;
                        } else {
                            display.setText("Error: Division by zero");
                            return;
                        }
                        break;
                }
                display.setText(String.valueOf(result));
            } catch (NumberFormatException ex) {
                display.setText("Error: Invalid input");
            }
        } else {
            // If an operator is pressed
            try {
                num1 = Double.parseDouble(display.getText());
                operator = command;
                display.setText("");
            } catch (NumberFormatException ex) {
                display.setText("Error: Invalid input");
            }
        }
    }

    // Main method to run the calculator
    public static void main(String[] args) {
        BasicCalculator calculator = new BasicCalculator();
        calculator.setVisible(true);
    }
}
