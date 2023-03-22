import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Spawner object is a place where a Vehicle can spawn. Each spawner is
 * able to check if there is already a Vehicle in the spot to avoid spawning
 * multiple Vehicles on top of each other.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class laneChecker extends SuperSmoothMover
{
    public static final Color TRANSPARENT_RED = new Color (255, 0, 0, 128);

    private GreenfootImage image;
    private boolean rightward;
    private boolean visible;
    private int height, width;
    private double speed;
    private int direction;

    public laneChecker (double speed, int direction, int vehicleWidth)
    {
        this.speed = speed;
        this.direction = direction;
        this.rightward = rightward;
        VehicleWorld world = (VehicleWorld) getWorld();
        this.height = (int)(48 * 0.75);
        width = vehicleWidth;
        visible = true;
        image = new GreenfootImage (width, height);
        if(visible){
            image.setColor(TRANSPARENT_RED);
            image.fillRect(0, 0, width-1, height - 1);
        }
        setImage(image);
    }

    public void act(){
        move(speed*direction);
        //amTouchingVehicle() ||
        if(isAtEdge()){
            getWorld().removeObject(this);
        }        
    }

    public boolean amTouchingVehicle(){
        Bus b = (Bus)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, -4, Bus.class);
        if(b != null){
            return true;
        }
        else if (isTouchingVehicle() && b == null){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isTouchingVehicle () {
        return this.isTouching(Vehicle.class);
    }
}
