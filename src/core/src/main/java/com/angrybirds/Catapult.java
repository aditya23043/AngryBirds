package com.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Catapult extends Actor {
    private Image catapultImage;
    private Vector2 basePosition;
    private Vector2 traject_position;
    private Vector2 branchLeft, branchRight;
    private Vector2 dragPosition;
    private boolean isStretching;
    private ShapeRenderer shapeRenderer;
    private float projectileMass = 1.0f;
    private float gravity = -9.8f;


    public Catapult(float x, float y, float scale) {
        Texture catapultTexture = new Texture("img/catapult.png");
        this.catapultImage = new Image(catapultTexture);

        this.basePosition = new Vector2(x, y);
        this.catapultImage.setPosition(x, y);
        this.catapultImage.setScale(scale);

        this.branchLeft = new Vector2(x +14, y + 100);
        this.branchRight = new Vector2(x + 40, y + 102);

        this.dragPosition = new Vector2(x, y);
        this.isStretching = false;
        this.traject_position = new Vector2(x+50, y+101);
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        catapultImage.draw(batch, parentAlpha);

        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0, 0, 0, 1);

        if (isStretching) {
            drawCurve(branchLeft, dragPosition, branchRight);
            Vector2 velocity = calculateVelocity(traject_position, dragPosition);
            shapeRenderer.setColor(1, 0, 0, 1);
            drawTrajectory(traject_position, velocity);
        } else {
            shapeRenderer.rectLine(branchLeft, branchRight, 5);
        }

        shapeRenderer.end();
        batch.begin();
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
        this.isStretching = true;
        this.dragPosition.set(startPosition);
    }

    public void updateStretch(Vector2 currentPosition) {
        if (isStretching) {
            this.dragPosition.set(currentPosition);
        }
    }

    public void releaseStretch() {
        this.isStretching = false;
        this.dragPosition.set(basePosition); // Reset to the base
    }

    private void drawTrajectory(Vector2 startPosition, Vector2 velocity) {
        float timeStep = 0.1f;
        int maxSteps = 6;
        Vector2 position = new Vector2(startPosition);
        Vector2 currentVelocity = new Vector2(velocity);

        for (int i = 0; i<maxSteps; i++) {
            shapeRenderer.circle(position.x, position.y, 5);

            position.add(currentVelocity.x * timeStep, currentVelocity.y * timeStep);
            currentVelocity.add(0, gravity * timeStep);
        }
    }

    private Vector2 calculateVelocity(Vector2 base, Vector2 drag) {
        float scale = 5.0f;
        Vector2 velocity = new Vector2(base).sub(drag).scl(scale);
        return velocity;
    }


}
