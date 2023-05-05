package com.ggl.baduk.animation.parser.model;

public class Player {
	
	private int capturedStones;
	
	private final String color, name, rank;

	public Player(String color, String name, String rank) {
		this.color = color;
		this.name = name;
		this.rank = rank;
		this.capturedStones = 0;
	}

	public int getCapturedStones() {
		return capturedStones;
	}
	
	public void incrementCapturedStones(int increment) {
		this.capturedStones += increment;
	}

	public void setCapturedStones(int capturedStones) {
		this.capturedStones = capturedStones;
	}

	public String getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public String getRank() {
		return rank;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Player [color=");
		builder.append(color);
		builder.append(", name=");
		builder.append(name);
		builder.append(", rank=");
		builder.append(rank);
		builder.append("]");
		return builder.toString();
	}

}
