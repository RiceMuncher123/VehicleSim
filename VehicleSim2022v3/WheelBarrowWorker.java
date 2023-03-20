import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WheelBarrowWorker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WheelBarrowWorker extends Pedestrian
{
    /**
     * Act - do whatever the WheelBarrowWorker wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    GreenfootImage image = new GreenfootImage("WheelBarrow.png");
    public WheelBarrowWorker(int direction){
        super(direction);
        image.scale(70,70);
        setImage(image);
        savedMaxSpeed = maxSpeed;

    }

    public void act()
    {
        walk();
    }
}
