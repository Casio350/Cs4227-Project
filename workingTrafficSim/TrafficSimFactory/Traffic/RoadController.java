package Traffic;


import java.util.ArrayList;

public class RoadController {
	
	private static ArrayList<Road> roads = new ArrayList<Road>();
	private static ArrayList<RoadView> views = new ArrayList<RoadView>();
	
	public RoadController(){}
	
	public static void  addRoad(Motorway r){
		roads.add(r);
		views.add(new RoadView(r));
	}
        
        public static void  addRoad(Sidelane r){
		roads.add(r);
		views.add(new RoadView(r));
	}
        
        //public void spawnVehicles(){
         //   for(Road r: roads)
        //       r.addVehicle(80,40);
        //}
	
	public static ArrayList<RoadView> getViews(){
		return views;
	}
        
        public static void getSpawnPoints(ArrayList<Vector> allSP){
            ArrayList<Vector> sp=new ArrayList<Vector>();
            for(Road r : roads){
                sp = r.getSpawnPoints();
                for(Vector v :sp)
                    allSP.add(v);
            }
        }

}