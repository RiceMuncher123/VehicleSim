import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <h1>The new and vastly improved 2022 Vehicle Simulation Assignment.</h1>
 * <p> This is the first redo of the 8 year old project. Lanes are now drawn dynamically, allowing for
 *     much greater customization. Pedestrians can now move in two directions. The graphics are better
 *     and the interactions smoother.</p>
 * <p> The Pedestrians are not as dumb as before (they don't want straight into Vehicles) and the Vehicles
 *     do a somewhat better job detecting Pedestrians.</p>
 * 
 * Version Notes - Feb 2023
 * --> Includes grid <--> lane conversion method
 * --> Now starts with 1-way, 5 lane setup (easier)
 */

/**
 * Notes:
 * The reference of "construction vehicles" refer to the following vehicles: Bull Dozer, Dump Truck, Cement Truck
 * Pedestrian sounds was recorded using my voice (hehe)
 * Concrete being in the air during tornado was intentional since it looked cool when it happens
 * Tornado event spawns clouds which fade out with the tornado
 * Can turn off global affects in Vehicle world by setting the variable globalAffectOn in the constructor to false
 * Pedestrians sometimes screams to prevent overlap of audios and for other sounds to shine
 * Put all the pedestrian images within each class to make it more modular instead of having 10 different pedestrian classes which do the same thing
 * 
 * 
 * Bugs:
 * Vehicles sometimes go into each other during lane switches for only temporarily (found a solution)
 * White Vans only sometimes stops vehicles
 * Very rarley vehicles just stop in midplace 
 * Vehicles very rarley go off road after tornado event
 * 
 * 
 * 
 * 
 * Features:
 * 
 * Vehicles:
 * 
 * Cement Truck:
 * Spawns concrete on the lane which slows down other vehicles except other construction type vehicles
 * 
 * Dump Truck:
 * Spawns a wheel barrow pedestrian which moves along with the vehicle
 * 
 * Bull Dozer
 * A vehicle which knocks up other vehicles and pedestrians excluding construction vehicles
 * 
 * Gas Tank Truck
 * A vehicle which when on hit, decides to detonate and explode erasing actors around in the world
 * 
 * White Van
 * A vehicle which when is close to a vehicle on the same lane, stops that vehicle, spawns a robber actor which then kicks the driver out of the car and drives off with the stolen vehicle
 * 
 * Car:
 * Changed the car's graphics to randomly predetermined colors rather than just a red color
 * 
 * 
 * 
 * 
 * Pedestrians:
 * J Walking Pedestrian
 * A pedestrian who moves diagonally across the road
 * 
 * Wheel Barrow Worker
 * A worker that moves along with the dump truck vehicle and picks dead pedestrians on the road and enters the dump truck once at the end of the road
 * 
 * Robber
 * A pedestrian which robs a pedestrian's car and drives off with the said vehicle
 * 
 * 
 * 
 * 
 * Global Affects:
 * Tornado:
 * An event which picks up all vehicles and spins them into the air and once gone, downs pedestrians and sends cars back to their original lane
 * Also spawns clouds which fade out with the tornado
 * 
 * 
 * Strong Wind:
 * An event which deaccelerates vehicles and sends vehicles and pedestrians backwards out of the world
 * 
 * Sounds
 * 
 * Added sounds for global affects, vehicles, interations between actors, pedestrians
 * 
 * Pedestrian sounds was recorded using my voice (hehe)
 * 
 */

/**
 * Credits:
 * 
 * ArtWorks:
 * 
 * Backround:
 * https://www.deviantart.com/animaltoonstudios20/art/Cartoon-City-Background-2-898691118
 * Artist: AnimalToonStudios20
 * Note: Edited it to make it fit
 * 
 * Car
 * Notes: Edited the color with the starter image code
 * 
 * Cement Truck:
 * https://www.pngegg.com/en/png-zywtq/download?height=80 
 * Artist: N/A
 * 
 * BullDozer:
 * https://pngtree.com/freepng/yellow-bulldozer-big-bulldozer-road-construction-bulldozer-cartoon-illustration_3873926.html
 * Artist: N/A
 * 
 * GasTankTruck: 
 * https://www.vecteezy.com/vector-art/8599569-cartoon-tank-truck-or-gas-truck
 * Uploaded By: Graphics RF
 * Note: Used this as a basis for what was actually used with editing
 * 
 * WhiteVan:
 * https://www.vexels.com/png-svg/preview/258899/white-van-transport-flat
 * Artist: N/A
 * 
 * Explosion:
 * https://www.kindpng.com/imgv/hwxRJTw_explosion-clipart-png-png-download-cartoon-explosion-transparent/
 * Uploaded By: Bhavyesh Kosadiya
 * 
 * Tornado
 * https://www.cleanpng.com/png-tornado-icon-light-blue-tornado-454029/download-png.html
 * Artist: N/A
 * 
 * Clouds
 * https://www.nicepng.com/downpng/u2a9o0o0r5a9r5i1_cloud-dark-cloud-clipart/
 * Artist: N/A
 * 
 * Blowing Clouds
 * https://pngtree.com/freepng/cartoon-cloudy-gale-anthropomorphic-weather-wind-clipart_5847266.html
 * Artist: dongcaiying
 * 
 * Pedestrian Images:
 * https://sanderfrenken.github.io/Universal-LPC-Spritesheet-Character-Generator/#?body=Body_color_light&head=Human_male_light&sex=female&clothes=Shortsleeve_red&hair=Pigtails_dark_brown&dress=Slit_dress_black&shoes=Shoes_lavender 
 * 
 * Authors: Authors: Benjamin K. Smith (BenCreating), bluecarrot16, TheraHedwig, Evert, MuffinElZangano, Durrani, Pierre Vigier (pvigier), ElizaWy, Matthew Krohn (makrohn), Johannes Sjölund (wulax), Stephen Challener (Redshrike), Radomir Dopieralski, Nila122, Joe White
 * Note: Used this as a baseline for the other pedestrians. Sources to links of other pedestrians below.
 * 
 * 
 * https://sanderfrenken.github.io/Universal-LPC-Spritesheet-Character-Generator/#?body=Body_color_light&head=Human_male_light&sex=teen&hair=Mop_blonde&clothes=Longsleeve_teal&legs=Pants_green&shoes=Boots_lavender&hat=none&accessory=none 
 * 
 * https://sanderfrenken.github.io/Universal-LPC-Spritesheet-Character-Generator/#?body=Body_color_light&head=Human_male_light&beard=Bigstache_blonde_2&clothes=Longsleeve_navy&legs=Pants_green&shoes=Slippers_pink&sex=teen&hair=Bob_sandy 
 * 
 * https://sanderfrenken.github.io/Universal-LPC-Spritesheet-Character-Generator/#?body=Body_color_light&head=Human_male_light&sex=muscular&legs=Wide_pants_black&shoes=Shoes_brown
 * 
 * https://sanderfrenken.github.io/Universal-LPC-Spritesheet-Character-Generator/#?body=Body_color_light&head=Human_male_light&sex=teen&hair=Spiked_liberty2_red&clothes=Longsleeve_teal&shoes=Boots_black&sash=Sash_blue  
 * 
 * https://sanderfrenken.github.io/Universal-LPC-Spritesheet-Character-Generator/#?body=Body_color_light&head=Human_male_light&sex=female&clothes=Longsleeve_rose&legs=Leggings_navy&shoes=Slippers_rose&hair=Shortknot_gray 
 * 
 * https://sanderfrenken.github.io/Universal-LPC-Spritesheet-Character-Generator/#?body=Body_color_light&head=Human_male_light&clothes=Longsleeve_orange&jacket=Collared_coat_black&legs=Pants_blue&shoes=Shoes_sky&beard=Beard_gold&hair=Loose_gold
 * 
 * https://sanderfrenken.github.io/Universal-LPC-Spritesheet-Character-Generator/#?body=Body_color_light&head=Human_child_light&sex=child&clothes=Child_shirts_blue&legs=Child_pants_red 
 * 
 * https://sanderfrenken.github.io/Universal-LPC-Spritesheet-Character-Generator/#?body=Body_color_light&head=Human_male_light&hair=Pixie_green&jacket=Collared_coat_brown_striped&earring=Earring_gold&legs=Pants_blue&shoes=Shoes_pink 
 * 
 * https://sanderfrenken.github.io/Universal-LPC-Spritesheet-Character-Generator/#?body=Body_color_light&head=Human_male_light&sex=female&dress=Slit_dress_blue&vest=none&legs=none&shoes=Slippers_black&hair=Xlong_blonde 
 * 
 * 
 * 
 * 
 * Sounds:
 * 
 * Road Ambience
 * https://www.zapsplat.com/music/city-ambience-busy-road-cars-trucks-and-motorcycles-passing-police-siren-horn-honks-occasional-people-mekong-delta-vietnam-2/ 
 * Credits FreeToUseSounds
 *
 * Tornado
 * https://www.zapsplat.com/music/inside-tornado-strong-wind-destruction/ 
 * Credits: PMSFK
 *
 * Car Honk
 * https://www.zapsplat.com/music/car-horn-2x-short-beeps-2017-toyota-corolla/ 
 * Credits: Zapslat
 * 
 * Strong winds event sound
 * https://www.zapsplat.com/music/wind-strong-stormy-wind-through-trees-2/
 * Credits: ZapSlat
 * 
 * Explosion
 * https://www.zapsplat.com/music/8bit-explosion-bomb-boom-or-blast-cannon-retro-old-school-classic-cartoon/ 
 * Credits:  Stock Media
 * Note: Used Audacity to fix the error
 * 
 * Car Accelerate
 * https://www.zapsplat.com/music/car-accelerate-away-fast-past-2/ 
 * 
 * Many Cars Honking during tornado event
 * https://soundbible.com/61-City-Car-Horn.html 
 * Credits: N/A
 * 
 * 
 * 
 * 
 * Code Sources:
 * 
 * Code for the effects and subclasses
 * Credits: Jordan Cohen
 * 
 * 
 */

public class VehicleWorld extends World
{
    private GreenfootImage background;
    private GreenfootImage backroundTornado;
    // Color Constants
    public static Color GREY_BORDER = new Color (108, 108, 108);
    public static Color GREY_STREET = new Color (88, 88, 88);
    public static Color YELLOW_LINE = new Color (255, 216, 0);

    // Instance variables / Objects
    private boolean twoWayTraffic, splitAtCenter;
    private int laneHeight, laneCount, spaceBetweenLanes;
    private int[] lanePositionsY;
    private VehicleSpawner[] laneSpawners;
    private static boolean effectActive;
    private int acts;
    protected static int typeGlobalEffect;
    private boolean globalAffectOn;
    protected boolean screamActive = true;
    protected int screamCounter = 0;

    private GreenfootSound tornadoSound;
    private GreenfootSound roadAmbience;
    private GreenfootSound manyCarsHonk;
    private GreenfootSound strongWindSound;
    private GreenfootSound GasTankExplosion;
    private GreenfootSound concreteLay;
    private GreenfootSound PedestrianPleadForHelp;

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public VehicleWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 600, 1, false); 
        effectActive = false;
        setPaintOrder (Explosion.class,Pedestrian.class, Vehicle.class,Concrete.class);
        Greenfoot.setSpeed(50);

        // set up background
        background = new GreenfootImage ("background01.png");
        backroundTornado = new GreenfootImage("background02.png");
        setBackground (background);
        tornadoSound = new GreenfootSound("TornadoSound.mp3");
        roadAmbience = new GreenfootSound("RoadAmbience.mp3");
        manyCarsHonk = new GreenfootSound("ManyCarsHonking.mp3");
        strongWindSound = new GreenfootSound("StrongWinds.mp3");
        GasTankExplosion = new GreenfootSound("Explosion.mp3");
        PedestrianPleadForHelp = new GreenfootSound("PedestrianSound/GotRobbed.mp3");
        // Set critical variables
        globalAffectOn = true;
        laneCount = 6;
        laneHeight = 48;
        spaceBetweenLanes = 6;
        splitAtCenter = false;
        twoWayTraffic = false;
        acts = 0;
        typeGlobalEffect = 100;
        // Init lane spawner objects 
        laneSpawners = new VehicleSpawner[laneCount];

        // Prepare lanes method - draws the lanes
        lanePositionsY = prepareLanes (this, background, laneSpawners, 222, laneHeight, laneCount, spaceBetweenLanes, twoWayTraffic, splitAtCenter);
    }

    public void started(){
        roadAmbience.playLoop();
    }

    public void stopped(){
        roadAmbience.stop();
    }

    public void setScreamTrue(){
        screamActive = true;
    }

    public void setScreamFalse(){
        screamCounter = 0;
        screamActive = false;
    }

    public boolean getScreamStatus(){
        return screamActive;
    }

    public void act () {
        spawn();
    }

    public static boolean isEffectActive () {
        return effectActive;
    }

    public static int getEffectType(){
        return typeGlobalEffect;
    }

    private void spawn () {
        // Chance to spawn a vehicle
        screamCounter++;
        acts++;
        if(screamCounter == 240){
            setScreamTrue();
        }
        if (Greenfoot.getRandomNumber(15) == 0){
            int lane = Greenfoot.getRandomNumber(laneCount);
            if (!laneSpawners[lane].isTouchingVehicle() && !effectActive && acts > 120){
                int vehicleType = Greenfoot.getRandomNumber(8);
                if (vehicleType == 0){
                    addObject(new Car(laneSpawners[lane]), 0, 0);
                } else if (vehicleType == 1){
                    addObject(new Bus(laneSpawners[lane]), 0, 0);
                } else if (vehicleType == 2){
                    addObject(new Ambulance(laneSpawners[lane]), 0, 0);
                }else if(vehicleType == 3){
                    addObject(new BullDozer(laneSpawners[lane]), 0, 0);
                }else if(vehicleType == 4){
                    addObject(new CementTruck(laneSpawners[lane]), 0, 0);
                }
                else if(vehicleType == 5){
                    addObject(new DumpTruck(laneSpawners[lane]), 0, 0);
                }
                else if(vehicleType == 6){
                    addObject(new GasTankTruck(laneSpawners[lane]), 0, 0);
                }
                else if(vehicleType == 7){
                    addObject(new WhiteVan(laneSpawners[lane]), 0, 0);
                }
            }
        }

        // Chance to spawn a Pedestrian
        if (!effectActive && Greenfoot.getRandomNumber (30) == 0){
            int xSpawnLocation = Greenfoot.getRandomNumber (600) + 100; // random between 99 and 699, so not near edges
            boolean spawnAtTop = Greenfoot.getRandomNumber(2) == 0 ? true : false;
            if (spawnAtTop){
                int pedestrianType = Greenfoot.getRandomNumber(2);
                if(pedestrianType == 0)
                    addObject(new JWalkPedestrian (1), xSpawnLocation,50);
                else if(pedestrianType == 1)
                    addObject(new Pedestrian1 (1), xSpawnLocation, 50);    
            } else {
                int pedestrianType = Greenfoot.getRandomNumber(2);
                if(pedestrianType == 0)
                    addObject(new JWalkPedestrian (-1), xSpawnLocation, 550);
                else if(pedestrianType == 1)
                    addObject(new Pedestrian1 (-1), xSpawnLocation, 550);

            }
        }
        if(globalAffectOn){
            if (!effectActive && Greenfoot.getRandomNumber(400) == 0 && acts >= 300){
                typeGlobalEffect = Greenfoot.getRandomNumber(2);
                stopCityAmbience();
                if(typeGlobalEffect == 0){
                    addObject (new Tornado(), 400, 300);
                    addObject(new ThunderClouds("right"), 0, 20 + Greenfoot.getRandomNumber(50));
                    addObject(new ThunderClouds("left"), 600, 20 + Greenfoot.getRandomNumber(50));
                    setBackground(backroundTornado);
                    lanePositionsY = prepareLanes (this, backroundTornado, laneSpawners, 222, laneHeight, laneCount, spaceBetweenLanes, twoWayTraffic, splitAtCenter);
                    tornadoSound.playLoop();
                    manyCarsHonk.play();
                }
                else if(typeGlobalEffect == 1){
                    addObject (new StrongWinds(), 700, 50);
                    strongWindSound.play();
                }

                effectActive = true;
            }
            if (effectActive && typeGlobalEffect == 0 && getObjects(Tornado.class).size() == 0){
                effectActive = false;
                acts = 0;
                typeGlobalEffect = 100;
                playCityAmbience();
            }
            else if (effectActive && typeGlobalEffect == 1 && getObjects(StrongWinds.class).size() == 0){
                effectActive = false;
                typeGlobalEffect = 100;
                stopStrongWindSounds();
                playCityAmbience();
                acts = 0;
            }
            if(typeGlobalEffect == 100 && acts == 180){
                setBackground(background);
                lanePositionsY = prepareLanes (this, background, laneSpawners, 222, laneHeight, laneCount, spaceBetweenLanes, twoWayTraffic, splitAtCenter);
                //END NADO SOUNDS
                tornadoSound.stop();
            }
        }

    }

    public void stopTornadoSound(){
        tornadoSound.stop();
    }

    public void stopStrongWindSounds(){
        strongWindSound.stop();
    }

    public void stopCityAmbience(){
        roadAmbience.stop();
    }

    public void playCityAmbience(){
        roadAmbience.playLoop();
    }

    public void playExplosion(){
        GasTankExplosion.playLoop();
    }

    public void stopExplosion(){
        GasTankExplosion.stop();
    }

    public void pedestrianPlead(){
        PedestrianPleadForHelp.play();
    }

    public int getActs(){
        return acts;
    }

    /**
     *  Given a lane number (zero-indexed), return the y position
     *  in the centre of the lane. (doesn't factor offset, so 
     *  watch your offset, i.e. with Bus).
     *  
     *  @param lane the lane number (zero-indexed)
     *  @return int the y position of the lane's center, or -1 if invalid
     */
    public int getLaneY (int lane){
        if (lane < lanePositionsY.length){
            return lanePositionsY[lane];
        } 
        return -1;
    }

    /**
     * Given a y-position, return the lane number (zero-indexed).
     * Note that the y-position must be valid, and you should 
     * include the offset in your calculations before calling this method.
     * For example, if a Bus is in a lane at y=100, but is offset by -20,
     * it is actually in the lane located at y=80, so you should send
     * 80 to this method, not 100.
     * 
     * @param y - the y position of the lane the Vehicle is in
     * @return int the lane number, zero-indexed
     * 
     */
    public int getLane (int y){
        for (int i = 0; i < lanePositionsY.length; i++){
            if (y <= lanePositionsY[i]){
                return i;
            }
        }
        return -1;
    }

    public int getLaneHeight(){
        return laneHeight;
    }

    public static int[] prepareLanes (World world, GreenfootImage target, VehicleSpawner[] spawners, int startY, int heightPerLane, int lanes, int spacing, boolean twoWay, boolean centreSplit, int centreSpacing)
    {
        // Declare an array to store the y values as I calculate them
        int[] lanePositions = new int[lanes];
        // Pre-calculate half of the lane height, as this will frequently be used for drawing.
        // To help make it clear, the heightOffset is the distance from the centre of the lane (it's y position)
        // to the outer edge of the lane.
        int heightOffset = heightPerLane / 2;

        // draw top border
        target.setColor (GREY_BORDER);
        target.fillRect (0, startY, target.getWidth(), spacing);

        // Main Loop to Calculate Positions and draw lanes
        for (int i = 0; i < lanes; i++){
            // calculate the position for the lane
            lanePositions[i] = startY + spacing + (i * (heightPerLane+spacing)) + heightOffset ;

            // draw lane
            target.setColor(GREY_STREET); 
            // the lane body
            target.fillRect (0, lanePositions[i] - heightOffset, target.getWidth(), heightPerLane);
            // the lane spacing - where the white or yellow lines will get drawn
            target.fillRect(0, lanePositions[i] + heightOffset, target.getWidth(), spacing);

            // Place spawners and draw lines depending on whether its 2 way and centre split
            if (twoWay && centreSplit){
                // first half of the lanes go rightward (no option for left-hand drive, sorry UK students .. ?)
                if ( i < lanes / 2){
                    spawners[i] = new VehicleSpawner(false, heightPerLane);
                    world.addObject(spawners[i], target.getWidth(), lanePositions[i]);
                } else { // second half of the lanes go leftward
                    spawners[i] = new VehicleSpawner(true, heightPerLane);
                    world.addObject(spawners[i], 0, lanePositions[i]);
                }

                // draw yellow lines if middle 
                if (i == lanes / 2){
                    target.setColor(YELLOW_LINE);
                    target.fillRect(0, lanePositions[i] - heightOffset - spacing, target.getWidth(), spacing);

                } else if (i > 0){ // draw white lines if not first lane
                    for (int j = 0; j < target.getWidth(); j += 120){
                        target.setColor (Color.WHITE);
                        target.fillRect (j, lanePositions[i] - heightOffset - spacing, 60, spacing);
                    }
                } 

            } else if (twoWay){ // not center split
                if ( i % 2 == 0){
                    spawners[i] = new VehicleSpawner(false, heightPerLane);
                    world.addObject(spawners[i], target.getWidth(), lanePositions[i]);
                } else {
                    spawners[i] = new VehicleSpawner(true, heightPerLane);
                    world.addObject(spawners[i], 0, lanePositions[i]);
                }

                // draw Grey Border if between two "Streets"
                if (i > 0){ // but not in first position
                    if (i % 2 == 0){
                        target.setColor(GREY_BORDER);
                        target.fillRect(0, lanePositions[i] - heightOffset - spacing, target.getWidth(), spacing);

                    } else { // draw dotted lines
                        for (int j = 0; j < target.getWidth(); j += 120){
                            target.setColor (YELLOW_LINE);
                            target.fillRect (j, lanePositions[i] - heightOffset - spacing, 60, spacing);
                        }
                    } 
                }
            } else { // One way traffic
                spawners[i] = new VehicleSpawner(true, heightPerLane);
                world.addObject(spawners[i], 0, lanePositions[i]);
                if (i > 0){
                    for (int j = 0; j < target.getWidth(); j += 120){
                        target.setColor (Color.WHITE);
                        target.fillRect (j, lanePositions[i] - heightOffset - spacing, 60, spacing);
                    }
                }
            }
        }
        // draws bottom border
        target.setColor (GREY_BORDER);
        target.fillRect (0, lanePositions[lanes-1] + heightOffset, target.getWidth(), spacing);

        return lanePositions;
    }

    /**
     * <p>The prepareLanes method is a static (standalone) method that takes a list of parameters about the desired roadway and then builds it.</p>
     * 
     * <p><b>Note:</b> So far, Centre-split is the only option, regardless of what values you send for that parameters.</p>
     *
     * <p>This method does three things:</p>
     * <ul>
     *  <li> Determines the Y coordinate for each lane (each lane is centered vertically around the position)</li>
     *  <li> Draws lanes onto the GreenfootImage target that is passed in at the specified / calculated positions. 
     *       (Nothing is returned, it just manipulates the object which affects the original).</li>
     *  <li> Places the VehicleSpawners (passed in via the array parameter spawners) into the World (also passed in via parameters).</li>
     * </ul>
     * 
     * <p> After this method is run, there is a visual road as well as the objects needed to spawn Vehicles. Examine the table below for an
     * in-depth description of what the roadway will look like and what each parameter/component represents.</p>
     * 
     * <pre>
     *                  <=== Start Y
     *  ||||||||||||||  <=== Top Border
     *  /------------\
     *  |            |  
     *  |      Y[0]  |  <=== Lane Position (Y) is the middle of the lane
     *  |            |
     *  \------------/
     *  [##] [##] [##| <== spacing ( where the lane lines or borders are )
     *  /------------\
     *  |            |  
     *  |      Y[1]  |
     *  |            |
     *  \------------/
     *  ||||||||||||||  <== Bottom Border
     * </pre>
     * 
     * @param world     The World that the VehicleSpawners will be added to
     * @param target    The GreenfootImage that the lanes will be drawn on, usually but not necessarily the background of the World.
     * @param spawners  An array of VehicleSpawner to be added to the World
     * @param startY    The top Y position where lanes (drawing) should start
     * @param heightPerLane The height of the desired lanes
     * @param lanes     The total number of lanes desired
     * @param spacing   The distance, in pixels, between each lane
     * @param twoWay    Should traffic flow both ways? Leave false for a one-way street (Not Yet Implemented)
     * @param centreSplit   Should the whole road be split in the middle? Or lots of parallel two-way streets? Must also be two-way street (twoWay == true) or else NO EFFECT
     * 
     */
    public static int[] prepareLanes (World world, GreenfootImage target, VehicleSpawner[] spawners, int startY, int heightPerLane, int lanes, int spacing, boolean twoWay, boolean centreSplit){
        return prepareLanes (world, target, spawners, startY, heightPerLane, lanes, spacing, twoWay, centreSplit, spacing);
    }

}
