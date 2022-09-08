package de.newenergycoes.hangman.tests;

import java.util.ArrayList;
import java.util.List;
import java.awt.Choice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import de.newenergycoes.hangman.MainWindow;
import de.newenergycoes.hangman.domainData.Hangman;
import de.newenergycoes.hangman.domainData.Player;
import de.newenergycoes.hangman.gui.GameBoard;
import de.newenergycoes.hangman.gui.Startpage;

import org.junit.Assert;

class HangmanTest {


	private Hangman hangman;
	private List<Player> players;
	private MainWindow mainWindow;
	private GameBoard gameBoard;

	@Test
	@BeforeEach
	void setUp() throws Exception {
		players = new ArrayList<>(
				List.of(new Player("Martin"), new Player("Matthi"), new Player("Peter"), new Player("Patrick")));
		mainWindow = new MainWindow();
		boolean allAgainstAll = false;
		gameBoard = new GameBoard(mainWindow, players, 0, true, allAgainstAll, mainWindow.getInitHeader());
		mainWindow.getNewGameBoard(mainWindow.getInitialStartpage().getPanel(), gameBoard);
		players = gameBoard.getTestingPlayer();
		hangman = new Hangman();
		Assert.assertEquals(0, hangman.getHangman().size());
	}

	@Test
	void buildHangman() {
		for (int errorCounter = 1; errorCounter <= 10; errorCounter++) {
			gameBoard.printHangman(hangman);
		}
		Assert.assertEquals("         _____\n         |/\n         |\n         |\n         |\n____|_____\n", 
				gameBoard.getHangmanLabelLeft().getText());
		System.out.println(gameBoard.getHangmanLabelLeft().getText());
		Assert.assertEquals("\n"
				+ "  |\n"
				+ " O\n"
				+ " `|´\n"
				+ "  ^\n", 
				gameBoard.getHangmanLabelRight().getText());
		System.out.println(gameBoard.getHangmanLabelRight().getText());
	}

}
