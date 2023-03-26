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
    public void act()
    {
        if(actTimer == 30){
            getWorld().removeObject(this);
        }
        actTimer++;
    }
}
