package com.angrybirds;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen extends ScreenAdapter {

    private AssetsManager assetsManager;
    private final Game game;
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private Table table;
    private Texture bg_img_texture;
    private Image bg_img;
    private PauseScreen pauseScreen;
    private World world;
    private Box2DDebugRenderer debug2D;
    private int birdnum=0;
    private Catapult catapult;
    private Bird r1;
    private RedBird r2;
    private RedBird r3;
    private RedBird r4;
    private Level level;


    public PlayScreen(Game game){
        this.game=game;
        this.stage=new Stage(new ExtendViewport(960, 540));
        this.skin=new Skin(Gdx.files.internal("skins/shade/uiskin.json"));
        this.pauseScreen=new PauseScreen(game, this, skin);
        world = new World(new Vector2(0, -9.8f), true);  // Gravity for Box2D world
        debug2D = new Box2DDebugRenderer();
        int level_num=1;
        LevelTwo levelOne= new LevelTwo(world);
        levelOne.add_birds();
        levelOne.add_pigs();
        levelOne.add_blocks();
        this.level= levelOne;
    }


    @Override
    public void show() {
        viewport = new ExtendViewport(960, 540);
        stage = new Stage(viewport);

        assetsManager = new AssetsManager();
        assetsManager.load_font();
        assetsManager.config(skin);
        assetsManager.backgroundImage("img/level1.jpg");
        assetsManager.backgroundImage.setSize(960, 540);
        stage.addActor(assetsManager.backgroundImage);

        // table
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // pause button
        ImageButton pause_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("img/pause.png"))));
        pause_button.setPosition(10, viewport.getWorldHeight()-60);
        pause_button.setSize(50, 50);

        stage.addActor(pause_button);

        pause_button.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                pauseScreen.togglePause();
                game.setScreen(pauseScreen);
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                pause_button.addAction(Actions.alpha(0.7f));
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                pause_button.addAction(Actions.alpha(1f));
            }
        });


        // level header
        Image level_title = assetsManager.loadImage("img/level_one_text.png");
        level_title.setScale(0.5f);
        level_title.setPosition(viewport.getWorldWidth()/2-70, viewport.getWorldHeight()-60);
        stage.addActor(level_title);

        // credits
        Label bg_credit = new Label("bg credits: https://in.pinterest.com/pin/616571005271619075/", skin, "default");
        bg_credit.setPosition(viewport.getWorldWidth()/2+100, 10);
        stage.addActor(bg_credit);

        Label stone_credit = new Label("stone texture credits: Image by kues1 on Freepik", skin, "default");
        stone_credit.setPosition(viewport.getWorldWidth()/2+100, 20);
        stage.addActor(stone_credit);

        Label wood_credit = new Label("wood texture credits: Image by kbza on Freepik", skin, "default");
        wood_credit.setPosition(viewport.getWorldWidth()/2+100, 30);
        stage.addActor(wood_credit);

        Label catapult_credit = new Label("catapult texture credits: https://www.klipartz.com/en/sticker-png-xulig", skin, "default");
        catapult_credit.setPosition(viewport.getWorldWidth()/2+100, 40);
        stage.addActor(catapult_credit);

        // catapult
        catapult = new Catapult(140, 174, 0.25f, world);
        stage.addActor(catapult);

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                catapult.startStretch(new Vector2(x, y));
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                catapult.updateStretch(new Vector2(x, y));
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Releasing");
                catapult.releaseStretch();
                if(birdnum<4){
                    calling_bird(birdnum);
                }
            }
        });

        for(Block block: level.get_blocks()){
            stage.addActor(block);
        }

        System.out.println("Completed");

        for(Bird bird: level.get_birds()){
            stage.addActor(bird);
        }

        for(Pig pig: level.get_pigs()){
            stage.addActor(pig);
        }

        r1=level.get_birds().get(0);
        r1.setIs_bounce(false);
        r1.set_jump(true);
        catapult.loadBird(r1);
        birdnum++;

        Button next_level = new Button(skin, "right");
        next_level.setPosition(viewport.getWorldWidth()-40, 10);
        next_level.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new levelVictoryScreen(1, 3));
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                next_level.addAction(Actions.alpha(0.7f));
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                next_level.addAction(Actions.alpha(1f));
            }
        });
        stage.addActor(next_level);

        Gdx.input.setInputProcessor(stage);

    }

    private TextButton button_add(String button_text) {
        TextButton button = new TextButton(button_text, skin);
        button.padTop(10);
        button.padBottom(10);
        button.padLeft(20);
        button.padRight(20);
        table.add(button).fillX().padBottom(10);
        table.row();
        return button;
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

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1/200f, 6, 2);

        stage.act(delta);
        stage.draw();

    }

    public InputProcessor getStage() {
        return stage;
    }

    public void calling_bird(int i){
        Bird trial= level.get_birds().get(i);
        if(catapult.isIs_empty()){
            trial.setIs_bounce(false);
            trial.set_jump(true);
            catapult.loadBird(trial);
        }
        birdnum++;
    }
}

