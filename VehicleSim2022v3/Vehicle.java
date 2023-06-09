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
    protected int turnCoolDown;
    protected boolean checkedLeft = false;
    protected boolean checkedRight = false;
    protected boolean beingRobbed = false;
    protected int robbedTimer;
    //make a stat bar of stats to fatalities to sucessful across
    //Make vehicles that hit eachother to swerve on other lanes
    private GreenfootSound carHonk;
    private GreenfootSound carAccelerate;
    protected abstract boolean checkHitPedestrian ();

    public Vehicle (VehicleSpawner origin) {
        this.origin = origin;
        moving = true;
        robbedTimer = 0;
        turnCoolDown = 0;
        //Sets direction
        if (origin.facesRightward()){
            direction = 1;

        } else {
            direction = -1;
            getImage().mirrorHorizontally();
        }
        //Assigns sound variables
        carHonk = new GreenfootSound("CarHonk.mp3");
        carAccelerate = new GreenfootSound("CarAccelerate.mp3");
    }
    //Check if it is touching a vehicle and if so, send that vehicle flying
    public boolean checkHitVehicle(){
        Vehicle v = (Vehicle)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Vehicle.class);
        if (v != null && !v.getIsConstructionVehicle()){
            v.fling();
            return true;
        }
        return false;
    }
    //Checks whether the vehicle is infront or touching concrete, if so, sets the speed to 1
    public boolean checkIsOnConcrete(){
        Concrete c = (Concrete)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Concrete.class);
        if(c != null && !isOnConcrete && !VehicleWorld.isEffectActive()){
            if(!isConstructionVehicle){
                maxSpeed = 1;
                isOnConcrete = true;
            }
            return true;
        }
        else if(c == null && isOnConcrete && !VehicleWorld.isEffectActive()){
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
    //Sets the isBullDozerHt Boolean to true and sends the vehicle upwards
    public void fling(){
        bullDozerHit = true;
        setLocation(getX()+1,getY()-8);
        isSpinning = true;
    }

    protected boolean checkEdge() {
        if (direction == 1)
        { // if moving right, check 200 pixels to the right (above max X)
            if (getX() > getWorld().getWidth() + 50){
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
    //Returns whether the vehicle is a conctruction vehicle
    public boolean getIsConstructionVehicle(){
        return isConstructionVehicle;
    }

    /**
     * Method that deals with movement. Speed can be set by individual subclasses in their constructors
     * Manages vehicle interactions
     */
    public void drive() 
    {
        if(gotHeight == false){
            laneYCoord = getY();
            carRotation = getRotation();
            gotHeight = true;
        }

        
        if(checkIfGlobalAffect()){
            if(isSwitchingLanes){
                isSwitchingLanes = false;
                switchingLeft = false;
                switchingRight = false;
            }
            speed = maxSpeed;
            move(direction*speed);
        }
        else if(VehicleWorld.getEffectType() != 0){

            globalAftermath();
            if(bullDozerHit){
                ifHitByBulldozer();
            }
            else{
                checkIsOnConcrete();
                LaneChecker lcLeftLane = (LaneChecker)getOneObjectAtOffset(0, -48-6, LaneChecker.class);
                LaneChecker lcRightLane = (LaneChecker)getOneObjectAtOffset(0, +48+6, LaneChecker.class);    
                LaneChecker lcFront = (LaneChecker)getOneObjectAtOffset(direction * (int)(speed + getImage().getWidth()/2 + maxSpeed), 0, LaneChecker.class);
                WheelBarrowWorker worker = (WheelBarrowWorker) getOneObjectAtOffset (direction * (int)(speed + getImage().getWidth()/2 + maxSpeed+1), 0, WheelBarrowWorker.class);
                if((worker != null && !isSwitchingLanes && getX() > 10 && worker.isAwake()) || (beingRobbed && robbedTimer < 31)){
                    if(beingRobbed){
                        robbedTimer++;
                    }
                    speed = 0;
                }
                else if(!isSwitchingLanes){
                    Vehicle ahead = (Vehicle) getOneObjectAtOffset (direction * (int)(speed + getImage().getWidth()/2 + maxSpeed), 0, Vehicle.class);
                    VehicleWorld world = (VehicleWorld) getWorld();
                    //change to if turning left and right instead of turning left
                    if (ahead == null)
                    {
                        speed = maxSpeed;
                    }
                    else if(ahead.getIsSwitchingLanes() == false && turnCoolDown > 120 && world.getActs() > 120){
                        lcLeftLane = (LaneChecker)getOneObjectAtOffset(0, -48-6, LaneChecker.class);
                        lcRightLane = (LaneChecker)getOneObjectAtOffset(0, +48+6, LaneChecker.class);        
                        if(lcLeftLane == null){
                            putLeftLaneChecker(getY());
                            lcLeftLane = (LaneChecker)getOneObjectAtOffset(0, -48-6, LaneChecker.class);
                            if(lcLeftLane != null){
                                if(lcLeftLane.amTouchingVehicle() == true){
                                    getWorld().removeObject(lcLeftLane);
                                }
                                else{
                                    carAccelerate.play();
                                    isSwitchingLanes = true;
                                    switchingLeft = true;
                                    laneYCoord = getY()-48 - 6;
                                    //maxSpeed+= 10;
                                    setRotation(-45);
                                    //return true;
                                }
                            }
                        }
                        if(!isSwitchingLanes && lcRightLane == null){
                            putRightLaneChecker(getY());
                            lcRightLane = (LaneChecker)getOneObjectAtOffset(0, +48+6, LaneChecker.class);
                            if(lcRightLane != null){
                                if(lcRightLane.amTouchingVehicle() == true){
                                    getWorld().removeObject(lcRightLane);
                                }
                                else{
                                    carAccelerate.play();
                                    isSwitchingLanes = true;
                                    switchingRight = true;
                                    laneYCoord = getY()+48 + 6;
                                    //maxSpeed+= 10;
                                    setRotation(45);
                                    //return true;
                                }
                            }

                        }
                        if(!isSwitchingLanes && getRotation() == 0){
                            //if lanes happen to have cars on them
                            carHonk.play();
                            speed = 0;
                        }
                        //ends here
                    }
                    else{
                        //if vehicle infront is switching lanes
                        speed = 0;
                    }
                    if(lcFront != null && isTouching(LaneChecker.class) && getRotation() == 0 && !isSwitchingLanes && turnCoolDown > 120)
                        speed = lcFront.getSpeed()*2;
                    else if(lcFront != null && !isTouching(LaneChecker.class) && getRotation() == 0 && !isSwitchingLanes && turnCoolDown > 120)
                        speed = lcFront.getSpeed();
                }
                else if(isSwitchingLanes && switchingLeft){        
                    if(checkSwitchedLeftLane(laneYCoord, maxSpeed) == true){
                        getWorld().removeObject(lcFront);
                    }
                    turnCoolDown = 0;
                }
                else if(isSwitchingLanes && switchingRight){
                    if(checkSwitchedRightLane(laneYCoord, maxSpeed) == true){
                        getWorld().removeObject(lcFront);
                    }
                    turnCoolDown = 0;
                }

                turnCoolDown++;
                move (speed * direction);

            }
        }  
    }
    //Sets the being robbed vehicle to true
    public void setBeingRobbedTrue(){
        beingRobbed = true;
    }
    //Sets the being robbed variable to false
    public void unSetBeingRobbedFalse(){
        beingRobbed = false;
    }
    //Returns if the vehicle is switching lanes
    public boolean getIsSwitchingLanes(){
        return isSwitchingLanes;
    }

    
    //returns whether if the vehicle is moving to the left lane
    public boolean checkSwitchedLeftLane(int destinationY, double speed){
        if(getY() -speed <= destinationY){
            setLocation(getX(),destinationY);
            setRotation(0);
            isSwitchingLanes = false;
            switchingLeft = false;
            maxSpeed = savedMaxSpeed;
            return true;
        }
        move(direction*maxSpeed);
        return false;
    }
    //returns whether if the vehicle is moving to the right lane
    public boolean checkSwitchedRightLane(int destinationY, double speed){
        if(getY() + speed >= destinationY){
            setLocation(getX(),destinationY);
            setRotation(0);
            isSwitchingLanes = false;
            switchingRight = false;
            maxSpeed = savedMaxSpeed;
            return true;
        }
        move(direction*maxSpeed);

        return false;
    }
    //Puts a left lane checker if it is in a viable spot (if its to be placed off road it will not place)
    public boolean putLeftLaneChecker(int y){
        VehicleWorld world = (VehicleWorld) getWorld();
        int lane = world.getLane(y);
        int laneY = world.getLaneY(lane);
        if(lane != 0){
            getWorld().addObject(new LaneChecker(speed, direction,(int)(getImage().getWidth()*3), "left"),getX(),getY()-48-6);
            return true;
        }
        return false;

    }
    //Puts a right lane checker if it is in a viable spot (if its to be placed off road it will not place)
    public boolean putRightLaneChecker(int y){
        VehicleWorld world = (VehicleWorld) getWorld();
        int lane = world.getLane(y);
        int laneY = world.getLaneY(lane);
        if(lane != 5){
            getWorld().addObject(new LaneChecker(speed, direction,(int)(getImage().getWidth()*3), "right"),getX(),getY()+48+6);
            return true;
        }
        return false;
    }

    /**
     * An accessor that can be used to get this Vehicle's speed. Used, for example, when a vehicle wants to see
     * if a faster vehicle is ahead in the lane.
     */
    public double getSpeed(){
        return speed;
    }
    //Returns if there is an active gloval effect and if there is, ethier slows down the vehicle or sends the vehicle in the air
    public boolean checkIfGlobalAffect(){
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
            return true;
        }
        return false;
    }
    //Sets the vehicles back to its designated lane after a global effect
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
    //A method which makes the vehicle spin and sends other vehicles flying in a chain reaction
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
