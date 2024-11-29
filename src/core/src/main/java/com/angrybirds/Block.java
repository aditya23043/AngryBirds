package com.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Block extends Actor {
    private final Vector2 position;
    private World world;
    private Texture block_tex;
    private Image block_image;
    protected Body body;  // Box2D body for collision detection

    public static final float PIXELS_PER_METER = 100f;

    public Block(String type_exp, float scale, int x, int y, World world, boolean check, int rot) {
        this.world = world;
        String tempo = "img/default.png";
        switch (type_exp) {
            case "stone_thick":
                tempo = "img/stone_thick.png";
                break;
            case "glass_rect_long":
                tempo = "img/glass_rect_long.png";
                break;
            case "wood_rect_small":
                tempo = "img/wood_rect_small.png";
                break;
            case "glass_rect_med":
                tempo = "img/glass_rect_med.png";
                break;
            case "stone_circle":
                tempo = "img/stone_circle.png";
                break;
            case "wood_rect_med":
                tempo = "img/wood_rect_med.png";
                break;
            case "stone_rect_long":
                tempo = "img/stone_rect_long.png";
                break;
            case "glass_square":
                tempo = "img/glass_square.png";
                break;
        }
        this.block_tex = new Texture(tempo);
        this.block_image = new Image(block_tex);
        this.setX(x);
        this.setY(y);
        this.setScale(scale);
        this.setRotation(rot);
        this.position = new Vector2(x, y);
        createBody(x, y, (int)(block_image.getWidth() * scale), (int)(block_image.getHeight() * scale));
    }

    private void createBody(int x, int y, int width, int height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / PIXELS_PER_METER, y / PIXELS_PER_METER);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / (2f * PIXELS_PER_METER), height / (2f * PIXELS_PER_METER));

        FixtureDef blockFixtureDef = new FixtureDef();
        blockFixtureDef.shape = shape;
        blockFixtureDef.density = 1.0f;
        blockFixtureDef.restitution = 0.5f;
        blockFixtureDef.filter.categoryBits = 0x0001;
        blockFixtureDef.filter.maskBits = 0x0002;

        body.createFixture(blockFixtureDef);
        shape.dispose();
        body.setGravityScale(0);

        body.setUserData(this);  // Allows identification of the block later
    }

    public void activateDynamicBody() {
        if (body.getType() != BodyDef.BodyType.DynamicBody) {
            body.setType(BodyDef.BodyType.DynamicBody);
            body.setGravityScale(1.0f);
        }
    }

    public void disposeBlock() {
        world.destroyBody(body);
        block_tex.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x * PIXELS_PER_METER - getWidth() / 2,
            body.getPosition().y * PIXELS_PER_METER - getHeight() / 2);
        block_image.setPosition(getX(), getY());
        block_image.setRotation(getRotation());
        block_image.draw(batch, parentAlpha);
    }
}
