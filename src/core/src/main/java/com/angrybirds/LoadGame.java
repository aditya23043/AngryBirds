package com.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadGame extends ScreenAdapter {
    private Skin skin;
    private Stage stage;
    private Viewport viewport;
    private Table table;
    private BitmapFont main_title_font;
    private BitmapFont input_font;
    private AssetsManager assetsManager;
    private BitmapFont dialog_font;


    public LoadGame(Skin skin) {
        this.skin=skin;
        stage= new Stage();
        viewport = new ExtendViewport(960, 540);
        table = new Table();
        table.setFillParent(true);
        //stage.addActor(table);

        main_title_font = font_set("fonts/angrybirds-regular.ttf", 55, Color.BLACK, Color.WHITE, 2,2);

        dialog_font = font_set("fonts/angrybirds-regular.ttf", 55, Color.WHITE, Color.WHITE, 2,2);


        skin.get(Label.LabelStyle.class).font = main_title_font;

        input_font= font_set("fonts/SF-Pro-Text-Medium.otf", 24, Color.WHITE, Color.BLACK, 0,0);
        skin.get(TextField.TextFieldStyle.class).font = input_font;

        assetsManager = new AssetsManager();
        assetsManager.backgroundImage("img/bg_load23.JPG");
        assetsManager.backgroundImage.setSize(960, 540);
        stage.addActor(assetsManager.backgroundImage);

    }

    public void show() {
        table.add(new Label("Enter name", skin)).padBottom(10);
        table.row();

        // Create TextField for name entry
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = input_font;
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.cursor = skin.newDrawable("cursor", Color.BLACK);
        textFieldStyle.selection = skin.newDrawable("text-selection", Color.WHITE);
        textFieldStyle.background = skin.newDrawable("textfield", Color.DARK_GRAY);
        TextField nameField = new TextField("", textFieldStyle);
        table.add(nameField).width(300).height(40).padTop(20).padBottom(30);
        table.row();

        Image back=assetsManager.loadImage("img/back.png");
        back.setPosition(3.5f,480);
        back.setSize(50,50);
        stage.addActor(back);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen((Game) Gdx.app.getApplicationListener()));
            }
        });


        Image next_button= assetsManager.loadImage("img/menu_item.png");
        Label next_label = new Label("        Next", skin);

        Stack stack = new Stack();
        stack.add(next_button);
        stack.add(next_label);

        table.add(stack).center().padBottom(50);

        skin.get(Window.WindowStyle.class).titleFont = dialog_font;
        skin.get(List.ListStyle.class).font = input_font;

        stack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog savedGamesDialog = new Dialog("Saved Games", skin) {
                    @Override
                    protected void result(Object object) {
                    }
                };

                String[] savedGames = {"Game 1", "Game 2", "Game 3", "Game 4", "Game 5", "Game 6", "Game 7", "Game 8", "Game 9", "Game 10"};
                List<String> gameList = new List<>(skin);
                gameList.setItems(savedGames);

                ScrollPane scrollPane = new ScrollPane(gameList, skin);
                scrollPane.setScrollingDisabled(false, false);  // Enable both horizontal and vertical scrolling
                scrollPane.setFlickScroll(true);  // Allow flicking for smoother scrolling
                scrollPane.setForceScroll(false, true);  // Ensure vertical scroll is always allowed
                scrollPane.setFadeScrollBars(false);  // Keep scroll bars visible

                // Add ScrollPane to the dialog's content table
                savedGamesDialog.getContentTable().add(scrollPane).width(400).height(250).pad(10);

                savedGamesDialog.button("Load", true);
                savedGamesDialog.button("Cancel", false);
                savedGamesDialog.show(stage);
            }
        });


        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
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

    public void dispose() {
        stage.dispose();
    }
}
