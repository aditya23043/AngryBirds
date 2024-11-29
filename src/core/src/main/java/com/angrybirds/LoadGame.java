package com.angrybirds;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadGame extends ScreenAdapter {

    private Skin skin;
    private Stage stage;
    private Viewport viewport;
    private Table table;
    private AssetsManager assetsManager;


    public LoadGame(Skin skin) {

        this.skin = skin;
        stage = new Stage();
        viewport = new ExtendViewport(960, 540);
        table = new Table();
        table.setFillParent(true);
        assetsManager = new AssetsManager();
        assetsManager.load_font();

        assetsManager.backgroundImage("img/redbg_level_selector.png");
        assetsManager.backgroundImage.setSize(960, 540);
        stage.addActor(assetsManager.backgroundImage);

    }

    public void show() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/angrybirds-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        // parameter.spaceX = -1;
        parameter.size = 30;
        parameter.borderWidth = 0;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.WHITE;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        parameter.shadowColor = new Color(0, 0f, 0, 0.75f);
        BitmapFont _font = generator.generateFont(parameter);
        LabelStyle label_style = new LabelStyle(_font, Color.WHITE);

        FreeTypeFontGenerator generator_2 = new FreeTypeFontGenerator(Gdx.files.internal("fonts/MesloLGSDZNerdFontMono-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter_2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        // parameter.spaceX = -1;
        parameter_2.size = 20;
        parameter_2.borderWidth = 0;
        parameter_2.color = Color.WHITE;
        parameter_2.borderColor = Color.BLACK;
        parameter_2.shadowOffsetX = 0;
        parameter_2.shadowOffsetY = 0;
        parameter_2.shadowColor = new Color(0, 0f, 0, 0.75f);
        BitmapFont _font_textfield = generator_2.generateFont(parameter_2);

        Texture back_texture = new Texture("img/back.png");
        Image back = new Image(back_texture);
        back.setPosition(10,480);
        back.setSize(50,50);
        stage.addActor(back);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen((Game)Gdx.app.getApplicationListener()));
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                back.addAction(Actions.alpha(0.7f));
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                back.addAction(Actions.alpha(1f));
            }
        });

        stage.addActor(table);

        // storing the configuration file in assets/ directory
        JFileChooser chooser = new JFileChooser(Gdx.files.internal("").file().getAbsolutePath());
        chooser.setFileFilter(new FileNameExtensionFilter("Angry Birds Config File", "dat"));
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION){


            // Label label = new Label("Config File: "+chooser.getSelectedFile().getName(), label_style);
            // label.setAlignment(Align.top);
            // stage.addActor(label);

            ConfData conf_data = new ConfData(chooser.getSelectedFile().getAbsoluteFile());

            if (chooser.getSelectedFile().getAbsoluteFile().length() == 0) {
                Label _label = new Label("Empty Configuration Selected!", label_style);
                _label.setPosition((960-_label.getWidth())/2, 540/2-20);
                stage.addActor(_label);
            }
            else {

                skin.get(TextField.TextFieldStyle.class).font = _font_textfield;

                TextField text_field = new TextField("", skin);
                text_field.setWidth(300);
                text_field.setHeight(50);
                text_field.setPosition((960 - text_field.getWidth()) / 2, 540/2-50);
                text_field.setAlignment(Align.center);

                Label _label = new Label("Enter the username with which the configuration file was saved", label_style);
                _label.setPosition((960-_label.getWidth())/2, 540/2+50);

                ImageButton submit_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("img/submit.png"))));
                submit_button.setSize(270, 60);
                submit_button.setPosition((960-submit_button.getWidth())/2, 540/2-150);

                Label bottom_label = new Label(" huh", label_style);
                bottom_label.setPosition((960-bottom_label.getWidth())/2, 540/2-200);
                stage.addActor(bottom_label);

                submit_button.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        for (ConfData _conf_data : conf_data.get_list()) {
                            if (_conf_data.get_username().equals(text_field.getText())) {
                                // auth for config done
                                break;
                            }
                            else if (_conf_data.equals(conf_data.get_list().get(conf_data.get_list().size() - 1))) {
                                bottom_label.setText("No user with that username found in this config file!");
                            }
                        }
                    }
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        submit_button.addAction(Actions.alpha(0.7f));
                    }
                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        submit_button.addAction(Actions.alpha(1f));
                    }
                });

                stage.addActor(_label);
                stage.addActor(text_field);
                stage.addActor(submit_button);
            }

        }
        else {
            Label label = new Label("No Configuration Selected!", label_style);
            label.setPosition((960-label.getWidth())/2, 540/2-20);
            stage.addActor(label);
        }

        Gdx.input.setInputProcessor(stage);
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
