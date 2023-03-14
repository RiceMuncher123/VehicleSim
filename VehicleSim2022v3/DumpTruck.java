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
    public DumpTruck(VehicleSpawner origin){
        super(origin);
        isConstructionVehicle = true;
        maxSpeed = 3;
        speed = maxSpeed;
        image.scale(110,70);
        setImage(image);
    }

    public boolean checkHitPedestrian () {
        Pedestrian p = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Pedestrian.class);

        if (p != null){
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
        checkHitPedestrian();
        drive();
        if (checkEdge()){
            getWorld().removeObject(this);
        }
    }
}
