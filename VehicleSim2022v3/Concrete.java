import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Concrete here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Concrete extends Actor
{
    /**
     * Act - do whatever the Concrete wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    GreenfootImage image = new GreenfootImage("ConcreteTrail.png");
    private int lifeTime = 200;
    public Concrete(){
        setImage(image);
    }
    public void act()
    {
        lifeTime--;
        if(lifeTime == 0)
            getWorld().removeObject(this);
    }
}
