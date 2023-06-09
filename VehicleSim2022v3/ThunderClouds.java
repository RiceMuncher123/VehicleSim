import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ThunderClouds here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ThunderClouds extends Effect
{

    protected int direction = 1;
    protected int xLocationMoveTo;
    protected int speed = 2;
    protected int fadeTimeLeft;
    protected boolean fadeSwitch = false;
    /**
     * Act - do whatever the ThunderClouds wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public ThunderClouds(String directionMoveTo){
        //Sets the direction the thunderclouds move in
        if(directionMoveTo.equals("left"))
            direction = -1;
        //Sets the destination the thunder cloud moves towards to
        xLocationMoveTo = Greenfoot.getRandomNumber(600);
        fadeTimeLeft = 240;
    }

    public void addedToWorld (World w){
        image = new GreenfootImage("thunderclouds.png");
        image.scale(100,50);
        setImage(image);

    }

    public void act()
    {  
        if(getX() != xLocationMoveTo)
            move(speed*direction);
        //Turns on the fade switch once the tornado class is out of the world
        if(getObjectsInRange(600, Tornado.class).size() == 1 &&  getObjectsInRange(600, Tornado.class).get(0).getDuration() == 90){
            fadeSwitch = true;
        }
        //If the fade switch is on starts to fade
        if(fadeSwitch){
            fade (fadeTimeLeft, 240);
            fadeTimeLeft--;
        }
        //Once the actor is completley faded out, it removes itself from the world
        if(fadeTimeLeft == 0){
            getWorld().removeObject(this);
        }
    }
}
