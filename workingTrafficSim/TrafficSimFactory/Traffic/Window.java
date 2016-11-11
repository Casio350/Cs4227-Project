package Traffic;


import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 *
 * @author Jeoff
 */

///THIS CAN BE REMOVED AND IGNORED

//this deals with the GUI
public class Window extends JPanel{
    
    private final JFrame frame;
    
    public Window(){ 
        frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DrawPanel drawPanel = new DrawPanel();

        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

        frame.setResizable(false);
        frame.setSize(300, 300);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    
    public void paint(){
        frame.repaint();
    }
}
