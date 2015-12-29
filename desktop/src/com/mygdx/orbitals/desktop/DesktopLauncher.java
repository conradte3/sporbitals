package com.mygdx.orbitals.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.orbitals.GdxOrbitals;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GdxOrbitals.WIDTH;
		config.height = GdxOrbitals.HEIGHT;
		config.title = GdxOrbitals.TITLE;
		new LwjglApplication(new GdxOrbitals(), config);
	}
}
