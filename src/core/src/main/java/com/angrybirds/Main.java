package com.angrybirds;

import com.badlogic.gdx.Game;

public class Main extends Game {

    // for enabling anti-aliasing for text in linux (or other barebones env)
    static {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
    }

    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));
    }
}
