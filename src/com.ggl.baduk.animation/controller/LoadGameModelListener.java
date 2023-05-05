package com.ggl.baduk.animation.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.ggl.baduk.animation.model.BadukAnimationModel;
import com.ggl.baduk.animation.parser.model.GameMove;
import com.ggl.baduk.animation.parser.model.GameRecord;
import com.ggl.baduk.animation.view.BadukAnimationFrame;

public class LoadGameModelListener implements ActionListener {
	
	private final BadukAnimationFrame view;

	private final BadukAnimationModel model;

	public LoadGameModelListener(BadukAnimationFrame view,
			BadukAnimationModel model) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		model.setFileName(view.getFileName());
		new Thread(new LoadRunnable()).start();
	}
	
	private class LoadRunnable implements Runnable {

		@Override
		public void run() {
			boolean isValidSGFFile = model.createGameRecord();
			
			if (!isValidSGFFile) {
				String fileName = model.getFileName();
				String message = "<html>The ";
				String fileText = "file ";
				String name = fileName;
				
				if (fileName.startsWith("http")) {
					fileText = "URL ";
				} else {
					File file = new File(model.getFileName());
					name = file.getName();
				}
				
				message += fileText + name + "<br>is an invalid SGF file";
				showErrorMessage(message);
			} else {
				model.setMoveCount(0);
				model.clearBoard();
				addHandicapStones();
				updateSummaryPanel();
			}
		}
		
		private void addHandicapStones() {
			GameRecord gameRecord = model.getGameRecord();
			List<GameMove> handicapMoves = gameRecord.getHandicapMoves();
			for (GameMove gameMove : handicapMoves) {
				model.addStone(gameMove);
			}
			repaint();
		}
		
		private void showErrorMessage(final String message) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					JOptionPane.showMessageDialog(view.getFrame(), message,
							"Invalid SGF File", JOptionPane.ERROR_MESSAGE);
				}
			});
		}
		
		private void repaint() {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					view.repaint();
				}
			});
		}
		
		private void updateSummaryPanel() {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					view.updateSummaryPanel();
				}
			});
		}
		
	}

}
