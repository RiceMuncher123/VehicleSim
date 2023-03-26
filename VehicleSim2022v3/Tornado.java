import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class Tornado here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tornado extends Effect
{
    private int duration;

    public Tornado (){
        this.duration = 240;
    }

    public void addedToWorld (World w){
        image = new GreenfootImage("Tornado.png");
        image.scale(400,600);
        setImage(image);
        // ArrayList<Vehicle> vehicles =  (ArrayList<Vehicle>) w.getObjects(Vehicle.class);
        // for (Vehicle v : vehicles){
        // v.tornadoMe();
        // }
    }

    /**
     * Act - do whatever the Snowstorm wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (duration == 0){
            // speed vehicles back up 
            // TODO ==== !!!!! ===================
            VehicleWorld world = (VehicleWorld) getWorld();
            world.stopTornadoSound();
            getWorld().removeObject(this);
        } else if (duration <= 90){
            fade (duration, 90);
        } 
        duration--;
    }

    public int getDuration(){
        return duration;
    }
}
