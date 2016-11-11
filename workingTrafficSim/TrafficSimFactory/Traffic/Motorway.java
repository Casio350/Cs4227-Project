package Traffic;


public class Motorway extends Road {
    
    
    
    public Motorway()
    {
    }
   
    public Motorway(int x, int y, int id, boolean LTR){
        super(x,y, id, LTR);       
    }
    
  
    
    @Override
    public int getY(){  //the y position is different if not left to right        
        if(super.getLTR()) return super.getY();
        return super.getY()+((getLaneCount()-1)*TrafficSim.RoadHeight);
    }
    
    public void instantiateRoad(int x, int y, int id, boolean LTR)
    {
        super.instantiateRoad(x,y,id,LTR);
    }
            
}