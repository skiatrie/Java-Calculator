import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    int boardWidth = 360;
    int boardHeight = 540;

    // Color values
    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color (255, 149, 0);

    // Iterate over array for each symbol
    String[] buttonValue = {
        "AC", "+/-", "%", "/",
        "7", "8", "9", "*",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "sqrt", "="
    };

    // Distinguish for color and operation type
    String[] rightSymbols = {"/", "*", "-", "+", "="}; // symbols on right
    String[] topSymbols = {"AC", "+/-", "%"}; // symbols on top

    JFrame frame  = new JFrame("Calculator"); // obj
    JLabel displayLabel = new JLabel(); // name of label
    JPanel displayPanel = new JPanel(); // name of panel
    JPanel buttonsPanel = new JPanel(); // put text in label, label inside panel, panel inside window

    // A+B, A-B, A*B, A/B are the two numbers working with, with operator in middle
    String a = "0";
    String operator = null;
    String b = null;

    void clearAll() {
        a = "0";
        operator = null;
        b = null;
    }

    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        } else {
            return Double.toString(numDisplay);
        }
    }

    // Constructor stages correct fields for Calculator object instantiation
    Calculator() { 
        frame.setVisible(true); // method to set frame visible
        frame.setSize(boardWidth, boardHeight); // method to set frame size
        frame.setLocationRelativeTo(null); // method to center the window
        frame.setResizable(false); // user cant drag to resize and change w/h
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // program terminates after x
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(customLightGray);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel); 
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        
        // Iterate button values to set attributes
        for (int i = 0; i < buttonValue.length; i++) {
            JButton button = new JButton();
            String buttonValues = buttonValue[i];

            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValues);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));

            // Set top symbol attributes
            if (Arrays.asList(topSymbols).contains(buttonValues)) {
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            }

            // Set right symbol attributes
            else if (Arrays.asList(rightSymbols).contains(buttonValues)) {
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            }

            // Set other field attributes
            else {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }

            buttonsPanel.add(button);

            // Action listener for each button
            button.addActionListener(e -> {
                JButton srcButton = (JButton) e.getSource();
                String clickedValue = srcButton.getText();

                if (Arrays.asList(rightSymbols).contains(clickedValue)) {
                    if (clickedValue.equals("=")) {
                        if(operator != null) {
                            b = displayLabel.getText();
                            double numA = Double.parseDouble(a);
                            double numB = Double.parseDouble(b);
                            double result = 0;

                            if (operator.equals ("+")) {
                                displayLabel.setText(removeZeroDecimal(numA+numB));
                            }
                            else if (operator.equals ("-")) {
                                displayLabel.setText(removeZeroDecimal(numA-numB));
                            }
                            if (operator.equals ("*")) {
                                displayLabel.setText(removeZeroDecimal(numA*numB));
                            }
                            if (operator.equals ("/")) {
                                displayLabel.setText(removeZeroDecimal(numA/numB));
                            }
                        }
                    }
                    else if ("+-*/".contains(clickedValue)) {
                        if (operator.equals (null)) {
                            a = displayLabel.getText();
                            b = "0";
                        }
                    operator = clickedValue;
                    }
                }
                else if (Arrays.asList(topSymbols).contains(clickedValue)) { 
                    if (clickedValue.equals ("AC")) { // Clears numbers
                        clearAll(); // clears numbers out of box
                        displayLabel.setText("0"); // sets value back to 0
                    }
                    else if (clickedValue.equals("+/-")) { // Changes sign of number
                        double numDisplay = Double.parseDouble(displayLabel.getText());
                        numDisplay *= -1; 
                        displayLabel.setText(removeZeroDecimal(numDisplay));
                    }
                    else if (clickedValue.equals ("%")) { // Takes percentage of number
                        double numDisplay = Double.parseDouble(displayLabel.getText());
                        numDisplay /= 100;
                        displayLabel.setText(removeZeroDecimal(numDisplay));
                    }
                }
                else {
                    if (clickedValue.equals(".")) {
                    }
                    else if ("0123456789".contains(clickedValue)) {
                        if (displayLabel.getText().equals("0")) {
                                displayLabel.setText(clickedValue);
                        }
                        else {
                            displayLabel.setText(displayLabel.getText() + clickedValue);
                        }
                    }
                }
            });
        }
    }
}