package com.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class AssetsManager {

    public BitmapFont title_font;
    public BitmapFont title_font_large;
    public BitmapFont button_font;
    public BitmapFont credit_font;
    private static Music music;

    public Texture backgroundTexture;
    public Image backgroundImage;

    public void load_font(){

        title_font_large = font_set("fonts/Ubuntu-M.ttf", 32, Color.WHITE, Color.WHITE, 0, 0);
        title_font = font_set("fonts/Ubuntu-M.ttf", 12, Color.WHITE, Color.WHITE, 0, 0);
        button_font = font_set("fonts/Ubuntu-M.ttf", 16, Color.WHITE, Color.BLACK, 3, 3);
        credit_font = font_set("fonts/Ubuntu-M.ttf", 8, Color.GRAY, Color.BLACK, 1, 1);

    }

    public void config(Skin skin) {
        skin.add("default-font", button_font);
        skin.add("font-label", title_font);
        skin.get(Label.LabelStyle.class).font = title_font;
        skin.get("subtitle", Label.LabelStyle.class).font = title_font;
        skin.get("default", Label.LabelStyle.class).font = credit_font;
        skin.get(TextButton.TextButtonStyle.class).font = button_font;
        skin.get(Window.WindowStyle.class).titleFont = title_font_large;
    }

    public void backgroundImage(String filepath) {
        this.backgroundTexture = new Texture(filepath);
        this.backgroundImage = new Image(backgroundTexture);
    }

    public Image loadImage(String filepath) {
        Texture texture = new Texture(filepath);
        Image image = new Image(texture);
        return image;
    }

    protected BitmapFont font_set(String font_name, int font_size, Color color, Color shadow_color, int shadow_offset_x, int shadow_offset_y){
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

    public void setMusic(Music _mus){
        music = _mus;
    }

    public static Music getMusic() {
        return music;
    }

}
