package com.angrybirds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

public abstract class Bird extends Actor {

    private int width;
    private int height;

    private Texture birdTexture;
    private Image birdImage;

    protected Body body;
    protected World world;

    protected static final float PIXELS_PER_METER = 100f;

    private float bounceOffset = 0f;
    private float bounceSpeed = 4f; // Adjust for faster or slower bounce
    private float bounceAmplitude = 5f; // Adjust for higher or lower bounce
    private float time = 0f;
    private Vector2 initialPosition;
    private float timeOffset;
    private boolean init_begin;
    private boolean jumping= false;
    private Vector2 cata_pos;
    private float jump_amp;
    private String name;
    private boolean is_bounce=false;

    public Bird(String texturepath, int width, int height, int x, int y, float scale, World world, String name) {
        this.world = world;
        this.birdTexture = new Texture(texturepath);
        this.birdImage = new Image(birdTexture);
        this.width = width;
        this.height = height;
        this.init_begin=true;
        this.name=name;
        this.jumping=false;
        birdImage.setScale(scale);
        this.initialPosition = new Vector2(x, y);
        this.timeOffset = (float) (Math.random() * 2 * Math.PI);
        this.cata_pos=new Vector2(130, 275);
        this.jump_amp=2f;

        initializePhysics(x / PIXELS_PER_METER, y / PIXELS_PER_METER);
    }

    public void draw(Batch batch, float parentAlpha) {
        birdImage.setPosition(getX(), getY());
        birdImage.draw(batch, parentAlpha);
    }

    protected void initializePhysics(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Dynamic body allows gravity and movement
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PIXELS_PER_METER, height / 2 / PIXELS_PER_METER);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f; // Determines the bird’s mass
        fixtureDef.restitution = 0.5f; // Makes the bird bouncy

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    abstract void dealDamage(int damage);

    abstract void move_bird(int damage);

    @Override
    public void act(float delta) {
        super.act(delta);

        if(init_begin && is_bounce){
            // Update bounce time with a speed factor
            time += delta * bounceSpeed;

            // Calculate the bounce offset using a sine wave for a smooth bounce effect
            bounceOffset = (float) Math.sin(time + timeOffset) * bounceAmplitude;

            // Update the position based on the body (for Box2D physics) and add bounce
            setPosition(
                body.getPosition().x * PIXELS_PER_METER,
                (body.getPosition().y * PIXELS_PER_METER) + bounceOffset
            );

            birdImage.setPosition(getX(), getY()); // Update the image's position to match the Actor
        }
        else{
            if(!jumping && !is_bounce){
                singleJumpToPosition(cata_pos, jump_amp);
                jumping=true;
            }
        }

    }

    public void singleJumpToPosition(Vector2 targetPosition, float duration) {
        float initialX = getX();
        float initialY = getY();
        float distanceX = targetPosition.x - initialX;
        float distanceY = targetPosition.y - initialY;
        float peakHeight = 50f; // Adjust for jump height

        System.out.println(this.getname()+ "position is changing");

        addAction(Actions.sequence(
            Actions.moveToAligned(targetPosition.x, targetPosition.y + peakHeight, Align.center, duration / 2),
            Actions.moveToAligned(targetPosition.x, targetPosition.y, Align.center, duration / 2)
        ));
    }

    public boolean isInit_begin() {
        return init_begin;
    }

    public void setInit_begin(boolean init_begin) {
        this.init_begin = init_begin;
    }

    public String getname(){
        return name;
    }

    public void setIs_bounce(boolean is_bounce) {
        this.is_bounce = is_bounce;
    }
}
