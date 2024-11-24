package com.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import org.w3c.dom.ls.LSOutput;

public class Catapult extends Actor {
    private Image catapultImage;
    private Vector2 basePosition;
    private Vector2 traject_position;
    private Vector2 branchLeft, branchRight;
    private Vector2 dragPosition;
    private boolean isStretching;
    private boolean isDragging;
    private ShapeRenderer shapeRenderer;
    private World world;
    private Body catapultBody;
    private Bird loadedBird;
    private boolean is_empty;
    private Vector2 temp_position;

    private float projectileMass = 1.0f;
    private float gravity = -9.8f;
    private float launchScale = 5.0f; // Adjust to control the strength of the launch

    public Catapult(float x, float y, float scale, World world) {
        Texture catapultTexture = new Texture("img/catapult.png");
        this.catapultImage = new Image(catapultTexture);

        this.basePosition = new Vector2(x, y);
        this.catapultImage.setPosition(x, y);
        this.catapultImage.setScale(scale);

        this.branchLeft = new Vector2(x + 14, y + 100);
        this.branchRight = new Vector2(x + 40, y + 102);

        this.dragPosition = new Vector2(x, y);
        this.isStretching = false;
        this.isDragging = false;
        this.traject_position = new Vector2(x+40, y + 101);
        this.temp_position = new Vector2(x, y + 96);
        this.shapeRenderer = new ShapeRenderer();
        this.world = world;
        this.is_empty=true;

        initializeCatapultBody();
    }

    private void initializeCatapultBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(basePosition.x / Bird.PIXELS_PER_METER, basePosition.y / Bird.PIXELS_PER_METER);
        bodyDef.type = BodyDef.BodyType.StaticBody; // Catapult body remains stationary

        catapultBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(catapultImage.getWidth() / 2 / Bird.PIXELS_PER_METER, catapultImage.getHeight() / 2 / Bird.PIXELS_PER_METER);

        catapultBody.createFixture(shape, 0.0f);
        shape.dispose();
    }

    public void loadBird(Bird bird) {
        this.loadedBird = bird;
        is_empty=false;
        System.out.println(bird.getname());
        System.out.println("bird is getting loaded");
        loadedBird.setInit_begin(false);
    }

    public boolean isIs_empty() {
        return is_empty;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0, 0, 0, 1);

        if (isStretching && isDragging) {
            drawCurve(branchLeft, dragPosition, branchRight);
            shapeRenderer.setColor(1, 0, 0, 1);

            if (loadedBird != null) {
                Vector2 launchVelocity = calculateVelocity(temp_position, dragPosition);
                drawTrajectory(traject_position, launchVelocity);
            }
        } else {
            shapeRenderer.rectLine(branchLeft, branchRight, 5);
        }

        shapeRenderer.end();
        batch.begin();
        catapultImage.draw(batch, parentAlpha);
    }


    private void drawCurve(Vector2 start, Vector2 control, Vector2 end) {
        Bezier<Vector2> curve = new Bezier<>(start, control, end);
        Vector2 tmp = new Vector2();

        int segments = 20;
        for (int i = 0; i < segments; i++) {
            float t1 = i / (float) segments;
            float t2 = (i + 1) / (float) segments;

            curve.valueAt(tmp, t1);
            Vector2 point1 = new Vector2(tmp);

            curve.valueAt(tmp, t2);
            Vector2 point2 = new Vector2(tmp);

            shapeRenderer.rectLine(point1, point2, 5);
        }
    }

    public void startStretch(Vector2 startPosition) {
        float distanceThreshold = 75f;
        if (startPosition.dst(temp_position) <= distanceThreshold) {
            this.isStretching = true;
            this.isDragging = true;
            this.dragPosition.set(startPosition);
        }
    }

    public void updateStretch(Vector2 currentPosition) {
        if (isStretching && isDragging) {
            Vector2 direction = new Vector2(currentPosition).sub(temp_position);

            if (direction.dot(new Vector2(1, 0)) > 0) {
                direction.x = 0;
            }

            float maxDistance = 75f;
            float distance = direction.len();

            if (distance > maxDistance) {
                direction.nor().scl(maxDistance);
            }

            Vector2 constrainedPosition = new Vector2(temp_position).add(direction);

            if (loadedBird != null) {
                loadedBird.setPosition(constrainedPosition.x, constrainedPosition.y);
            }

            dragPosition.set(constrainedPosition);
        }
    }




    public void releaseStretch() {
        if (loadedBird != null && isStretching) {
            System.out.println("Releasing bird...");

            Vector2 launchVelocity = calculateVelocity(temp_position, dragPosition);
            System.out.println("Launch Velocity: " + launchVelocity);

            if (loadedBird.body != null) {
                System.out.println("Here");
                loadedBird.releaseBird(new Vector2(dragPosition),launchVelocity);
                isStretching = false;
                isDragging = false;
                loadedBird = null;
                is_empty = true;
            }
            else {
                System.err.println("Error: Bird body is null or not initialized.");
            }
        }
    }



    private Vector2 calculateVelocity(Vector2 base, Vector2 drag) {
        return new Vector2(base).sub(drag).scl(launchScale);
    }

    private void drawTrajectory(Vector2 startPosition, Vector2 velocity) {
        float timeStep = 0.1f;
        int maxSteps = 5;
        Vector2 position = new Vector2(startPosition);
        Vector2 currentVelocity = new Vector2(velocity);

        for (int i = 0; i < maxSteps; i++) {
            shapeRenderer.circle(position.x, position.y, 5);

            position.add(currentVelocity.x * timeStep, currentVelocity.y * timeStep);
            currentVelocity.add(0, gravity * timeStep);

            if (position.y < 0) {
                break;
            }
        }
    }

}
