import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is the superclass for Vehicles.
 * 
 */
public abstract class Vehicle extends SuperSmoothMover
{
    protected boolean isConstructionVehicle = false;
    protected double maxSpeed;
    protected double speed;
    protected double savedMaxSpeed;
    protected int direction; // 1 = right, -1 = left
    protected boolean moving;
    protected int yOffset;
    protected boolean bullDozerHit = false;
    protected VehicleSpawner origin;
    protected int rotation = 0;
    static final int rotationIncrease = 10;
    protected boolean isSpinning = false;
    protected boolean isOnConcrete = false;
    protected boolean isTornado = false;
    protected int laneYCoord;
    protected int carRotation;
    protected boolean gotHeight = false;
    protected boolean tornadoSpeedDecrease = false;
    protected boolean isSwitchingLanes = false;
    protected boolean switchingLeft = false;
    protected boolean switchingRight = false;
    protected int nextLaneY;
    //make a stat bar of stats to fatalities to sucessful across
    //Make vehicles that hit eachother to swerve on other lanes

    protected abstract boolean checkHitPedestrian ();

    public Vehicle (VehicleSpawner origin) {
        this.origin = origin;
        moving = true;

        if (origin.facesRightward()){
            direction = 1;

        } else {
            direction = -1;
            getImage().mirrorHorizontally();
        }
    }

    public boolean checkHitVehicle(){
        Vehicle v = (Vehicle)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Vehicle.class);
        if (v != null && !v.getIsConstructionVehicle()){
            v.fling();
            return true;
        }
        return false;
    }

    public boolean checkIsOnConcrete(){
        Concrete c = (Concrete)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Concrete.class);
        if(c != null && !isOnConcrete){
            if(!isConstructionVehicle){
                maxSpeed = 1;
                isOnConcrete = true;
            }
            return true;
        }
        else if(c == null && isOnConcrete){
            maxSpeed = savedMaxSpeed;
            isOnConcrete = false;
            return false;
        }
        return false;
    }

    public void addedToWorld (World w){
        setLocation (origin.getX() - (direction * 100), origin.getY() - yOffset);
    }

    /**
     * A method used by all Vehicles to check if they are at the edge.
     * 
     * Note that this World is set to unbounded (The World's super class is (int, int, int, FALSE) which means
     * that objects should not be stopped from leaving the World. However, this introduces a challenge as there
     * is the potential for objects to disappear off-screen but still be fully acting and thus wasting resources
     * and affecting the simulation even though they are not visible.
     */

    public void fling(){
        bullDozerHit = true;
        setLocation(getX()+1,getY()-8);
        isSpinning = true;
    }

    protected boolean checkEdge() {
        if (direction == 1)
        { // if moving right, check 200 pixels to the right (above max X)
            if (getX() > getWorld().getWidth() + 200){
                return true;
            }
        } 
        else 
        { // if moving left, check 200 pixels to the left (negative values)
            if (getX() < -200){
                return true;
            }
        }
        return false;
    }

    public boolean getIsConstructionVehicle(){
        return isConstructionVehicle;
    }

    /**
     * Method that deals with movement. Speed can be set by individual subclasses in their constructors
     */
    public void drive() 
    {
        if(gotHeight == false){
            laneYCoord = getY();
            carRotation = getRotation();
            gotHeight = true;
        }

        // Ahead is a generic vehicle - we don't know what type BUT
        // since every Vehicle "promises" to have a getSpeed() method,
        // we can call that on any vehicle to find out it's speed

        checkIfGlobalAffect();
        if(VehicleWorld.getEffectType() != 0){
            globalAftermath();
            if(bullDozerHit){
                ifHitByBulldozer();
            }
            else{
                checkIsOnConcrete();
                if(!isSwitchingLanes){
                    //change to if turning left and right instead of turning left
                    Vehicle ahead = (Vehicle) getOneObjectAtOffset (direction * (int)(speed + getImage().getWidth()/2 + maxSpeed), 0, Vehicle.class);
                    if (ahead == null)
                    {
                        speed = maxSpeed;

                    }else if(checkLeft(getY()) == true && !VehicleWorld.isEffectActive() && (getClass() != BullDozer.class ||getClass() == BullDozer.class && ahead.getIsConstructionVehicle() == true)){

                        isSwitchingLanes = true;
                        switchingLeft = true;
                        setRotation(-45);
                        VehicleWorld world = (VehicleWorld) getWorld();
                        laneYCoord = getY()-48 - 6;
                    }
                    else if(checkRight(getY()) == true && !VehicleWorld.isEffectActive() && (getClass() != BullDozer.class ||getClass() == BullDozer.class && ahead.getIsConstructionVehicle() == true)){
                        isSwitchingLanes = true;
                        switchingRight = true;
                        setRotation(45);
                        VehicleWorld world = (VehicleWorld) getWorld();
                        laneYCoord = getY()+48 + 6;
                    }
                    else if(speed > ahead.getSpeed()){
                        speed = ahead.getSpeed();
                    }
                }
                else if(isSwitchingLanes && switchingLeft){
                    checkSwitchedLeftLane(laneYCoord);
                }
                else if(isSwitchingLanes && switchingRight){
                    checkSwitchedRightLane(laneYCoord);
                }

                move (speed * direction);
            }

        }

    }   

    public void checkSwitchedLeftLane(int destinationY){
        if(getY() <= destinationY){
            setLocation(getX(),destinationY);
            setRotation(0);
            isSwitchingLanes = false;
            switchingLeft = false;
            maxSpeed = savedMaxSpeed;
        }
    }

    public void checkSwitchedRightLane(int destinationY){
        if(getY() >= destinationY){
            setLocation(getX(),destinationY);
            setRotation(0);
            isSwitchingLanes = false;
            switchingRight = false;
            maxSpeed = savedMaxSpeed;
        }
    }

    public boolean checkLeft(int y){
        VehicleWorld world = (VehicleWorld) getWorld();
        int lane = world.getLane(y);
        int laneY = world.getLaneY(lane);
        if(lane == 0){
            return false;
        }
        else{
            getWorld().addObject(new laneChecker(speed, direction,(int)(getImage().getWidth()*1.5), "left"),getX(),getY()-48-6);
            laneChecker lc = (laneChecker)getOneObjectAtOffset(0, -48-6, laneChecker.class);
            if(lc == null)
                return false;
            else{
                getWorld().removeObject(lc);
                return true;
            }
        }
    }

    public boolean checkRight(int y){
        VehicleWorld world = (VehicleWorld) getWorld();
        int lane = world.getLane(y);
        int laneY = world.getLaneY(lane);
        if(lane == 5){
            return false;
        }
        else{
            getWorld().addObject(new laneChecker(speed, direction,(int)(getImage().getWidth()*1.5), "right"),getX(),getY()+48+6);
            laneChecker lc = (laneChecker)getOneObjectAtOffset(0, +48+6, laneChecker.class);
            if(lc == null)
                return false;
            else{
                getWorld().removeObject(lc);
                return true;
            }
        }
    }

    /**
     * An accessor that can be used to get this Vehicle's speed. Used, for example, when a vehicle wants to see
     * if a faster vehicle is ahead in the lane.
     */
    public double getSpeed(){
        return speed;
    }

    public void checkIfGlobalAffect(){
        if(VehicleWorld.isEffectActive()){
            if(VehicleWorld.getEffectType() == 0){
                if(getRotation() == 0 || getRotation() == 180)
                    setRotation(Greenfoot.getRandomNumber(359));
                if(getY() > 20)
                    setLocation(getX(),getY()-4);
                maxSpeed = 0;
            }
            //maxSpeed -= 0.05;
            else if(VehicleWorld.getEffectType() == 1){
                maxSpeed -= 0.05;
            }
        }
    }

    public void globalAftermath(){
        if(!isSwitchingLanes){
            if(!bullDozerHit && getY() != laneYCoord){
                setLocation(getX(),getY()+4);
                if(getY() > laneYCoord){
                    setLocation(getX(), laneYCoord);
                }
                if(getY() == laneYCoord){
                    setRotation(0);
                    maxSpeed = savedMaxSpeed;
                }

            }
        }
    }

    public void ifHitByBulldozer(){
        maxSpeed = savedMaxSpeed;
        checkHitVehicle();
        this.fling();
        rotation+=rotationIncrease;
        setRotation(rotation);
        checkHitPedestrian();
        move (speed * direction);
    }

}
