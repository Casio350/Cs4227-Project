package Traffic;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Jeoff
 */
public class DrawPanel extends JPanel
    {
    private BufferedImage carImg, reverseCarImg, roadImg, sideRoadImg;
    
    public DrawPanel(){ 
        //initalise images
        File carFile = new File("Images/car.png");
        File reverseCarFile = new File("Images/carFlipped.png");
        File roadFile = new File("Images/road.png");
        File sideRoadFile = new File ("Images/sideroad.png");
        if(carFile.exists() && roadFile.exists() && reverseCarFile.exists()){
        try{
        carImg = ImageIO.read(carFile);
        roadImg = ImageIO.read(roadFile);
        reverseCarImg = ImageIO.read(reverseCarFile);
        sideRoadImg = ImageIO.read(sideRoadFile);
        }
        catch(Exception e){ e.printStackTrace(); }
        }
        else{
            System.out.print("File not found");
        }
    
    }

        @Override
        public void paintComponent(Graphics g)
        { 
            TrafficSim trafficSim = new TrafficSim();
            ArrayList<RoadView> RoadViews = trafficSim.getRoadViews();
            
            for(RoadView r : RoadViews) //draw each road
                if(r.getType()==0){ //type is motorway
                    g.drawImage(roadImg,r.getX(),r.getY(), r.getW(), r.getH(), null);
                }
                else if(r.getType()==1){ //type is sidelane
                    g.drawImage(sideRoadImg, r.getX(), r.getY(), r.getW(), r.getH(), null);
                }
            
            
            ArrayList<VehicleView> vehViews = TrafficSim.getVehicleViews();
            BufferedImage img=carImg;
            boolean ImageIsLTR=true;
                    for(VehicleView v : vehViews){ //draw each vehicle
                        //change image depending on Left To Right (LTR)
                        if(v.getLTR() && !ImageIsLTR){
                            img=carImg;
                            ImageIsLTR=true;
                        }
                        
                        if(v.getLTR()==false && ImageIsLTR){
                            img=reverseCarImg;
                            ImageIsLTR=false;
                        }
                        
                        g.drawImage(img,v.posX(), v.posY(), 
                                TrafficSim.CarWidth, TrafficSim.CarHeight,null);
                        
                    }
                        
        }
    }
