package de.newenergycoes.hangman;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.newenergycoes.hangman.gui.GameBoard;
import de.newenergycoes.hangman.gui.Header;
import de.newenergycoes.hangman.gui.Startpage;

public class MainWindow {

	private JFrame frame;
	private Startpage startpage;
	private Header header;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainWindow() {
		initialize();
	}

	private void initialize() {	
		frame = new JFrame("Hangman");
		frame.setBounds(100, 100, 787, 516);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		header = new Header(frame);
		startpage = new Startpage(this, header);
		frame.getContentPane().add(header.getPanel());
		frame.getContentPane().add(startpage.getPanel());
		frame.getContentPane().setLayout(null);
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
	
	public void getNewGameBoard(JPanel panel, GameBoard gameBoard) {
		frame.getContentPane().remove(panel);
		frame.getContentPane().add(gameBoard.getPanel());
		frame.repaint();
	}
	
	public void getStartpage(JPanel panel) {
		Header newHeader = new Header(frame);
		Startpage startpage = new Startpage(this, newHeader);
		panel.removeAll();
		frame.getContentPane().removeAll();
		frame.getContentPane().add(newHeader.getPanel());
		frame.getContentPane().remove(panel);
		frame.repaint();
		frame.getContentPane().add(startpage.getPanel());
	}
	
	public Startpage getInitialStartpage() {
		return this.startpage;
	}
	
	public Header getInitHeader() {
		return this.header;
	}
	
}
