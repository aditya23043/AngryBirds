package com.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class Pig{
    private int x;
    private int y;
    private int width;
    private int height;
    private Texture pigTexture;
    private Image pigImage;
    protected Body body;
    protected World world;
    protected static final float PIXELS_PER_METER = 100f;


    public Pig(String texturepath, int width, int height,int x, int y,float scale, World world){
        this.world = world;
        this.pigTexture= new Texture(texturepath);
        this.pigImage = new Image(pigTexture);
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        pigImage.setScale(scale);
        pigImage.setSize(width * scale, height * scale);
        pigImage.setPosition(x,y);
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

    public void update() {
    }

    public void dispose() {
    }
    abstract void takeDamage(int damage);
    abstract boolean isDead();
    abstract void move_pig(int damage);
}
