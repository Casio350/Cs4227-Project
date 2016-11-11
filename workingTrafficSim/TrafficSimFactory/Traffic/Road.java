package Traffic;


import java.util.ArrayList;

public class Road{
    private ArrayList<Vector> spawnPoints= new ArrayList<Vector>();
    public int getX(){return spawnPoints.get(0).X();}
    public int getY(){ return spawnPoints.get(0).Y();}
   
    private final int laneCount=2; 
    private boolean idInitialized = false;
    private boolean LTRInitialized = false;
    private int id;
    private int x;
    private int y;
    private boolean LTR; //left to right, the direction of the traffic
    public int getID(){return id;}
    public boolean getLTR(){return LTR;}
    

       
    public void setID(int id){
        if(!idInitialized)
        {
            this.id = id;
            idInitialized = true;
        }
    }
    
    public void setLTR(boolean LTR){
        if(!LTRInitialized)
        {
            this.LTR = LTR;
            LTRInitialized = true;
        }
    }
    
    public int getLaneCount(){
    return laneCount;
    }
   
    
    public Road(){

    }
  
   
   public void instantiateRoad(int x, int y, int id, boolean LTR)
    {
	   setID(id);
       setLTR(LTR);
//        setX(x);
//        setY(y);
       if(!LTR)
       {
    	   x = TrafficSim.frameSizeX + (TrafficSim.CarWidth*2);
       }
       addSpawnPoint(x, y);
        
        
        
    }
    
    
    public Road(int x, int y, int id, boolean LTR){
        this.id=id;
        this.LTR=LTR;
        if(!LTR) x=TrafficSim.frameSizeX+(TrafficSim.CarWidth*2);
        addSpawnPoint(x,y);
    }

    //road can have multiple spawn points
    public void addSpawnPoint(int x, int y) {
        spawnPoints.add(new Vector(x,y));
    }

    public ArrayList<Vector> getSpawnPoints() {
        return spawnPoints;
    }
    
    public int TopLeftX(){
        if(LTR)return spawnPoints.get(0).X();
        return 0;
    }
    
    public int TopLeftY(){
        if(LTR) return spawnPoints.get(0).Y();
        return spawnPoints.get(0).Y();
    }
}
