package Traffic;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class VehicleController implements Subject{

	private static ArrayList<Observer> models = new ArrayList<Observer>();
        private static ArrayList<VehicleView> views = new ArrayList<VehicleView>();
        public static final int carOffsetY=(TrafficSim.RoadHeight-TrafficSim.CarHeight)/2;
        private int lastID=0;
        private static ArrayList<Vector> types = new ArrayList<Vector>();
        final static int spacing =20;
        
	public VehicleController(){} //no arg constructor
	
        public void addType(int startVel, int desiredVel){
            types.add(new Vector(startVel, desiredVel));
        }
        
        
        //add new vehicle and then register it to models and views
	public void addVehicle(int SpawnX, int SpawnY, int roadID, boolean LTR){
        
            int newVehID=uniqueID();
            int typeIndex= (int)(Math.random()*types.size());
            
	Vehicle v= new Vehicle(   SpawnX-TrafficSim.CarWidth, 
                                    SpawnY+carOffsetY, 
                                    types.get(typeIndex).X(), 
                                    types.get(typeIndex).Y(), newVehID, 
                                    roadID, LTR);
        register(v);
	}
        
	
        //update the model and the view
	public static void updateVehicles(){
            int id=0, index=0;
		for(int i=0; i<models.size(); i++){
                    //update model
			models.get(i).update();
                        
                        //remove model and view if being removed
			if(!((Vehicle)models.get(i)).checkValid()) 
                            removeVehicle(((Vehicle)models.get(i)).getID()); 
                        //else update the view
                        else {
                            id=((Vehicle)models.get(i)).getID();
                            
                            //find the view of this model
                            if(id==views.get(i).getID())    
                                index=i;
                            else {
                                index=findViewIndex(id);
                                if(index==-1) index=i;
                            }
                            
                            
                            //update the view
                            views.get(index).setPosition(  ((Vehicle)models.get(i)).getPositionX(),
                                                        ((Vehicle)models.get(i)).getPositionY() );
                            }
                        }
                
                checkCollision();
	}
        
        //find model with id given
        private static int findModelIndex(int viewID){
            for(int i=0; i<models.size(); i++){
                if(viewID==((Vehicle)models.get(i)).getID())
                    return i;
            }
            return -1;
        }
        
        //find the view index with the id given
    private static int findViewIndex(int id){
        for(int i=0; i<views.size(); i++){
            if(id==views.get(i).getID()){
                return i;
            }
        }
        return -1;
    }
    
    //sort views in order of their X position
    private static void sortViews(){
        Collections.sort(views, new Comparator<VehicleView>() {
        @Override 
        public int compare(VehicleView v1, VehicleView v2) {
            return v1.posX()- v2.posX();
        }
        });
    }
    
    //check collision between cars and decide which event that vehicle should enter
    private static void checkCollision(){
        sortViews();
        int carBox = TrafficSim.CarWidth+spacing;
        boolean LTR=true;
        
        for(int i=0; i<views.size() && i>=0; i++){
            LTR=views.get(i).getLTR();
            int increment=1;
            if(LTR==false)increment=-1;
            
            
            int vehInFrontIndex=0;
            boolean flag=true;
            
            //find the veiw index of the car in front on the same road
            for(int j=i+increment; j<views.size() && j>=0 && flag; j+=increment)
                if(views.get(i).getRoadID()==views.get(j).getRoadID()){
                    vehInFrontIndex=j;
                    flag=false;
                }
            
        if(!flag){ //if not the last car on the road
            //check infront
            if(vehInFrontIndex<views.size() && vehInFrontIndex>=0)
                //check vehicle in front
                //if the front of the car hits the back of the car infront of it
                if((LTR && views.get(i).posX()+carBox > 
                        views.get(vehInFrontIndex).posX()) || 
                    (!LTR && views.get(i).posX() < 
                        views.get(vehInFrontIndex).posX()+carBox) ){
                    if(requestPassout(views.get(i).getID()))
                        changeLane(views.get(i).getID(), i);
                }
                    
            }
        }
        
    }
    
    //returns true if viable option to passout, if not then brake
    private static boolean requestPassout(int id){
        int modelInd=findModelIndex(id);
        int LTR=1;
        boolean flag=true;
        for(int i=0; i<models.size() && flag; i++){
            //check if any other vehicles are blokcing it to move into the new lane
            //the other vehicle must be in the fast lane and have the same roadID
            if(modelsOnSameRoad(i,modelInd)){
                    if(((Vehicle)models.get(modelInd)).getLTR()==false)
                        LTR=-1;
                    else LTR=1;
                
                //reqestedPos = position of the vehicle if it actually moved into the lane
                Vector requestedPos=new Vector(
                        ((Vehicle)models.get(modelInd)).getPositionX(),
                        ((Vehicle)models.get(modelInd)).getPositionY()+(TrafficSim.RoadHeight*LTR));
                
                if(((Vehicle)models.get(i)).intersects(requestedPos, spacing) 
                        && ((Vehicle)models.get(i)).fastLane()) //and vehicle is in fast lane
                    flag=false;
                
                //check for braking in this lane
                requestedPos.setY(((Vehicle)models.get(modelInd)).getPositionY());
                if(((Vehicle)models.get(i)).intersects(requestedPos, spacing))
                    ((Vehicle)models.get(i)).brake();
            }
        }
        return flag;
    }
    
    //check if both models are on the same road
    private static boolean modelsOnSameRoad(int indexA, int indexB){
        //index out of bounds
        if(indexA<0 || indexA>=models.size() || indexB<0 || indexB>=models.size())
            return false;
        
        //if same roadID return true
        if(((Vehicle)models.get(indexA)).getRoadID()==
                ((Vehicle)models.get(indexB)).getRoadID())
            return true;
        
        return false;
    }
    
    //tell the model to change the lane and then update the view
    private static void changeLane(int id, int viewIndex){
        int modelIndex=findModelIndex(id);
        if(modelIndex>=0);
        ((Vehicle)models.get(modelIndex)).changeToFastLane();
        
        views.get(viewIndex).setPosY(((Vehicle)models.get(modelIndex)).getPositionY());
    }
    
    //tell the model to brake
    private static void brake(int ViewIndex){
        int modelIndex=findModelIndex(ViewIndex);
        int positionX=((Vehicle)models.get(modelIndex)).brake();
        views.get(ViewIndex).setPosX(positionX);
    }
        
    //create a unique id
    private int uniqueID(){
        lastID++;
        if(lastID>1000000)
            lastID=0;
        return lastID;
    }
        
    //unregister model and view
    public static void removeVehicle(int id){
            int modelInd, viewInd;
            viewInd=findViewIndex(id);
            modelInd=findModelIndex(id);
            models.remove(modelInd);
            views.remove(viewInd);
        }

    //get all the views
    public ArrayList<VehicleView> getViews() {
        return views;
    }
    
    @Override
    public void register(Observer o) {
        models.add(o);
        views.add(new VehicleView((Vehicle)o));
    }

    @Override
    public void unregister(Observer o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyObserver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	
}