import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Pedestrian that tries to walk across the street
 */
public abstract class Pedestrian extends SuperSmoothMover
{
    protected double speed;
    protected double maxSpeed;
    protected double savedMaxSpeed;
    protected int direction; // direction is always -1 or 1, for moving down or up, respectively
    protected boolean awake;
    protected boolean bullDozerHit = false;
    protected int rotation = 0;
    static final int rotationIncrease = 10;
    protected boolean isTornado = false;
    protected int laneYCoord;
    protected int carRotation;
    protected boolean gotHeight = false;
    protected boolean tornadoSpeedDecrease = false;
    protected boolean beenHitTornado = false;
    //Speeds for the j walkers
    protected int timeToArrive;
    protected int xDestination;
    protected int yDestination;
    protected double xSpeed;
    protected double ySpeed;
    public Pedestrian(int direction) {
        // choose a random speed
        maxSpeed = Math.random() * 2 + 1;
        speed = maxSpeed;
        // start as awake 
        awake = true;
        this.direction = direction;
    }

    /**
     * Act - do whatever the Pedestrian wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // If there is a v

    }

    /**
     * Method to cause this Pedestrian to become knocked down - stop moving, turn onto side
     */

    public void walk(){
        if(gotHeight == false){
            laneYCoord = 300;
            carRotation = getRotation();
            gotHeight = true;
        }

        if(VehicleWorld.isEffectActive()){
            if(VehicleWorld.getEffectType() == 0){
                rotation += rotationIncrease;
                setRotation(rotation);
                if(getY() > 20)
                    setLocation(getX(),getY()-4);
                maxSpeed = 0;
                beenHitTornado = true;
            }
            if(VehicleWorld.getEffectType() == 1){
                rotation += rotationIncrease;
                setRotation(rotation);
                maxSpeed -= 0.05;
                setLocation(getX() + maxSpeed,getY());
            }

        }
        else{
            if(!bullDozerHit && getY() != laneYCoord && beenHitTornado){
                knockDown();
                setLocation(getX(),getY()+4);
                if(getY() > laneYCoord){
                    setLocation(getX(), laneYCoord);
                }
            }
            if(bullDozerHit){
                this.fling();
                rotation += rotationIncrease;
                setRotation(rotation);
            }
            if (awake){
                setRotation(0);
                maxSpeed = savedMaxSpeed;

                if (getOneObjectAtOffset(0, (int)(direction * getImage().getHeight()/2 + (int)(direction * speed)), Vehicle.class) == null){
                    setLocation (getX(), getY() + (int)(speed*direction));
                }
                if(getClass() != JWalkPedestrian.class){
                    if (direction == -1 && getY() < 100){
                        getWorld().removeObject(this);
                    } else if (direction == 1 && getY() > 550){
                        getWorld().removeObject(this);
                    }

                }
                else{
                    if (getOneObjectAtOffset((int)xSpeed, (int)(direction * getImage().getHeight()/2 + (int)(direction * ySpeed)), Vehicle.class) == null){
                        setLocation(getX() + xSpeed, getY() + ySpeed);
                    }
                }
            }
        }
    }

    public void knockDown () {
        speed = 0;
        setRotation (90);
        awake = false;
    }

    /**
     * Method to allow a downed Pedestrian to be healed
     */
    public void healMe () {
        speed = maxSpeed;
        setRotation (0);
        awake = true;
        beenHitTornado = false;
    }

    public void boardBus(){
        getWorld().removeObject(this);
    }

    public void fling(){
        bullDozerHit = true;
        setLocation(getX()+1,getY()-8);

    }

    public boolean isAwake () {
        return awake;
    }
}
