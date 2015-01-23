package recogestureherger;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants; 
 
public class HergerUI extends JPanel {
    private ArrayList<Point> path;
    private OneDollarRecognizer myRecognizer;
 
    public HergerUI(){
       super();
       initComponent();
       path = new ArrayList<>();
       myRecognizer = new OneDollarRecognizer();
       myRecognizer.setup();
    }
   
    private void initComponent() {
       this.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseReleased(MouseEvent e) {
               panelMouseReleased(e);
           } 
       });
       this.addMouseMotionListener(new MouseMotionAdapter() {
           @Override
           public void mouseDragged(MouseEvent e) {
               panelMouseDragged(e);
           }
       });
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for (Point p : path) {
            g.drawLine(p.x, p.y, p.x, p.y);
        }
    }  
 
    private void panelMouseDragged(MouseEvent e) {
        Point newPoint = new Point(e.getX(), e.getY());
        path.add(newPoint);
        repaint();
 
    }
 
    private void panelMouseReleased(MouseEvent e) {
        System.out.println("Result : " + path.size());
        
        // do the algorithm
        OneDollarRecognizer.Result form = myRecognizer.recognizer.recognize(path);
        System.out.println("Result of recognition is : Name(" + form.Name + ") | Score(" + form.Score + ") | Ratio(" + form.Ratio + ")");

        // clear the path
        path.clear();
    }
 
    public static void main(String a[]){
        EventQueue.invokeLater(new Runnable(){
        
            @Override
            public void run() {
                JFrame frame = new JFrame();
                HergerUI linedraw= new HergerUI();
                frame.add(linedraw);
                frame.setSize(500,500);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);  
            }
        });
   }  
}