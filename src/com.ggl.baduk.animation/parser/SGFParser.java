package com.ggl.baduk.animation.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.ggl.baduk.animation.parser.model.GameMove;
import com.ggl.baduk.animation.parser.model.GameRecord;
import com.ggl.baduk.animation.parser.model.Node;
import com.ggl.baduk.animation.parser.model.Player;

public class SGFParser {

	private boolean firstMoveSwitch, firstTimeSwitch;
	private boolean header, validGame, validSGFLevel;

	private int level;

	private GameRecord gameRecord;

	private InputStream inputStream;

	private String blackPlayerName, whitePlayerName;

	public SGFParser(String fileString) {
		if (fileString.startsWith("http")) {
			try {
				this.inputStream = new URL(fileString).openStream();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if (!fileString.isEmpty()) {
				try {
					this.inputStream = new FileInputStream(
							new File(fileString));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		setValues();
	}

	public SGFParser(File file) {
		try {
			this.inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		setValues();
	}

	private void setValues() {
		this.level = 0;
		this.firstMoveSwitch = true;
		this.firstTimeSwitch = true;
		this.header = true;
		this.validSGFLevel = true;
		this.validGame = true;
	}

	public GameRecord parseInput() {
		this.gameRecord = new GameRecord();
		this.gameRecord.setValidBaduckSGFFile(false);

		if (inputStream == null) {
			return gameRecord;
		}

		try {
			InputStreamReader isr = new InputStreamReader(inputStream,
					StandardCharsets.UTF_8);
			BufferedReader reader = new BufferedReader(isr);

			String line = reader.readLine();

			if (!line.startsWith("(")) {
				return gameRecord;
			}

			while (line != null) {
				parseLine(line.trim());
				line = reader.readLine();
			}

			gameRecord.setValidBaduckSGFFile(true);
			gameRecord.setLastMove(gameRecord.getCurrentMove());
			gameRecord.setCurrentMove(null);

			reader.close();
			closeInput();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return gameRecord;
	}

	private void parseLine(String line) {
		line = isLevelOpen(line);
		line = isMoveBody(line);

		isLevelClose(line);

//		System.out.println(line);

		if (header) {
			isValidSGFLevel(line);
			isValidGame(line);

			setBoardSize(line);
			setGameDate(line);
			setJapaneseDate(line);
			setGameName(line);
			setGameTime(line);
			setKomi(line);
			setPlace(line);
			setEvent(line);
			setResult(line);
			setRuleset(line);
			setHandicap(line);
			setHandicapMoves(line);

			setBlackPlayerName(line);
			setWhitePlayerName(line);
			setBlackPlayerRank(line);
			setWhitePlayerRank(line);
		} else {
			setMove(line);
		}
	}

	private String isLevelOpen(String line) {
		String levelIndicator = "(";
		if (line.startsWith(levelIndicator)) {
			level++;
			line = line.substring(levelIndicator.length());
		}
		return line;
	}

	private void isLevelClose(String line) {
		if (line.startsWith(")")) {
			for (char c : line.toCharArray()) {
				if (c == ')') {
					level--;
				}
			}
		}
	}

	private String isMoveBody(String line) {
		String bodyIndicator = ";";
		if (line.startsWith(bodyIndicator)) {
			if (firstTimeSwitch) {
				firstTimeSwitch = false;
			} else {
				this.header = false;
			}
			line = line.substring(bodyIndicator.length());
		}
		return line;
	}

	private void isValidSGFLevel(String line) {
		if (line.startsWith("FF")) {
			int level = Integer.valueOf(parseValue(line));
			if (level != 4) {
				this.validSGFLevel = false;
			}
		}
	}

	private void isValidGame(String line) {
		if (line.startsWith("GM")) {
			int gameNumber = Integer.valueOf(parseValue(line));
			if (gameNumber != 1) {
				this.validGame = false;
			}
		}
	}

	private void setGameDate(String line) {
		if (line.startsWith("DT")) {
			this.gameRecord.setGameDate(parseValue(line));
		}
	}

	private void setJapaneseDate(String line) {
		if (line.startsWith("JD")) {
			this.gameRecord.setJapaneseDate(parseValue(line));
		}
	}

	private void setEvent(String line) {
		if (line.startsWith("EV")) {
			this.gameRecord.setEvent(parseValue(line));
		}
	}

	private void setPlace(String line) {
		if (line.startsWith("PC")) {
			this.gameRecord.setPlace(parseValue(line));
		}
	}

	private void setGameName(String line) {
		if (line.startsWith("GN")) {
			this.gameRecord.setGameName(parseValue(line));
		}
	}

	private void setGameTime(String line) {
		if (line.startsWith("TM")) {
			int seconds = Integer.valueOf(parseValue(line));
			int minutes = seconds / 60;
			int beginIndex = line.indexOf("OT");

			String overtime = "";
			if (beginIndex >= 0) {
				line = line.substring(beginIndex);
				overtime = ", " + parseValue(line);
			}

			String minuteLabel = " minute";
			if (minutes > 1) {
				minuteLabel += "s";
			}

			String timeControl = minutes + minuteLabel + overtime;
			this.gameRecord.setTimeControl(timeControl);
		}
	}

	private void setResult(String line) {
		if (line.startsWith("RE")) {
			this.gameRecord.setResult(parseValue(line));
		}
	}

	private void setBoardSize(String line) {
		if (line.startsWith("SZ")) {
			int boardSize = Integer.valueOf(parseValue(line));
			this.gameRecord.setBoardSize(boardSize);
		}
	}

	private void setKomi(String line) {
		if (line.startsWith("KM")) {
			this.gameRecord.setKomi(parseValue(line));
		}
	}

	private void setRuleset(String line) {
		if (line.startsWith("RU")) {
			this.gameRecord.setRuleset(parseValue(line));
		}
	}

	private void setHandicap(String line) {
		if (line.startsWith("HA")) {
			this.gameRecord.setHandicap(Integer.valueOf(parseValue(line)));
		}
	}

	private void setHandicapMoves(String line) {
		if (line.startsWith("AB")) {
			List<String> moveList = parseMultipleValues(line);
			for (String s : moveList) {
				GameMove gameMove = new GameMove("B", s.substring(0, 1),
						s.substring(1), false);
				this.gameRecord.addHandicapMove(gameMove);
			}
		}
		
		if (line.startsWith("AW")) {
			List<String> moveList = parseMultipleValues(line);
			for (String s : moveList) {
				GameMove gameMove = new GameMove("W", s.substring(0, 1),
						s.substring(1), false);
				this.gameRecord.addHandicapMove(gameMove);
			}
		}
	}

	private void setBlackPlayerName(String line) {
		if (line.startsWith("PB")) {
			this.blackPlayerName = parseValue(line);
		}
	}

	private void setWhitePlayerName(String line) {
		if (line.startsWith("PW")) {
			this.whitePlayerName = parseValue(line);
		}
	}

	private void setBlackPlayerRank(String line) {
		if (line.startsWith("BR")) {
			String rank = parseValue(line);
			Player player = new Player("B", blackPlayerName, rank);
			this.gameRecord.addPlayer(player);
		}
	}

	private void setWhitePlayerRank(String line) {
		if (line.startsWith("WR")) {
			String rank = parseValue(line);
			Player player = new Player("W", whitePlayerName, rank);
			this.gameRecord.addPlayer(player);
		}
	}

	private void setMove(String line) {
		if (line.startsWith("B")) {
			GameMove gameMove = createGameMove(line, "B");
			Node<GameMove> rootNode = gameRecord.getMoveRoot();
			if (rootNode == null) {
				Node<GameMove> gameMoveNode = new Node<>(null, gameMove);
				gameRecord.setMoveRoot(gameMoveNode);
				gameRecord.setCurrentMove(gameMoveNode);
			} else {
				addMoveToTree(gameMove);
			}
		} else if (line.startsWith("W")) {
			GameMove gameMove = createGameMove(line, "W");
			Node<GameMove> rootNode = gameRecord.getMoveRoot();
			if (rootNode == null) {
				Node<GameMove> gameMoveNode = new Node<>(null, gameMove);
				gameRecord.setMoveRoot(gameMoveNode);
				gameRecord.setCurrentMove(gameMoveNode);
			} else {
				addMoveToTree(gameMove);
			}
		}
	}

	private GameMove createGameMove(String line, String moveColor) {
		String value = parseValue(line);
		if (value.isEmpty()) {
			return new GameMove(moveColor, "", "", true);
		} else {
			String column = value.substring(0, 1);
			String row = value.substring(1);
			if (column.equals("t") && row.equals("t")) {
				return new GameMove(moveColor, "", "", true);
			} else {
				return new GameMove(moveColor, column, row, false);
			}
		}
	}

	private void addMoveToTree(GameMove gameMove) {
		Node<GameMove> parentNode = gameRecord.getCurrentMove();
		Node<GameMove> gameMoveNode = new Node<>(parentNode, gameMove);
		parentNode.addChild(gameMoveNode);
		gameRecord.setCurrentMove(gameMoveNode);
	}

	private String parseValue(String line) {
		int beginIndex = line.indexOf("[") + 1;
		int endIndex = line.indexOf("]", beginIndex);

		if (beginIndex >= 0 && endIndex >= 0) {
			return line.substring(beginIndex, endIndex);
		} else {
			return "";
		}
	}

	private List<String> parseMultipleValues(String line) {
		List<String> valueList = new ArrayList<>();
		int beginIndex = line.indexOf("[") + 1;
		int endIndex = line.indexOf("]", beginIndex);

		while (beginIndex >= 0 && endIndex >= 0) {
			valueList.add(line.substring(beginIndex, endIndex));
			beginIndex = line.indexOf("[", endIndex);
			if (beginIndex >= 0) {
				beginIndex++;
				endIndex = line.indexOf("]", beginIndex);
			}
		}

		return valueList;
	}

	private void closeInput() {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
