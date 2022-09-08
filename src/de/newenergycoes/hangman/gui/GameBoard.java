package de.newenergycoes.hangman.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import de.newenergycoes.hangman.MainWindow;
import de.newenergycoes.hangman.domainData.Hangman;
import de.newenergycoes.hangman.domainData.Player;
import de.newenergycoes.hangman.domainData.Wording;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class GameBoard {

	private JPanel panel = new JPanel();
	private int errorCounter = 0;
	private List<Player> players = new ArrayList();
	private List<Player> guessingPlayers;
	protected int playerToChange;
	protected int guessingPlayerToChange = 0;
	private Player wordGiver;
	private MyTable looserTable;
	private MyTable winnerTable;
	private JTextArea hangmanLabel;
	private JTextArea hangmanLabel_right;
	private JButton startButton;
	private Wording wording;
	private TextField solutionField;

	public GameBoard(MainWindow mainWindow, List<Player> players, int playerToChange, boolean isInitialGame, boolean allAgainstAll, Header header) {
		this.players = players;
		this.playerToChange = playerToChange;
		if (isInitialGame) {
			this.players.stream().forEach(player -> players.stream().filter(playerTwo -> !playerTwo.equals(player))
					.forEach(playerTwo -> player.getOtherPlayer().add(playerTwo)));
		}
		if (players.size() - 1 == playerToChange) {
			this.playerToChange = 0;
		}
		wordGiver = players.get(playerToChange);
		guessingPlayers = new ArrayList(players);
		guessingPlayers.remove(wordGiver);
		this.panel.setBounds(10, 11, 787, 516);
		panel.setLayout(null);
		Label mainLabel = new Label(wordGiver.getPlayerName() + " bitte geben Sie ein Wort ein!");
		mainLabel.setAlignment(Label.CENTER);
		mainLabel.setBounds(268, 90, 250, 22);
		panel.add(mainLabel);
		this.playerToChange++;
		int newPlayer = this.playerToChange;

		solutionField = new TextField();
		solutionField.setForeground(new Color(255, 255, 255));
		solutionField.setBounds(292, 136, 203, 22);
		panel.add(solutionField);

		Label hiddenWordLabel = new Label("");
		hiddenWordLabel.setAlignment(Label.CENTER);
		hiddenWordLabel.setBounds(213, 226, 361, 22);
		panel.add(hiddenWordLabel);

		startButton = new JButton("Eingabe");
		startButton.setBackground(new Color(153, 204, 102));
		startButton.setForeground(new Color(0, 0, 102));

		JRootPane rootPane = mainWindow.getFrame().getRootPane();
		rootPane.setDefaultButton(startButton);
		
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
		
		hangmanLabel = new JTextArea("");
		hangmanLabel.setForeground(new Color(255, 0, 0));
		hangmanLabel.setEnabled(true);
		hangmanLabel.setLineWrap(false);
		hangmanLabel.setEditable(false);
		hangmanLabel.setOpaque(false);
		hangmanLabel.setBounds(602, 136, 62, 119);
		panel.add(hangmanLabel);

		hangmanLabel_right = new JTextArea("");
		hangmanLabel_right.setForeground(new Color(255, 0, 0));
		hangmanLabel_right.setLineWrap(false);
		hangmanLabel_right.setEnabled(true);
		hangmanLabel_right.setEditable(false);
		hangmanLabel_right.setOpaque(false);
		hangmanLabel_right.setBounds(663, 136, 57, 119);
		panel.add(hangmanLabel_right);

		Hangman hangman = new Hangman();
		int initHangmanSize = hangman.getInitialHangmanListSize();
		wording = new Wording(null);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println(wording.getSolutionWord());
				System.out.println(wording.getHiddenWord());
				
				solutionField.setText(solutionField.getText().trim());

				if (errorCounter == initHangmanSize
						|| wording.getHiddenWord() != null && !wording.getHiddenWord().contains("_")) {
					//TODO: Info 1: need other solution to show the last feet of hangman!
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					GameBoard gameBoard = new GameBoard(mainWindow, players, newPlayer, false, allAgainstAll, header);
					mainWindow.getNewGameBoard(panel, gameBoard);
				}
				if (!solutionField.getText().trim().equals("")) {
					if (wording.getSolutionWord() == null) {
						solutionField.setForeground(new Color(0, 0, 0));
						wording.setHiddenWord(solutionField.getText());
					} else if (errorCounter < initHangmanSize) {
						if (wording.getSolutionWord().contains(solutionField.getText().charAt(0) + "")) {
							wording.getNewWordingResult(solutionField.getText().charAt(0), guessingPlayers.get(guessingPlayerToChange));
							guessingPlayerToChange++;
						} else {
							printHangman(hangman);
						}
					}
					hiddenWordLabel.setText(wording.getHiddenWord());
					if (guessingPlayerToChange == guessingPlayers.size()) {
						guessingPlayerToChange = 0;
					}
					mainLabel.setText(guessingPlayers.get(guessingPlayerToChange).getPlayerName() + " geben Sie einen Buchstaben ein!");
					solutionField.setText("");

					if (errorCounter == initHangmanSize) {
						wordGiver.setWinningScore();
						mainLabel.setText("Der Wortgeber " + wordGiver.getPlayerName() + " gewinnt!");
					} else if (!wording.getHiddenWord().contains("_")) {
						if (allAgainstAll == false) {
							wordGiver.setLoosingScore();
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
					if (errorCounter == initHangmanSize || !wording.getHiddenWord().contains("_")) {
						clickButton();
					}
				} else {
					hiddenWordLabel.setText("Bitte geben Sie eine gültige Eingabe ein!");
				}
			}
		});
		startButton.setBounds(337, 180, 113, 23);
		panel.add(startButton);

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
		
		JLabel lblNewLabel = new JLabel("Gewinnzähler:");
		lblNewLabel.setBounds(348, 254, 95, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Verlustzähler:");
		lblNewLabel_1.setBounds(348, 356, 95, 14);
		panel.add(lblNewLabel_1);
	}

	public JPanel getPanel() {
		return this.panel;
	}
	
	public List<Player> getTestingPlayer() {
		return this.players;
	}
	
	public void setErrorCounterForTest(int errorCounter) {
		this.errorCounter = errorCounter;
	}
	
	public void clickButton() {
		new Thread(new Runnable() {
			public void run() {
				//TODO: Info 1: startButton again because the feet don't shown in last round. Need other solution!
				startButton.doClick();
			}
		}).start();
	}
	
	public JButton getStartButton() {
		return this.startButton;
	}
	
	public void setWording(Wording wording) {
		this.wording = wording;
	}
	
	public void setSolutionField(String input) {
		this.solutionField.setText(input);
	}
	
	public void printHangman(Hangman hangman) {
		errorCounter++;
		hangmanLabel.setText("");
		String left = "";
		String right = "\n";
		hangman.updateHangman(errorCounter);
		for (int i = hangman.getHangman().size(); i > 0; i--) {
			left += hangman.getHangman().get(i - 1).get(0) + "\n";
			if (hangman.getHangman().get(i - 1).size() > 1) {
				right += hangman.getHangman().get(i - 1).get(1) + "\n";
			}
		}
		hangmanLabel.setText(left);
		hangmanLabel_right.setText(right);
		guessingPlayerToChange++;
	}
	
	public JTextArea getHangmanLabelLeft() {
		return this.hangmanLabel;
	}
	
	public JTextArea getHangmanLabelRight() {
		return this.hangmanLabel_right;
	}
	
	
}
