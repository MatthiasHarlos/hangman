package de.newenergycoes.hangman.gui;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;

import javax.swing.border.LineBorder;

/**
 * Header of hangman with a hangman logo and title!
 * 
 * @author matthias.harlos
 */

public class Header {

	private JPanel panel = new JPanel();

	public Header(JFrame frame) {
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel.setBackground(new Color(255, 255, 204));
		panel.setBounds(10, 10, 750, 89);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Header.class.getResource("/de/newenergycoes/hangman/img/hangmanIcon.jpg")));
		lblNewLabel.setBounds(0, 0, 92, 89);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Hanging Man!");
		lblNewLabel_1.setFont(new Font("Vivaldi", Font.BOLD | Font.ITALIC, 24));
		lblNewLabel_1.setBounds(307, 22, 172, 35);
		panel.add(lblNewLabel_1);
	}

	public JPanel getPanel() {
		return this.panel;
	}
}
