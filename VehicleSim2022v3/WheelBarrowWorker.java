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
    }

    public void act()
    {
        // If there is a v
        if(bullDozerHit){
            this.fling();
            rotation += rotationIncrease;
            setRotation(rotation);
        }
        if (awake){
            if (getOneObjectAtOffset(0, (int)(direction * getImage().getHeight()/2 + (int)(direction * speed)), Vehicle.class) == null){
                setLocation (getX(), getY() + (int)(speed*direction));
            }
            if (direction == -1 && getY() < 100){
                getWorld().removeObject(this);
            } else if (direction == 1 && getY() > 550){
                getWorld().removeObject(this);
            }
        }

    }
}
