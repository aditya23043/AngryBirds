package com.angrybirds;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {

	private Skin skin;
	private Texture bg_texture;
	private Image bg_image;
	private Stage stage;
	private Table table;
    private BitmapFont font;

	@Override
	public void create() {

        // Generate font from the .ttf file
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/SF-Pro-Text-Medium.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;  // Set the font size
        parameter.color = Color.WHITE;  // Font color
        font = generator.generateFont(parameter);  // Generate BitmapFont
        generator.dispose();  // Dispose of the generator when done

		// BG IMAGE
		bg_texture = new Texture(Gdx.files.internal("img/vecteezy_background-for-presentation-green-grass-with-flower-under_17308322-1.jpg"));
		bg_image = new Image(bg_texture);
		bg_image.setFillParent(true);

		// LOADING SKIN
		skin = new Skin(Gdx.files.internal("skins/shade/uiskin.json"));

		// STAGE
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		// TABLE (LAYOUT)
		table = new Table();
		table.setFillParent(true);
		stage.addActor(bg_image);
		stage.addActor(table);

		// MAIN TITLE
		Label title_label = new Label("Angry Birds", skin, "title");
		title_label.setFontScale(2);

		// BUTTONS
		TextButton playButton = new TextButton("Play", skin);
		TextButton newLevelButton = new TextButton("New Level", skin);
		TextButton loadLevelButton = new TextButton("Load Level", skin);
		TextButton exitButton = new TextButton("Exit", skin);

		// PLACEMENT (not the btech one lol)
		table.top().pad(50);
		table.add(title_label).center().padBottom(50);
		table.row();
        table.add(playButton).width(200).padBottom(20);  // Set button width and padding
        table.row();
        table.add(newLevelButton).width(200).padBottom(20);
        table.row();
        table.add(loadLevelButton).width(200).padBottom(20);
        table.row();
        table.add(exitButton).width(200).padBottom(20);

		// QUIT EVENT
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();  // Quit the application when exit is clicked
            }
        });

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(.9f, .9f, .9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {
		skin.dispose();
		stage.dispose();
	}
}
