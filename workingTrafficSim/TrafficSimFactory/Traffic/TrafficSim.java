package Traffic;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.*;

public class TrafficSim {
    private static boolean updateFlag=true;
    private static final short frameTime = 20; //20 milliseconds

    public static final int RoadHeight=50, CarHeight=32, CarWidth=64;
    
    //private static Window window = new Window();
    private static JFrame frame;
    public static final int frameSizeX=900, frameSizeY=400;
    private DrawPanel drawPanel=new DrawPanel();
    
    
    private static VehicleController vehCtrlr = new VehicleController();
    //private static RoadController roadCtrlr = new RoadController();
        private static ArrayList<Road> roads = new ArrayList<Road>();
    
    private static int countUpdates=0; //this is temp
    RoadFactory roadFactory = new RoadFactory();
    
    public static void main(String[] args) {
        TrafficSim trafficSim = new TrafficSim();
        trafficSim.initiate();
        
        long startTime=0,endTime=0, sleepTime=20, difference=0;
        //limiting the frame rate to 50fps
        while(true){
            startTime=System.currentTimeMillis();
            update();
            frame.repaint();
            
            //calculate sleepTime
            endTime=System.currentTimeMillis();
            difference= endTime-startTime;
            sleepTime=frameTime-difference;
            
            //sleep thread
            try{ Thread.sleep(sleepTime); }
            catch(Exception e)
            {e.printStackTrace();}
        }
    }

    private void initiate() {
        initFrame();
        
        //initiate all objects here from input  
                int roadCount=1, vehTypeCount=1;
                String roadType="";
                String [] roadSelect = {"Motorway", "Sidelane"};

                      int  minRoads=1, maxRoads=3, minTypes=1, maxTypes=5;
                
                roadCount=Input("Enter number of roads"
                            + "("+minRoads+"-"+maxRoads+")", minRoads,maxRoads);
                
                vehTypeCount=Input("Enter number vehicle types"
                            + "("+minTypes+"-"+maxTypes+")", minTypes,maxTypes);
                            
                roadType = (String) JOptionPane.showInputDialog(null,"Please enter road type motorway or sidelane", "Road Type Selection",JOptionPane.QUESTION_MESSAGE, null, roadSelect,roadSelect[0]);
                
                boolean LTR=false;
                for(int i=0; i<roadCount; i++){
                    LTR=!LTR; //invert traffic direction every second road
                    Road road1 = roadFactory.createRoad(roadType);
                    if(road1!=null)
                    {
                       road1.instantiateRoad(0,(int)(i*(TrafficSim.RoadHeight*2.2)),i,LTR);
                       roads.add(road1);
                    }
                }
                
                //create all the types of vehicles
                int startVel=0, desiredVel=0, min=30,max=150;
                for(int i=0; i<vehTypeCount; i++){
                    startVel=Input("Enter starting velocity for vehicle " + (i+1)
                            + "("+min+"-"+max+")", min,max);
                    desiredVel=Input("Enter desired velocity for vehicle " + (i+1) 
                            + "("+min+"-"+max+")", min,max);
                    
                    vehCtrlr.addType(desiredVel,startVel);
                }       
    }
    
    //take integer input witha  mi and max value 
    private static int Input(String msg, int min, int max){
        boolean valid=false;
        String input="";
        int value=0;
        while(!valid){
            input=JOptionPane.showInputDialog(null, msg,"Input",-1);
            try{
                value=Integer.parseInt(input);
                if(value>=min && value<=max)
                    valid=true;
                else{
                    ErrorMsg("value must be between " +min+" and "+max);
                }
            }
            catch(Exception e){ e.printStackTrace(); }
        }
        return value;
    }
    
    private static void ErrorMsg(String msg){
        JOptionPane.showMessageDialog(null,msg,"Input Invalid",1);
    }
    
    //intialise frame
    private static void initFrame() {
        frame = new JFrame("Traffic Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DrawPanel drawPanel = new DrawPanel();

        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

        frame.setResizable(false);
        frame.setSize(frameSizeX, frameSizeY);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    //update all the vehicles 
    private static void update() {
        countUpdates++;
        vehCtrlr.updateVehicles();
        
        if(countUpdates>50){ //every 50 loops spawn new vehicles
            countUpdates=0;

        for(Road r : roads){ //one vehicle spawned per road
            vehCtrlr.addVehicle(r.getX()-TrafficSim.CarWidth, 
                               r.getY()+vehCtrlr.carOffsetY, 
                               r.getID(), r.getLTR());
            }
        }
    } 
    
    
    public ArrayList<RoadView> getRoadViews(){ //create road views for the gui
        ArrayList<RoadView> views = new ArrayList<RoadView>();
        RoadView roadView = new RoadView();
        
        for(Road r : roads)
        {
            views.add(new RoadView(r));
        }
        
       
        return views;
    }
    
    //ask controller for the vehicle views
    public static ArrayList<VehicleView> getVehicleViews(){
       return  vehCtrlr.getViews();
    }
    
}
