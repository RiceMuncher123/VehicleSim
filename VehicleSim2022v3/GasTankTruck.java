import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class GasTankTruck here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GasTankTruck extends Vehicle
{
    /**
     * Act - do whatever the GasTankTruck wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    GreenfootImage image;

    public GasTankTruck(VehicleSpawner origin) {
        super(origin); // call the superclass' constructor
        maxSpeed = 1.5 + ((Math.random() * 30)/5);
        savedMaxSpeed = maxSpeed;
        speed = maxSpeed;
        image = new GreenfootImage("images/GasTankTruck.png");
        image.mirrorHorizontally();
        setImage(image);
        yOffset = 0;
    }

    public void act()
    {
        checkIsOnConcrete();
        drive();
        checkHitPedestrian();
        Vehicle frontCheck = (Vehicle)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Vehicle.class);
        Vehicle backCheck = (Vehicle)getOneObjectAtOffset((int)speed - getImage().getWidth()/2, 0, Vehicle.class);
        //Checks whether there is a vehicle behind or infront and calls the explode method if so
        if(frontCheck != null || backCheck != null){
            explode();
        }
        else if(checkEdge()){
            getWorld().removeObject(this);
        }
    }

    public void explode(){
        Explosion e = new Explosion();
        getWorld().addObject(e, getX(), getY());
        //Uses 2 arraylists for vehicles and pedestrians and removes them from the world in a given range
        ArrayList<Pedestrian> pedestrianList = (ArrayList<Pedestrian>) getObjectsInRange​(e.getImage().getWidth()/2, Pedestrian.class);    
        ArrayList<Vehicle> vehicleList = (ArrayList<Vehicle>) getObjectsInRange​(e.getImage().getWidth(), Vehicle.class);
        for(Vehicle v : vehicleList){
            getWorld().removeObject(v);
        }
        for(Pedestrian p : pedestrianList){
            getWorld().removeObject(p);
        }
        getWorld().removeObject(this);
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
