package com.ggl.baduk.animation.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.Timer;

import com.ggl.baduk.animation.model.BadukAnimationModel;
import com.ggl.baduk.animation.parser.model.GameRecord;
import com.ggl.baduk.animation.view.BadukAnimationFrame;

public class AnimationListener implements ActionListener {

	private final BadukAnimationFrame view;

	private final BadukAnimationModel model;

	private final Timer timer;

	public AnimationListener(BadukAnimationFrame view,
			BadukAnimationModel model) {
		this.view = view;
		this.model = model;
		
		int milliseconds = model.getMoveTime() * 1000;
		this.timer = new Timer(milliseconds, new NodeIncrementListener(view, model));
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton button = (JButton) event.getSource();
		GameRecord gameRecord = model.getGameRecord();
		if (gameRecord != null && gameRecord.isValidBaduckSGFFile()) {
			if (button.getText().equals("Start Animation")) {
				model.setAnimationActive(true);
				int seconds;
				try {
					seconds = Integer.valueOf(view.getTimerField());
				} catch (NumberFormatException e) {
					seconds = 3;
				}
				seconds = Math.max(3, seconds);
				timer.setDelay(seconds * 1000);
				timer.restart();
				button.setText("Pause Animation");
			} else {
				model.setAnimationActive(false);
				timer.stop();
				button.setText("Start Animation");
			}
		}
	}

}
