package etl;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Subhash Nadkarni
 */
public class Home extends JFrame {
    
        JPanel jp;
        JButton Extract, Transform, Load;
    public Home(){
        super("ETL");
        setSize(500,150);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jp=new JPanel();
        Extract=new JButton("Extract");
        Transform=new JButton("transform");
        Load=new JButton("Load");
        jp.setLayout(new FlowLayout(FlowLayout.CENTER,10,25));
        jp.add(Extract);
        jp.add(Transform);
        jp.add(Load);
        add(jp);
        setLocation(300,200);
        setVisible(true);
        
        Extract.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                Methods e=new Methods();
                e.extractaccess();
                e.extractmysql();
                e.extracttext();
                e.putaccess();
            }
        });
        
        Transform.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                Methods e=new Methods();
                e.transform();
                e.dumptransform();
            }
        });
        
        Load.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                Methods e=new Methods();
                e.forload();
                e.dumpload();
            }
        });
    }
    public static void main(String[] args) {
        Home h=new Home();
    }
}
