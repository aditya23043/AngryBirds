package com.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class levelVictoryScreen extends ScreenAdapter {

    int level_num;

    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private Table table;
    private BitmapFont level_font;
    private BitmapFont button_font;
    private BitmapFont credit_font;
    private Texture bg_img_texture;
    private Image bg_img;

    levelVictoryScreen(int level_num) {
        this.level_num = level_num;
    }

    @Override
    public void show() {

        // common inits
        viewport = new ExtendViewport(960, 540);
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("skins/shade/uiskin.json"));

        AssetsManager assetsManager = new AssetsManager();
        assetsManager.load_font();
        assetsManager.config(skin);

        // credits not needed as mentioned in the comments
        // https://in.pinterest.com/pin/255720085081889550/
        assetsManager.backgroundImage("img/victory_2.jpg");
        assetsManager.backgroundImage.setSize(960, 540);
        stage.addActor(assetsManager.backgroundImage);

        ImageButton next_level_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("img/next_level.png"))));
        next_level_button.setSize(60, 60);
        next_level_button.setPosition(480+75-30, 100);

        stage.addActor(next_level_button);
        next_level_button.addListener(new ClickListener(){
              
        });

        ImageButton replay_level_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("img/replay_level.png"))));
        replay_level_button.setSize(60, 60);
        replay_level_button.setPosition(480-30, 100);

        stage.addActor(replay_level_button);
        replay_level_button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelOne());
            }
        });

        ImageButton menu_select_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("img/menu_select.png"))));
        menu_select_button.setSize(60, 60);
        menu_select_button.setPosition(480-75-30, 100);

        stage.addActor(menu_select_button);
        menu_select_button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelManagerScreen((Game)Gdx.app.getApplicationListener()));
            }
        });

        Gdx.input.setInputProcessor(stage);
        
    }

    @Override
    public void render(float delta) {
        
        Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

    }




}

