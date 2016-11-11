package Traffic;

public class Vehicle implements MovingBody, Observer {
  
    private final int id;
    private int roadID;
    private boolean isInFastLane=false, LTR=true;
    public boolean fastLane(){return isInFastLane;}
    public int getID() {return id;}
    public int getRoadID(){return roadID;}
    public boolean getLTR(){return LTR;}
    
    
    private int posX, posY, velocity, desiredVel, acceleration;
    @Override public int getPositionX() {return posX;}
    @Override public int getPositionY() {return posY;}
    @Override public int getCurVel(){ return velocity;}
    
    //constructor
    public Vehicle(int x, int y, int desiredVel, int velocity, 
            int id, int roadID, boolean LTR){
        //set start position
        posX=x;
        posY=y;
        this.desiredVel= desiredVel;
        this.id=id;
        this.roadID=roadID;
        this.LTR=LTR;
    }
    
    //if the point of another vehicle intersects this vehicle
    //spacing between vehicles is taken into account
    public boolean intersects(Vector veh2Pos, int spacing){      
        final int w=TrafficSim.CarWidth;
        boolean hitsX=false;
        //all collision takes the collsion of this vehicle in comparision of the other vehicle
        
        //check back of vehicle
        if((LTR && posX-spacing < veh2Pos.X()+w) ||
            (!LTR && posX+w+spacing > veh2Pos.X()))
            hitsX= true;
        
        //check front of vehicle
        if(hitsX && 
                ((LTR && posX+w+spacing > veh2Pos.X())||
                  (!LTR && posX+spacing < veh2Pos.X()+w)))
            hitsX= true;
        else hitsX=false;
        
        //check y
        if(hitsX && posY==veh2Pos.Y())
            return true;
        
        return false;
    }

    @Override
    public void update(){ //update logic here
        int direction = 1;
        if(!LTR) direction=-1;
        
        acceleration=0;
        reachDesiredVel();
        velocity+=acceleration;
        
        
        posX+=direction*(velocity/10); //put velocity to scale
    }
    
    private void reachDesiredVel() {
        if(velocity<desiredVel) //if not at desired, speed up
            acceleration=3;
        if(velocity+acceleration > desiredVel)//if too much acceleration, cap it
            acceleration=desiredVel-velocity;
    }
    
    //change vehicle to fast lane
    public void changeToFastLane(){
        if(!isInFastLane){
            int direction=1;
            if(!LTR) direction=-1;
            posY+=(TrafficSim.RoadHeight*direction);
            isInFastLane=true;
        }
    }
    
    //returns new position of vehicle after braking
    public int brake(){
        int direction=1;
        if(!LTR) direction=-1;
        int brakeAmount=(int)(-2*acceleration); 
        posX+=brakeAmount;
        return posX;
    }


    //check if the vehicle needs to be destroyed
    public boolean checkValid() { //not valid if at the end of the road
        if(posX>TrafficSim.frameSizeX && LTR) return false;
        else if(posX<=-TrafficSim.CarWidth && !LTR) return false;
        else return true;
    }
    
    
}
