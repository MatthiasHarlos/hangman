package de.newenergycoes.hangman.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import de.newenergycoes.hangman.domainData.Player;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.ImageIcon;

public class ChangePlayerPopUp {

	private JDialog dialog;
	private JTextField inputField;

	
	public ChangePlayerPopUp(JFrame frame, List<Player> players) {
		dialog = new JDialog(frame);

		dialog.setBounds(500, 300, 630, 315);
        
        JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new MigLayout("", "[556px,grow]", "[20px][][grow][][grow][grow][][][grow]"));
		
		JLabel lblNewLabel = new JLabel("Hinzufügen:");
		panel.add(lblNewLabel, "flowx,cell 0 0,grow");
		
		inputField = new JTextField();
		panel.add(inputField, "cell 0 0,growx,aligny top");
		inputField.setColumns(10);
		
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.WEST);
        dialog.getContentPane().setLayout(new BorderLayout(0, 0));

        dialog.getContentPane().add(panel);
        
        String colString = players.stream().map(player -> player.getPlayerName()).collect(Collectors.joining(","));
		String col[] = colString.split(",");
		String colStringWinning = players.stream().map(player -> player.getWinningScore() + "")
				.collect(Collectors.joining(","));
		String colScore[] = colStringWinning.split(",");
		String data[][] = { col, colScore };

        DefaultListModel listModel = new DefaultListModel();
        for (int i = 0; i < players.size(); i++) {
        	listModel.add(i, players.get(i));
        }
        JList list = new JList(listModel);
        panel.add(list, "cell 0 3,grow");
        list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				System.out.println(list.getSelectedValue());
				System.out.println(list.getSelectedIndex());
				
			}
        });
        
        JButton btnNewButton = new JButton("");
        btnNewButton.setIcon(new ImageIcon(ChangePlayerPopUp.class.getResource("/org/eclipse/jface/text/source/projection/images/expanded.png")));
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		listModel.remove(list.getSelectedIndex());
        	}
        });
        panel.add(btnNewButton, "cell 0 4");
        
        JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(ChangePlayerPopUp.class.getResource("/org/eclipse/jface/text/source/projection/images/collapsed.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String alreadyExistPlayer = "";
				if ( players.stream().filter(ply -> ply.getPlayerName().equals(inputField.getText())).map(ply -> ply.getPlayerName()).findFirst().isPresent()) {
					alreadyExistPlayer = players.stream().filter(ply -> ply.getPlayerName().equals(inputField.getText())).map(ply -> ply.getPlayerName()).findFirst().get();
				}
				if (!inputField.getText().trim().equals("") && alreadyExistPlayer == "") {
					Player player = new Player(inputField.getText());
					players.add(player);
					listModel.add(players.size()-1, player);
					
				}
			}
		});
		panel.add(btnNewButton_1, "cell 0 0");
		
        
        JPanel panel_2 = new JPanel();
        panel.add(panel_2, "cell 0 8,grow");
        
                
                JButton jButton = new JButton("Fertig!");
                panel_2.add(jButton);
                jButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	dialog.setVisible(false);
                    }
                });
        dialog.setVisible(true);
		
		
	}
	
	public JDialog getDialog() {
		return this.dialog;
	}
}
