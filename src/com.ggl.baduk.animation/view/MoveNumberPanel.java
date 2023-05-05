package com.ggl.baduk.animation.view;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ggl.baduk.animation.model.AppFonts;
import com.ggl.baduk.animation.model.BadukAnimationModel;

public class MoveNumberPanel {
	
//	private final BadukAnimationFrame view;

	private final BadukAnimationModel model;

	private final JPanel panel;
	
	private JTextField moveCountField;

	public MoveNumberPanel(BadukAnimationFrame view,
			BadukAnimationModel model) {
//		this.view = view;
		this.model = model;
		this.panel = createMoveNumberPanel();
	}
	
	private JPanel createMoveNumberPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
		Font labelFont = AppFonts.getLabelFont();
		Font textFont = AppFonts.getTextFont();
		
		JLabel label = new JLabel("Move:");
		label.setFont(labelFont);
		panel.add(label);
		
		moveCountField = new JTextField(4);
		moveCountField.setEditable(false);
		moveCountField.setFont(textFont);
		panel.add(moveCountField);
		
		return panel;
	}
	
	public void setMoveNumber() {
		int number = model.getMoveCount();
		if (number > 0) {
			moveCountField.setText(Integer.toString(number));
		}
	}

	public JPanel getPanel() {
		return panel;
	}

}
