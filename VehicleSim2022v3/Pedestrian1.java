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

        image = new GreenfootImage("images/PedestrianImages/PedestrianImg" + Greenfoot.getRandomNumber(11) + ".png");
        if(Greenfoot.getRandomNumber(2) == 0){
            image.mirrorHorizontally();
        }
        setImage(image);

    }

    public void takeAStep(){
        if (getOneObjectAtOffset(0, (int)(direction * getImage().getHeight()/2 + (int)(direction * speed)), Vehicle.class) == null){
            setLocation (getX(), getY() + (int)(speed*direction));
        }
        if (direction == -1 && getY() < 0){
            getWorld().removeObject(this);
        } else if (direction == 1 && getY() > 600){
            getWorld().removeObject(this);

        }
    }

    public void act()
    {
        // If there is a v
        walk();

    }
}
