package Traffic;

 

public class RoadView { //used to draw road images
	
	private int x,y, width, height, type;
        /* type:
            0=motorway
            1=sidelane
        */
       
    public RoadView()
    {
        
    }

	/*public RoadView(Motorway r){
		x=r.TopLeftX();
		y=r.TopLeftY();
		width=TrafficSim.frameSizeX;
		height=r.getLaneCount()*TrafficSim.RoadHeight;
                type =0;
       }
        
        public RoadView(Sidelane r){
		   x=r.getX();
		   y=r.getY();
                
                width=TrafficSim.frameSizeX;
                height=r.getLaneCount()*TrafficSim.RoadHeight;
                type =1;
          }*/
        
    public RoadView(Road r)
    {
        x=r.TopLeftX();
		y=r.TopLeftY();
		width=TrafficSim.frameSizeX;
		height=r.getLaneCount()*TrafficSim.RoadHeight;
		
		if(r instanceof Motorway)
                type =0;
        else if(r instanceof Sidelane)
                type =1;
    }
          
       //getters
       public int getX(){return x;}
       public int getY(){return y;}
       public int getW(){return width;}
       public int getH(){return height;}
       public int getType(){return type;}
}