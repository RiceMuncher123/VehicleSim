import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Ambulance subclass
 */
public class Ambulance extends Vehicle
{
    public Ambulance(VehicleSpawner origin){
        super (origin); // call the superclass' constructor first

        maxSpeed = 2.5;
        speed = maxSpeed;
        savedMaxSpeed = maxSpeed;

    }

    /**
     * Act - do whatever the Ambulance wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        drive();
        checkHitPedestrian();
        
        if (checkEdge()){
            getWorld().removeObject(this);
        }

    }

    public boolean checkHitPedestrian () {
        Pedestrian p = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Pedestrian.class);
        if (p != null){
            //If it is not spinning heal the pedestrian else, send it flying too
            if(!isSpinning){
                p.healMe();
            }
            else{
                p.fling();
            }
            return true;
        }
        return false;
    }

    
}
