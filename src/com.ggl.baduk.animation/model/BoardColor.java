package com.ggl.baduk.animation.model;

import java.awt.Color;

public class BoardColor {

	private final Color boardColor, lineColor;
	private final Color blackStoneLightColor, blackStoneDarkColor;
	private final Color whiteStoneLightColor, whiteStoneDarkColor;

	public BoardColor(Color boardColor, Color lineColor,
			Color blackStoneLightColor, Color blackStoneDarkColor,
			Color whiteStoneLightColor, Color whiteStoneDarkColor) {
		this.boardColor = boardColor;
		this.lineColor = lineColor;
		this.blackStoneLightColor = blackStoneLightColor;
		this.blackStoneDarkColor = blackStoneDarkColor;
		this.whiteStoneLightColor = whiteStoneLightColor;
		this.whiteStoneDarkColor = whiteStoneDarkColor;
	}

	public Color getBoardColor() {
		return boardColor;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public Color getBlackStoneLightColor() {
		return blackStoneLightColor;
	}

	public Color getBlackStoneDarkColor() {
		return blackStoneDarkColor;
	}

	public Color getWhiteStoneLightColor() {
		return whiteStoneLightColor;
	}

	public Color getWhiteStoneDarkColor() {
		return whiteStoneDarkColor;
	}

}
