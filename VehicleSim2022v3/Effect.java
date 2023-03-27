import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Effect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Effect extends Actor
{
    /**
     * Act - do whatever the Effect wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected GreenfootImage image;
    protected int newTransparency;
    //Method which allows the effect subclasses to fade
    protected void fade (int timeLeft, int fadeTime){
        double percent = timeLeft / (double)fadeTime;
        newTransparency = (int)(percent * 255);
        image.setTransparency (newTransparency);
        
    }
    //Returns the opacity of the image
    protected int getTransparency(){
        return newTransparency;
    }
    
    
    
}
