package de.newenergycoes.hangman.tests;

import java.util.List;
import java.awt.Choice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import de.newenergycoes.hangman.MainWindow;
import de.newenergycoes.hangman.domainData.Player;
import de.newenergycoes.hangman.gui.Startpage;

import org.junit.Assert;

class StartpageTest {

	private static Startpage startpage;
	private static MainWindow mainWindow;
	private static List<Player> players;

	@Test
	@BeforeEach
	void createPlayer() throws Exception {
		mainWindow = new MainWindow();
		startpage = new Startpage(mainWindow, mainWindow.getInitHeader());
		mainWindow.getStartpage(startpage.getPanel());

		startpage.setInputForNewUserName("Martin");
		startpage.getButton("anlegen").doClick();
		Assert.assertEquals("Martin", startpage.getPlayersForTest().get(0).getPlayerName());
		Assert.assertEquals(1, startpage.getPlayersForTest().size());

		startpage.setInputForNewUserName("Peter");
		startpage.getButton("anlegen").doClick();
		Assert.assertEquals("Peter", startpage.getPlayersForTest().get(1).getPlayerName());
		Assert.assertEquals(2, startpage.getPlayersForTest().size());

		startpage.setInputForNewUserName("Patrick");
		startpage.getButton("anlegen").doClick();
		Assert.assertEquals("Patrick", startpage.getPlayersForTest().get(2).getPlayerName());
		Assert.assertEquals(3, startpage.getPlayersForTest().size());

		startpage.setInputForNewUserName("Kilian");
		startpage.getButton("anlegen").doClick();
		Assert.assertEquals("Kilian", startpage.getPlayersForTest().get(3).getPlayerName());
		Assert.assertEquals(4, startpage.getPlayersForTest().size());

		players = startpage.getPlayersForTest();

	}

	@Test
	void deletePlayer() {
		// Remove Martin
		startpage.getButton("löschen").doClick();
		Assert.assertEquals(3, startpage.getPlayersForTest().size());
		Assert.assertEquals(players, startpage.getPlayersForTest());
		Assert.assertEquals("Peter", startpage.getPlayersForTest().get(0).getPlayerName());
		Assert.assertEquals("Patrick", startpage.getPlayersForTest().get(1).getPlayerName());
		Assert.assertEquals("Kilian", startpage.getPlayersForTest().get(2).getPlayerName());

		// Remove Kilian
		startpage.getChoiceTesting().select(2);
		startpage.getButton("löschen").doClick();
		Assert.assertEquals(2, startpage.getPlayersForTest().size());
		Assert.assertEquals(players, startpage.getPlayersForTest());
		Assert.assertEquals("Peter", startpage.getPlayersForTest().get(0).getPlayerName());
		Assert.assertEquals("Patrick", startpage.getPlayersForTest().get(1).getPlayerName());

		// Remove Patrick
		startpage.getChoiceTesting().select(1);
		startpage.getButton("löschen").doClick();
		Assert.assertEquals(1, startpage.getPlayersForTest().size());
		Assert.assertEquals(players, startpage.getPlayersForTest());
		Assert.assertEquals("Peter", startpage.getPlayersForTest().get(0).getPlayerName());

		// Remove Peter
		startpage.getButton("löschen").doClick();
		Assert.assertEquals(0, startpage.getPlayersForTest().size());
		Assert.assertEquals(players, startpage.getPlayersForTest());

	}

}
