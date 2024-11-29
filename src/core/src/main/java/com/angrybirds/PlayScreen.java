package com.angrybirds;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.ArrayList;
import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

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
    private int birdnum = 0;
    private Catapult catapult;
    private Bird r1;
    private Level level = new LevelOne(world);
    private ArrayList<Body> bodies_list;
    private GameContactListener contactListener;
    private int level_num;
    private BitmapFont main_title_font;
    private Label.LabelStyle style_h1;
    private int score = 0; // Add score variable
    private Label scoreLabel; // Add score label

    public PlayScreen(Game game, int num) {
        this.game = game;
        this.stage = new Stage(new ExtendViewport(960, 540));
        this.skin = new Skin(Gdx.files.internal("skins/shade/uiskin.json"));
        world = new World(new Vector2(0, -9.8f), true);
        debug2D = new Box2DDebugRenderer();
        this.level_num = num;
        this.pauseScreen = new PauseScreen(game, this, skin, level_num);
        if (level_num == 1) {
            LevelOne levelOne = new LevelOne(world);
            levelOne.add_birds();
            levelOne.add_pigs();
            levelOne.add_blocks();
            this.level = levelOne;
        } else if (level_num == 2) {
            LevelTwo levelOne = new LevelTwo(world);
            levelOne.add_birds();
            levelOne.add_pigs();
            levelOne.add_blocks();
            this.level = levelOne;
        } else if (level_num == 3) {
            LevelThree levelOne = new LevelThree(world);
            levelOne.add_birds();
            levelOne.add_pigs();
            levelOne.add_blocks();
            this.level = levelOne;
        }
        this.bodies_list = new ArrayList<>();
        contactListener = new GameContactListener(bodies_list);
        world.setContactListener(contactListener);
    }

    public PlayScreen(Game game, int num, int num_birds_left, int pigs_left, ArrayList<String> blocks_left) {
        this.game=game;
        this.stage=new Stage(new ExtendViewport(960, 540));
        this.skin=new Skin(Gdx.files.internal("skins/shade/uiskin.json"));
        world = new World(new Vector2(0, -9.8f), true);
        debug2D = new Box2DDebugRenderer();
        this.level_num=num;
        System.out.println(level_num);
        this.pauseScreen=new PauseScreen(game, this, skin, level_num);
        if(level_num==1){
            LevelOne levelOne= new LevelOne(world);
            levelOne.add_birds(num_birds_left);
            levelOne.add_pigs(pigs_left);
            levelOne.add_blocks(blocks_left);
            this.level= levelOne;
            if(level==null){
                System.out.println("Here");
            }
            else{
                System.out.println("Here now");
            }
        }
        else if(level_num==2){
            LevelTwo levelOne= new LevelTwo(world);
            levelOne.add_birds();
            levelOne.add_pigs();
            levelOne.add_blocks();
            this.level= levelOne;
        }
        else if(level_num==3){
            LevelThree levelOne= new LevelThree(world);
            levelOne.add_birds();
            levelOne.add_pigs();
            levelOne.add_blocks();
            this.level= levelOne;
        }
        this.bodies_list= new ArrayList<>();
        contactListener = new GameContactListener(bodies_list);
        world.setContactListener(contactListener);
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
        pause_button.setPosition(10, viewport.getWorldHeight() - 60);
        pause_button.setSize(50, 50);
        stage.addActor(pause_button);

        pause_button.addListener(new ClickListener() {
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

        // load game button
        ImageButton load_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("img/load.png"))));
        load_button.setPosition(10+50+10, viewport.getWorldHeight()-60);
        load_button.setSize(50, 50);

        stage.addActor(load_button);

        load_button.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {


                FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/angrybirds-regular.ttf"));
                FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                // parameter.spaceX = -1;
                parameter.size = 30;
                parameter.borderWidth = 1;
                parameter.color = Color.WHITE;
                parameter.borderColor = Color.BLACK;
                parameter.shadowOffsetX = 3;
                parameter.shadowOffsetY = 3;
                parameter.shadowColor = new Color(0, 0f, 0, 0.75f);
                BitmapFont _font = generator.generateFont(parameter);
                LabelStyle label_style = new LabelStyle(_font, Color.WHITE);

                FreeTypeFontGenerator generator_2 = new FreeTypeFontGenerator(Gdx.files.internal("fonts/angrybirds-regular.ttf"));
                FreeTypeFontGenerator.FreeTypeFontParameter parameter_2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
                // parameter.spaceX = -1;
                parameter_2.size = 20;
                parameter_2.borderWidth = 1;
                parameter_2.color = Color.WHITE;
                parameter_2.borderColor = Color.BLACK;
                parameter_2.shadowOffsetX = 3;
                parameter_2.shadowOffsetY = 3;
                parameter_2.shadowColor = new Color(0, 0f, 0, 0.75f);
                BitmapFont _font_2 = generator_2.generateFont(parameter);
                LabelStyle label_style_normal = new LabelStyle(_font, Color.WHITE);

                Image translucent_bg = assetsManager.loadImage("img/translucent.png");
                Image pause_menu_bg = assetsManager.loadImage("img/pause_bg.png");
                pause_menu_bg.setSize(300, 400+10+10+7);
                pause_menu_bg.setPosition(Gdx.graphics.getWidth()/2-150, Gdx.graphics.getHeight()/2-150-20-7-20);

                Label title = new Label("Save Game", label_style);
                title.setPosition((Gdx.graphics.getWidth()-title.getWidth())/2, Gdx.graphics.getHeight()/2+165);

                Label username = new Label("Username:", label_style_normal);
                username.setPosition((Gdx.graphics.getWidth()-title.getWidth())/2-50, Gdx.graphics.getHeight()/2+50);

                TextField text_field = new TextField("", skin);
                text_field.setWidth(200);
                text_field.setHeight(50);
                text_field.setPosition((Gdx.graphics.getWidth()-title.getWidth())/2-50, Gdx.graphics.getHeight()/2);
                text_field.setAlignment(Align.center);

                ImageButton select_file = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("img/submit.png"))));
                select_file.setSize(180, 40);
                select_file.setPosition((Gdx.graphics.getWidth()-select_file.getWidth())/2-50, Gdx.graphics.getHeight()/2-100);
                select_file.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        level.save(text_field.getText());
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new PlayScreen((Game)Gdx.app.getApplicationListener(), level_num));
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        select_file.addAction(Actions.alpha(0.7f));
                    }
                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        select_file.addAction(Actions.alpha(1f));
                    }
                });

                stage.addActor(translucent_bg);
                stage.addActor(pause_menu_bg);
                stage.addActor(title);
                stage.addActor(username);
                stage.addActor(text_field);
                stage.addActor(select_file);
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                load_button.addAction(Actions.alpha(0.7f));
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                load_button.addAction(Actions.alpha(1f));
            }
        });

        style_h1 = new Label.LabelStyle();
        main_title_font = assetsManager.font_set("fonts/angrybirds-regular.ttf", 42, Color.WHITE, Color.BROWN, 3, 3);
        style_h1.font = main_title_font;
        style_h1.fontColor = Color.WHITE;

        // level header
        Label level_title = new Label("Level  " + level_num, style_h1);
        level_title.setScale(0.5f);
        level_title.setPosition(viewport.getWorldWidth() / 2 - 70, viewport.getWorldHeight() - 60);
        stage.addActor(level_title);

        // credits
        Label bg_credit = new Label("bg credits: https://in.pinterest.com/pin/616571005271619075/", skin, "default");
        bg_credit.setPosition(viewport.getWorldWidth() / 2 + 100, 10);
        stage.addActor(bg_credit);

        Label stone_credit = new Label("stone texture credits: Image by kues1 on Freepik", skin, "default");
        stone_credit.setPosition(viewport.getWorldWidth() / 2 + 100, 20);
        stage.addActor(stone_credit);

        Label wood_credit = new Label("wood texture credits: Image by kbza on Freepik", skin, "default");
        wood_credit.setPosition(viewport.getWorldWidth() / 2 + 100, 30);
        stage.addActor(wood_credit);

        Label catapult_credit = new Label("catapult texture credits: https://www.klipartz.com/en/sticker-png-xulig", skin, "default");
        catapult_credit.setPosition(viewport.getWorldWidth() / 2 + 100, 40);
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
                catapult.releaseStretch();
                if (birdnum < 4) {
                    calling_bird(birdnum);
                }
            }
        });

        for (Block block : level.get_blocks()) {
            stage.addActor(block);
            add_bodies(block.body);
        }

        for (Bird bird : level.get_birds()) {
            stage.addActor(bird);
            add_bodies(bird.body);
        }

        for (Pig pig : level.get_pigs()) {
            stage.addActor(pig);
            add_bodies(pig.body);
        }

        r1 = level.get_birds().get(0);
        r1.setIs_bounce(false);
        r1.set_jump(true);
        catapult.loadBird(r1);
        birdnum++;

        // Score display
        scoreLabel = new Label("Score: " + score, skin);
        scoreLabel.setPosition(viewport.getWorldWidth() - 120, viewport.getWorldHeight() - 30);
        stage.addActor(scoreLabel);

        Gdx.input.setInputProcessor(stage);
    }

    public void incrementScore() {
        score++;
        scoreLabel.setText("Score: " + score);
    }

    public void checkGameOver() {
        boolean allPigsDead = true;
        for (Pig pig : level.get_pigs()) {
            if (!pig.isDead()) {
                allPigsDead = false;
                break;
            }
        }

        boolean allBirdsUsed = birdnum >= level.get_birds().size();

        if (allPigsDead){
            game.setScreen(new levelVictoryScreen(level_num, 2));
        }

        if(allBirdsUsed && !allPigsDead){
            game.setScreen(new LevelFailed(game, skin,this, level_num));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1 / 200f, 6, 2);
        stage.act(delta);
        stage.draw();
    }

    public void calling_bird(int i) {
        Bird trial = level.get_birds().get(i);
        if (catapult.isIs_empty()) {
            trial.setIs_bounce(false);
            trial.set_jump(true);
            catapult.loadBird(trial);
        }
        birdnum++;
    }

    public void add_bodies(Body body) {
        bodies_list.add(body);
    }

    public ArrayList<Body> get_bodies_list() {
        return bodies_list;
    }

    public InputProcessor getStage() {
        return stage;
    }
}
