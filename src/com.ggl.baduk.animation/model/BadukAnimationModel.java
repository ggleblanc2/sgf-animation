package com.ggl.baduk.animation.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ggl.baduk.animation.parser.SGFParser;
import com.ggl.baduk.animation.parser.model.GameMove;
import com.ggl.baduk.animation.parser.model.GameRecord;

public class BadukAnimationModel {

	private boolean animationActive;

	private char[][] board;

	private int moveCount, moveTime;

	private BoardColor boardColor;

	private GameRecord gameRecord;

	private Point lastMove;

	private String fileName;

	public BadukAnimationModel() {
		this.fileName = System.getProperty("user.home") + "/Downloads";
		this.boardColor = new BoardColors().getBoardColor(0);
		this.animationActive = false;
		this.moveCount = 0;
		this.moveTime = 3;
		this.board = new char[19][19];
	}

	public GameRecord getGameRecord() {
		return gameRecord;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public BoardColor getBoardColor() {
		return boardColor;
	}

	public void setBoardColor(BoardColor boardColor) {
		this.boardColor = boardColor;
	}

	public boolean createGameRecord() {
		SGFParser parser = new SGFParser(fileName);
		this.gameRecord = parser.parseInput();
		return gameRecord.isValidBaduckSGFFile();
	}

	public boolean isAnimationActive() {
		return animationActive;
	}

	public void setAnimationActive(boolean animationActive) {
		this.animationActive = animationActive;
	}

	public Point getLastMove() {
		return lastMove;
	}

	public void setLastMove(Point lastMove) {
		this.lastMove = lastMove;
	}

	public void incrementMoveCount(int increment) {
		this.moveCount += increment;
	}

	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}

	public int getMoveCount() {
		return moveCount;
	}

	public int getMoveTime() {
		return moveTime;
	}

	public void setMoveTime(int moveTime) {
		this.moveTime = moveTime;
	}

	public void clearBoard() {
		int numberOfLines = 19;
		for (int rowIndex = 0; rowIndex < numberOfLines; rowIndex++) {
			for (int columnIndex = 0; columnIndex < numberOfLines; columnIndex++) {
				board[rowIndex][columnIndex] = ' ';
				lastMove = null;
			}
		}
	}

	public void addStone(GameMove gameMove) {
		if (!gameMove.isPass()) {
			int columnIndex = gameMove.getColumn().charAt(0) - 'a';
			int rowIndex = gameMove.getRow().charAt(0) - 'a';
			board[rowIndex][columnIndex] = gameMove.getColor().charAt(0);
			lastMove = new Point(rowIndex, columnIndex);
		}
	}

	public void removeStone(String column, String row) {
		int columnIndex = column.charAt(0) - 'a';
		int rowIndex = row.charAt(0) - 'a';
		board[rowIndex][columnIndex] = ' ';
	}

	/**
	 * The <code>findOrthogonalGroups</CODE> method takes a two-dimensional char
	 * array <code>arr</code> as input and returns a list of lists, where each
	 * inner list represents an orthogonally connected group of either 'B' or
	 * 'W'. The method first initializes a boolean array visited to keep track
	 * of visited cells, and then iterates through each cell in the array. If
	 * the cell has not been visited and is not empty, the method starts a new
	 * group by calling the traverse method.
	 * 
	 * @param color - Either 'B' for the black groups or 'W' for the white
	 *              groups
	 * @return List of Lists, where each inner list represents an orthogonally
	 *         connected group
	 */
	public List<List<int[]>> findOrthogonalGroups(char color) {
		List<List<int[]>> groups = new ArrayList<>();
		int rows = board.length;
		int cols = board[0].length;
		boolean[][] visited = new boolean[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (!visited[i][j] && board[i][j] == color) {
					List<int[]> group = new ArrayList<>();
					traverse(visited, group, i, j);
					groups.add(group);
				}
			}
		}
		return groups;
	}

	/**
	 * The <code>traverse</code> method takes the current cell (i, j) and adds
	 * it to the current group. It then checks the adjacent cells in the four
	 * cardinal directions ((-1, 0), (1, 0), (0, -1), and (0, 1)) and
	 * recursively calls itself on any adjacent cell that has the same value as
	 * the current cell and has not been visited yet. This ensures that the
	 * method traverses all orthogonally connected cells in the current group.
	 * 
	 * @param visited - Two dimensional <code>boolean</code> array moting all
	 *                the visited locations.
	 * @param group   - List of row, column instances that form an orthogonally
	 *                connected group
	 * @param i       - Row index
	 * @param j       - Column index
	 */
	private void traverse(boolean[][] visited, List<int[]> group, int i,
			int j) {
		visited[i][j] = true;
		group.add(new int[] { i, j });
		int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
		for (int[] dir : dirs) {
			int row = i + dir[0];
			int col = j + dir[1];
			if (row >= 0 && row < board.length && col >= 0
					&& col < board[0].length && !visited[row][col]
					&& board[row][col] == board[i][j]) {
				traverse(visited, group, row, col);
			}
		}
	}

	/**
	 * The <code>removeSurroundedGroups</code> will remove any groups of the
	 * opposite color that are completely surrounded by stones of the color just
	 * played.
	 * 
	 * @param groups - List of Lists, where each inner list represents an
	 *               orthogonally connected group
	 */
	public void removeSurroundedGroups(List<List<int[]>> groups) {
		int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
		for (List<int[]> positions : groups) {
			boolean surrounded = true;
			for (int[] position : positions) {
				for (int[] dir : dirs) {
					int row = position[0] + dir[0];
					int col = position[1] + dir[1];
					if (row >= 0 && row < board.length && col >= 0
							&& col < board[0].length) {
						if (board[row][col] == ' ') {
							surrounded = false;
						}
					}

					if (!surrounded) {
						break;
					}
				}

				if (!surrounded) {
					break;
				}
			}

			if (surrounded) {
				for (int[] position : positions) {
					int row = position[0];
					int col = position[1];
					board[row][col] = ' ';
				}
			}
		}
	}

	public char[][] getBoard() {
		return board;
	}

	public void printBoard() {
		for (char[] row : board) {
			System.out.println(Arrays.toString(row));
		}
		System.out.println();
	}

}
