package de.newenergycoes.hangman.domainData;

public class Wording {
	
	private String solutionWord;
	private String hiddenWord;
	
	public Wording (String solutionWord) {
		this.solutionWord = solutionWord;
	}
	
	public String getSolutionWord() {
		return this.solutionWord;
	}
	
	public String getHiddenWord() {
		return this.hiddenWord;
	}
	
	public String setHiddenWord(String solutionWord) {
		this.solutionWord = solutionWord;
		for (int i = 0; i < solutionWord.length(); i++) {
			int isHidden = (int) Math.round(Math.random() * 5);
			if (isHidden > 0) {
				solutionWord = solutionWord.replace(solutionWord.charAt(i), '_');
			}
		}
		this.hiddenWord = solutionWord;
		return hiddenWord;
	}

	public String getNewWordingResult(char result, Player player) {
		StringBuilder builder = new StringBuilder(this.hiddenWord);
		for (int i = 0; i < this.solutionWord.length(); i++) {
			if (this.solutionWord.charAt(i) == result) {
				builder.setCharAt(i, result);
				player.setGuessingPoints();
			}
		}
		this.hiddenWord = builder.toString();
		return builder.toString();
	}
}
