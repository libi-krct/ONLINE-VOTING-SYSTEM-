import java.awt.*;
import java.awt.event.*;
import java.util.*;

class OnlineVotingSystemFrontend extends Frame implements ActionListener {

    // Declare components
    Label titleLabel, candidateLabel, resultsLabel;
    Button voteButton, cancelButton, registerButton, loginButton, viewResultsButton;
    CheckboxGroup candidateGroup;
    Checkbox candidate1, candidate2, candidate3;
    TextField usernameField;
    TextField passwordField;
    TextArea resultArea;

    static Map<String, String> userDatabase = new HashMap<>();  // Store username:hashedPassword pairs
    static String loggedInUser = null;

    // Track votes for each candidate
    static Map<String, Integer> voteCount = new HashMap<>();  // Store votes for each candidate

    // Constructor for the voting system frontend
    public OnlineVotingSystemFrontend() {
        setTitle("Online Voting System");
        setSize(500, 400); // Set the size of the window
        setLayout(new FlowLayout()); // Use FlowLayout for simple component arrangement

        // Initialize components
        titleLabel = new Label("Online Voting System");
        candidateLabel = new Label("Choose your candidate:");
        resultsLabel = new Label("Results will be displayed here.");

        candidateGroup = new CheckboxGroup();
        candidate1 = new Checkbox("Candidate 1", candidateGroup, false);
        candidate2 = new Checkbox("Candidate 2", candidateGroup, false);
        candidate3 = new Checkbox("Candidate 3", candidateGroup, false);

        usernameField = new TextField(20);
        passwordField = new TextField(20);
        passwordField.setEchoChar('*'); // Mask password input

        voteButton = new Button("Vote");
        cancelButton = new Button("Cancel");
        registerButton = new Button("Register");
        loginButton = new Button("Login");
        viewResultsButton = new Button("View Results");

        resultArea = new TextArea(5, 40); // Text area for displaying results
        resultArea.setEditable(false); // Make it non-editable

        // Add components to the frame
        add(titleLabel);
        add(new Label("Username:"));
        add(usernameField);
        add(new Label("Password:"));
        add(passwordField);
        add(loginButton);
        add(candidateLabel);
        add(candidate1);
        add(candidate2);
        add(candidate3);
        add(voteButton);
        add(cancelButton);
        add(registerButton);
        add(viewResultsButton);
        add(resultsLabel);
        add(resultArea);

        // Add action listeners for the buttons
        voteButton.addActionListener(this);
        cancelButton.addActionListener(this);
        registerButton.addActionListener(this);
        loginButton.addActionListener(this);
        viewResultsButton.addActionListener(this);

        setVisible(true); // Make the window visible

        // Pre-populate userDatabase with sample users (default users)
        userDatabase.put("john_doe", "securePass123");  // Username: john_doe, Password: securePass123
        userDatabase.put("jas", "123");  // Username: jas, Password: 123
    }

    // Handle button click events
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Vote")) {
            // Handle voting logic
            if (loggedInUser != null) {
                Checkbox selectedCandidate = candidateGroup.getSelectedCheckbox();
                if (selectedCandidate != null) {
                    String candidate = selectedCandidate.getLabel();
                    voteCount.put(candidate, voteCount.getOrDefault(candidate, 0) + 1);
                    resultArea.append("Vote casted for " + candidate + "\n");
                } else {
                    resultArea.append("Please select a candidate to vote for.\n");
                }
            } else {
                resultArea.append("You must log in to vote.\n");
            }

        } else if (command.equals("Cancel")) {
            // Clear the form or reset the interface
            usernameField.setText("");
            passwordField.setText("");
            candidateGroup.setSelectedCheckbox(null);
            resultArea.setText("");
            loggedInUser = null;
            resultArea.append("Form reset. You can log in again.\n");

        } else if (command.equals("Register")) {
            // Logic for user registration (for simplicity, we just store username/password)
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (!username.isEmpty() && !password.isEmpty()) {
                userDatabase.put(username, password);
                resultArea.append("User registered successfully!\n");
            } else {
                resultArea.append("Please provide both username and password.\n");
            }

        } else if (command.equals("Login")) {
            // Handle user login
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
                loggedInUser = username;
                resultArea.append("Logged in as " + username + "\n");
            } else {
                resultArea.append("Invalid credentials. Try again.\n");
            }

        } else if (command.equals("View Results")) {
            // Display vote results
            if (voteCount.isEmpty()) {
                resultArea.append("No votes casted yet.\n");
            } else {
                resultArea.append("Voting Results:\n");
                for (Map.Entry<String, Integer> entry : voteCount.entrySet()) {
                    resultArea.append(entry.getKey() + ": " + entry.getValue() + " votes\n");
                }
            }
        }
    }

    // Main method to run the program
    public static void main(String[] args) {
        new OnlineVotingSystemFrontend(); // Create and display the voting system frontend
    }
}



7