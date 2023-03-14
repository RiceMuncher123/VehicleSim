import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Bus subclass
 */
public class Bus extends Vehicle
{
    private boolean isMoving = true;
    private boolean isWaiting = false;
    private int maxWaitTime = 60;
    private int curWaitTime = 0;
    public Bus(VehicleSpawner origin){
        super (origin); // call the superclass' constructor first

        //Set up values for Bus
        maxSpeed = 1.5 + ((Math.random() * 10)/5);
        speed = maxSpeed;
        savedMaxSpeed = maxSpeed;

        // because the Bus graphic is tall, offset it a up (this may result in some collision check issues)
        yOffset = 15;
    }

    /**
     * Act - do whatever the Bus wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        checkIsOnConcrete();
        if(checkHitPedestrian()){
            isMoving = false;
            isWaiting = true;
            curWaitTime = maxWaitTime;
        }
        if(!bullDozerHit && isMoving)
            drive();
        else{
            curWaitTime--;
            if(curWaitTime == 0){
                isMoving = true;
                isWaiting = false;
            }
        }
        checkHitVehicle();
        if(bullDozerHit){
            this.fling();
            rotation+=rotationIncrease;
            setRotation(rotation);
        }
        if (checkEdge()){
            getWorld().removeObject(this);
        }

    }

    public boolean checkHitPedestrian () {
        // currently empty

        Pedestrian p = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Pedestrian.class);
        if (p != null){
            p.boardBus();
            return true;
        }   
        return false;
    }

}
