package com.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class LevelFailed implements Screen {
    private Image levelf;
    private Stage stage;
    private Skin skin;
    private Game game;
    private Viewport viewport;
    BitmapFont main_title_font;

    public LevelFailed(Game game, Skin skin, PlayScreen playScreen){
        this.game = game;
        this.skin = skin;
        viewport=new ExtendViewport(960, 540);
        viewport.apply();
        stage=new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        main_title_font = font_set("fonts/angrybirds-regular.ttf", 50, Color.GREEN, Color.BLACK, 1,1 );

        Label.LabelStyle custom_label = new Label.LabelStyle();
        custom_label.font = main_title_font;
        //custom_label.fontColor = Color.WHITE;

        AssetsManager assetsManager = new AssetsManager();
        assetsManager.load_font();
        assetsManager.config(skin);

        skin.get(Label.LabelStyle.class).font = main_title_font;

        Table table= new Table();
        table.setFillParent(true);
        table.center();

        Label main_title = new Label("Level failed", custom_label);
        main_title.setAlignment(Align.center);
        table.add(main_title).center().expandX().padTop(30);
        table.row();

        Image failed_pig = assetsManager.loadImage("img/failed_pig.png");
        failed_pig.setScaling(Scaling.fit);
        failed_pig.setSize(50, 50);
        table.add(failed_pig).center().padBottom(30).expandX();
        table.row();

        Image restart = assetsManager.loadImage("img/menu_restart_button.png");
        Image exit = assetsManager.loadImage("img/menu_exit_button.png");

        restart.setSize(200, 60);
        restart.setScaling(Scaling.fit);

        exit.setSize(200, 60);
        exit.setScaling(Scaling.fit);

        table.add(restart).center().padBottom(20).width(200).height(60);
        table.row();
        table.add(exit).center().padBottom(70).width(200).height(60);

        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x,float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        restart.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game));
            }
        });

        stage.addActor(table);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 0.65f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }

    private BitmapFont font_set(String font_name, int font_size, Color color, Color shadow_color, int shadow_offset_x, int shadow_offset_y){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(font_name));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.shadowColor = shadow_color; // Shadow color
        parameter.shadowOffsetX = shadow_offset_x; // Horizontal offset for shadow
        parameter.shadowOffsetY = shadow_offset_y; // Vertical offset for shadow
        parameter.size = font_size;  // Set the font size
        parameter.color = color;  // Font color
        BitmapFont font = generator.generateFont(parameter);  // Generate BitmapFont
        generator.dispose();  // Dispose of the generator when done
        return font;
    }
}
