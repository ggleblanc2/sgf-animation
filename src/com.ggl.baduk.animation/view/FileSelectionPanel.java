package com.ggl.baduk.animation.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ggl.baduk.animation.controller.FileBrowserListener;
import com.ggl.baduk.animation.controller.LoadGameModelListener;
import com.ggl.baduk.animation.model.AppFonts;
import com.ggl.baduk.animation.model.BadukAnimationModel;

public class FileSelectionPanel {

	private final BadukAnimationFrame view;

	private final BadukAnimationModel model;

	private final JPanel panel;

	private JTextField fileNameField;

	public FileSelectionPanel(BadukAnimationFrame view,
			BadukAnimationModel model) {
		this.view = view;
		this.model = model;
		this.panel = createFileSelectionPanel();
	}

	private JPanel createFileSelectionPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		Font labelFont = AppFonts.getLabelFont();
		Font textFont = AppFonts.getTextFont();

		JLabel label = new JLabel("File name or URL:");
		label.setFont(labelFont);
		panel.add(label);

		fileNameField = new JTextField(45);
		fileNameField.setFont(textFont);
		panel.add(fileNameField);

		panel.add(Box.createHorizontalStrut(20));

		JButton browseButton = new JButton("Browse");
		browseButton.addActionListener(new FileBrowserListener(view, model));
		browseButton.setFont(labelFont);
		panel.add(browseButton);

		panel.add(Box.createHorizontalStrut(20));

		JButton loadButton = new JButton("Load SGF File");
		loadButton.addActionListener(new LoadGameModelListener(view, model));
		loadButton.setFont(labelFont);
		panel.add(loadButton);
		
		Dimension d = loadButton.getPreferredSize();
		d.width += 5;
		browseButton.setPreferredSize(d);
		loadButton.setPreferredSize(d);
		
		return panel;
	}
	
	public String getFileName() {
		return fileNameField.getText().trim();
	}

	public void setFileName(String fileString) {
		fileNameField.setText(fileString);
	}

	public JPanel getPanel() {
		return panel;
	}

}
