package com.ggl.baduk.animation.view;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.ggl.baduk.animation.controller.AnimationListener;
import com.ggl.baduk.animation.model.AppFonts;
import com.ggl.baduk.animation.model.BadukAnimationModel;

public class AnimationPanel {

	private final BadukAnimationFrame view;

	private final BadukAnimationModel model;

	private JButton startAnimationButton;

	private final JPanel panel;

	public AnimationPanel(BadukAnimationFrame view, BadukAnimationModel model) {
		this.view = view;
		this.model = model;
		this.panel = createAnimationPanel();
	}

	private JPanel createAnimationPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		Font labelFont = AppFonts.getLabelFont();

//		JButton gameStartButton = new JButton("Game Start");
//		gameStartButton.setFont(labelFont);
//		panel.add(gameStartButton);

//		JButton previousMoveButton = new JButton("Previous Move");
//		previousMoveButton.setFont(labelFont);
//		panel.add(previousMoveButton);

		startAnimationButton = new JButton("Pause Animation");
		startAnimationButton.addActionListener(new AnimationListener(view, model));
		startAnimationButton.setFont(labelFont);
		panel.add(startAnimationButton);

//		JButton nextMoveButton = new JButton("Next Move");
//		nextMoveButton.setFont(labelFont);
//		panel.add(nextMoveButton);

//		JButton gameEndButton = new JButton("Game End");
//		gameEndButton.setFont(labelFont);
//		panel.add(gameEndButton);
		
//		Dimension d = gameEndButton.getPreferredSize();
//		d.width = getMaximumWidth(gameStartButton, previousMoveButton,
//				startAnimationButton, nextMoveButton, gameEndButton);
//		gameStartButton.setPreferredSize(d);
//		previousMoveButton.setPreferredSize(d);
//		startAnimationButton.setPreferredSize(d);
		startAnimationButton.setText("Start Animation");
//		nextMoveButton.setPreferredSize(d);
//		gameEndButton.setPreferredSize(d);

		return panel;
	}

//	private int getMaximumWidth(JButton... buttons) {
//		int maximumWidth = 0;
//		for (JButton button : buttons) {
//			maximumWidth = Math.max(maximumWidth,
//					button.getPreferredSize().width);
//		}
//		return maximumWidth + 5;
//	}

	public JPanel getPanel() {
		return panel;
	}

}
