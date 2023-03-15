import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BullDozer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BullDozer extends Vehicle
{
    GreenfootImage image = new GreenfootImage("BullDozer.png");
    public BullDozer(VehicleSpawner origin){
        super(origin);
        isConstructionVehicle = true;
        maxSpeed = 8;
        speed = maxSpeed;
        savedMaxSpeed = maxSpeed;
        image.scale(80,80);
        image.mirrorHorizontally();
        setImage(image);
    }

    /**
     * Act - do whatever the BullDozer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        drive();
        checkHitPedestrian();
        checkHitVehicle();
        if (checkEdge()){
            getWorld().removeObject(this);
        }
    }

    public boolean checkHitPedestrian (){
        // currently empty
        Pedestrian p = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Pedestrian.class);

        if (p != null){
            p.fling();
            return true;
        }
        return false;
    }


}
