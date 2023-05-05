package com.ggl.baduk.animation.parser.model;

import java.util.ArrayList;
import java.util.List;

public class GameMove {

	private final boolean pass;

	private final List<String> comments;

	private final String color, column, row;

	public GameMove(String color, String column, String row, boolean pass) {
		this.color = color;
		this.column = column;
		this.row = row;
		this.pass = pass;
		this.comments = new ArrayList<>();
	}

	public void addComment(String comment) {
		this.comments.add(comment);
	}

	public boolean isPass() {
		return pass;
	}

	public String getColor() {
		return color;
	}

	public String getColumn() {
		return column;
	}

	public String getRow() {
		return row;
	}

	public List<String> getComments() {
		return comments;
	}

	public String getBoardCoordinates(int boardSize) {
		String[] columnLetters = { "A", "B", "C", "D", "E", "F", "G", "H", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T" };
		int[] rowNumbers = { 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6,
				5, 4, 3, 2, 1 };

		int offset = 19 - boardSize;
		int index = (int) (column.charAt(0) - 'a');
		String output = columnLetters[index];
		index = (int) (row.charAt(0) - 'a') + offset;
		return output + rowNumbers[index];
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result + (pass ? 1231 : 1237);
		result = prime * result + ((row == null) ? 0 : row.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameMove other = (GameMove) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		if (pass != other.pass)
			return false;
		if (row == null) {
			if (other.row != null)
				return false;
		} else if (!row.equals(other.row))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameMove [color=");
		builder.append(color);
		builder.append(", column=");
		builder.append(column);
		builder.append(", row=");
		builder.append(row);
		builder.append(", pass=");
		builder.append(pass);
		if (!pass) {
			builder.append(", coordinate=");
			builder.append(getBoardCoordinates(19));
		}
		builder.append("]");
		return builder.toString();
	}

}
