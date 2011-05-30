package org.romaframework.aspect.view.html.screen;

import org.romaframework.aspect.view.screen.Screen;
import org.romaframework.aspect.view.screen.ScreenContainer;

public class HtmlViewScreenContainer implements ScreenContainer {

	private Screen	screen;

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(final Screen desktop) {
		screen = desktop;
	}

	public void destroy() {
		screen = null;
	}

}
