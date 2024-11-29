package com.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
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

    private Vector2 velocity;
    private Vector2 position;
    private float gravity = -500f;
    private boolean isReleased = false;

    private float bounceOffset = 0f;
    private float bounceSpeed = 4f;
    private float bounceAmplitude = 5f;
    private float time = 0f;
    private Vector2 initialPosition;
    private float timeOffset;
    private boolean initBegin;
    private boolean jumping = false;
    private Vector2 catapultPosition;
    private float jumpAmplitude;
    private String name;
    private boolean isBounce = false;

    public Bird(String texturePath, int width, int height, int x, int y, float scale, World world, String name) {
        this.world = world;
        if(world==null){
            System.out.println("Here");
        }
        this.birdTexture = new Texture(texturePath);
        this.birdImage = new Image(birdTexture);
        this.width = width;
        this.height = height;
        this.initBegin = true;
        this.name = name;
        this.jumping = false;
        birdImage.setScale(scale);
        this.initialPosition = new Vector2(x, y);
        this.timeOffset = (float) (Math.random() * 2 * Math.PI);
        this.catapultPosition = new Vector2(130, 275);
        this.jumpAmplitude = 2f;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);

        initializePhysics(x / PIXELS_PER_METER, y / PIXELS_PER_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        birdImage.setPosition(getX(), getY());
        birdImage.draw(batch, parentAlpha);
    }

    protected void initializePhysics(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f / PIXELS_PER_METER, height / 2f / PIXELS_PER_METER);

        FixtureDef birdFixtureDef = new FixtureDef();
        birdFixtureDef.shape = shape;
        birdFixtureDef.density = 1.0f;
        birdFixtureDef.restitution = 0.5f;
        birdFixtureDef.filter.categoryBits = 0x0002;  // Bird category
        birdFixtureDef.filter.maskBits = 0x0001;

        body.createFixture(birdFixtureDef);
        shape.dispose();
        // Bird collides with Blocks and other Birds
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (isReleased) {
            syncWithPhysics();
            Vector2 velocity = body.getLinearVelocity();
            float maxSpeed = 12f;
            if (velocity.len() > maxSpeed) {
                velocity = velocity.nor().scl(maxSpeed);
                body.setLinearVelocity(velocity);
            }

            if (getX() < 0 || getX() > 960 || getY() < 0 || getY() > 540) {
                die();
            }

        }
        else if (initBegin && isBounce) {
            updateBounce(delta);

        }
        else if(jumping){
            System.out.println("Function getting called");
            handleJumpToCatapult();
        }
    }

    private void syncWithPhysics() {
        setPosition(
            body.getPosition().x * PIXELS_PER_METER,
            body.getPosition().y * PIXELS_PER_METER
        );
    }

    private void checkGroundCollision() {
        if (body.getPosition().y * PIXELS_PER_METER <= 0) {
            body.setLinearVelocity(0, 0);
            body.setTransform(body.getPosition().x, 0 / PIXELS_PER_METER, 0);
            isReleased = false;
        }
    }

    private void updateBounce(float delta) {
        time += delta * bounceSpeed;

        bounceOffset = (float) Math.sin(time + timeOffset) * bounceAmplitude;

        setPosition(
            initialPosition.x,
            initialPosition.y + bounceOffset
        );
    }


    private void handleJumpToCatapult() {
        if (jumping && !isBounce) {
            singleJumpToPosition(catapultPosition, jumpAmplitude);
            jumping = false;
        }
    }

    public void singleJumpToPosition(Vector2 targetPosition, float duration) {
        float peakHeight = 50f;

        addAction(Actions.sequence(
            Actions.moveToAligned(targetPosition.x, targetPosition.y + peakHeight, Align.center, duration / 2),
            Actions.moveToAligned(targetPosition.x, targetPosition.y, Align.center, duration / 2)
        ));
    }

    public void releaseBird(Vector2 initialPosition, Vector2 initialVelocity) {
        System.out.println(initialVelocity);
        this.isReleased = true;
        body.setTransform(initialPosition.x / PIXELS_PER_METER, initialPosition.y / PIXELS_PER_METER, 0);
        body.setLinearVelocity(initialVelocity.x, initialVelocity.y);
        body.setGravityScale(1);
        setInit_begin(false);
    }

    public void setInit_begin(boolean initBegin) {
        this.initBegin = initBegin;
    }

    public String getName() {
        return name;
    }

    public void setIs_bounce(boolean isBounce) {
        this.isBounce = isBounce;
    }

    public void dispose() {
        birdTexture.dispose();
    }

    abstract void dealDamage(int damage);

    abstract void moveBird(int damage);

    public String getname(){
        return name;
    }

    public void set_jump(boolean val){
        System.out.println(name);
        this.jumping=val;
    }

    public void die() {
        // Stop the bird's movement and set its velocity to zero
        body.setLinearVelocity(0, 0);
        body.setGravityScale(0);  // Optionally, turn off gravity if needed
        isReleased = false;       // The bird is no longer released
        // You can trigger animations or other effects here
        System.out.println(name + " has died.");
    }

    public Body getBody() {
        return body;
    }

    public void setReleased(boolean b) {
        isReleased=b;
    }
}
