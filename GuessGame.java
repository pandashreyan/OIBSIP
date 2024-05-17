import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

class GuessGame extends JFrame {
    JTextField inputField, bestScoreField, guessButton,guessCountField, timerField;
    JLabel messageLabel;
    GuessButtonListener guessButtonListener;
    GiveUpButtonListener giveUpButtonListener;
    PlayAgainButtonListener playAgainButtonListener;

    int randomNumber;
    int guessCount = 0;
    int bestScore = 100;
    int timeRemaining = 61; // 60 seconds
    Timer timer;

    enum Difficulty {
        EASY(20),
        MEDIUM(50),
        HARD(100);

        private final int maxNumber;

        Difficulty(int maxNumber) {
            this.maxNumber = maxNumber;
        }

        public int getMaxNumber() {
            return maxNumber;
        }
    }

    Difficulty difficulty = Difficulty.EASY; // Default difficulty

    public GuessGame() {
        //Get the container
        Container gg = getContentPane();

        //Set absolute layout
        gg.setLayout(null);

        //Set Background Color
        gg.setBackground(Color.LIGHT_GRAY);



        //Creating label Guess my number text
        JLabel titleLabel = new JLabel("Guess Game");
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setSize(270, 160);
        titleLabel.setLocation(10, 35);


        //Creating TextField for input guess
        inputField = new JTextField(10);
        inputField.setSize(100, 30);
        inputField.setLocation(300, 110);
        inputField.setEditable(false);

        //Creating Label for Display message
        messageLabel = new JLabel("Guess my number");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 17));
        messageLabel.setSize(270, 20);
        messageLabel.setLocation(270, 80);

        //Creating Text field for best score
        bestScoreField = new JTextField(10);
        bestScoreField.setSize(50, 30);
        bestScoreField.setLocation(10, 10);

        //Creating Label for best score
        JLabel bestScoreLabel = new JLabel("Score");
        bestScoreLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        bestScoreLabel.setSize(100, 30);
        bestScoreLabel.setLocation(70, 10);

        //Creating guess text field
        guessCountField = new JTextField(10);
        guessCountField.setSize(50, 30);
        guessCountField.setLocation(180, 10);

        //Creating guess Label
        JLabel guessCountLabel = new JLabel("Guesses");
        guessCountLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        guessCountLabel.setSize(100, 30);
        guessCountLabel.setLocation(240, 10);

        //Creating timer text field
        timerField = new JTextField(10);
        timerField.setSize(50, 30);
        timerField.setLocation(350, 10);
        timerField.setEditable(false);

        //Creating timer Label
        JLabel timerLabel = new JLabel("Timer");
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        timerLabel.setSize(100, 30);
        timerLabel.setLocation(410, 10);

        //creating 3 buttons
        JButton guessButton = new JButton("Guess");
        guessButton.setSize(100, 40);
        guessButton.setFont(new Font("Arial", Font.BOLD, 15));
        guessButton.setLocation(220, 160); // Changed position
        guessButtonListener = new GuessButtonListener();
        guessButton.addActionListener(guessButtonListener);

        JButton giveUpButton = new JButton("Give up!");
        giveUpButton.setSize(100, 40);
        giveUpButton.setFont(new Font("Arial", Font.BOLD, 15));
        giveUpButton.setLocation(350, 160); // Changed position
        giveUpButtonListener = new GiveUpButtonListener();
        giveUpButton.addActionListener(giveUpButtonListener);

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setSize(150, 40);
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 15));
        playAgainButton.setLocation(0, 300); // Changed position
        playAgainButtonListener = new PlayAgainButtonListener();
        playAgainButton.addActionListener(playAgainButtonListener);

        JComboBox<Difficulty> difficultyDropdown = new JComboBox<>(Difficulty.values());
        difficultyDropdown.setSelectedItem(difficulty);
        difficultyDropdown.addActionListener(e -> {
            difficulty = (Difficulty) difficultyDropdown.getSelectedItem();
            resetGame();
        });
        difficultyDropdown.setSize(120, 30);
        difficultyDropdown.setLocation(500, 10);
        gg.add(difficultyDropdown);

        //Place the components in the pane
        gg.add(bestScoreLabel);
        gg.add(messageLabel);
        gg.add(titleLabel);
        gg.add(inputField);
        gg.add(bestScoreField);
        gg.add(guessCountField);
        gg.add(guessButton);
        gg.add(giveUpButton);
        gg.add(playAgainButton);
        gg.add(guessCountLabel);
        gg.add(timerField);
        gg.add(timerLabel);

        //Changing TextFields to UnEditable
        bestScoreField.setEditable(false);
        guessCountField.setEditable(false);
        timerField.setEditable(false);

        //Set the title of the window
        setTitle("GUESS MY NUMBER");

        //Set the size of the window and display it
        setSize(650, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        resetGame();
    }

    private class GuessButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int guess = Integer.parseInt(inputField.getText());
            guessCount++;
            if (guess < randomNumber) {
                messageLabel.setText(guess + " is too low, try again!!");
            } else if (guess > randomNumber) {
                messageLabel.setText(guess + " is too high, try again!!");
            } else {
                messageLabel.setText("CORRECT, YOU WIN!!!!");
                if (guessCount < bestScore) {
                    bestScore = guessCount;
                    bestScoreField.setText(bestScore + "");
                } else
                    bestScoreField.setText("" + bestScore);
                inputField.setEditable(false);
                timer.cancel();
            }

            //setting focus to input guess text field
            inputField.requestFocus();
            inputField.selectAll();
            guessCountField.setText(guessCount + "");
        }
    }

    private class GiveUpButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            guessCountField.setText("");
            messageLabel.setText(randomNumber + " is the answer!");
            inputField.setText("");
            inputField.setEditable(false);
            timer.cancel();
        }
    }

    private void resetGame() {
        randomNumber = (int) (Math.random() * difficulty.getMaxNumber());
        guessCount = 0;
        messageLabel.setText("Guess my number");
        guessCountField.setText(guessCount + "");
        inputField.setEditable(true);
        inputField.setText("");
        timerField.setText(timeRemaining + "");

        // Start the timer
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (timeRemaining > 0) {
                    timeRemaining--;
                    timerField.setText(timeRemaining + "");
                } else {
                    messageLabel.setText("Out of Time!");
                    guessButton.setEnabled(false); // Disable guess button after time runs out
                    timer.cancel();
                }
            }
        }, 0, 1000); // Start timer after 0 delay and update every 1 second
    }

    private class PlayAgainButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            resetGame();
            guessButton.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        new GuessGame();
    }
}
