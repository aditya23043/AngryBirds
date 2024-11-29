package com.angrybirds;

import com.badlogic.gdx.physics.box2d.*;
import java.util.List;

public class GameContactListener implements ContactListener {

    private final List<Body> allBodies;  // List of bodies to iterate through

    public GameContactListener(List<Body> allBodies) {
        this.allBodies = allBodies;  // Pass the list from the PlayScreen or Game class
    }

    @Override
    public void beginContact(Contact contact) {
        // Get user data associated with the bodies
        Object userDataA = contact.getFixtureA().getBody().getUserData();
        Object userDataB = contact.getFixtureB().getBody().getUserData();

        // Check if one of the objects is a Block and the other is a Bird
        if ((userDataA instanceof Block && userDataB instanceof Bird) ||
            (userDataB instanceof Block && userDataA instanceof Bird)) {

            Block block = userDataA instanceof Block ? (Block) userDataA : (Block) userDataB;
            block.disposeBlock(); // Handle block removal when hit

            // Activate blocks above the hit block (falling behavior)
            for (Body body : allBodies) {  // Use the list of bodies
                if (body.getUserData() instanceof Block) {
                    Block aboveBlock = (Block) body.getUserData();
                    if (aboveBlock.getY() > block.getY()) {  // Check if the block is above the hit block
                        aboveBlock.activateDynamicBody(); // Apply gravity to blocks above
                    }
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        // Optional: Implement logic for when contact ends
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Optional: Handle pre-solve logic (e.g., adjust collision behavior)
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Optional: Handle post-solve logic (e.g., change physics properties after collision)
    }
}
