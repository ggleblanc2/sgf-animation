package com.ggl.baduk.animation.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import com.ggl.baduk.animation.model.BadukAnimationModel;
import com.ggl.baduk.animation.parser.model.GameMove;
import com.ggl.baduk.animation.parser.model.GameRecord;
import com.ggl.baduk.animation.parser.model.Node;
import com.ggl.baduk.animation.view.BadukAnimationFrame;

public class NodeIncrementListener implements ActionListener {

	private final BadukAnimationFrame view;

	private final BadukAnimationModel model;

	public NodeIncrementListener(BadukAnimationFrame view,
			BadukAnimationModel model) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (model.isAnimationActive()) {
			GameRecord gameRecord = model.getGameRecord();
			Node<GameMove> currentNode = gameRecord.getCurrentMove();
			Node<GameMove> lastNode = gameRecord.getLastMove();
			if (currentNode == null) {
				currentNode = gameRecord.getMoveRoot();
				gameRecord.setCurrentMove(currentNode);
				incrementMoveCount(currentNode.getData());
			} else if (!currentNode.getData().equals(lastNode.getData())) {
				List<Node<GameMove>> children = currentNode.getChildren();
				if (children.size() == 1) {
					Node<GameMove> child = children.get(0);
					gameRecord.setCurrentMove(child);
					incrementMoveCount(child.getData());
				} else {
					// TODO Ask the user for the node
					String message = "The current node has multiple children";
					JOptionPane.showMessageDialog(view.getFrame(), message,
							"Multiple Paths", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

	}

	private void incrementMoveCount(GameMove gameMove) {
//		System.out.println(gameMove);
		model.incrementMoveCount(1);
		model.addStone(gameMove);
//		System.out.println("Move: " + gameMove);
//		model.printBoard();
		
		char color = 'W';
		if (gameMove.getColor().equals("W")) {
			color = 'B';
		}
		
		List<List<int[]>> groups = model.findOrthogonalGroups(color);
//		for (List<int[]> group : groups) {
//			System.out.println(Arrays.deepToString(group.toArray()));
//		}
//		model.printBoard();
//		System.out.println();
		model.removeSurroundedGroups(groups);
		view.setMoveNumber();
		view.repaint();
	}

}
