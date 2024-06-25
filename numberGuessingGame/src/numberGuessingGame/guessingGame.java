package numberGuessingGame;

import java.util.HashMap;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

import javax.swing.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;

public class guessingGame extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//GUI
	static JFrame frame;
	static JPanel buttonPanel, textAreaPanel, inputPanel, submitPanel;
	static JButton yesBtn, noBtn, submit;
	static JTextPane textPane;
	static JTextField input;
	StyledDocument doc;
	String guess;
	int numGuess;
	Timer timer;
	HashMap<String, Integer> guessValues = new HashMap<>();
	int count = 0;
	
	public guessingGame() {
		super(new BorderLayout());
		textAreaPanel = new JPanel(new GridLayout(1, 1));
		buttonPanel = new JPanel(new FlowLayout());
		
		textAreaPanel.setPreferredSize(new Dimension(400, 200));
		buttonPanel.setPreferredSize(new Dimension(400, 200));
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		add(textAreaPanel, BorderLayout.PAGE_START);
		add(buttonPanel, BorderLayout.PAGE_END);
		
		textPane = new JTextPane();
		textPane.setOpaque(false);
		textAreaPanel.add(textPane, BorderLayout.CENTER);
		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		textPane.setEditable(false);

		yesBtn = new JButton("Yes");
    	noBtn = new JButton("No");
    	

    	
		
		// Start the Game.
		
		textPane.setText("Welcome to the Number Guessing game!!");
		
		timer = new Timer();
		timer.schedule(new TimerTask() {
		    public void run() {
		    	textPane.setText("Would you like to play?");
		    }
		}, 4000); // Delay in milliseconds
		
		timer.schedule(new TimerTask() {
		    public void run() {	
    			textPane.setText("Would you like to play?");
    			buttonPanel.add(yesBtn);
    	    	buttonPanel.add(noBtn);
    	    	yesBtn.setVisible(true);
    	    	noBtn.setVisible(true);
		    }
		}, 5000); // Delay in milliseconds

		yesBtn.addActionListener(this);
		noBtn.addActionListener(this);
		
		
		
		
		setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		setSize(400, 400);
		
		

	}
	
	
	protected void playGame() {
		count = 0;
		input = new JTextField("", 10);
		submit = new JButton("Submit");
		submit.addActionListener(this);
		
		inputPanel = new JPanel();
		submitPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout());
		submitPanel.setLayout(new FlowLayout());
		submitPanel.setPreferredSize(new Dimension(200,150));
		
		textPane.setText("Great. Let's get started!");
		yesBtn.setVisible(false);
		noBtn.setVisible(false);
		buttonPanel.remove(yesBtn);
		buttonPanel.remove(noBtn);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(inputPanel);
		buttonPanel.add(submitPanel);
		takeGuess();	
			
	}
	
	// Guess input method
	protected void takeGuess() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				textPane.setText(null);
				textPane.setText("Guess a number between 1 and 100 and type it into the input box below.");
				inputPanel.add(input);
				submitPanel.add(submit);
				input.setText("");
			 }
		 }, 3000);
	}
	
	// submit method
	protected void submit() {
		guess = input.getText();
		try {
			numGuess = Integer.parseInt(guess);
		} catch(NumberFormatException f) {
			textPane.setText("please enter a valid number");
			takeGuess();
		}
		if(numGuess < 1 || numGuess > 100) {
			textPane.setText("please enter a valid number");
			takeGuess();
		} else {
			if (count < 2) {
				textPane.setText("You have selected the number " + guess + ".");
				String countString = Integer.toString(count + 1);
				guessValues.put("guess " + countString, numGuess);
				count++;
				if(count < 3) {
					takeGuess();
				} else {
					finalize();	
				}
			} else {
				String countString = Integer.toString(count + 1);
				guessValues.put("guess " + countString, numGuess);
				count++;
				if(count < 2) {
					takeGuess();
				} else {
					finalize();	
				}
			}
			
		}
	}
	
	// Finalize
	
	protected void finalize() {
		submit.setVisible(false);
		inputPanel.remove(input);
		submitPanel.remove(submit);
		buttonPanel.remove(inputPanel);
		buttonPanel.remove(submitPanel);
		buttonPanel.revalidate();
		buttonPanel.repaint();
		
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					textPane.setText(null);
					doc  = textPane.getStyledDocument();
					doc.insertString(0, "your guesses were " + guessValues.get("guess 1")
					+ ", " + guessValues.get("guess 2") + " and " + guessValues.get("guess 3"), null);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 1000);
		
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				int comRandNum = numRandom();
				if(guessValues.get("guess 1") == comRandNum || 
				   guessValues.get("guess 2") == comRandNum || 
				   guessValues.get("guess 3") == comRandNum) {
					try {
						doc.remove(0, doc.getLength());
						doc.insertString(0, "You guessed correctly!!! \n"
								+ "the correct number is " + Integer.toString(comRandNum)
								+ ".", null);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				} else {
					try {
						doc.remove(0, doc.getLength());
						doc.insertString(0, "Sorry. You guessed incorrectly. \n"
								+ "the correct number is " + Integer.toString(comRandNum)
								+ ".", null);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
			}
		}, 4000);
		
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					doc.remove(0, doc.getLength());
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				
				textPane.setText("Would you like to play again?");
				buttonPanel.setLayout(new FlowLayout());
    			buttonPanel.add(yesBtn);
    	    	buttonPanel.add(noBtn);
    	    	yesBtn.setVisible(true);
    	    	noBtn.setVisible(true);
			}
		}, 9000);
	}
	
	// ActionListener 
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand() == "Yes") {
					playGame();
				}
				else if(e.getActionCommand() == "No") {
					textPane.setText("Thank you for playing. Goodbye");
					timer = new Timer();
					timer.schedule(new TimerTask() {
						public void run() {
							System.exit(0);
						}
					}, 3000);
				}
				else if(e.getActionCommand() == "Submit") {				
					submit();
				}
			}
	
	
	// custom function numRandom
	static int numRandom() {
		Random generator = new Random();
		int numRandom = generator.nextInt(100)+1;
		return numRandom;
	}
	
	// create and show GUI
	public static void createAndShowGUI() {
		//Create and set up the window.
        frame = new JFrame("Number Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new guessingGame();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}
	
	
	

	// MAIN METHOD
	public static void main(String[] args) {
		
		//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
}
