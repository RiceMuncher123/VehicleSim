import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Car subclass
 */
public class Car extends Vehicle
{
    GreenfootImage image;

    public Car(VehicleSpawner origin) {
        super(origin); // call the superclass' constructor
        maxSpeed = 1.5 + ((Math.random() * 30)/5);
        savedMaxSpeed = maxSpeed;
        speed = maxSpeed;
        //Sets the image to a randomized color from the CarImages file
        image = new GreenfootImage("images/CarImages/car0" + Greenfoot.getRandomNumber(6) + ".png");
        setImage(image);
        yOffset = 0;
    }

    public void act()
    {
        checkIsOnConcrete();
        drive();
        checkHitPedestrian();
        if (checkEdge()){
            getWorld().removeObject(this);
        }

    }

    /**
     * When a Car hit's a Pedestrian, it should knock it over
     */
    public boolean checkHitPedestrian () {
        Pedestrian p = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Pedestrian.class);

        if (p != null){
            p.knockDown();
            return true;
        }
        return false;
    }

    public boolean checkHitVehicle(){
        Vehicle v = (Vehicle)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Vehicle.class);
        if (v != null){
            if(isSpinning)
                v.fling();
            return true;
        }
        return false;
    }
}
