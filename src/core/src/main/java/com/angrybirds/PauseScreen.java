package com.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PauseScreen implements Screen {
    private final Window pauseWindow;
    private final TextButton resumeButton;
    private final TextButton restartButton;
    private final TextButton exitButton;
    private final PlayScreen playScreen;
    private final Game game;
    private final Stage stage;
    private boolean isPaused = false;

    public PauseScreen(Game game, PlayScreen playScreen, Skin skin) {
        this.game = game;
        this.playScreen = playScreen;
        this.stage = new Stage(new ScreenViewport());
        this.pauseWindow = new Window("Paused", skin, "default");
        pauseWindow.setSize(600, 300);  // Set smaller size
        pauseWindow.setColor(new Color(0, 0, 0, 1f)); // Slightly transparent black background

        // Center the window on the screen
        pauseWindow.setPosition(
            (Gdx.graphics.getWidth() - pauseWindow.getWidth()) / 2,
            (Gdx.graphics.getHeight() - pauseWindow.getHeight()) / 2
        );
        pauseWindow.setVisible(false); // Initially hidden
        // Create buttons
        resumeButton = new TextButton("Resume", skin, "default");
        restartButton = new TextButton("Restart", skin, "default");
        exitButton = new TextButton("Exit", skin, "default");

        // Set up button listeners
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                togglePause(); // Resume the game
            }
        });

        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PlayScreen(game)); // Restart the game
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit(); // Exit the game
            }
        });

        // Create layout for buttons
        Table buttonTable = new Table();
        buttonTable.add(resumeButton).uniform().pad(10);
        buttonTable.row();
        buttonTable.add(restartButton).uniform().pad(10);
        buttonTable.row();
        buttonTable.add(exitButton).uniform().pad(10);
        buttonTable.row();

        // Add the button table to the window
        pauseWindow.add(buttonTable).center();
        pauseWindow.pack();

        // Add the window to the stage
        stage.addActor(pauseWindow);
        //isPaused=true;
    }

    public void togglePause() {
        isPaused = !isPaused; // Toggle pause state
        pauseWindow.setVisible(isPaused); // Show or hide pause window
        Gdx.input.setInputProcessor(isPaused ? stage : playScreen.getStage()); // Set input processor
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); // Set input processor to this stage
    }

    @Override
    public void render(float delta) {
        playScreen.render(delta);

        if (isPaused) {
            stage.act(delta);
            stage.draw();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            togglePause();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Resize the stage
        pauseWindow.setPosition((width-pauseWindow.getWidth())/2, (height-pauseWindow.getHeight())/2);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose(); // Dispose of the stage
    }

    public Stage getStage() {
        return stage; // Get the stage for input handling
    }
}
