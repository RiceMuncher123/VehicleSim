import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CementTruck here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CementTruck extends Vehicle
{
    GreenfootImage image = new GreenfootImage("CementTruck.png");
    public CementTruck(VehicleSpawner origin){
        super(origin);
        isConstructionVehicle = true;
        image.scale(70,70);
        maxSpeed = 2;
        speed = maxSpeed;
        image.mirrorHorizontally();
        setImage(image);
    }

    public void act()
    {
        checkHitPedestrian();
        drive();
        if(!isTouching(Concrete.class))
            getWorld().addObject(new Concrete(), getX()-60, getY());            
        if (checkEdge()){
            getWorld().removeObject(this);
        }
    }

    public boolean checkHitPedestrian () {
        Pedestrian p = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Pedestrian.class);

        if (p != null){
            p.knockDown();
            return true;
        }
        return false;
    }

    
}
