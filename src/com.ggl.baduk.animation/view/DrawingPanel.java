package com.ggl.baduk.animation.view;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import com.ggl.baduk.animation.model.BadukAnimationModel;
import com.ggl.baduk.animation.model.BoardColor;
import com.ggl.baduk.animation.parser.model.GameRecord;

public class DrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

//	private final BadukAnimationFrame view;

	private final BadukAnimationModel model;

	public DrawingPanel(BadukAnimationFrame view, BadukAnimationModel model) {
//		this.view = view;
		this.model = model;
		this.setPreferredSize(new Dimension(800, 800));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);

		GameRecord gameRecord = model.getGameRecord();

		int width = this.getWidth();
		int height = this.getHeight();
		int fontSize = height / 40;
		int margin = 10;
		int coordinateMargin = height / 30;
		int smallest = Math.min(width, height);
		smallest = smallest - 2 * margin;
		int xOffset = (width - smallest) / 2;
		int yOffset = margin;

		BoardColor boardColor = model.getBoardColor();
		// Draw board
		g2d.setColor(boardColor.getBoardColor());
		g2d.fillRect(xOffset, yOffset, smallest, smallest);

		g2d.setFont(getLabelFont(fontSize));
		g2d.setColor(boardColor.getLineColor());
		g2d.setStroke(new BasicStroke(3f));
		FontMetrics metrics = g2d.getFontMetrics();
		int textWidth = metrics.stringWidth("19");
		int lineLength = smallest - 2 * margin - 2 * coordinateMargin
				- 2 * fontSize;

		int numberOfLines = 19;
		if (gameRecord != null) {
			int boardSize = model.getGameRecord().getBoardSize();
			if (boardSize > 0) {
				numberOfLines = boardSize;
			}
		}

		int lineSpacing = lineLength / numberOfLines;
		lineLength = lineSpacing * (numberOfLines - 1);
		int xLineOffset = xOffset + (smallest - lineLength) / 2;
		int yLineOffset = yOffset + (smallest - lineLength) / 2;

		drawBoardLines(g2d, xLineOffset, yLineOffset, numberOfLines, lineLength,
				lineSpacing);
		drawStarPoints(g2d, xLineOffset, yLineOffset, numberOfLines,
				lineSpacing);
		drawColumnLetters(g2d, xLineOffset, yLineOffset, fontSize,
				coordinateMargin, lineLength, lineSpacing);
		drawRowNumbers(g2d, metrics, fontSize, xLineOffset, yLineOffset,
				coordinateMargin, textWidth, lineLength, numberOfLines,
				lineSpacing);
		drawGameStones(g2d, boardColor, xLineOffset, yLineOffset, lineSpacing,
				numberOfLines);
	}

	private void drawBoardLines(Graphics2D g2d, int xLineOffset,
			int yLineOffset, int numberOfLines, int lineLength,
			int lineSpacing) {
		int x = xLineOffset;
		int y = yLineOffset;
		for (int index = 0; index < numberOfLines; index++) {
			int x1 = x + lineLength;
			g2d.drawLine(x, y, x1, y);
			y += lineSpacing;
		}

		x = xLineOffset;
		y = yLineOffset;
		for (int index = 0; index < numberOfLines; index++) {
			int y1 = y + lineLength;
			g2d.drawLine(x, y, x, y1);
			x += lineSpacing;
		}
	}

	private void drawStarPoints(Graphics2D g2d, int xLineOffset,
			int yLineOffset, int numberOfLines, int lineSpacing) {
		int radius = 7;
		int diameter = radius + radius;
		int offset = 4;

		if (numberOfLines < 13) {
			offset = 3;
		}

		int[] offsets = { offset - 1, numberOfLines / 2,
				numberOfLines - offset };
		for (int i = 0; i < offsets.length; i++) {
			for (int j = 0; j < offsets.length; j++) {
				int x = xLineOffset + offsets[j] * lineSpacing;
				int y = yLineOffset + offsets[i] * lineSpacing;
				if (numberOfLines < 19) {
					if (!logicalXOR(i == 1, j == 1)) {
						g2d.fillOval(x - radius, y - radius, diameter,
								diameter);
					}
				} else {
					g2d.fillOval(x - radius, y - radius, diameter, diameter);
				}
			}
		}
	}

	private void drawColumnLetters(Graphics2D g2d, int xLineOffset,
			int yLineOffset, int fontSize, int coordinateMargin, int lineLength,
			int lineSpacing) {
		int x;
		String columnLetters = "ABCDEFGHJKLMNOPQRST";
		x = xLineOffset;
		int y1 = yLineOffset - coordinateMargin;
		int y2 = yLineOffset + lineLength + coordinateMargin + fontSize;
		int halfWidth = lineSpacing / 2;

		for (char c : columnLetters.toCharArray()) {
			Rectangle rect = new Rectangle(x - halfWidth, y1 - fontSize,
					lineSpacing, fontSize);
			String text = Character.toString(c);
			drawCenteredString(g2d, text, rect);
			rect = new Rectangle(x - halfWidth, y2 - fontSize, lineSpacing,
					fontSize);
			drawCenteredString(g2d, text, rect);
			x += lineSpacing;
		}
	}

	private void drawRowNumbers(Graphics2D g2d, FontMetrics metrics,
			int fontSize, int xLineOffset, int yLineOffset,
			int coordinateMargin, int textWidth, int lineLength,
			int numberOfLines, int lineSpacing) {
		int x1 = xLineOffset - coordinateMargin - textWidth / 2;
		int x2 = xLineOffset + lineLength + coordinateMargin + textWidth / 2;
		int y = yLineOffset + metrics.getDescent();
		int halfWidth = textWidth / 2;
		int halfSpacing = lineSpacing / 2;
		for (int number = numberOfLines; number > 0; number--) {
			Rectangle rect = new Rectangle(x1 - halfWidth, y - halfSpacing,
					textWidth, fontSize);
			String text = Integer.toString(number);
			drawCenteredString(g2d, text, rect);
			rect = new Rectangle(x2 - halfWidth, y - halfSpacing, textWidth,
					fontSize);
			drawCenteredString(g2d, text, rect);
			y += lineSpacing;
		}
	}

	private void drawGameStones(Graphics2D g2d, BoardColor boardColor,
			int xLineOffset, int yLineOffset, int lineSpacing,
			int numberOfLines) {
		char[][] board = model.getBoard();
		int halfSpacing = lineSpacing / 2 - 2;
		int diameter = halfSpacing + halfSpacing;

		for (int rowIndex = 0; rowIndex < numberOfLines; rowIndex++) {
			for (int columnIndex = 0; columnIndex < numberOfLines; columnIndex++) {
				char c = board[rowIndex][columnIndex];
				if (c == 'B') {
					int x = xLineOffset + lineSpacing * columnIndex;
					int y = yLineOffset + lineSpacing * rowIndex;
					GradientPaint blackGP = new GradientPaint(x - halfSpacing,
							y - halfSpacing,
							boardColor.getBlackStoneLightColor(),
							x - halfSpacing + diameter,
							y - halfSpacing + diameter,
							boardColor.getBlackStoneDarkColor());
					g2d.setPaint(blackGP);
					g2d.fillOval(x - halfSpacing, y - halfSpacing, diameter,
							diameter);
					Point p = model.getLastMove();
					if (p != null && p.x == rowIndex && p.y == columnIndex) {
						g2d.setColor(boardColor.getWhiteStoneDarkColor());
						int radius = halfSpacing * 2 / 3;
						int d = radius + radius;
						g2d.fillOval(x - radius, y - radius, d, d);
						g2d.setColor(boardColor.getBlackStoneDarkColor());
						radius = halfSpacing / 3;
						d = radius + radius;
						g2d.fillOval(x - radius, y - radius, d, d);
					}
				} else if (c == 'W') {
					int x = xLineOffset + lineSpacing * columnIndex;
					int y = yLineOffset + lineSpacing * rowIndex;
					GradientPaint whiteGP = new GradientPaint(x - halfSpacing,
							y - halfSpacing,
							boardColor.getWhiteStoneLightColor(),
							x - halfSpacing + diameter,
							y - halfSpacing + diameter,
							boardColor.getWhiteStoneDarkColor());
					g2d.setPaint(whiteGP);
					g2d.fillOval(x - halfSpacing, y - halfSpacing, diameter,
							diameter);
					Point p = model.getLastMove();
					if (p != null && p.x == rowIndex && p.y == columnIndex) {
						g2d.setColor(boardColor.getBlackStoneDarkColor());
						int radius = halfSpacing * 2 / 3;
						int d = radius + radius;
						g2d.fillOval(x - radius, y - radius, d, d);
						g2d.setColor(boardColor.getWhiteStoneDarkColor());
						radius = halfSpacing / 3;
						d = radius + radius;
						g2d.fillOval(x - radius, y - radius, d, d);
					}
				}
			}
		}
	}

	private void drawCenteredString(Graphics2D g2d, String text,
			Rectangle rect) {
		Font font = g2d.getFont();
		FontMetrics metrics = g2d.getFontMetrics(font);
		int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		int y = rect.y + ((rect.height - metrics.getHeight()) / 2)
				+ metrics.getAscent();
		g2d.drawString(text, x, y);
	}

	private boolean logicalXOR(boolean x, boolean y) {
		return ((x || y) && !(x && y));
	}

	private Font getLabelFont(int fontSize) {
		return this.getFont().deriveFont(Font.BOLD, fontSize);
	}

}
