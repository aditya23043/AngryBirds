package com.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen extends ScreenAdapter {

    private final Game game;
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private Table table;
    private BitmapFont main_title_font;
    private BitmapFont button_font;
    private Texture bg_img_texture;
    private Image bg_img;
    private AssetsManager assetsManager;
    private Image volume;


    public MainMenuScreen(Game game) {
        this.game = game;
        assetsManager= new AssetsManager();
    }

    @Override
    public void show() {

        // MUSIC (BONUS)
        Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        if (!MuteStateManager.isMuted()) {
          music.play();
        }

        assetsManager.setMusic(music);

        // custom fonts
        main_title_font = font_set("fonts/angrybirds-regular.ttf", 38, Color.BLACK);
        button_font = font_set("fonts/Ubuntu-M.ttf", 16, Color.WHITE);

        // viewport
        viewport = new ExtendViewport(960, 540);
        stage = new Stage(viewport);

        // skin
        skin = new Skin(Gdx.files.internal("skins/shade/uiskin.json"));
        skin.add("default-font", button_font);
        skin.add("font-label", main_title_font);

        // Update styles to use custom font
        skin.get(Label.LabelStyle.class).font = main_title_font;  // Set default Label style font
        skin.get("title", Label.LabelStyle.class).font = main_title_font;  // Set default Label style font
        skin.get(TextButton.TextButtonStyle.class).font = button_font;  // Set default TextButton style font

        // background image
        bg_img_texture = new Texture("img/redbg.jpg");
        bg_img = new Image(bg_img_texture);
        bg_img.setSize(960, 540);

        // table
        table = new Table();
        table.setFillParent(true);
        stage.addActor(bg_img);
        stage.addActor(table);
        table.padTop(100);

        // buttons
        ImageButton play_button = button_add("img/main_menu_play.png");
        play_button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelManagerScreen(game));
                dispose();
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                play_button.addAction(Actions.alpha(0.7f));
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                play_button.addAction(Actions.alpha(1f));
            }
        });
        //ImageButton load_button = button_add("img/main_menu_select_level.png");

        Image saved_button= assetsManager.loadImage("img/main_menu_bg.png");
        saved_button.setSize(313,66);
        Label save_label = new Label("       Saved Games", skin);

        Stack stack = new Stack();
        stack.add(saved_button);
        stack.add(save_label);

        table.add(stack).center().fillX().width(313).height(66).padBottom(10);
        table.row();
        stack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LoadGame(skin));
                dispose();
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                stack.addAction(Actions.alpha(0.7f));
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                stack.addAction(Actions.alpha(1f));
            }
        });


        ImageButton help_button = button_add("img/main_menu_help.png");
        help_button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HelpMenuScreen(game));
                dispose();
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                help_button.addAction(Actions.alpha(0.7f));
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                help_button.addAction(Actions.alpha(1f));
            }
        });

        ImageButton exit_button = button_add("img/main_menu_exit.png");
        exit_button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exit_button.addAction(Actions.alpha(0.7f));
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exit_button.addAction(Actions.alpha(1f));
            }
        });

        TextureRegionDrawable volumedown = new TextureRegionDrawable(new TextureRegion(new Texture("img/volume_mute.png")));
        TextureRegionDrawable volumeup = new TextureRegionDrawable(new TextureRegion(new Texture("img/volume.png")));
        volume = new Image(MuteStateManager.isMuted() ? volumedown : volumeup);

        volume.setPosition(3.5f,480);
        volume.setSize(50,50);
        stage.addActor(volume);
        volume.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MuteStateManager.setMuted(!MuteStateManager.isMuted());
                volume.setDrawable(MuteStateManager.isMuted() ? volumedown : volumeup);
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                volume.addAction(Actions.alpha(0.7f));
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                volume.addAction(Actions.alpha(1f));
            }

        });

        AssetsManager assetsManager = new AssetsManager();
        ImageButton github_button = new ImageButton(new TextureRegionDrawable(new Texture("img/github.png")));
        github_button.setPosition(10, 10);
        github_button.setSize(64, 64);
        github_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://github.com/aditya23043/AngryBirds/");
            }
        });
        stage.addActor(github_button);

        Gdx.input.setInputProcessor(stage);

    }

    private ImageButton button_add(String img_path) {
        ImageButton button = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(img_path))));
        table.add(button).fillX().padBottom(10);
        table.row();
        return button;
    }

    private BitmapFont font_set(String font_name, int font_size, Color color){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(font_name));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.shadowColor = Color.BLACK; // Shadow color
        parameter.shadowOffsetX = 0; // Horizontal offset for shadow
        parameter.shadowOffsetY = 0; // Vertical offset for shadow
        parameter.size = font_size;  // Set the font size
        parameter.color = color;  // Font color
        BitmapFont font = generator.generateFont(parameter);  // Generate BitmapFont
        generator.dispose();  // Dispose of the generator when done
        return font;
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

}

