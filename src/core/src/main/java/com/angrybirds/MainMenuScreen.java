package com.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen extends ScreenAdapter {

    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private Table table;
    private BitmapFont font;

    @Override
    public void show() {
        // custom fonts
        BitmapFont main_title_font = font_set("fonts/FiraSansCondensed-Bold.ttf", 128, Color.WHITE);
        BitmapFont button_font = font_set("fonts/Ubuntu-M.ttf", 16, Color.WHITE);

        // viewport
        viewport = new ExtendViewport(960, 540);
        stage = new Stage(viewport);

        // skin
        skin = new Skin(Gdx.files.internal("skins/shade/uiskin.json"));
        skin.add("default-font", font);

        // Update styles to use custom font
        skin.get(Label.LabelStyle.class).font = font;  // Set default Label style font
        skin.get(TextButton.TextButtonStyle.class).font = font;  // Set default TextButton style font

        // table
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // main title
        Label main_title = new Label("Angry Birds", skin, "title");
        table.padTop(20);
        table.add(main_title).center().padBottom(50);
        table.row();

        // buttons
        button_add("Play").addListener(new ClickListener(){
            
        });
        button_add("Select Level");
        button_add("Help");
        button_add("Exit").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

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
    
    private BitmapFont font_set(String font_name, int font_size, Color color){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(font_name));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = font_size;  // Set the font size
        parameter.color = color;  // Font color
        font = generator.generateFont(parameter);  // Generate BitmapFont
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

