package com.angrybirds;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadGame extends ScreenAdapter {

    private Skin skin;
    private Stage stage;
    private Viewport viewport;
    private Table table;
    private AssetsManager assetsManager;


    public LoadGame(Skin skin) {

        this.skin = skin;
        stage = new Stage();
        viewport = new ExtendViewport(960, 540);
        table = new Table();
        table.setFillParent(true);
        assetsManager = new AssetsManager();
        assetsManager.load_font();

        assetsManager.backgroundImage("img/redbg_level_selector.png");
        assetsManager.backgroundImage.setSize(960, 540);
        stage.addActor(assetsManager.backgroundImage);

    }

    public void show() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AgaveNerdFont-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        // parameter.spaceX = -1;
        parameter.size = 20;
        parameter.borderWidth = 1;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.shadowOffsetX = 2;
        parameter.shadowOffsetY = 2;
        parameter.shadowColor = new Color(0, 0f, 0, 0.75f);
        BitmapFont _font = generator.generateFont(parameter);
        LabelStyle label_style = new LabelStyle(_font, Color.WHITE);

        Texture back_texture = new Texture("img/back.png");
        Image back = new Image(back_texture);
        back.setPosition(10,480);
        back.setSize(50,50);
        stage.addActor(back);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen((Game)Gdx.app.getApplicationListener()));
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                back.addAction(Actions.alpha(0.7f));
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                back.addAction(Actions.alpha(1f));
            }
        });

        // storing the configuration file in assets/ directory
        System.out.println(Gdx.files.internal("").file().getAbsolutePath());
        JFileChooser chooser = new JFileChooser(Gdx.files.internal("").file().getAbsolutePath());
        chooser.setFileFilter(new FileNameExtensionFilter("Angry Birds Config File", "dat"));
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            Label label = new Label("Config File: "+chooser.getSelectedFile().getName(), label_style);
            label.setAlignment(Align.top);
            stage.addActor(label);
            ConfData sample_data = new ConfData(chooser.getSelectedFile().getAbsoluteFile().toString());
            sample_data.write("Aditya Gautam", 20);
            sample_data.read();
        }
        // System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
        else {

        }

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
}
