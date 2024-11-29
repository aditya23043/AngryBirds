package com.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class Pig extends Actor {
    private int x;
    private int y;
    private int width;
    private int height;
    private Texture pigTexture;
    private Image pigImage;
    protected Body body;
    protected World world;
    protected static final float PIXELS_PER_METER = 100f;
    protected Vector2 position;
    boolean is_dead;
    PlayScreen playScreen;

    public Pig(String texturePath, int width, int height, int x, int y, float scale, World world, PlayScreen play) {
        this.world = world;
        this.pigTexture = new Texture(texturePath);
        this.pigImage = new Image(pigTexture);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.position = new Vector2(x, y);
        pigImage.setScale(scale);
        pigImage.setSize(width * scale, height * scale);
        this.playScreen=play;

        initializePhysics(x / PIXELS_PER_METER, y / PIXELS_PER_METER);
    }

    protected void initializePhysics(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f / PIXELS_PER_METER, height / 2f / PIXELS_PER_METER);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);
        shape.dispose();
        body.setGravityScale(0);

        System.out.println("Pig physics initialized at: " + body.getPosition());
    }

    public void syncWithPhysics() {
        float posX = body.getPosition().x * PIXELS_PER_METER;
        float posY = body.getPosition().y * PIXELS_PER_METER;
        pigImage.setPosition(posX - pigImage.getWidth() / 2, posY - pigImage.getHeight() / 2);
    }

    public void draw(Batch batch, float parentAlpha) {
        syncWithPhysics();
        pigImage.draw(batch, parentAlpha);
    }

    public void act(float delta) {
        super.act(delta);
        update();
    }

    public void update() {
        syncWithPhysics();

        if (isOutOfBounds()) {
            is_dead=true;
            System.out.println("Pig is out of bounds and dies!");
            playScreen.incrementScore();
        }
    }

    private boolean isOutOfBounds() {
        float posX = body.getPosition().x * PIXELS_PER_METER;
        float posY = body.getPosition().y * PIXELS_PER_METER;

        return posX < 0 || posX > 960 || posY < 0 || posY > 540;
    }

    public void dispose() {
        if (pigTexture != null) {
            pigTexture.dispose();
        }
    }

    abstract void takeDamage(int damage);

    abstract boolean isDead();

    abstract void move_pig(int damage);

    public Image getImage() {
        return this.pigImage;
    }
}
