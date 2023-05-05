package com.ggl.baduk.animation.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.ggl.baduk.animation.model.AppFonts;
import com.ggl.baduk.animation.model.BadukAnimationModel;
import com.ggl.baduk.animation.view.BadukAnimationFrame;

public class FileBrowserListener implements ActionListener {

	private final BadukAnimationFrame view;

	private final BadukAnimationModel model;

	public FileBrowserListener(BadukAnimationFrame view,
			BadukAnimationModel model) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		selectSGFFile();
	}

	private void selectSGFFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setCurrentDirectory(new File(model.getFileName()));
		chooser.setFont(AppFonts.getLabelFont());
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"SGF files", "sgf");
		chooser.addChoosableFileFilter(filter);
		int returnCode = chooser.showOpenDialog(view.getFrame());

		if (returnCode == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			String fileName = file.getAbsolutePath();
			model.setFileName(fileName);
			view.setSelectedFileName(fileName);
		}
	}

}
