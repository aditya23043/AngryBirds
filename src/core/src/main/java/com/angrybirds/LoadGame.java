package com.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
        skin.get(TextButton.TextButtonStyle.class).font = input_font;

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
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                back.addAction(Actions.alpha(0.7f));
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                back.addAction(Actions.alpha(1f));
            }
        });


        Image next_button= assetsManager.loadImage("img/menu_item.png");
        Label next_label = new Label("        Next", skin);

        Stack stack = new Stack();
        stack.add(next_button);
        stack.add(next_label);

        table.add(stack).center().padBottom(50);

        stack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openSavedGamesWindow();
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                stack.addAction(Actions.alpha(0.7f));
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                stack.addAction(Actions.alpha(1f));
            }
        });

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    private void openSavedGamesWindow() {
        Image translucent_bg = assetsManager.loadImage("img/translucent.png");
        stage.addActor(translucent_bg);
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = main_title_font;

        final Window savedGamesWindow = new Window("", windowStyle);
        savedGamesWindow.setSize(400, 400); // Set window size
        savedGamesWindow.setPosition(Gdx.graphics.getWidth()/2-150-40, Gdx.graphics.getHeight()/2-150-50);
        savedGamesWindow.center();

        Image backgroundImage = assetsManager.loadImage("img/pause_bg.png");
        backgroundImage.setSize(savedGamesWindow.getWidth(), savedGamesWindow.getHeight());
        savedGamesWindow.addActor(backgroundImage);

        savedGamesWindow.add(new Label("Saved Games", skin)).padTop(15);
        savedGamesWindow.row();

        Table contentTable = new Table();
        contentTable.top();
        savedGamesWindow.addActor(contentTable);


        String[] savedGames = {"Game 1", "Game 2", "Game 3", "Game 4", "Game 5", "Game 6", "Game 7", "Game 8", "Game 9", "Game 10"};

        for (String game : savedGames) {
            TextButton gameButton = new TextButton(game, skin);
            gameButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Loading " + game); // Simulate loading game
                    savedGamesWindow.remove(); // Close the window after selection
                }
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    gameButton.addAction(Actions.alpha(0.7f));
                }
                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    gameButton.addAction(Actions.alpha(1f));
                }
            });
            contentTable.top();
            contentTable.add(gameButton).width(250).padBottom(1.5f);
            contentTable.row();
        }
        ScrollPane scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.layout();
        scrollPane.setScrollY(0);

        savedGamesWindow.add(scrollPane).expand().fill().height(250);
        savedGamesWindow.row();

        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                savedGamesWindow.remove();
                translucent_bg.remove();
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                closeButton.addAction(Actions.alpha(0.7f));
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                closeButton.addAction(Actions.alpha(1f));
            }
        });

        savedGamesWindow.add(closeButton).padTop(10).width(100);

        stage.addActor(savedGamesWindow); // Add the window to the stage
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
