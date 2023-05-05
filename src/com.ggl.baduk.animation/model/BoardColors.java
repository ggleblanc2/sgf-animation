package com.ggl.baduk.animation.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class BoardColors {

	private final List<BoardColor> boardColors;

	public BoardColors() {
		this.boardColors = new ArrayList<>();

		Color boardColor = new Color(221, 176, 109);
		Color lineColor = Color.BLACK;
		Color blackStoneDarkColor = Color.black;
		Color blackStoneLightColor = Color.darkGray;
//		Color blackStoneLightColor = Color.white;
		Color whiteStoneDarkColor = Color.lightGray;
//		Color whiteStoneDarkColor = Color.black;
		Color whiteStoneLightColor = Color.white;
		this.boardColors.add(new BoardColor(boardColor, lineColor,
				blackStoneLightColor, blackStoneDarkColor,
				whiteStoneLightColor, whiteStoneDarkColor));
	}
	
	public BoardColor getBoardColor(int index) {
		return boardColors.get(index);
	}
}
