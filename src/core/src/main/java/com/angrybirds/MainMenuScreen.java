package com.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

    public MainMenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        // custom fonts
        main_title_font = font_set("fonts/OleoScriptSwashCaps-Regular.ttf", 48, Color.WHITE);
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
        button_add("img/main_menu_play.png").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelManagerScreen(game));
                dispose();
            }
        });
        button_add("img/main_menu_select_level.png");
        button_add("img/main_menu_help.png");
        button_add("img/main_menu_exit.png").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

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
        parameter.shadowOffsetX = 3; // Horizontal offset for shadow
        parameter.shadowOffsetY = 3; // Vertical offset for shadow
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

