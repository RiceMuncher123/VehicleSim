import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Robber here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Robber extends Pedestrian
{
    /**
     * Act - do whatever the Robber wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected int actCounter;
    public Robber(int direction){
        super(direction);
        image = new GreenfootImage("images/Robbera.png");
        setImage(image);
        actCounter = 0;
    }

    public void act()
    {
        if(actCounter == 30){
            EnterVehicle();
        }
        actCounter++;
    }

    

    public void EnterVehicle(){
        getWorld().addObject(new Pedestrian1(1), getX(), getY());
        getWorld().removeObject(this);
    }
    
    public void takeAStep(){
        //Not using this but abstract 
    }
    public void knockDown () {
        //dont want it to knock down so overide and empty
    }
}
