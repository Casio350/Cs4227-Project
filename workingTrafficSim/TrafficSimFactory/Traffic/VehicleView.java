package Traffic;


public class VehicleView {

        private final Vector pos;
        private final int id;
        private int roadID; //id is vehicleID
        private final boolean LTR;

        
        public VehicleView(Vehicle v){ 
            pos=new Vector(v.getPositionX(), v.getPositionY());
            id=v.getID();
            roadID=v.getRoadID();
            LTR=v.getLTR();
        }
        
        //if this vehicle is ahead of x
        public boolean isAheadOf(int x){
            if(pos.X()>x)
                return true;
            return false;
        }
	
	
        //setters
        public void setPosition(int x, int y){
            pos.setX(x); 
            pos.setY(y);
        }
        
        public void setPosY(int y){
            pos.setY(y);
        }
        
        public void setPosX(int x){
            pos.setX(x);
        }
        
        public void setPosition(Vector v){
            pos.setX(v.X()); 
            pos.setY(v.Y());
        }
        
        
        
        //getters
        public int getID(){return id;}
        public int getRoadID(){return roadID;}
        public boolean getLTR(){return LTR;}
        
        public int posX(){return pos.X();}
        public int posY(){return pos.Y();}
        
}