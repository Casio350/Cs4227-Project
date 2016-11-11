package Traffic;


public class Vector { //helper class for x and y positions
    private int x=0,y=0;
    
    public Vector(int x, int y){
        this.x=x; 
        this.y=y; 
    }
    
    //getter and setters
    public int X() {return x;}
    public void setX(int x) {this.x=x;}
    
    public int Y() {return y;}
    public void setY(int y) {this.y=y;}
}
