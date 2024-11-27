package com.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import static com.angrybirds.Pig.PIXELS_PER_METER;

public class Block extends Actor {
    private final Vector2 position;
    private World world;
    private Texture block_tex;
    private Image block_image;
    private boolean check;

    public Block(String texturePath, int width, int height, int x, int y, World world) {
        this.world = world;
        this.block_tex = new Texture(texturePath);
        this.block_image = new Image(block_tex);
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.position = new Vector2(x, y);
        block_image.setSize(width, height);
    }

    public Block(String type_exp, float scale, int x, int y, World world, boolean check, float rot) {
        this.world = world;
        String tempo="a";
        if(type_exp.equals("stone_thick")){
            tempo="img/stone_thick.png";
        }
        else if(type_exp.equals("glass_rect_long")){
            tempo="img/glass_rect_long.png";
        }
        else if(type_exp.equals("wood_rect_small")){
            tempo="img/wood_rect_small.png";
        }
        else if(type_exp.equals("glass_rect_med")){
            tempo="img/glass_rect_med.png";
        }
        else if(type_exp.equals("stone_circle")){
            tempo="img/stone_circle.png";
        }
        else if(type_exp.equals("wood_rect_med")){
            tempo="img/wood_rect_med.png";
        }
        else if(type_exp.equals("stone_rect_long")){
            tempo="img/stone_rect_long.png";
        }
        else if(type_exp.equals("glass_square")){
            tempo="img/glass_square.png";
        }
        this.block_tex = new Texture(tempo);
        this.block_image = new Image(block_tex);
        this.setX(x);
        this.setY(y);
        block_image.setScale(scale);
        this.setRotation(rot);
        this.position = new Vector2(x, y);
        this.check=true;
    }

    public void draw(Batch batch, float parentAlpha) {
        block_image.setPosition(getX(), getY());
        block_image.draw(batch, parentAlpha);
        block_image.setRotation(getRotation());
        //block_image.setSize(getWidth(), getHeight());
    }

    public void stone_circle(){
        block_tex=new Texture("img/stone_circle.png");
        block_image= new Image(block_tex);
        block_image.setSize(50,50);
    }

    public void stone_thick(){
        block_tex=new Texture("img/stone_thick.png");
        block_image= new Image(block_tex);
        block_image.setSize(20,98);
        block_image.setRotation(90);
    }

    public void glass_rect_med(){
        block_tex=new Texture("img/glass_rect_med.png");
        block_image= new Image(block_tex);
    }

}
