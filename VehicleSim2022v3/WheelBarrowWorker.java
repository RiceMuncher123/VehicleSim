import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
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

    protected int laneHeight;
    protected int actTimer = 61;
    public WheelBarrowWorker(int direction, double speed){
        super(direction);
        setImage(image);
        this.speed = speed;
        savedMaxSpeed = speed;
        maxSpeed = speed;

    }


    public void takeAStep(){
        VehicleWorld world = (VehicleWorld) getWorld();
        laneHeight = world.getLaneHeight();
        actTimer++;
        if(actTimer >= 61){
            //Moves and removes pedestrians from the world in it's radius using an array list
            ArrayList<Pedestrian> pedestrianList = (ArrayList<Pedestrian>) getObjectsInRange​(laneHeight/2, Pedestrian.class);
            if(getOneObjectAtOffset((int)(direction * getImage().getHeight()/2 + (int)(direction * speed)), 0, Vehicle.class) == null){
                move(direction*speed); 
            }
            //If there are pedestrians in its range
            if(pedestrianList.size() != 0){
                for(int i = 0; i < pedestrianList.size(); i++){
                    getWorld().removeObject(pedestrianList.get(0));
                }
            }
            //If it's close to the edge of the world, the actor will turn around and enter the vehicle after
            if((getX()+ speed) >= world.getWidth() - 20){
                image.mirrorHorizontally();
                setImage(image);
                //Sets the timer to 0
                actTimer = 0;
            }
        }
        //If the timer is at 60 (61 once the actor is added), it will remove itself from the world
        if(actTimer == 60 || (getX() < -10 || getX() > 810 || getY() < -10 || getY() > 610)){
            getWorld().removeObject(this);
        }
    }

    public void act()
    {
        walk();
    }
}
