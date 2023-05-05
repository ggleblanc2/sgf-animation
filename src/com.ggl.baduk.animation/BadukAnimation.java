package com.ggl.baduk.animation;

import javax.swing.SwingUtilities;

import com.ggl.baduk.animation.model.BadukAnimationModel;
import com.ggl.baduk.animation.view.BadukAnimationFrame;

public class BadukAnimation implements Runnable {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new BadukAnimation());
	}

	@Override
	public void run() {
		new BadukAnimationFrame(new BadukAnimationModel());
	}

}
