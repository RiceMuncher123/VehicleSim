import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Robber here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Robber extends Pedestrian
{
    /**
     * Act - do whatever the Robber wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected int actCounter;

    public Robber(int direction){
        super(direction);
        image = new GreenfootImage("images/Robbera.png");
        setImage(image);
        actCounter = 0;
    }

    public void act()
    {
        if(actCounter == 120){
            //Waits 2 seconds until it's enter's the vehicle
            EnterVehicle(); 
        }
        actCounter++;
    }


    public void EnterVehicle(){
        //Adds a pedestrian in the world , plays the audio sound of pedestrian being robbed and removes itself from the world after
        getWorld().addObject(new Pedestrian1(1), getX(), getY());
        VehicleWorld world = (VehicleWorld) getWorld();
        world.pedestrianPlead();
        getWorld().removeObject(this);
    }

    public void takeAStep(){
        //Not using this but abstract 
    }

    public void knockDown () {
        //dont want it to knock down so overide and empty
    }
}
