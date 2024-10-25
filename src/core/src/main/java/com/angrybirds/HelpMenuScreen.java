package com.angrybirds;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HelpMenuScreen extends ScreenAdapter {

    private Game game;
    private Skin skin;
    private Stage stage;
    private Viewport viewport;
    private AssetsManager assetsManager;
    private BitmapFont main_title_font;
    private Label.LabelStyle style_h1;
    private Label.LabelStyle style_h2;
    private Label.LabelStyle style_h3;
    private Table table;
    private int index = 0;

    private ArrayList<ArrayList<String>> help_list = new ArrayList<ArrayList<String>>();
    private Label title;
    private Label desc;
    private Image reference_img;

    HelpMenuScreen(Game game){
        this.game = game;
    }

    @Override
    public void show() {
        viewport = new ExtendViewport(960, 540);
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("skins/shade/uiskin.json"));

        assetsManager = new AssetsManager();
        assetsManager.load_font();
        assetsManager.config(skin);
        assetsManager.backgroundImage("img/redbgwo.jpg");
        assetsManager.backgroundImage.setSize(960, 540);
        stage.addActor(assetsManager.backgroundImage);

        table = new Table();
        table.setFillParent(true);
        table.align(Align.top);
        table.padTop(10);

        stage.addActor(table);

        // label styles
        style_h1 = new Label.LabelStyle();
        main_title_font = assetsManager.font_set("fonts/angrybirds-regular.ttf", 32, Color.WHITE, Color.BROWN, 3, 3);
        style_h1.font = main_title_font;
        style_h1.fontColor = Color.WHITE;

        style_h2 = new Label.LabelStyle();
        main_title_font = assetsManager.font_set("fonts/angrybirds-regular.ttf", 16, Color.WHITE, Color.BROWN, 3, 3);
        style_h2.font = main_title_font;
        style_h2.fontColor = Color.WHITE;

        style_h3 = new Label.LabelStyle();
        main_title_font = assetsManager.font_set("fonts/angrybirds-regular.ttf", 8, Color.WHITE, Color.BROWN, 3, 3);
        style_h3.font = main_title_font;
        style_h3.fontColor = Color.WHITE;

        Label main_title = new Label("HELP", style_h1);
        main_title.setAlignment(Align.center);
        // table.add(main_title).center().padBottom(10);
        // table.row();

        title = new Label("", style_h1);
        desc = new Label("", style_h2);
        reference_img = new Image();

        load_help_msgs();
        print_help_msg();


        Gdx.input.setInputProcessor(stage);
    }

    public void print_help_msg(){
        update_help_msg();

        ImageButton back_button = new ImageButton(new TextureRegionDrawable(new Texture("img/back.png")));
        back_button.setPosition(10,480);
        back_button.setSize(50,50);
        back_button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(back_button);

        table.clear();

        Table sub_table_top = new Table();
        sub_table_top.add(title).pad(10).center();
        sub_table_top.row();
        sub_table_top.add(desc).pad(10);
        sub_table_top.row();

        table.add(sub_table_top).pad(10);
        table.row();

        Table sub_table_bottom = new Table();

        ImageButton prev_button = new ImageButton(new TextureRegionDrawable(new Texture("img/back.png")));
        prev_button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                index = (index == 0) ? (help_list.size()-1) : (index-1);
                update_help_msg();
            }
        });
        sub_table_bottom.add(prev_button).size(64, 64).padRight(20).left();

        sub_table_bottom.add(reference_img).pad(10).maxWidth(640).maxHeight(360);

        ImageButton next_button = new ImageButton(new TextureRegionDrawable(new Texture("img/next.png")));
        next_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                index = (index == help_list.size() - 1) ? 0 : index + 1;
                update_help_msg();
            }
        });
        sub_table_bottom.add(next_button).size(64, 64).padRight(20).right();

        table.add(sub_table_bottom).pad(10);

    }

    public void update_help_msg() {
        title.setText(help_list.get(index).get(0));
        desc.setText(help_list.get(index).get(1));
        reference_img.setDrawable(new TextureRegionDrawable(new Texture(help_list.get(index).get(2))));
    }

    public void load_help_msgs() {
        help_list.add(new ArrayList<String>(Arrays.asList(
            "How to start the game?",
            "Click on the PLAY button in the main menu",
            "img/help_play.png"
        )));
        help_list.add(new ArrayList<String>(Arrays.asList(
            "How to open the help menu?",
            "Well! You are already in the help menu but sure, here you go, you have to click the HELP button in the main menu",
            "img/help_help.png"
        )));
        help_list.add(new ArrayList<String>(Arrays.asList(
            "How to exit the game? (the age old vim question isn't it?)",
            "Click the EXIT button in the main menu",
            "img/help_exit.png"
        )));
        help_list.add(new ArrayList<String>(Arrays.asList(
            "How to select levels?",
            "Click on any level which has been unlocked in the level selector menu",
            "img/help_level_select.png"
        )));
        help_list.add(new ArrayList<String>(Arrays.asList(
            "Why can't I click on these locks in the level selector?",
            "These are locked levels i.e. you need to complete the previous level in order to be able to play these levels",
            "img/help_level_locked.png"
        )));
        help_list.add(new ArrayList<String>(Arrays.asList(
            "How to select any other level after winning a level?",
            "Click on the button with menu icon on the victory screen",
            "img/help_reselect_level.png"
        )));
        help_list.add(new ArrayList<String>(Arrays.asList(
            "How to restart the current level after winning that level?",
            "Click on the button with restart icon on the victory screen",
            "img/help_restart_level.png"
        )));
        help_list.add(new ArrayList<String>(Arrays.asList(
            "How to proceed to another level after winning a level?",
            "Click on the button with forward icon on the victory screen",
            "img/help_next_level.png"
        )));
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            index = (index == help_list.size() - 1) ? 0 : index + 1;
            update_help_msg();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            index = (index == 0) ? (help_list.size() - 1) : (index - 1);
            update_help_msg();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

}
