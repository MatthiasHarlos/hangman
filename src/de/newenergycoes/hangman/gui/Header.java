package de.newenergycoes.hangman.gui;

import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

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
