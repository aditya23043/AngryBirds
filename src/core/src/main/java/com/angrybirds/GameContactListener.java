package com.angrybirds;

import com.badlogic.gdx.physics.box2d.*;
import java.util.List;

public class GameContactListener implements ContactListener {

    private List<Body> allBodies;  // List of bodies to iterate through

    public GameContactListener(List<Body> allBodies){
        this.allBodies = allBodies;
    }

    @Override
    public void beginContact(Contact contact) {

        Object userDataA = contact.getFixtureA().getBody().getUserData();
        Object userDataB = contact.getFixtureB().getBody().getUserData();

        if ((userDataA instanceof Block && userDataB instanceof Bird) ||
            (userDataB instanceof Block && userDataA instanceof Bird)) {

            Block block = userDataA instanceof Block ? (Block) userDataA : (Block) userDataB;
            block.disposeBlock();

            for (Body body : allBodies) {
                if (body.getUserData() instanceof Block) {
                    Block aboveBlock = (Block) body.getUserData();
                    if (aboveBlock.getY() > block.getY()) {
                        aboveBlock.activateDynamicBody();
                    }
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
