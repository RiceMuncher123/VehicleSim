import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WhiteVan here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WhiteVan extends Vehicle
{
    /**
     * Act - do whatever the WhiteVan wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    GreenfootImage image;
    protected boolean robberReleased;
    protected boolean driveSwitch;
    public WhiteVan(VehicleSpawner origin) {
        super(origin);
        image = new GreenfootImage("images/WhiteVan.png");
        image.mirrorHorizontally();
        setImage(image);

        maxSpeed = 1.5 + ((Math.random() * 20)/5);
        savedMaxSpeed = maxSpeed;
        speed = maxSpeed;
        driveSwitch = false;
        robberReleased = false;
    }

    public void act()
    {
        Vehicle frontCheck = (Vehicle)getOneObjectAtOffset((int)speed*2 + getImage().getWidth()/2, 0, Vehicle.class);
        Vehicle backCheck = (Vehicle)getOneObjectAtOffset((int)speed*2 - getImage().getWidth()/2, 0, Vehicle.class);
        Robber rob = new Robber(1);
        if(robberReleased == false){

            checkIsOnConcrete();
            
            if(frontCheck != null){
                driveSwitch = false;
                //stop front vehicle
                frontCheck.setBeingRobbedTrue();
                //spawn burgaler
                getWorld().addObject(rob, getX() +  (3+ frontCheck.getImage().getWidth() + getImage().getWidth()/2), getY());
                //maxSpeed = 0;
                robberReleased = true;
            }
            else if(backCheck != null){
                driveSwitch = false;
                //stop back vehicle
                backCheck.setBeingRobbedTrue();
                //spawn burgaler
                getWorld().addObject(rob, getX() -  (3 + backCheck.getImage().getWidth() + getImage().getWidth()/2), getY());
                robberReleased = true;

                //maxSpeed = 0;
            }
            drive();
            checkHitPedestrian();
        }
        else if(robberReleased && !driveSwitch){
            if(rob.getWorld() == null){
                driveSwitch = true;
            }
        }
        else{
            checkIsOnConcrete();
            drive();
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

    public void spawnRobber(){

    }
}
