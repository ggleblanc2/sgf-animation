package com.ggl.baduk.animation.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.ggl.baduk.animation.model.BadukAnimationModel;

public class BadukAnimationFrame {
	
	private final AnimationPanel animationPanel;
	
//	private final BadukAnimationModel model;
	
	private final DrawingPanel drawingPanel;
	
	private final FileSelectionPanel fileSelectionPanel;
	
	private final JFrame frame;
	
	private final MoveNumberPanel moveNumberPanel;
	
	private final SummaryPanel summaryPanel;

	public BadukAnimationFrame(BadukAnimationModel model) {
//		this.model = model;
		this.moveNumberPanel = new MoveNumberPanel(this, model);
		this.animationPanel = new AnimationPanel(this, model);
		this.fileSelectionPanel = new FileSelectionPanel(this, model);
		this.drawingPanel = new DrawingPanel(this, model);
		this.summaryPanel = new SummaryPanel(this, model);
		this.frame = createAndShowGUI();
	}
	
	private JFrame createAndShowGUI( ) {
		JFrame frame = new JFrame("SGF Animation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(fileSelectionPanel.getPanel(), BorderLayout.NORTH);
		frame.add(createMainPanel(), BorderLayout.CENTER);
		frame.add(summaryPanel.getPanel(), BorderLayout.EAST);
		
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		
		System.out.println("Frame size: " + frame.getSize());
		
		return frame;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(moveNumberPanel.getPanel(), BorderLayout.NORTH);
		panel.add(drawingPanel, BorderLayout.CENTER);
		panel.add(animationPanel.getPanel(), BorderLayout.SOUTH);
		return panel;
	}

	public JFrame getFrame() {
		return frame;
	}
	
	public void repaint() {
		drawingPanel.repaint();
	}
	
	public String getFileName() {
		return fileSelectionPanel.getFileName();
	}
	
	public void setMoveNumber() {
		moveNumberPanel.setMoveNumber();
	}
	
	public void setSelectedFileName(String fileString) {
		fileSelectionPanel.setFileName(fileString);
	}
	
	public String getTimerField() {
		return summaryPanel.getTimerField();
	}
	
	public void updateSummaryPanel() {
		summaryPanel.updateSummaryPanel();
	}

}
