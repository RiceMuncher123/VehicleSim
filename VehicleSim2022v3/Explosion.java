import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Explosion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Explosion extends Actor
{
    GreenfootImage image;
    protected int actTimer;
    /**
     * Act - do whatever the Explosion wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Explosion(){
        image = new GreenfootImage("images/Explosion.png");
        setImage(image);
        actTimer = 0;
    }

    protected void addedToWorld(World worlds)
    {
        VehicleWorld world = (VehicleWorld) getWorld();
        world.playExplosion();
    }

    public void act()
    {
        VehicleWorld world = (VehicleWorld) getWorld();

        if(actTimer == 30){
            //Once lifespan is up, stops the explosion sound and removes itself from the world
            world.stopExplosion();
            getWorld().removeObject(this);
        }
        actTimer++;
    }
}
