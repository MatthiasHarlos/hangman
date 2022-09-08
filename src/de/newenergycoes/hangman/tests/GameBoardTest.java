package de.newenergycoes.hangman.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import de.newenergycoes.hangman.MainWindow;
import de.newenergycoes.hangman.domainData.Hangman;
import de.newenergycoes.hangman.domainData.Player;
import de.newenergycoes.hangman.domainData.Wording;
import de.newenergycoes.hangman.gui.GameBoard;
import org.junit.Assert;


class GameBoardTest {

	private static GameBoard gameBoard;
	private static List<Player> players;
	private static MainWindow mainWindow;


	@BeforeEach
	void setUp() throws Exception {
		players = new ArrayList<>(
				List.of(new Player("Martin"), new Player("Matthi"), new Player("Peter"), new Player("Patrick")));
		mainWindow = new MainWindow();
		boolean allAgainstAll = false;
		gameBoard = new GameBoard(mainWindow, players, 0, true, allAgainstAll, mainWindow.getInitHeader());
		mainWindow.getNewGameBoard(mainWindow.getInitialStartpage().getPanel(), gameBoard);
		players = gameBoard.getTestingPlayer();
	}

	@Test
	void testPlayerSize() {
		Assert.assertEquals(4, players.size());
	}

	@Test
	void testWinnerOfAllAgainstAll1() {
		// PlayerOne wordGiver, otherPlayer are all same guessingpoints and are the
		// winner! AllAgainstAll
		for (int y = 1; y < players.size(); y++) {
			for (int i = 0; i < 2; i++) {
				players.get(y).setGuessingPoints();
			}
		}
		List<Player> winner = players.get(0).getWinnerWithGuessingPoints();
		Assert.assertEquals(3, winner.size());
		Assert.assertEquals(2, winner.get(0).getGuessingPoints());
		Assert.assertEquals("Matthi", winner.get(0).getPlayerName());
		Assert.assertEquals("Peter", winner.get(1).getPlayerName());
		Assert.assertEquals("Patrick", winner.get(2).getPlayerName());
		
	}

	@Test
	void testWinnerOfAllAgainstAll2() {
		// PlayerOne wordGiver, secondPlayer Matthi has most points and is the winner!
		// AllAgainstAll
		for (int i = 0; i < 2; i++) {
			players.get(1).setGuessingPoints();
		}
		players.get(2).setGuessingPoints();
		players.get(3).setGuessingPoints();
		Assert.assertEquals(1, players.get(0).getWinnerWithGuessingPoints().size());
		Assert.assertEquals("Matthi", players.get(0).getWinnerWithGuessingPoints().get(0).getPlayerName());
	}


	@Test
	void testWinnerOfOneAgainstAll() {
		// The WordGiver wins the round because errorCounter == initHangmanSize!
		Wording wording = new Wording("hallo");
		wording.setHiddenWord("hallo");
		Hangman hangman = new Hangman();
		int initHangmanSize = 0;
		gameBoard.setErrorCounterForTest(initHangmanSize);
		gameBoard.setWording(wording);
		for (int i = 0; i< 10; i++) {
			gameBoard.setSolutionField("t");
			gameBoard.getStartButton().doClick();
		}
		players = gameBoard.getTestingPlayer();
		Assert.assertEquals(1, players.get(0).getWinningScore());
		Assert.assertEquals(0, players.get(1).getWinningScore());
		Assert.assertEquals(0, players.get(2).getWinningScore());
		Assert.assertEquals(0, players.get(3).getWinningScore());
		Assert.assertEquals(0, players.get(0).getLoosingScore());
		Assert.assertEquals(1, players.get(1).getLoosingScore());
		Assert.assertEquals(1, players.get(2).getLoosingScore());
		Assert.assertEquals(1, players.get(3).getLoosingScore());
	}
	
	@Test
	void testWinnerOfOneAgainstAllTwo() {
		// All other player Wins the Game because they guessed the hiddenWord! Wording guesses Right tested!
		Wording wording = new Wording("hallo");
		wording.setHiddenWord("hallo");
		Hangman hangman = new Hangman();
		int initHangmanSize = 0;
		gameBoard.setErrorCounterForTest(initHangmanSize);
		gameBoard.setWording(wording);
		for (char solution : "hallo".toCharArray()) {
			gameBoard.setSolutionField(solution+"");
			gameBoard.getStartButton().doClick();
		}
		players = gameBoard.getTestingPlayer();
		Assert.assertEquals(0, players.get(0).getWinningScore());
		Assert.assertEquals(1, players.get(1).getWinningScore());
		Assert.assertEquals(1, players.get(2).getWinningScore());
		Assert.assertEquals(1, players.get(3).getWinningScore());
		Assert.assertEquals(1, players.get(0).getLoosingScore());
		Assert.assertEquals(0, players.get(1).getLoosingScore());
		Assert.assertEquals(0, players.get(2).getLoosingScore());
		Assert.assertEquals(0, players.get(3).getLoosingScore());
	}


}
