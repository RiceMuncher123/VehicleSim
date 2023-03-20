import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Pedestrian1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pedestrian1 extends Pedestrian
{
    //GreenfootImage image = new GreenfootImage("WheelBarrow.png");
    public Pedestrian1(int direction){
        super(direction);
        
        savedMaxSpeed = maxSpeed;
        //image.scale(70,70);
        //setImage(image);
    }
    public void act()
    {
        // If there is a v
        walk();

    }
}
