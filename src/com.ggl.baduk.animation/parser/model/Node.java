package com.ggl.baduk.animation.parser.model;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
	
	private int mainChildIndex;

	private final T data;

	private final Node<T> parent;

	private List<Node<T>> children;

	public Node(Node<T> parent, T data) {
		this.parent = parent;
		this.data = data;
		this.children = new ArrayList<>();
		this.mainChildIndex = 0;
	}

	public int getMainChildIndex() {
		return mainChildIndex;
	}

	public void setMainChildIndex(int mainChildIndex) {
		this.mainChildIndex = mainChildIndex;
	}

	public void addChild(Node<T> node) {
		this.children.add(node);
	}

	public boolean removeChild(Node<T> node) {
		for (int index = children.size() - 1; index >= 0; index--) {
			Node<T> childNode = children.get(index);
			if (childNode.getData().equals(node.getData())) {
				children.remove(index);
				return true;
			}
		}
		
		return false;
	}

	public List<Node<T>> getChildren() {
		return children;
	}

	public T getData() {
		return data;
	}

	public Node<T> getParent() {
		return parent;
	}
	
}
