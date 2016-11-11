package Traffic;



public class RoadFactory
{
        public RoadFactory()
    {

    }

    
    public Road createRoad(String roadType)
    {
        if(roadType == null)
            return null;
        else if(roadType.equalsIgnoreCase("MOTORWAY"))
            return new Motorway();
        else if(roadType.equalsIgnoreCase("SIDELANE"))
            return new Sidelane();
        
        return null;    
    }
}
