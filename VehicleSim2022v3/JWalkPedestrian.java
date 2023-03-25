import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class JWalkPedestrian here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class JWalkPedestrian extends Pedestrian
{
    /**
     * Act - do whatever the JWalkPedestrian wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected boolean hasSetLocation;
    GreenfootImage image;
    public JWalkPedestrian(int direction){
        super(direction);
        image = new GreenfootImage("images/PedestrianImages/PedestrianImg" + Greenfoot.getRandomNumber(11) + ".png");

        setImage(image);
        hasSetLocation = false;
        timeToArrive = 3;
    }

    public double getXSpeed(){
        return xSpeed;
    }

    public double getYSpeed(){
        return ySpeed;
    }

    public void setDestination(int direction){
        //If is moving to the top
        if(direction == -1){
            //If the j walker spawns on the left side of the world
            yDestination = 0;
            //time in seconds to get to the other side
            //distance/time = speed for y
            //slope formula :P

            ySpeed = -1*getY()/(timeToArrive* 60);
            if(getX() < getWorld().getWidth()/2){
                xDestination = Greenfoot.getRandomNumber(getWorld().getWidth()/2) + getWorld().getWidth()/2;
                xSpeed = (xDestination - getX()) / (timeToArrive* 60);

            }
            //If the j walker spawns on the right side of the world
            else if(getX() > getWorld().getWidth()/2){
                xDestination = Greenfoot.getRandomNumber(getWorld().getWidth()/2);
                xSpeed = (getX() - xDestination)/(timeToArrive* 60);

            }

        }
        else if(direction == 1){
            //if starting from the top and move towards the bottom
            yDestination = getWorld().getHeight();
            ySpeed = (yDestination - getY())/(timeToArrive* 60);
            if(getX() < getWorld().getWidth()/2){
                xDestination = Greenfoot.getRandomNumber(getWorld().getWidth()/2) + getWorld().getWidth()/2;
                xSpeed = (xDestination - getX()) / (timeToArrive* 60);

            }
            else if(getX() > getWorld().getWidth()/2){
                xDestination = Greenfoot.getRandomNumber(getWorld().getWidth()/2);
                xSpeed = (xDestination - getX())/(timeToArrive* 60);

            }
        }
        if(xSpeed < 0){
            image.mirrorHorizontally();
            setImage(image);
        }
    }

    public void act()
    {
        if(!hasSetLocation){
            setDestination(direction);
            hasSetLocation = true;
        }
        walk();
        // Add your action code here.
    }
}