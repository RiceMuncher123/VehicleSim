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
        if(checkHitPedestrian()){
            //If it did hit, switches the move and waiting boolean switches
            isMoving = false;
            isWaiting = true;
            curWaitTime = maxWaitTime;
        }
        //Checks whether it is moving or if it has been hit by a bull dozer
        if(isMoving || bullDozerHit)
            drive();
        else{
            //waits until the wait timer is 0 for the wait time for a pedestrian to board the bus
            curWaitTime--;
            if(curWaitTime == 0){
                isMoving = true;
                isWaiting = false;
            }
        }
        if (checkEdge()){
            getWorld().removeObject(this);
        }

    }

    public boolean checkHitPedestrian () {
        // currently empty

        Pedestrian p = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Pedestrian.class);
        if (p != null){
            //calls the board bus method which removes the pedestrian from the world
            p.boardBus();
            return true;
        }   
        return false;
    }

}
