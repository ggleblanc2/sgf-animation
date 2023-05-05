package com.ggl.baduk.animation.parser.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GameRecord {

	private boolean validBaduckSGFFile;

	private int boardSize, handicap, moveCount;
	
	private List<GameMove> handicapMoves;

	private List<Player> players;

	private LocalDate gameDate;

	private Node<GameMove> currentMove, lastMove, moveRoot;

	private String event, gameName, japaneseDate, komi, place; 
	private String result, ruleset, timeControl;

	public GameRecord() {
		this.players = new ArrayList<>();
		this.handicapMoves = new ArrayList<>();
		this.moveCount = 0;
		this.handicap = 0;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public List<GameMove> getHandicapMoves() {
		return handicapMoves;
	}
	
	public void addHandicapMove(GameMove gameMove) {
		this.handicapMoves.add(gameMove);
	}

	public void setGameDate(String gameDateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		try {
			this.gameDate = LocalDate.parse(gameDateString, formatter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getFormattedGameDate() {
		if (gameDate == null) {
			return "";
		} else {
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("EEEE, MMMM d, yyyy");
			return gameDate.format(formatter);
		}
	}

	public String getJapaneseDate() {
		return japaneseDate;
	}

	public void setJapaneseDate(String japaneseDate) {
		this.japaneseDate = japaneseDate;
	}

	public Node<GameMove> getCurrentMove() {
		return currentMove;
	}

	public void setCurrentMove(Node<GameMove> currentMove) {
		this.currentMove = currentMove;
	}

	public Node<GameMove> getLastMove() {
		return lastMove;
	}

	public void setLastMove(Node<GameMove> lastMove) {
		this.lastMove = lastMove;
	}

	public Node<GameMove> getMoveRoot() {
		return moveRoot;
	}

	public void setMoveRoot(Node<GameMove> moveRoot) {
		this.moveRoot = moveRoot;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getKomi() {
		return komi;
	}

	public void setKomi(String komi) {
		this.komi = komi;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getHandicap() {
		return handicap;
	}

	public void setHandicap(int handicap) {
		this.handicap = handicap;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRuleset() {
		return ruleset;
	}

	public void setRuleset(String ruleset) {
		this.ruleset = ruleset;
	}

	public String getTimeControl() {
		return timeControl;
	}

	public void setTimeControl(String timeControl) {
		this.timeControl = timeControl;
	}

	public void setValidBaduckSGFFile(boolean validBaduckSGFFile) {
		this.validBaduckSGFFile = validBaduckSGFFile;
	}

	public boolean isValidBaduckSGFFile() {
		return validBaduckSGFFile;
	}

	public void incrementMoveCount() {
		this.moveCount++;
	}

	public int getMoveCount() {
		return moveCount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameRecord [validBaduckSGFFile=");
		builder.append(validBaduckSGFFile);
		builder.append(", boardSize=");
		builder.append(boardSize);
		builder.append(", gameDate=");
		builder.append(gameDate);
		builder.append(", gameName=");
		builder.append(gameName);
		builder.append(", komi=");
		builder.append(komi);
		builder.append(", moveCount=");
		builder.append(moveCount);
		builder.append(", place=");
		builder.append(place);
		builder.append(", result=");
		builder.append(result);
		builder.append(", ruleset=");
		builder.append(ruleset);
		builder.append(", timeControl=");
		builder.append(timeControl);
		builder.append(System.lineSeparator());
		builder.append("    players=");
		builder.append(players);
		builder.append(System.lineSeparator());
		builder.append("    currentMove=");
		builder.append(currentMove.getData());
		builder.append(", moveRoot=");
		builder.append(moveRoot.getData());
		builder.append("]");
		return builder.toString();
	}

}
