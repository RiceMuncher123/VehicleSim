import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StrongWinds here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StrongWinds extends Effect
{
    /**
     * Act - do whatever the StrongWinds wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected int speed = 3;
    public void act()
    {
        move(speed*-1);
        if(getX() < -300){
            getWorld().removeObject(this);
            //switch blow global affect
        }
    }
    public void addedToWorld (World w){
        image = new GreenfootImage("blowcloud.png");
        image.scale(200,100);
        setImage(image);

    }
}
