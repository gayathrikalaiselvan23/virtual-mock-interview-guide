import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Helper class for Question structure
class Question {
    String questionText;
    Map<String, String> options; // Key: option letter (A, B, C, D), Value: text
    String correctAnswerKey;

    public Question(String text, Map<String, String> options, String correctKey) {
        this.questionText = text;
        this.options = options;
        this.correctAnswerKey = correctKey;
    }
}

public class VirtualMockInterviewGuide extends JFrame implements ActionListener {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    // UI Components for different panels
    private JComboBox<String> domainComboBox;
    private JComboBox<String> difficultyComboBox;

    private JTextArea questionArea;
    private JPanel optionsPanel; 
    private ButtonGroup optionsGroup;
    private JButton submitAnswerButton;

    // Data Store and State Management
    private Map<String, List<Question>> questionBank;
    private List<Question> currentQuestions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private Map<Question, String> candidateAnswers;

    public VirtualMockInterviewGuide() {
        setTitle("Virtual Mock Interview Guide");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        initializeQuestionBank();
        candidateAnswers = new HashMap<>();

        // Add all panels
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createSelectionPanel(), "Selection");
        mainPanel.add(createInterviewPanel(), "Interview");
        mainPanel.add(createReportPanel(), "Report");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
        setVisible(true);
    }

    // --- Data Initialization (Question Bank Module) ---
    private void initializeQuestionBank() {
        questionBank = new HashMap<>();
        
        // --- JAVA EASY (5 Qs) ---
        List<Question> javaEasy = new ArrayList<>();
        javaEasy.add(new Question("Q1: What is a Class in Java?", Map.of("A", "A template for creating objects.", "B", "A primitive data type.", "C", "A method container.", "D", "An interface implementation."), "A"));
        javaEasy.add(new Question("Q2: Which concept refers to Encapsulation?", Map.of("A", "Hiding implementation details.", "B", "Wrapping data and code into a single unit.", "C", "Acquiring properties from another class.", "D", "Defining multiple methods with the same name."), "B"));
        javaEasy.add(new Question("Q3: Which keyword prevents method overriding?", Map.of("A", "static", "B", "final", "C", "abstract", "D", "void"), "B"));
        javaEasy.add(new Question("Q4: Which package contains the Scanner class?", Map.of("A", "java.lang", "B", "java.io", "C", "java.util", "D", "java.awt"), "C"));
        javaEasy.add(new Question("Q5: What is the size of 'int' data type in Java?", Map.of("A", "8 bits", "B", "16 bits", "C", "32 bits", "D", "64 bits"), "C"));
        questionBank.put("Java/Easy", javaEasy);

        // --- JAVA MEDIUM (5 Qs) ---
        List<Question> javaMedium = new ArrayList<>();
        javaMedium.add(new Question("Q1: Which is NOT a benefit of Java's garbage collector?", Map.of("A", "Memory leakage prevention.", "B", "Manual memory deallocation.", "C", "Improved security.", "D", "Efficient memory management."), "B"));
        javaMedium.add(new Question("Q2: What is the difference between '==' and '.equals()'?", Map.of("A", "Both compare object content.", "B", "'==' compares references, '.equals()' compares content.", "C", "They are synonyms.", "D", "'.equals()' compares references, '==' compares content."), "B"));
        javaMedium.add(new Question("Q3: Which access modifier is the most restrictive?", Map.of("A", "public", "B", "protected", "C", "default (no modifier)", "D", "private"), "D"));
        javaMedium.add(new Question("Q4: The term for providing runtime polymorphism in Java is:", Map.of("A", "Method Overloading", "B", "Method Hiding", "C", "Method Overriding", "D", "Method Signature"), "C"));
        javaMedium.add(new Question("Q5: What does the 'transient' keyword indicate?", Map.of("A", "The variable cannot be serialized.", "B", "The variable is volatile.", "C", "The variable is temporary.", "D", "The variable is constant."), "A"));
        questionBank.put("Java/Medium", javaMedium);
        
        // --- SQL EASY (5 Qs) ---
        List<Question> sqlEasy = new ArrayList<>();
        sqlEasy.add(new Question("Q1: Which command removes all rows from a table without logging?", Map.of("A", "DELETE", "B", "DROP", "C", "TRUNCATE", "D", "REMOVE"), "C"));
        sqlEasy.add(new Question("Q2: Which clause filters results after GROUP BY?", Map.of("A", "WHERE", "B", "HAVING", "C", "ORDER BY", "D", "FILTER"), "B"));
        sqlEasy.add(new Question("Q3: What is a primary key?", Map.of("A", "A non-unique identifier.", "B", "A unique identifier for a row.", "C", "A link to another table.", "D", "The first column in a table."), "B"));
        sqlEasy.add(new Question("Q4: Which JOIN returns matching rows from both tables?", Map.of("A", "LEFT JOIN", "B", "RIGHT JOIN", "C", "FULL JOIN", "D", "INNER JOIN"), "D"));
        sqlEasy.add(new Question("Q5: DDL stands for:", Map.of("A", "Data Definition Language", "B", "Data Deletion Logic", "C", "Database Debugging Link", "D", "Data Display Language"), "A"));
        questionBank.put("SQL/Easy", sqlEasy);

        // --- SQL MEDIUM (5 Qs) ---
        List<Question> sqlMedium = new ArrayList<>();
        sqlMedium.add(new Question("Q1: Which ACID property guarantees that a transaction is all-or-nothing?", Map.of("A", "Consistency", "B", "Isolation", "C", "Durability", "D", "Atomicity"), "D"));
        sqlMedium.add(new Question("Q2: What is normalization primarily designed to eliminate?", Map.of("A", "SQL injection.", "B", "Data redundancy and dependencies.", "C", "Query optimization issues.", "D", "Concurrency problems."), "B"));
        sqlMedium.add(new Question("Q3: Which function is used to calculate the variance of a set of values?", Map.of("A", "VAR", "B", "AVG", "C", "STDEV", "D", "SUM"), "A"));
        sqlMedium.add(new Question("Q4: What is a View in SQL?", Map.of("A", "A physical table on disk.", "B", "A stored procedure.", "C", "A virtual table based on the result-set of a SQL statement.", "D", "An index optimization."), "C"));
        sqlMedium.add(new Question("Q5: What is the purpose of a Composite Key?", Map.of("A", "To encrypt data.", "B", "To use two or more columns to uniquely identify a row.", "C", "To create a foreign key.", "D", "To define a clustered index."), "B"));
        questionBank.put("SQL/Medium", sqlMedium);

        // --- PYTHON EASY (5 Qs) ---
        List<Question> pythonEasy = new ArrayList<>();
        pythonEasy.add(new Question("Q1: Which of these is immutable in Python?", Map.of("A", "list", "B", "dictionary", "C", "tuple", "D", "set"), "C"));
        pythonEasy.add(new Question("Q2: Which symbol is used for single-line comments?", Map.of("A", "//", "B", "/* */", "C", "#", "D", "--"), "C"));
        pythonEasy.add(new Question("Q3: How do you check the type of a variable?", Map.of("A", "typeof()", "B", "type()", "C", "checktype()", "D", "gettype()"), "B"));
        pythonEasy.add(new Question("Q4: What is the output of '2' + '3'?", Map.of("A", "5", "B", "23", "C", "Error", "D", "2 3"), "B"));
        pythonEasy.add(new Question("Q5: The correct way to write a function is:", Map.of("A", "function my_func:", "B", "define my_func:", "C", "def my_func():", "D", "func my_func():"), "C"));
        questionBank.put("Python/Easy", pythonEasy);
        
        // --- PYTHON MEDIUM (5 Qs) ---
        List<Question> pythonMedium = new ArrayList<>();
        pythonMedium.add(new Question("Q1: Which method adds an element to a list?", Map.of("A", "append()", "B", "add()", "C", "insert_last()", "D", "push()"), "A"));
        pythonMedium.add(new Question("Q2: What is the purpose of the 'init' method in a class?", Map.of("A", "To destroy an object.", "B", "To define class methods.", "C", "To initialize object attributes.", "D", "To define static variables."), "C"));
        pythonMedium.add(new Question("Q3: Which module is Python's standard way to handle regular expressions?", Map.of("A", "regex", "B", "re", "C", "string", "D", "pattern"), "B"));
        pythonMedium.add(new Question("Q4: What data type is returned by the 'range()' function?", Map.of("A", "List", "B", "Tuple", "C", "Generator (Range object)", "D", "Set"), "C"));
        pythonMedium.add(new Question("Q5: The correct way to open a file for writing, creating it if necessary:", Map.of("A", "open('f', 'r')", "B", "open('f', 'a')", "C", "open('f', 'w')", "D", "open('f', 'x')"), "C"));
        questionBank.put("Python/Medium", pythonMedium);
        
        // --- C++ EASY (5 Qs) ---
        List<Question> cppEasy = new ArrayList<>();
        cppEasy.add(new Question("Q1: Which operator is used for dynamic memory allocation?", Map.of("A", "malloc", "B", "new", "C", "calloc", "D", "alloc"), "B"));
        cppEasy.add(new Question("Q2: What is the entry point function of a C++ program?", Map.of("A", "start()", "B", "begin()", "C", "main()", "D", "entry()"), "C"));
        cppEasy.add(new Question("Q3: Which header file is required for input/output operations?", Map.of("A", "stdlib.h", "B", "conio.h", "C", "iostream", "D", "math.h"), "C"));
        cppEasy.add(new Question("Q4: The feature allowing the same function name to be used for different purposes is:", Map.of("A", "Inheritance", "B", "Polymorphism", "C", "Abstraction", "D", "Encapsulation"), "B"));
        cppEasy.add(new Question("Q5: Pointers are variables that store:", Map.of("A", "Values", "B", "Addresses", "C", "Functions", "D", "Constants"), "B"));
        questionBank.put("C++/Easy", cppEasy);
        
        // --- C++ MEDIUM (5 Qs) ---
        List<Question> cppMedium = new ArrayList<>();
        cppMedium.add(new Question("Q1: Which keyword is used to access members of a base class from a derived class?", Map.of("A", "super", "B", "parent", "C", ":: (scope resolution operator)", "D", "this"), "C"));
        cppMedium.add(new Question("Q2: What is a Virtual Function primarily used for?", Map.of("A", "Function Overloading.", "B", "Achieving Runtime Polymorphism.", "C", "Preventing Inheritance.", "D", "Static Binding."), "B"));
        cppMedium.add(new Question("Q3: The default visibility mode for members in a C++ class is:", Map.of("A", "public", "B", "protected", "C", "private", "D", "default"), "C"));
        cppMedium.add(new Question("Q4: The correct syntax for passing an array to a function is:", Map.of("A", "func(arr[])", "B", "func(arr)", "C", "func(arr*)", "D", "func(arr[]) or func(arr*)"), "D"));
        cppMedium.add(new Question("Q5: Which loop executes the block of code at least once?", Map.of("A", "for", "B", "while", "C", "do-while", "D", "if-else"), "C"));
        questionBank.put("C++/Medium", cppMedium);

        // --- TESTING EASY (5 Qs) ---
        List<Question> testingEasy = new ArrayList<>();
        testingEasy.add(new Question("Q1: What is the primary goal of Unit Testing?", Map.of("A", "Verify system requirements.", "B", "Verify individual software components.", "C", "Verify user acceptance.", "D", "Verify performance under load."), "B"));
        testingEasy.add(new Question("Q2: Black box testing mainly focuses on:", Map.of("A", "Internal code structure.", "B", "Security vulnerabilities.", "C", "Software functionality.", "D", "Database schema."), "C"));
        testingEasy.add(new Question("Q3: The process of finding the root cause of an error is called:", Map.of("A", "Debugging", "B", "Execution", "C", "Validation", "D", "Verification"), "A"));
        testingEasy.add(new Question("Q4: Which testing type verifies the entire integrated system?", Map.of("A", "Integration testing", "B", "System testing", "C", "Smoke testing", "D", "Regression testing"), "B"));
        testingEasy.add(new Question("Q5: UAT stands for:", Map.of("A", "Unit Acceptance Test", "B", "User Access Terminology", "C", "User Acceptance Testing", "D", "Universal Application Test"), "C"));
        questionBank.put("Testing/Easy", testingEasy);

        // --- TESTING MEDIUM (5 Qs) ---
        List<Question> testingMedium = new ArrayList<>();
        testingMedium.add(new Question("Q1: What is 'Boundary Value Analysis'?", Map.of("A", "Testing random inputs.", "B", "Testing values at the edges of valid partitions.", "C", "Testing only positive values.", "D", "Testing only negative values."), "B"));
        testingMedium.add(new Question("Q2: Regression testing is performed to ensure:", Map.of("A", "The new code is flawless.", "B", "The system performs under heavy load.", "C", "New changes haven't broken existing functionality.", "D", "User requirements are met."), "C"));
        testingMedium.add(new Question("Q3: A defect found in the production environment is known as a:", Map.of("A", "Bug", "B", "Error", "C", "Incident", "D", "Failure"), "D"));
        testingMedium.add(new Question("Q4: What is the primary purpose of a Test Plan?", Map.of("A", "To document test cases.", "B", "To define the scope, objective, and resources for testing.", "C", "To record defects.", "D", "To track team hours."), "B"));
        testingMedium.add(new Question("Q5: Which is NOT a level of software testing?", Map.of("A", "Unit Testing", "B", "Integration Testing", "C", "Module Testing", "D", "System Testing"), "C"));
        questionBank.put("Testing/Medium", testingMedium);
    }

    // --- Panel Creation Methods ---

    // Custom Panel for Professional Blue/White Gradient Background (VIBRANT)
    private class VibrantGradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            // Electric Blue/Cyan to White gradient for a crisp, modern look
            GradientPaint gp = new GradientPaint(0, 0, new Color(0, 191, 255), 0, getHeight(), new Color(240, 255, 255));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
    
    // 1. Candidate Registration & Profile (Login Module) - VIBRANT & ATTRACTIVE
    private JPanel createLoginPanel() {
        JPanel panel = new VibrantGradientPanel();
        panel.setLayout(new GridBagLayout());
        
        JPanel loginBox = new JPanel(new GridLayout(6, 1, 10, 10));
        loginBox.setPreferredSize(new Dimension(380, 450));
        loginBox.setBorder(new EmptyBorder(40, 40, 40, 40));

        // Slightly opaque white box for contrast
        loginBox.setBackground(new Color(255, 255, 255, 245)); 
        
        // Title Text
        JLabel title = new JLabel("VIRTUAL MOCK INTERVIEW");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(0, 150, 255)); // Bright Blue
        title.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Subtitle/Icon
        JLabel subtitle = new JLabel("🎯 Ready for your technical challenge?");
        subtitle.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        subtitle.setForeground(new Color(0, 191, 255)); // Deep Sky Blue
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        JTextField usernameField = new JTextField(20);
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 255)));
        
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 255)));

        JButton loginButton = new JButton("ACCESS DASHBOARD");
        
        loginBox.add(title);
        loginBox.add(subtitle);
        loginBox.add(new JLabel("Username:"));
        loginBox.add(usernameField);
        loginBox.add(new JLabel("Password:"));
        loginBox.add(passwordField);
        
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setBackground(new Color(0, 150, 255)); // Bright Blue Button
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        loginButton.setActionCommand("LOGIN");
        loginButton.addActionListener(this);
        
        loginBox.add(loginButton);
        
        panel.add(loginBox);
        return panel;
    }

    // 2. Domain & Difficulty Selection Module
    private JPanel createSelectionPanel() {
        JPanel panel = new VibrantGradientPanel();
        panel.setLayout(new GridBagLayout());
        
        JPanel selectionBox = new JPanel(new GridLayout(7, 1, 15, 15));
        selectionBox.setPreferredSize(new Dimension(380, 450));
        selectionBox.setBorder(new EmptyBorder(30, 40, 30, 40));
        selectionBox.setBackground(new Color(255, 255, 255, 245));

        JLabel header = new JLabel("Select Interview Domain");
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setForeground(new Color(0, 150, 255));
        selectionBox.add(header);
        
        selectionBox.add(new JLabel("Choose Technical Area (5 Categories):"));
        String[] domains = {"Java", "SQL", "Python", "C++", "Testing"}; 
        domainComboBox = new JComboBox<>(domains);
        selectionBox.add(domainComboBox);

        selectionBox.add(new JLabel("Choose Difficulty Level:"));
        String[] difficulties = {"Easy", "Medium"};
        difficultyComboBox = new JComboBox<>(difficulties);
        selectionBox.add(difficultyComboBox);

        JButton startInterviewButton = new JButton("START SIMULATION");
        startInterviewButton.setActionCommand("START_INTERVIEW");
        startInterviewButton.addActionListener(this);
        
        startInterviewButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        startInterviewButton.setBackground(new Color(0, 191, 255)); // Deep Sky Blue
        startInterviewButton.setForeground(Color.WHITE);
        startInterviewButton.setFocusPainted(false);
        startInterviewButton.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        selectionBox.add(new JPanel()); 
        selectionBox.add(startInterviewButton);

        panel.add(selectionBox);
        return panel;
    }

    // 3. Interview Simulation & Scoring Module
    private JPanel createInterviewPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));
        panel.setBackground(new Color(245, 245, 245)); // Light Gray Background

        // North: Question Area
        questionArea = new JTextArea("Select a domain to begin.", 5, 50);
        questionArea.setFont(new Font("Serif", Font.PLAIN, 18));
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setBackground(new Color(230, 245, 255)); // Very Light Blue
        questionArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JScrollPane questionScrollPane = new JScrollPane(questionArea);
        questionScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 150, 255)), "CURRENT QUESTION (MCQ)", 0, 0, new Font("Arial", Font.BOLD, 14)));
        panel.add(questionScrollPane, BorderLayout.NORTH);

        // Center: Options Panel
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBorder(BorderFactory.createTitledBorder("SELECT ANSWER"));
        optionsPanel.setBackground(Color.WHITE);
        panel.add(new JScrollPane(optionsPanel), BorderLayout.CENTER);

        // South: Control Button
        submitAnswerButton = new JButton("SUBMIT AND NEXT QUESTION");
        submitAnswerButton.setActionCommand("SUBMIT_ANSWER");
        submitAnswerButton.addActionListener(this);
        submitAnswerButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitAnswerButton.setBackground(new Color(0, 191, 255)); // Deep Sky Blue
        submitAnswerButton.setForeground(Color.WHITE);
        submitAnswerButton.setFocusPainted(false);
        submitAnswerButton.setBorder(new EmptyBorder(10, 0, 10, 0));
        panel.add(submitAnswerButton, BorderLayout.SOUTH);

        return panel;
    }
    
    // 4 & 5. Performance Report Generation Module & History Module Placeholder
    private JPanel createReportPanel() {
        JPanel panel = new VibrantGradientPanel();
        panel.setLayout(new GridBagLayout());
        
        JTextArea reportText = new JTextArea();
        reportText.setEditable(false);
        reportText.setFont(new Font("Monospaced", Font.PLAIN, 14));
        reportText.setBackground(new Color(245, 245, 255)); 
        
        JScrollPane scrollPane = new JScrollPane(reportText);
        scrollPane.setPreferredSize(new Dimension(550, 450));
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 150, 255)), "PERFORMANCE REPORT", 0, 0, new Font("Arial", Font.BOLD, 16)));
        
        JButton homeButton = new JButton("LOGOUT & SAVE HISTORY");
        homeButton.setActionCommand("LOGOUT");
        homeButton.addActionListener(this);
        homeButton.setFont(new Font("Arial", Font.BOLD, 14));
        homeButton.setBackground(new Color(255, 99, 71)); 
        homeButton.setForeground(Color.WHITE);
        homeButton.setFocusPainted(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        
        panel.add(scrollPane, gbc);
        
        gbc.gridy = 1;
        panel.add(homeButton, gbc);
        
        mainPanel.putClientProperty("ReportTextArea", reportText);
        return panel;
    }
    

    // --- Action Handling & Logic (Scoring Engine Module) ---

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("LOGIN".equals(command)) {
            cardLayout.show(mainPanel, "Selection");
        } else if ("START_INTERVIEW".equals(command)) {
            startInterview();
            cardLayout.show(mainPanel, "Interview");
        } else if ("SUBMIT_ANSWER".equals(command)) {
            submitAnswer();
        } else if ("LOGOUT".equals(command)) {
             System.exit(0);
        }
    }

    private void startInterview() {
        String domain = (String) domainComboBox.getSelectedItem();
        String difficulty = (String) difficultyComboBox.getSelectedItem();
        String key = domain + "/" + difficulty;

        currentQuestions = questionBank.get(key);
        currentQuestionIndex = 0;
        score = 0; 
        candidateAnswers.clear(); 

        if (currentQuestions != null && !currentQuestions.isEmpty()) {
            displayQuestion(currentQuestionIndex);
        } else {
            questionArea.setText("No questions available for this selection.");
        }
    }

    private void displayQuestion(int index) {
        Question q = currentQuestions.get(index);
        
        questionArea.setText(String.format("Domain: %s | Question %d of %d\n\n%s", 
            domainComboBox.getSelectedItem(), (index + 1), currentQuestions.size(), q.questionText));

        optionsPanel.removeAll();
        optionsGroup = new ButtonGroup();
        
        List<String> sortedKeys = new ArrayList<>(q.options.keySet());
        java.util.Collections.sort(sortedKeys); 

        for (String key : sortedKeys) {
            JRadioButton radioButton = new JRadioButton(key + ") " + q.options.get(key));
            radioButton.setActionCommand(key);
            radioButton.setFont(new Font("Arial", Font.PLAIN, 16));
            radioButton.setBackground(Color.WHITE);
            optionsGroup.add(radioButton);
            optionsPanel.add(radioButton);
            optionsPanel.add(Box.createVerticalStrut(8)); 
        }
        
        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

    private void submitAnswer() {
        String selectedKey = optionsGroup.getSelection() != null ? optionsGroup.getSelection().getActionCommand() : null;
        
        if (selectedKey == null) {
            JOptionPane.showMessageDialog(this, "Please select an answer before proceeding.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Question currentQ = currentQuestions.get(currentQuestionIndex);
        
        // Scoring Logic
        candidateAnswers.put(currentQ, selectedKey);
        if (selectedKey.equals(currentQ.correctAnswerKey)) {
            score++;
        }

        currentQuestionIndex++;

        if (currentQuestionIndex < currentQuestions.size()) {
            displayQuestion(currentQuestionIndex);
        } else {
            // End of Interview
            generateReport();
            cardLayout.show(mainPanel, "Report");
        }
    }
    
    // Performance Report Generation & History Tracking (Display only)
    private void generateReport() {
        JTextArea reportText = (JTextArea) mainPanel.getClientProperty("ReportTextArea");
        if (reportText == null) return;
        
        int totalQuestions = currentQuestions.size();
        double percentage = (double) score / totalQuestions * 100;
        
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("=======================================================\n");
        reportBuilder.append("           FINAL PERFORMANCE REPORT (MOCK INTERVIEW)\n");
        reportBuilder.append("=======================================================\n");
        reportBuilder.append(String.format("Candidate: %s | Domain: %s | Difficulty: %s\n", 
            "DemoUser", domainComboBox.getSelectedItem(), difficultyComboBox.getSelectedItem()));
        reportBuilder.append("-------------------------------------------------------\n");
        reportBuilder.append(String.format("Total Questions Attempted: %d\n", totalQuestions));
        reportBuilder.append(String.format("Correct Answers: %d\n", score));
        reportBuilder.append(String.format("Final Score: %.2f%% %s\n", percentage, (percentage >= 70 ? "🎉 (PASS)" : "📉 (FAIL)")));
        reportBuilder.append("=======================================================\n\n");
        
        // Detailed Review
        reportBuilder.append("--- DETAILED QUESTION REVIEW ---\n");
        int reviewIndex = 1;
        for (Question q : currentQuestions) {
            String candidateAnswerKey = candidateAnswers.get(q);
            boolean isCorrect = candidateAnswerKey.equals(q.correctAnswerKey);
            String status = isCorrect ? "✅ CORRECT" : "❌ INCORRECT";
            
            reportBuilder.append(String.format("%d. %s\n", reviewIndex++, q.questionText));
            reportBuilder.append(String.format("   Your Choice: %s\n", q.options.get(candidateAnswerKey)));
            reportBuilder.append(String.format("   Correct Answer: %s\n", q.options.get(q.correctAnswerKey)));
            reportBuilder.append(String.format("   Status: %s\n\n", status));
        }
        
        reportText.setText(reportBuilder.toString());
    }

    // --- Main Method ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VirtualMockInterviewGuide());
    }
}
