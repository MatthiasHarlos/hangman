package de.newenergycoes.hangman;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.newenergycoes.hangman.domainData.Player;
import de.newenergycoes.hangman.gui.ChangePlayerPopUp;
import de.newenergycoes.hangman.gui.GameBoard;
import de.newenergycoes.hangman.gui.Header;
import de.newenergycoes.hangman.gui.Startpage;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MainWindow {

	private JFrame frame;
	private Startpage startpage;
	private Header header;
	private List<Player> players;
	private int newPlayer;
	private JPanel panelBefore;

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
		frame.setBounds(100, 100, 787, 612);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		header = new Header(frame);
		startpage = new Startpage(this, header);
		frame.getContentPane().add(header.getPanel());
		frame.getContentPane().add(startpage.getPanel());
		frame.getContentPane().setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Einstellungen");
		menuBar.add(mnNewMenu);

		JRadioButtonMenuItem oneAgainstAll = new JRadioButtonMenuItem("Alle gegen Wortgeber");
		mnNewMenu.add(oneAgainstAll);
		//oneAgainstAll.setSelected(true);
		oneAgainstAll.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent it) {
				System.out.println("radio menu item clicked");
				if (players != null && oneAgainstAll.isSelected()) {
					System.out.println("oneAgainstAll");
					getNewGameBoardTestTwo(panelBefore, false);
				}
			}
		});
		
		JRadioButtonMenuItem allAgainstAll = new JRadioButtonMenuItem("Jeder gegen Jeden");
		mnNewMenu.add(allAgainstAll);
		allAgainstAll.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent it) {
				System.out.println("radio menu item clicked");
				if (players != null && allAgainstAll.isSelected()) {
					System.out.println("AllAgainstAll");
					getNewGameBoardTestTwo(panelBefore, true);
				}
			}
		});


		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(oneAgainstAll);
		buttonGroup.add(allAgainstAll);
		
		JMenuItem changePlayer = new JMenuItem("Spieler bearbeiten");
		mnNewMenu.add(changePlayer);
		changePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangePlayerPopUp changePlayerPopUp = new ChangePlayerPopUp(frame, players);
				
				 
			}
		});
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public void setActuallyPanelData(List<Player> players, int newPlayer, JPanel panelBefore) {
		this.players = players;
		this.newPlayer = newPlayer;
		this.panelBefore = panelBefore;
	}
	
	public void getNewGameBoardTestTwo(JPanel panel, boolean allAgainstAll) {
		Header newHeader = new Header(frame);
		GameBoard gameBoard = new GameBoard(this, this.players, this.newPlayer, false, allAgainstAll, newHeader);
		panel.removeAll();
		frame.getContentPane().removeAll();
		frame.getContentPane().add(newHeader.getPanel());
		frame.getContentPane().remove(panel);
		frame.repaint();
		frame.getContentPane().add(gameBoard.getPanel());
	}

	public void getNewGameBoard(JPanel panel, GameBoard gameBoard) {
		panel.removeAll();
		frame.getContentPane().remove(panel);
		frame.repaint();
		frame.getContentPane().add(gameBoard.getPanel());
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
