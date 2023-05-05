package com.ggl.baduk.animation.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ggl.baduk.animation.model.AppFonts;
import com.ggl.baduk.animation.model.BadukAnimationModel;
import com.ggl.baduk.animation.parser.model.GameRecord;
import com.ggl.baduk.animation.parser.model.Player;

public class SummaryPanel {

//	private final BadukAnimationFrame view;

	private final BadukAnimationModel model;

	private final JPanel panel;

	private JTextField titleField, serverField, blackPlayerField;
	private JTextField whitePlayerField, resultField, datePlayedField;
	private JTextField rulesetField, timeControlField, komiField;
	private JTextField eventField, handicapField, japaneseDateField;
	private JTextField timerField;

	public SummaryPanel(BadukAnimationFrame view, BadukAnimationModel model) {
//		this.view = view;
		this.model = model;
		this.panel = createSummaryPanel();
	}

	private JPanel createSummaryPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		Font labelFont = AppFonts.getLabelFont();
		Font textFont = AppFonts.getTextFont();
		int textFieldLength = 30;

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 5, 5, 5);

		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel label = new JLabel("Game Summary");
		label.setFont(AppFonts.getTitleFont());
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label, gbc);

		gbc.gridwidth = 1;

		titleField = addLabelTextField(panel, gbc, "Game Name:", labelFont,
				textFont, textFieldLength);
		serverField = addLabelTextField(panel, gbc, "Server:", labelFont,
				textFont, textFieldLength);
		eventField = addLabelTextField(panel, gbc, "Event:", labelFont,
				textFont, textFieldLength);
		datePlayedField = addLabelTextField(panel, gbc, "Date Played:",
				labelFont, textFont, textFieldLength);
		japaneseDateField = addLabelTextField(panel, gbc, "Japanese Date:",
				labelFont, textFont, textFieldLength);
		blackPlayerField = addLabelTextField(panel, gbc, "Black Player:",
				labelFont, textFont, textFieldLength);
		whitePlayerField = addLabelTextField(panel, gbc, "White Player:",
				labelFont, textFont, textFieldLength);
		rulesetField = addLabelTextField(panel, gbc, "Rule Set:", labelFont,
				textFont, textFieldLength);
		handicapField = addLabelTextField(panel, gbc, "Handicap:", labelFont,
				textFont, textFieldLength);
		komiField = addLabelTextField(panel, gbc, "Komi:", labelFont, textFont,
				textFieldLength);
		timeControlField = addLabelTextField(panel, gbc, "Time Control:",
				labelFont, textFont, textFieldLength);
		resultField = addLabelTextField(panel, gbc, "Result:", labelFont,
				textFont, textFieldLength);
		
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(30, 5, 5, 5);
		label = new JLabel("Animation Parameters");
		label.setFont(AppFonts.getTitleFont());
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.insets = new Insets(0, 5, 5, 5);
		
		timerField = addLabelTextField(panel, gbc, "Time Between Moves:", labelFont,
				textFont, textFieldLength);
		timerField.setEditable(true);
		timerField.setText(Integer.toString(model.getMoveTime()));
		
		return panel;
	}

	public void updateSummaryPanel() {
		GameRecord gameRecord = model.getGameRecord();
		if (gameRecord != null && gameRecord.isValidBaduckSGFFile()) {
			titleField.setText(gameRecord.getGameName());
			serverField.setText(gameRecord.getPlace());
			eventField.setText(gameRecord.getEvent());
			datePlayedField.setText(gameRecord.getFormattedGameDate());
			japaneseDateField.setText(gameRecord.getJapaneseDate());
			rulesetField.setText(gameRecord.getRuleset());
			komiField.setText(gameRecord.getKomi());
			handicapField.setText(Integer.toString(gameRecord.getHandicap()));
			timeControlField.setText(gameRecord.getTimeControl());
			resultField.setText(gameRecord.getResult());

			for (Player player : gameRecord.getPlayers()) {
				String output = player.getName() + " - " + player.getRank();
				if (player.getColor().equals("B")) {
					blackPlayerField.setText(output);
				} else {
					whitePlayerField.setText(output);
				}
			}
		}
	}

	private JTextField addLabelTextField(JPanel panel, GridBagConstraints gbc,
			String labelString, Font labelFont, Font textFont,
			int textFieldLength) {
		gbc.gridx = 0;
		gbc.gridy++;
		JLabel label = new JLabel(labelString);
		label.setFont(labelFont);
		panel.add(label, gbc);

		gbc.gridx++;
		JTextField textField = new JTextField(textFieldLength);
		textField.setEditable(false);
		textField.setFont(textFont);
		panel.add(textField, gbc);

		return textField;
	}
	
	public String getTimerField() {
		return timerField.getText().trim();
	}

	public JPanel getPanel() {
		return panel;
	}

}
