package com.angrybirds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class Bird {

    private int x;
    private int y;

    private int width;
    private int height;

    private Texture birdTexture;
    private Image birdImage;

    protected Body body;
    protected World world;

    protected static final float PIXELS_PER_METER = 100f;

    private Color color;
    private int hits_dealt;

    public Bird(String texturepath, int width, int height, int x, int y, float scale, World world) {
        this.world = world;
        this.birdTexture = new Texture(texturepath);
        this.birdImage = new Image(birdTexture);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        birdImage.setScale(scale);
        birdImage.setPosition(x,y);
        initializePhysics(x / PIXELS_PER_METER, y / PIXELS_PER_METER);
    }

    protected void initializePhysics(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x,y);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PIXELS_PER_METER, height / 2 / PIXELS_PER_METER);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public Image getImage() {
        return this.birdImage;
    }

    abstract void dealDamage(int damage);

    abstract void move_bird(int damage);
}
