package com.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PauseScreen implements Screen {
    private final Window pauseWindow;
    private final PlayScreen playScreen;
    private final Game game;
    private final Stage stage;
    private boolean isPaused = false;
    private Image volume;
    private int level_num;

    public PauseScreen(Game game, PlayScreen playScreen, Skin skin, int num){
        this.game = game;
        this.playScreen = playScreen;
        System.out.println("number is "+num);
        this.level_num=num;
        this.stage = new Stage(new ScreenViewport());
        this.pauseWindow = new Window("Paused", skin, "default");
        pauseWindow.setSize(600, 300);
        pauseWindow.setColor(new Color(0, 0, 0, 1f));

        pauseWindow.setPosition(
            (Gdx.graphics.getWidth() - pauseWindow.getWidth()) / 2,
            (Gdx.graphics.getHeight() - pauseWindow.getHeight()) / 2
        );
        pauseWindow.setVisible(false);

        AssetsManager assetsManager = new AssetsManager();

        // Create buttons
        Image pause_menu_bg = assetsManager.loadImage("img/pause_bg.png");
        pause_menu_bg.setSize(300, 400+10+10+7);
        pause_menu_bg.setPosition(Gdx.graphics.getWidth()/2-150, Gdx.graphics.getHeight()/2-150-20-7-20);

        Image translucent_bg = assetsManager.loadImage("img/translucent.png");

        ImageButton resume_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("img/menu_resume_button.png"))));
        resume_button.setPosition(Gdx.graphics.getWidth() / 2 - 150 + 20, Gdx.graphics.getHeight() / 2 - 150 + 300-87-10-10-20);

        ImageButton restart_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("img/menu_restart_button.png"))));
        restart_button.setPosition(Gdx.graphics.getWidth() / 2 - 150 + 20, Gdx.graphics.getHeight() / 2 - 150 + 300-87-10-10-87-10-20);

        ImageButton exit_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("img/menu_exit_button.png"))));
        exit_button.setPosition(Gdx.graphics.getWidth() / 2 - 150 + 20, Gdx.graphics.getHeight() / 2 - 150 + 300-87-10-10-87-10-87-10-20);

        TextureRegionDrawable vol = new TextureRegionDrawable(new TextureRegion(new Texture("img/menu_mute_button.png")));
        TextureRegionDrawable vol_mute = new TextureRegionDrawable(new TextureRegion(new Texture("img/menu_unmute_button.png")));
        volume = new Image(MuteStateManager.isMuted() ? vol_mute : vol);
        volume.setPosition(Gdx.graphics.getWidth() / 2 - 150 + 20, Gdx.graphics.getHeight() / 2 - 150 + 300-10-20);


        // Set up button listeners
        resume_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                togglePause(); // Resume the game
            }
        });
        resume_button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                resume_button.addAction(Actions.alpha(0.7f));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                resume_button.addAction(Actions.alpha(1f));
            }
        });

        restart_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("this is"+level_num);
                game.setScreen(new PlayScreen(game, level_num)); // Restart the game
            }
        });
        restart_button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                restart_button.addAction(Actions.alpha(0.7f));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                restart_button.addAction(Actions.alpha(1f));
            }
        });

        exit_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game)); // Exit the game
            }
        });
        exit_button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exit_button.addAction(Actions.alpha(0.7f));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exit_button.addAction(Actions.alpha(1f));
            }
        });
        volume.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MuteStateManager.setMuted(!MuteStateManager.isMuted());
                volume.setDrawable(MuteStateManager.isMuted() ? vol_mute : vol);
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                volume.addAction(Actions.alpha(0.7f));
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                volume.addAction(Actions.alpha(1f));
            }
        });

        stage.addActor(translucent_bg);
        stage.addActor(pause_menu_bg);
        stage.addActor(volume);
        stage.addActor(resume_button);
        stage.addActor(restart_button);
        stage.addActor(exit_button);
    }

    public void togglePause() {
        this.isPaused = !this.isPaused; // Toggle pause state
        pauseWindow.setVisible(this.isPaused); // Show or hide pause window
        Gdx.input.setInputProcessor(this.isPaused ? stage : playScreen.getStage()); // Set input processor
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
