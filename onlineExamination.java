import java.awt.event.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

  class onlineTest extends JFrame implements ActionListener {

     JLabel l;
     JRadioButton jb[] = new JRadioButton[5];
     JButton b1, b2,logout;
     ButtonGroup bg;
     int count = 0, current = 0, x = 1, y = 1, now = 0;
     int m[] = new int[10];
     int remainingTime = 103; // Adjust this value for desired time in seconds
     Timer timer;
     JLabel timerLabel;
      private String username;
      private String password;
      private JTextField usernameField;
      private JPasswordField passwordField;
      private JButton loginButton;
      private JFrame loginWindow;

     onlineTest(String s) {
         super(s);
         l = new JLabel();
         add(l);
         bg = new ButtonGroup();
         for (int i = 0; i < 5; i++) {
             jb[i] = new JRadioButton();
             add(jb[i]);
             bg.add(jb[i]);
         }
         b1 = new JButton("Next");
         b2 = new JButton("Bookmark");
         logout = new JButton("Logout");
         b1.addActionListener(this);
         b2.addActionListener(this);
         logout.addActionListener(this);
         add(b1);
         add(b2);
         add(logout);
         timerLabel = new JLabel("Time Remaining: " + remainingTime + " seconds");
         add(timerLabel);
         set();
         l.setBounds(30, 40, 450, 20);
         jb[0].setBounds(50, 80, 100, 20);
         jb[1].setBounds(50, 110, 100, 20);
         jb[2].setBounds(50, 140, 100, 20);
         jb[3].setBounds(50, 170, 100, 20);
         b1.setBounds(100, 240, 100, 30);
         b2.setBounds(270, 240, 100, 30);
         logout.setBounds(450, 240, 100, 30);
         timerLabel.setBounds(350, 10, 150, 20);
         timer = new Timer();
         startTimer();
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setLayout(null);
         setLocation(250, 100);
         setVisible(true);
         setSize(600, 350);
         loginWindow = new JFrame("Login");
         loginWindow.setLayout(null);
         JLabel usernameLabel = new JLabel("Username:");
         usernameLabel.setBounds(30, 50, 100, 20);
         usernameField = new JTextField();
         usernameField.setBounds(140, 50, 150, 20);
         JLabel passwordLabel = new JLabel("Password:");
         passwordLabel.setBounds(30, 80, 100, 20);
         passwordField = new JPasswordField();
         passwordField.setBounds(140, 80, 150, 20);
         loginButton = new JButton("Login");
         loginButton.setBounds(100, 120, 100, 30);
         this.setVisible(false);
         loginButton.addActionListener(this);
         loginWindow.add(usernameLabel);
         loginWindow.add(usernameField);
         loginWindow.add(passwordLabel);
         loginWindow.add(passwordField);
         loginWindow.add(loginButton);
         loginWindow.setSize(300, 200);
         loginWindow.setLocationRelativeTo(null); // Center the login window
         loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         loginWindow.setVisible(true);

         // Initialize timer and timer label

     }

     public void actionPerformed(ActionEvent e) {
         // Existing logic for button clicks (next, bookmark, result)
         // ...
         if (e.getSource() == loginButton) {
             username = usernameField.getText();
             password = new String(passwordField.getPassword()); // Get password as a string
             if (isValidLogin(username, password)) { // Implement login validation logic
                 loginWindow.setVisible(false); // Hide login window on successful login
                 this.setVisible(true); // Show the main quiz window
             } else {
                 JOptionPane.showMessageDialog(loginWindow, "Invalid username or password.");
             }
         }
         else if (e.getSource() == logout) {  // Handle logout button click
             int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
             if (choice == JOptionPane.YES_OPTION) {
                 // Logout logic (e.g., clear session data, reset quiz, show login window)
                 username = null;
                 password = null;
                 count = 0;
                 current = 0;
                 remainingTime = 60;  // Reset timer
                 timer.cancel();  // Stop the timer
                 // ... (other reset logic)
                 loginWindow.setVisible(true);
                 this.setVisible(false);
             }
         }
             else
             {
                 if (e.getSource() == b1) {
                     if (check())
                         count = count + 1;
                     current++;
                     set();
                     if (current == 9) {
                         b1.setEnabled(false);
                         b2.setText("Result");
                         timer.cancel();
                     }
                 }
                 if (e.getActionCommand().equals("Bookmark")) {
                     JButton bk = new JButton("Bookmark" + x);
                     bk.setBounds(480, 20 + 30 * x, 100, 30);
                     add(bk);
                     bk.addActionListener(this);
                     m[x] = current;
                     x++;
                     current++;
                     set();
                     if (current == 9)
                         b2.setText("Result");
                     setVisible(false);
                     setVisible(true);
                 }
                 for (int i = 0, y = 1; i < x; i++, y++) {
                     if (e.getActionCommand().equals("Bookmark" + y)) {
                         if (check())
                             count = count + 1;
                         now = current;
                         current = m[y];
                         set();
                         ((JButton) e.getSource()).setEnabled(false);
                         current = now;
                     }
                 }

                 if (e.getActionCommand().equals("Result")) {
                     if (check())
                         count = count + 1;
                     current++;
                     //System.out.println("correct ans="+count);
                     JOptionPane.showMessageDialog(this, "correct ans=" + count);
                     System.exit(0);
                 }
             }

     }

      private boolean isValidLogin(String username, String password) {
          // Replace with your actual login validation logic (e.g., checking against a file or database)
          return username.equals("shreyan") && password.equals("secret"); // Example for hardcoded credentials
      }

      void set() {
         jb[4].setSelected(true);
         if (current == 0) {
             l.setText("Que1:Number of primitive data types in Java are?");
             jb[0].setText("6");
             jb[1].setText("8");
             jb[2].setText("7");
             jb[3].setText("10");
         }
         if (current == 1) {
             l.setText("Que2:Which keyword is used for accessing the features of a package?");
             jb[0].setText("package");
             jb[1].setText("extends");
             jb[2].setText("import");
             jb[3].setText("implement");
         }
         if (current == 2) {
             l.setText("Que3: When an array is passed to a method, what does the method receive?");
             jb[0].setText("Copy of first element");
             jb[1].setText("length of array");
             jb[2].setText("Copy of array");
             jb[3].setText("Reference of array");
         }
         if (current == 3) {
             l.setText("Que4:What is the default encoding for an OutputStreamWriter?");
             jb[0].setText("Default encoding of the host platform");
             jb[1].setText("UTF-8");
             jb[2].setText("UTF-12");
             jb[3].setText("None of the above");
         }
         if (current == 4) {
             l.setText("Que5: Which institute is best for java coaching?");
             jb[0].setText("Utek");
             jb[1].setText("Aptech");
             jb[2].setText("SSS IT");
             jb[3].setText("jtek");
         }
         if (current == 5) {
             l.setText("Que6: compareTo() returns");
             jb[0].setText("True");
             jb[1].setText("False");
             jb[2].setText("int value");
             jb[3].setText("positive value only");
         }
         if (current == 6) {
             l.setText("Que7:Which of the following is a mutable class in java?");
             jb[0].setText("java.lang.Short");
             jb[1].setText("java.lang.StringBuilder");
             jb[2].setText("java.lang.Byte");
             jb[3].setText("java.lang.String");
         }
         if (current == 7) {
             l.setText("Que8:Output of Math.floor(3.6)?");
             jb[0].setText("ERROR");
             jb[1].setText("3.5");
             jb[2].setText("4.0");
             jb[3].setText("3.0");
         }
         if (current == 8) {
             l.setText("Que9: Which Exception is thrown when divided by zero statement is executed?");
             jb[0].setText("ArithmeticException");
             jb[1].setText("NullPointerException");
             jb[2].setText("NumberFormatException");
             jb[3].setText("None");
         }
         if (current == 9) {
             l.setText("Que10: If a thread goes to sleep ?");
             jb[0].setText("It releases half of its locks.");
             jb[1].setText("It releases all the locks it has.");
             jb[2].setText("It does not release any locks.");
             jb[3].setText("It releases all of its lock except one.");
         }
         l.setBounds(30, 40, 450, 20);
         for (int i = 0, j = 0; i <= 90; i += 30, j++)
             jb[j].setBounds(50, 80 + i, 200, 20);
         // Existing logic for setting question and answer options
         // ...

         // Update timer label on each question change
         timerLabel.setText("Time Remaining: " + remainingTime + " seconds");
     }

     boolean check() {
         if (current == 0)
             return (jb[1].isSelected());
         if (current == 1)
             return (jb[2].isSelected());
         if (current == 2)
             return (jb[3].isSelected());
         if (current == 3)
             return (jb[0].isSelected());
         if (current == 4)
             return (jb[2].isSelected());
         if (current == 5)
             return (jb[2].isSelected());
         if (current == 6)
             return (jb[1].isSelected());
         if (current == 7)
             return (jb[3].isSelected());
         if (current == 8)
             return (jb[1].isSelected());
         if (current == 9)
             return (jb[2].isSelected());
         return false;
         // Existing logic for checking answer
         // ...
     }


     private void startTimer() {
         timer.scheduleAtFixedRate(new TimerTask() {
             @Override
             public void run() {
                 remainingTime--;
                 timerLabel.setText("Time Remaining: " + remainingTime + " seconds");
                 if (remainingTime == 0) {
                     timer.cancel();
                     // Handle timeout scenario (e.g., show message, submit answers automatically)
                     JOptionPane.showMessageDialog(onlineTest.this, "Time's up! Submitting answers..."+"\n correct ans ="+count);
                     //  Call your logic to submit or evaluate answers here
                     System.exit(0);
                 }
             }
         }, 0, 1000); // Schedule task to run every 1 second
     }

     public static void main(String s[]) {
         new onlineTest("Online Test Of Java");
     }
 }
