package com.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LevelManagerScreen extends ScreenAdapter {
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private Texture levelbg_texture;
    private Image levelbg_img;
    private Preferences preferences;
    private static final int num_levels=3;
    private BitmapFont main_title_font;
    private Game game;
    private Label.LabelStyle custom_label;
    private Image back;
    private Texture back_texture;


    public LevelManagerScreen(Game game){
        this.game=game;
    }
    public void show() {
        viewport=new ExtendViewport(960, 540);
        viewport.apply();
        stage=new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        main_title_font = font_set("fonts/angrybirds-regular.ttf", 64, Color.WHITE, true);

        custom_label = new Label.LabelStyle();
        custom_label.font = main_title_font;
        custom_label.fontColor = Color.WHITE;

        skin=new Skin(Gdx.files.internal("skins/shade/uiskin.json"));
        preferences= Gdx.app.getPreferences("Level progress");
        skin.get(Label.LabelStyle.class).font = main_title_font;

        levelbg_texture=new Texture("img/Levels.png");
        levelbg_img=new Image(levelbg_texture);
        levelbg_img.setPosition(0,0);
        levelbg_img.setSize(960,540);
        stage.addActor(levelbg_img);
        back_texture=new Texture("img/back.png");
        back=new Image(back_texture);
        back.setPosition(3.5f,480);
        back.setSize(50,50);
        stage.addActor(back);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Table maintable=new Table();
        maintable.setFillParent(true);
        maintable.align(Align.top);
        maintable.padTop(80);

        Label main_title = new Label("Select Level", custom_label);
        main_title.setAlignment(Align.center);
        maintable.add(main_title).center().padBottom(30);
        maintable.row();
        Table buttontable= new Table();
        initialiseLevelButtons(buttontable);
        maintable.add(buttontable).center();
        stage.addActor(maintable);
    }

    public void initialiseLevelButtons(Table table){
        table.center();

        for(int i=0; i<num_levels; i++){
            boolean is_upgraded=preferences.getBoolean("Level"+(i+1), i==0);
            Texture textr= new Texture(is_upgraded ? "img/Levelnum.png" : "img/lock.png");
            Image levelbutton=new Image(textr);
            TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.font= font_set("fonts/angrybirds-regular.ttf", 38, Color.BLACK, false);
            textButtonStyle.downFontColor = Color.RED;

            TextButton level_num = new TextButton(String.valueOf(i + 1), textButtonStyle);
            level_num.getLabel().setAlignment(Align.center);
            level_num.setDisabled(!is_upgraded);

            if(is_upgraded){
                Stack stack = new Stack();
                stack.add(levelbutton);
                stack.add(level_num);

                // Optional: Center the label on the button
                stack.setSize(80,80);
                table.add(stack).size(110,110).pad(20);

            if(is_upgraded){
                stack.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
<<<<<<< HEAD
                        game.setScreen(new PlayScreen(game));
=======
                        game.setScreen(new LevelOne());
>>>>>>> f85c7c6 (victory screen done and some assets changed to match with the angry birds aesthetic)
                    }
                });
            }
            }
            else{
                table.add(levelbutton).size(150,150).pad(10);
            }
        }
    }

    public void unlock_next_level(int completed_level) {
        int nextLevel = completed_level + 1;
        if (nextLevel <= num_levels) {
            preferences.putBoolean("Level" + nextLevel, true);
            preferences.flush(); // Save the changes
        }
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        stage.dispose();
        levelbg_texture.dispose();
        skin.dispose();
    }

    private BitmapFont font_set(String font_name, int font_size, Color color, boolean with_shadow){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(font_name));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        if(with_shadow){
            parameter.shadowColor = Color.BROWN; // Shadow color
            parameter.shadowOffsetX = 5; // Horizontal offset for shadow
            parameter.shadowOffsetY = 5; // Vertical offset for shadow
        }
        parameter.size = font_size;  // Set the font size
        parameter.color = color;  // Font color
        BitmapFont font = generator.generateFont(parameter);  // Generate BitmapFont
        generator.dispose();  // Dispose of the generator when done
        return font;
    }
}

