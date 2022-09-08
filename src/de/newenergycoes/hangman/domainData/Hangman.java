package de.newenergycoes.hangman.domainData;

import java.util.ArrayList;
import java.util.List;

public class Hangman {
	
	private List<String> hangmanListLeft = new ArrayList<>(
			List.of("____|_____", "         |", "         |", "         |", "         |/", "         _____"));
	private List<String> hangmanListRight = new ArrayList<>(List.of("  |", " O", " `|´", "  ^"));
	private List<List<String>> hangman = new ArrayList<>();
	
	
	public List<List<String>> getHangman() {
		return hangman;
	}
	
	public int getInitialHangmanListSize() {
		return this.hangmanListLeft.size() + this.hangmanListRight.size();
	}
	
	public void updateHangman(int errorCounter) {
		if (errorCounter <= this.hangmanListLeft.size()) {
			List<String> part = new ArrayList<>();
			part.add(this.hangmanListLeft.get(errorCounter - 1));
			this.hangman.add(part);
		} else {
			this.hangman.get(this.hangmanListRight.size()).add(this.hangmanListRight.get(0));
			this.hangmanListRight.remove(0);
		}
	}
}
