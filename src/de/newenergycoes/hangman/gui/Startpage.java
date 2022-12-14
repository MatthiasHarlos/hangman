package de.newenergycoes.hangman.gui;

import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import de.newenergycoes.hangman.MainWindow;
import de.newenergycoes.hangman.domainData.Player;
import javax.swing.JRadioButton;
import java.awt.Choice;

/** 
 * Startpage of hangman!
 * Create player or delete them and start the game!
 * @author matthias.harlos
 */
public class Startpage {

	private JPanel panel = new JPanel();
	private List<Player> players = new ArrayList<>();
	private Choice choice = new Choice();
	private JButton newUserButton;
	private JButton deleteButton;
	private JButton startButton;
	private JTextField inputField;

	/** 
	 * Constructor for Startpage to initialize this
	 * @param mainWindow MainWindow
	 * @param header Header
	 */
	public Startpage(MainWindow mainWindow, Header header) {
		panel.setBounds(10, 10, 787, 516);
		panel.setLayout(null);

		Label label = new Label("Herzlich Willkommen bei Hangman");
		label.setAlignment(Label.CENTER);
		label.setBounds(187, 91, 413, 53);
		label.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 21));
		panel.add(label);
		
		// GameModes
		JRadioButton allAgainstAll = new JRadioButton("Jeder gegen Jeden");
		allAgainstAll.setBounds(455, 150, 156, 23);
		panel.add(allAgainstAll);
		
		JRadioButton oneAgainstAll = new JRadioButton("Alle gegen Einen");
		oneAgainstAll.setSelected(true);
		oneAgainstAll.setBounds(218, 150, 165, 23);
		panel.add(oneAgainstAll);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(allAgainstAll);		
		buttonGroup.add(oneAgainstAll);

		startButton = new JButton("Spiel starten!");
		startButton.setBackground(new Color(153, 204, 153));
		startButton.setForeground(new Color(0, 0, 102));
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (players.size() > 1) {
					GameBoard gameBoard = new GameBoard(mainWindow, players, 0, true, allAgainstAll.isSelected(), header);
					mainWindow.getNewGameBoard(panel, gameBoard);
				} else {
					JFrame jFrame = new JFrame();
					JOptionPane.showMessageDialog(jFrame, "Bitte geben Sie mindestens zwei Spieler ein!");
				}
			}
		});
		startButton.setBounds(603, 256, 123, 22);
		panel.add(startButton);
		JLabel lblNewLabel1 = new JLabel("Geben Sie Namen f??r die Spieler ein:");
		lblNewLabel1.setBounds(59, 218, 214, 14);
		panel.add(lblNewLabel1);

		inputField = new JTextField();
		inputField.setBounds(317, 215, 165, 20);
		panel.add(inputField);
		inputField.setColumns(10);
		
		//Output for created Player
		JLabel allPlayerLabel = new JLabel("");
		allPlayerLabel.setVerticalAlignment(SwingConstants.TOP);
		allPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		allPlayerLabel.setBounds(37, 314, 712, 38);
		panel.add(allPlayerLabel);

		choice.setBounds(597, 382, 129, 93);
		panel.add(choice);

		newUserButton = new JButton("Spieler anlegen");
		newUserButton.setBackground(new Color(153, 204, 102));
		newUserButton.setForeground(new Color(0, 0, 153));
		newUserButton.setBounds(576, 214, 150, 23);
		newUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String alreadyExistPlayer = "";
				if ( players.stream().filter(ply -> ply.getPlayerName().equals(inputField.getText())).map(ply -> ply.getPlayerName()).findFirst().isPresent()) {
					alreadyExistPlayer = players.stream().filter(ply -> ply.getPlayerName().equals(inputField.getText())).map(ply -> ply.getPlayerName()).findFirst().get();
				}
				if (!inputField.getText().trim().equals("") && alreadyExistPlayer == "") {
					allPlayerLabel.setForeground(new Color(255, 153, 0));
					Player player = new Player(inputField.getText());
					choice.add(inputField.getText());
					players.add(player);
					allPlayerLabel.setText(players.stream().map(play -> play.getPlayerName()).collect(Collectors.joining(",  ")));	
				} else {

					String errorInfo = "";
					if(!alreadyExistPlayer.equals("")) {
						errorInfo = "Benutzer existiert bereits: ";
					} else {
						errorInfo = "Geben Sie g??ltige Namen ein! ";
					}
					allPlayerLabel.setText(errorInfo + alreadyExistPlayer);
					allPlayerLabel.setForeground(new Color(255, 0, 0));
				}
				inputField.setText("");
			}
		});
		
		panel.add(newUserButton);
		JRootPane rootPane = mainWindow.getFrame().getRootPane();
		rootPane.setDefaultButton(newUserButton);
				
		deleteButton = new JButton("Spieler l??schen?");
		deleteButton.setBackground(new Color(255, 204, 204));
		deleteButton.setForeground(new Color(153, 51, 51));
		deleteButton.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 9));
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				players.remove(players.stream().filter(player -> player.getPlayerName().equals(choice.getSelectedItem())).findFirst().get());
				choice.remove(choice.getSelectedIndex());
				inputField.setText("");
				allPlayerLabel.setText(players.stream().map(play -> play.getPlayerName()).collect(Collectors.joining(",  ")));
			}
		});
		deleteButton.setBounds(472, 382, 115, 23);
		panel.add(deleteButton);
	}

	/** 
	 * returns the startpage as JPanel for the MainWindow frame
	 * @return this Startpage's panel.
	 */
	public JPanel getPanel() {
		return this.panel;
	}
	
	/** 
	 * returns the Button for Test-Classes
	 * @param buttonType String // "anlegen" "l??schen" else is startButton
	 */
	public JButton getButton(String buttonType) {
		if (buttonType.equals("anlegen")) {
			return this.newUserButton;
		} else if (buttonType.equals("l??schen")) {
			return this.deleteButton;
		} else {
			return this.startButton; 
		}
	}
	
	/** 
	 * Testing Method 
	 */
	public List<Player> getPlayersForTest() {
		return this.players;
	}
	
	/** 
	 * Testing Method 
	 */
	public void setInputForNewUserName(String username) {
		this.inputField.setText(username);
	}
	
	/** 
	 * Testing Method 
	 */
	public Choice getChoiceTesting() {
		return choice;
	}
	
	/** 
	 * Testing Method 
	 */
	public void setCoiceTesting(Choice choice) {
		this.choice = choice;
	}
}
