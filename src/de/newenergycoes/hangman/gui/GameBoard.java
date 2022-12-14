package de.newenergycoes.hangman.gui;

import java.awt.Color;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import de.newenergycoes.hangman.MainWindow;
import de.newenergycoes.hangman.domainData.Hangman;
import de.newenergycoes.hangman.domainData.Player;
import de.newenergycoes.hangman.domainData.Wording;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

/** 
 * GameBoard of hangman :-)
 * @author matthias.harlos
 */
public class GameBoard {

	private JPanel panel = new JPanel();
	private int errorCounter = 0;
	private List<Player> players;
	private List<Player> guessingPlayers;
	protected int playerToChange;
	protected int guessingPlayerToChange = 0;
	private Player wordGiver;
	private MyTable looserTable;
	private MyTable winnerTable;
	private JTextArea gallows;
	private JTextArea hangingMan;
	private JButton startButton;
	private Wording wording;
	private TextField solutionField;
	
	/** 
	 * Constructor creates the gameBoard and have the game logic
	 * @param mainWindow MainWindow
	 * @param players List<Player>
	 * @param playerToChange Integer
	 * @param isInitialGame boolean
	 * @param allAgainstAll boolean // defines GameMode
	 * @param header Header //Header is needed for exit button
	 */
	public GameBoard(MainWindow mainWindow, List<Player> players, int playerToChange, boolean isInitialGame, boolean allAgainstAll, Header header) {
		//Exit the game and return to startPage! Add to Header
				JButton btnEnde = new JButton("");
				btnEnde.setIcon( new ImageIcon(GameBoard.class.getResource("/de/newenergycoes/hangman/img/exitDoor.png")));
				btnEnde.setForeground(new Color(0, 0, 102));
				btnEnde.setBackground(new Color(200, 20, 20));
				btnEnde.setBounds(700, 5, 45, 40);
				btnEnde.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						mainWindow.getStartpage(panel);
					}
				});
				header.getPanel().add(btnEnde);
		// initialize GameBoard
		this.players = players;
		this.playerToChange = playerToChange;
		if (isInitialGame) {
			this.players.stream().forEach(player -> players.stream().filter(playerTwo -> !playerTwo.equals(player))
					.forEach(playerTwo -> player.getOtherPlayer().add(playerTwo)));
		}
		mainWindow.setActuallyPanelData(players, playerToChange, this.panel);
		//We begin initial with player 0 but need to count high on start to give playerToChange to next Wording round!
		if (players.size() - 1 == playerToChange) {
			this.playerToChange = 0;
		} else {
			this.playerToChange++;
		}
		//Here we need the initial playerToChange and not this.playerToChange because this.playerToChange needs
		// to count high for the next Wording round with new gameBoard and new playerToChange counted from last round!
		wordGiver = players.get(playerToChange);
		guessingPlayers = new ArrayList<Player>(players);
		guessingPlayers.remove(wordGiver);
		
		this.panel.setBounds(10, 11, 787, 516);
		panel.setLayout(null);
		Label mainLabel = new Label(wordGiver.getPlayerName() + " bitte geben Sie ein Wort ein!");
		mainLabel.setAlignment(Label.CENTER);
		mainLabel.setBounds(268, 90, 250, 22);
		panel.add(mainLabel);
		int newPlayer = this.playerToChange;

		solutionField = new TextField();
		solutionField.setForeground(new Color(255, 255, 255));
		solutionField.setBounds(292, 136, 203, 22);
		panel.add(solutionField);

		Label hiddenWordLabel = new Label("");
		hiddenWordLabel.setAlignment(Label.CENTER);
		hiddenWordLabel.setBounds(213, 226, 361, 22);
		panel.add(hiddenWordLabel);

		//Winner table
		String colString = players.stream().map(player -> player.getPlayerName()).collect(Collectors.joining(","));
		String col[] = colString.split(",");
		String colStringWinning = players.stream().map(player -> player.getWinningScore() + "")
				.collect(Collectors.joining(","));
		String colScore[] = colStringWinning.split(",");
		String data[][] = { col, colScore };
		winnerTable = new MyTable(data, col);
		winnerTable.getTable().setBounds(88, 279, 615, 53);
		winnerTable.setAutoResizeTable();
		panel.add(winnerTable.getTable());		
		
		//Looser table
		String colStringLoosing = players.stream().map(player -> player.getLoosingScore() + "")
				.collect(Collectors.joining(","));
		String colLoosScore[] = colStringLoosing.split(",");
		String looserData[][] = { col, colLoosScore };
		looserTable = new MyTable(looserData, colLoosScore);
		looserTable.getTable().setBounds(88, 381, 615, 53);
		looserTable.setAutoResizeTable();
		panel.add(looserTable.getTable());
		
		//Hangman field for false Input!
		gallows = new JTextArea("");
		gallows.setForeground(new Color(255, 0, 0));
		gallows.setEnabled(true);
		gallows.setLineWrap(false);
		gallows.setEditable(false);
		gallows.setOpaque(false);
		gallows.setBounds(602, 136, 62, 119);
		panel.add(gallows);

		hangingMan = new JTextArea("");
		hangingMan.setForeground(new Color(255, 0, 0));
		hangingMan.setLineWrap(false);
		hangingMan.setEnabled(true);
		hangingMan.setEditable(false);
		hangingMan.setOpaque(false);
		hangingMan.setBounds(663, 136, 57, 119);
		panel.add(hangingMan);

		Hangman hangman = new Hangman();
		int initHangmanSize = hangman.getInitialHangmanListSize();
		wording = new Wording(null);
		
		startButton = new JButton("Eingabe");
		startButton.setBackground(new Color(153, 204, 102));
		startButton.setForeground(new Color(0, 0, 102));
		JRootPane rootPane = mainWindow.getFrame().getRootPane();
		rootPane.setDefaultButton(startButton);
		startButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				solutionField.setText(solutionField.getText().trim());


				//TODO: Info 1: need other solution to show the last feet of hangman!
				// Here will start a new round with new word
				if (errorCounter == initHangmanSize
						|| wording.getHiddenWord() != null && !wording.getHiddenWord().contains("_")) {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					GameBoard gameBoard = new GameBoard(mainWindow, players, newPlayer, false, allAgainstAll, header);
					mainWindow.getNewGameBoard(panel, gameBoard);
				}
				// If inputField is not empty
				if (!solutionField.getText().trim().equals("")) {
					//Set initial secret Word if not set!
					if (wording.getSolutionWord() == null) {
						solutionField.setForeground(new Color(0, 0, 0));
						wording.setHiddenWord(solutionField.getText());
						// Check if char in word gives guessingPoints else gives hangman error!
					} else if (errorCounter < initHangmanSize) {
						if (wording.getSolutionWord().contains(solutionField.getText().charAt(0) + "")) {
							wording.getNewWordingResult(solutionField.getText().charAt(0), guessingPlayers.get(guessingPlayerToChange));
							guessingPlayerToChange++;
						} else {
							printHangman(hangman);
						}
					}
					hiddenWordLabel.setText(wording.getHiddenWord());
					//If the last guessingPlayer in this list return to first guessingPlayer!
					if (guessingPlayerToChange == guessingPlayers.size()) {
						guessingPlayerToChange = 0;
					}
					
					mainLabel.setText(guessingPlayers.get(guessingPlayerToChange).getPlayerName() + " geben Sie einen Buchstaben ein!");
					solutionField.setText("");
					
					// guessingPlayer lost the round and the man is hanging on the gallows
					if (errorCounter == initHangmanSize) {
						wordGiver.setWinningScore();
						mainLabel.setText("Der Wortgeber " + wordGiver.getPlayerName() + " gewinnt!");
					
					// guessingPlayer wins the round against wordGiver
					} else if (!wording.getHiddenWord().contains("_")) {
						// Here wins all against one
						if (allAgainstAll == false) {
							wordGiver.setLoosingScore();
						
						// Here wins the guessinPlayer with most guessingPoints!
						} else {
							wordGiver.setOtherLoosingScore();
							List<Player> winner = guessingPlayers.get(guessingPlayerToChange).getWinnerWithGuessingPoints();
							winner.stream().forEach(win -> win.setOhterWinningScore());
							List<Player> looser = new ArrayList<>(guessingPlayers);
							looser.removeAll(winner);
							looser.stream().forEach(loos -> loos.setOtherLoosingScore());
							guessingPlayers.get(guessingPlayerToChange).resetGuessingPointsForRound();	
						}
						mainLabel.setText("Der Wortgeber " + wordGiver.getPlayerName() + " verliert!");
					}
					//TODO: Info 1: startButton again because the feet of hanging man don't shown in last round. Need other solution!
					if (errorCounter == initHangmanSize || !wording.getHiddenWord().contains("_")) {
						clickButton();
					}
					// If user didn't make any input in inputField!
				} else {
					hiddenWordLabel.setText("Bitte geben Sie eine g??ltige Eingabe ein!");
				}
			}
		});
		startButton.setBounds(337, 180, 113, 23);
		panel.add(startButton);

		JLabel lblNewLabel = new JLabel("Gewinnz??hler:");
		lblNewLabel.setBounds(348, 254, 95, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Verlustz??hler:");
		lblNewLabel_1.setBounds(348, 356, 95, 14);
		panel.add(lblNewLabel_1);
	}

	/** 
	 * returns the gameBoard as JPanel for the MainWindow frame
	 * @return this GameBoard's panel.
	 */
	public JPanel getPanel() {
		return this.panel;
	}
	
	/** 
	 * Prints the hanging man on the gallows with gameBoards errorCounter Integer
	 * @param hangman Hangman
	 */
	public void printHangman(Hangman hangman) {
		errorCounter++;
		gallows.setText("");
		String left = "";
		String right = "\n";
		hangman.updateHangman(errorCounter);
		for (int i = hangman.getHangman().size(); i > 0; i--) {
			left += hangman.getHangman().get(i - 1).get(0) + "\n";
			if (hangman.getHangman().get(i - 1).size() > 1) {
				right += hangman.getHangman().get(i - 1).get(1) + "\n";
			}
		}
		gallows.setText(left);
		hangingMan.setText(right);
		guessingPlayerToChange++;
	}
	
	/** 
	 * This Button is only for the last hanging man error to startButton again
	 * and get the feet of the hanging man! 
	 */
	public void clickButton() {
		new Thread(new Runnable() {
			public void run() {
				startButton.doClick();
			}
		}).start();
	}
	
	/** 
	 * Testing Method 
	 */
	public List<Player> getTestingPlayer() {
		return this.players;
	}
	
	/** 
	 * Testing Method 
	 */
	public void setErrorCounterForTest(int errorCounter) {
		this.errorCounter = errorCounter;
	}
	
	/** 
	 * Testing Method 
	 */
	public JButton getStartButton() {
		return this.startButton;
	}
	
	/** 
	 * Testing Method 
	 */
	public void setWording(Wording wording) {
		this.wording = wording;
	}
	
	/** 
	 * Testing Method 
	 */
	public void setSolutionField(String input) {
		this.solutionField.setText(input);
	}
	
	/** 
	 * Testing Method 
	 */
	public JTextArea getHangmanLabelLeft() {
		return this.gallows;
	}
	
	/** 
	 * Testing Method 
	 */
	public JTextArea getHangmanLabelRight() {
		return this.hangingMan;
	}
	
	
}
