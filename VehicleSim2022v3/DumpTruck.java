import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DumpTruck here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DumpTruck extends Vehicle
{
    GreenfootImage image = new GreenfootImage("DumpTruck.png");
    protected boolean addedWheelBarrow;
    public DumpTruck(VehicleSpawner origin){
        super(origin);
        isConstructionVehicle = true;
        maxSpeed = 3;
        speed = maxSpeed;
        savedMaxSpeed = maxSpeed;
        image.scale(110,70);
        setImage(image);
        addedWheelBarrow = false;
    }
    
    public boolean checkHitPedestrian () {
        Pedestrian p = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Pedestrian.class);
        WheelBarrowWorker w = (WheelBarrowWorker)getOneObjectAtOffset(((int)speed) + getImage().getWidth()/2, 0, WheelBarrowWorker.class);
        if(w != null && w.getClass() == WheelBarrowWorker.class){
            //Makes sure it does not hit a wheelbarrowworker actor
            return false;
        }
        else if (p != null){
            p.knockDown();
            return true;
        }
        return false;
    }

    /**
     * Act - do whatever the DumpTruck wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(!addedWheelBarrow){
            //Adds 1 wheelbarrow actor infront of it
            if(getY() > getWorld().getHeight()/2){
                getWorld().addObject (new WheelBarrowWorker (1, speed), 0, getY());
            }
            else{
                getWorld().addObject (new WheelBarrowWorker (1, speed), 0, getY());
            }
            addedWheelBarrow = true;
        }
        checkHitPedestrian();
        drive();
        if (checkEdge()){
            getWorld().removeObject(this);
        }
    }
}
