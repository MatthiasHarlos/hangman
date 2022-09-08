package de.newenergycoes.hangman.domainData;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private String playerName;
	private int winningScore;
	private int loosingScore;
	private List<Player> otherPlayer = new ArrayList<>();
	private int guessingPoints; 
	
	public Player(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return playerName;
	}
	
	
	public int getWinningScore() {
		return winningScore;
	}
	
	public void setWinningScore() {
		this.winningScore++;
		this.otherPlayer.stream().forEach(player -> player.setOtherLoosingScore());
	}
	
	public void setOhterWinningScore() {
		this.winningScore++;
	}
	
	public void setLoosingScore() {
		this.loosingScore++;
		this.otherPlayer.stream().forEach(player -> player.setOhterWinningScore());
	}
	
	public int getLoosingScore() {
		return loosingScore;
	}
	
	public void setOtherLoosingScore() {
		this.loosingScore++;
	}
	
	public List<Player> getOtherPlayer() {
		return this.otherPlayer;
	}
	
	public void setGuessingPoints() {
		guessingPoints++;
	}
	
	public int getGuessingPoints() {
		return guessingPoints;
	}
	
	public List<Player> getWinnerWithGuessingPoints() {
		int result = this.getHighestGuessingPointsOfAll();
		List<Player> playerList = new ArrayList<>(this.getOtherPlayer());
		playerList.add(this);
		return playerList.stream().filter(winner -> winner.getGuessingPoints() == result).toList();
	}
	
	public int getHighestGuessingPointsOfAll() {
		Player result = this.getOtherPlayer().get(0);
		for (Player player : this.getOtherPlayer()) {
			if (player.getGuessingPoints() > result.getGuessingPoints() && player.getGuessingPoints() > this.getGuessingPoints()) {
				result = player;
				//return result.getGuessingPoints();
			} else if (this.getGuessingPoints() > player.getGuessingPoints() && this.getGuessingPoints() > result.getGuessingPoints()) {
				result = this;
			}
		}
		
		//return this.getGuessingPoints();
		return result.getGuessingPoints();
	}
	
	
	public void resetGuessingPointsForRound() {
		this.guessingPoints = 0;
		this.otherPlayer.stream().forEach(player -> player.guessingPoints = 0);
	}
	
	@Override
	public String toString() {
		return playerName + ": " + "ratePunkte: " + guessingPoints + " RundenPunkte: " + winningScore;
	}
	
	
}
