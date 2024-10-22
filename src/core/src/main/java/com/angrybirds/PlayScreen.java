package com.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen extends ScreenAdapter {

    private final Game game;
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private Table table;
    private BitmapFont level_font;
    private BitmapFont button_font;
    private BitmapFont credit_font;
    private Texture bg_img_texture;
    private Image bg_img;
    private PauseScreen pauseScreen;

    public PlayScreen(Game game) {
        this.game=game;
        this.stage=new Stage(new ExtendViewport(960, 540));
        this.skin=new Skin(Gdx.files.internal("skins/shade/uiskin.json"));
        this.pauseScreen=new PauseScreen(game, this, skin);
    }


    @Override
    public void show() {
        viewport = new ExtendViewport(960, 540);
        stage = new Stage(viewport);
        level_font = font_set("fonts/Ubuntu-M.ttf", 12, Color.WHITE, Color.WHITE, 0, 0);
        button_font = font_set("fonts/Ubuntu-M.ttf", 16, Color.WHITE, Color.BLACK, 3, 3);
        credit_font = font_set("fonts/Ubuntu-M.ttf", 8, Color.GRAY, Color.BLACK, 1, 1);

        skin = new Skin(Gdx.files.internal("skins/shade/uiskin.json"));
        skin.add("default-font", button_font);
        skin.add("font-label", level_font);

        skin.get(Label.LabelStyle.class).font = level_font;
        skin.get("subtitle", Label.LabelStyle.class).font = level_font;
        skin.get("default", Label.LabelStyle.class).font = credit_font;
        skin.get(TextButton.TextButtonStyle.class).font = button_font;

        // background image
        bg_img_texture = new Texture("img/level1.jpg");
        bg_img = new Image(bg_img_texture);
        bg_img.setSize(960, 540);

        // table
        table = new Table();
        table.setFillParent(true);
        stage.addActor(bg_img);
        stage.addActor(table);

        // pause button
        Texture bg_pause = new Texture("img/pause.png");
        Image pause_button = new Image(bg_pause);
        pause_button.setPosition(0, viewport.getWorldHeight()-175);
        stage.addActor(pause_button);

        pause_button.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                pauseScreen.togglePause();
                game.setScreen(pauseScreen);
            }
        });


        // level header
        Label level_title = new Label("Level 1", skin, "title");
        level_title.setAlignment(1);
        level_title.setPosition(viewport.getWorldWidth()/2-60, viewport.getWorldHeight()-70);
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
        Texture catapult_tex = new Texture("img/catapult.png");
        Image catapult = new Image(catapult_tex);
        catapult.setScale(0.25f);;
        catapult.setPosition(100, 174);
        stage.addActor(catapult);

        // birds
        Texture red_bird_tex = new Texture("img/red.png");
        Image red_bird = new Image(red_bird_tex);
        red_bird.setScale(1.2f);
        red_bird.setPosition(60, 170);
        stage.addActor(red_bird);

        Texture red_bird_2_tex = new Texture("img/red.png");
        Image red_bird_2 = new Image(red_bird_2_tex);
        red_bird_2.setScale(1.2f);
        red_bird_2.setPosition(20, 170);
        stage.addActor(red_bird_2);

        // stone
        Texture stone_tex = new Texture("img/stone.png");
        Image stone = new Image(stone_tex);
        stone.setSize(50, 50);
        stone.setPosition(700, 174);
        stage.addActor(stone);
        Texture stone_2_tex = new Texture("img/stone.png");
        Image stone_2 = new Image(stone_2_tex);
        stone_2.setSize(50, 50);
        stone_2.setPosition(700, 224);
        stage.addActor(stone_2);

        // wood
        Texture wood_tex = new Texture("img/wood.png");
        Image wood = new Image(wood_tex);
        wood.setSize(50, 50);
        wood.setPosition(600, 174);
        stage.addActor(wood);
        Texture wood_2_tex = new Texture("img/wood.png");
        Image wood_2 = new Image(wood_2_tex);
        wood_2.setSize(50, 50);
        wood_2.setPosition(600, 224);
        stage.addActor(wood_2);

        // glass
        Texture glass_tex = new Texture("img/glass.png");
        Image glass = new Image(glass_tex);
        glass.setSize(50, 50);
        glass.setPosition(500, 174);
        stage.addActor(glass);
        Texture glass_2_tex = new Texture("img/glass.png");
        Image glass_2 = new Image(glass_2_tex);
        glass_2.setSize(50, 50);
        glass_2.setPosition(500, 224);
        stage.addActor(glass_2);

        // pigs
        Texture pig_1_tex = new Texture("img/pig1.png");
        Image pig_1 = new Image(pig_1_tex);
        pig_1.setScale(1f);
        pig_1.setSize(32, 32);
        pig_1.setPosition(500+9, 274);
        stage.addActor(pig_1);

        Texture pig_2_tex = new Texture("img/pig1.png");
        Image pig_2 = new Image(pig_2_tex);
        pig_2.setScale(1.5f);
        pig_2.setSize(32, 32);
        pig_2.setPosition(600, 274);
        stage.addActor(pig_2);

        Texture pig_3_tex = new Texture("img/pig1.png");
        Image pig_3 = new Image(pig_3_tex);
        pig_3.setScale(2f);
        pig_3.setSize(32, 32);
        pig_3.setPosition(700-9, 274);
        stage.addActor(pig_3);

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

        stage.act();
        stage.draw();

    }

    public InputProcessor getStage() {
        return stage;
    }

}
