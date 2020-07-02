
package Tools;

import Movement.Direction;
import Sprites.Pacman;
import com.Pacman.game.Main;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener{

    
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        int collisionDefinition = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        
        switch(collisionDefinition){
            case Main.EAT_AREA_BIT | Main.PILL_BIT:
                if (fixA.getFilterData().categoryBits == Main.PILL_BIT)
                    ((InteractiveObject) fixA.getUserData()).onCollide();
                else 
                    ((InteractiveObject) fixB.getUserData()).onCollide();
                break;
            case Main.EAT_AREA_BIT | Main.PRIZE_BIT:
                if (fixA.getFilterData().categoryBits == Main.PRIZE_BIT)
                    ((InteractiveObject) fixA.getUserData()).onCollide();
                else    
                    ((InteractiveObject) fixB.getUserData()).onCollide();
                break;
            case Main.EAT_AREA_BIT | Main.KEY_BIT:
                if (fixA.getFilterData().categoryBits == Main.KEY_BIT)
                    ((InteractiveObject) fixA.getUserData()).onCollide();
                else    
                    ((InteractiveObject) fixB.getUserData()).onCollide();
                break;
            case Main.ENEMY_CENTER_BIT | Main.DIRECTION_BIT:
                if (fixA.getFilterData().categoryBits == Main.DIRECTION_BIT && fixB.getUserData() == "Blinky")
                    ((Direction) fixA.getUserData()).onBlinkyCollide();
                else if (fixB.getFilterData().categoryBits == Main.DIRECTION_BIT && fixA.getUserData() == "Blinky")
                    ((Direction) fixB.getUserData()).onBlinkyCollide();
                else if (fixA.getFilterData().categoryBits == Main.DIRECTION_BIT && fixB.getUserData() == "Inky")
                    ((Direction) fixA.getUserData()).onInkyCollide();
                else if (fixB.getFilterData().categoryBits == Main.DIRECTION_BIT && fixA.getUserData() == "Inky")
                    ((Direction) fixB.getUserData()).onInkyCollide();
                break;
            case Main.PACMAN_BIT | Main.ENEMY_BIT:
                Pacman.dead();
        }
        
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact arg0, Manifold arg1) {
    }

    @Override
    public void postSolve(Contact arg0, ContactImpulse arg1) {
    }
    
}
