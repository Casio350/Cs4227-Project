package Traffic;


public class Sidelane extends Road {
    //not used but uses of inheritance for future expansion
    private boolean isEntry;
    
    
     public Sidelane()
    {  
    }
    
    public boolean isLaneEntry(){ return isEntry; }
    
    public Sidelane(boolean isEntry, int x, int y, int id, boolean LTR){
        super(x,y,id, LTR);
        this.isEntry=isEntry;
    }
    
        
    public void instantiateRoad(int x, int y, int id, boolean LTR)
    {
        super.instantiateRoad(x,y,id,LTR);
    }
    
    @Override
    public int getY(){  //the y position is different if not left to right        
        if(super.getLTR()) return super.getY();
        return super.getY()+((getLaneCount()-1)*TrafficSim.RoadHeight);
    }
}