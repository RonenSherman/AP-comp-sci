// Java program to demonstrate textfield and
// display typed text using KeyListener
import java.awt.*;
import java.awt.event.*;

public class GUI extends Frame implements KeyListener {

    private final TextField textField;
    private final Label displayLabel;

    // Constructor
    public GUI() {
        // Set frame properties
        setTitle("Typed Text Display");
        setSize(400, 200);
        setLayout(new FlowLayout());

        // Create and add a TextField for text input
        textField = new TextField(20);
        textField.addKeyListener(this);
        add(textField);

        // Create and add a Label to display typed text
        displayLabel = new Label("Typed Text: ");
        add(displayLabel);

        // Ensure the frame can receive key events
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        // Make the frame visible
        setVisible(true);
    }

    // Implement the keyPressed method
    @Override
    public void keyPressed(KeyEvent e) {
        // You can add custom logic here if needed
    }

    // Implement the keyReleased method
    @Override
    public void keyReleased(KeyEvent e) {
        // You can add custom logic here if needed
    }

    // Implement the keyTyped method
    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();
        displayLabel.setText("Typed Text: " + textField.getText() + keyChar);
    }

    public static void main(String[] args) {
        new GUI();
    }
}

