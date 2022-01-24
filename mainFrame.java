package JLINKBuilder;

import JLINKLibrary.JLINKSuperBox;
import JLINKLibrary.JLINKUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 *
 * @author carlos
 */
public class mainFrame implements ActionListener {
    JFrame frame = new JFrame();
    
    JPanel panel = new JPanel();
    
    JButton button1;
    JButton button2;
    JButton button3;
    JButton button4;
    JButton button5;
    
    JLINKUtils utils = new JLINKUtils();
    
    public mainFrame(){
        button1 = new JButton("Create new JLINK");
        button1.setFocusable(false);
        button1.addActionListener(this);
        
        button2 = new JButton("Build XML");
        button2.setFocusable(false);
        button2.addActionListener(this);
        button2.setVisible(true);
        
        button3 = new JButton("Explore JLINK");
        button3.setFocusable(false);
        button3.addActionListener(this);
        button3.setVisible(true);
        
        button4 = new JButton("Merge JLINK into JPEG");
        button4.setFocusable(false);
        button4.addActionListener(this);
        button4.setVisible(true);
        
        button5 = new JButton("Exit");
        button5.setFocusable(false);
        button5.addActionListener(this);
        button5.setVisible(true);
        
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(button5);
        panel.setSize(600, 100);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,100);
        frame.setVisible(true);
        
        frame.add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "Create new JLINK":
                try {
                    Builder main = new Builder("Main JlINK", true, null, (short) 1);
                } catch (Exception ex) {
                    Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "Exit":
                frame.dispose();
                break;
            case "Explore JLINK":
                fileGetter fg = new fileGetter();
                try {
                    File f = fg.getFile("Select a JLINK file");
                    //JLINKLibrary.JLINKSuperBox jlink = utils.shapeJLINKSuperBox(Files.readAllBytes(fg.getFile("Select a JLINK file (.jumbf)").toPath()));
                    //utils.DisplayJlink(jlink);

                    JLINKSuperBox jlink = utils.shapeJLINKSuperBox(utils.getJUMBFUtils().getBoxesFromFile(f.getAbsolutePath()));
                    utils.analizeJPEGFile(f);
                    
                } catch (Exception ex) {
                    Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                fg.dispose();
                break;
            case "Merge JLINK into JPEG":
                fileGetter fg2 = new fileGetter();
                fileGetter fg3 = new fileGetter();
                
                File image = fg2.getFile("Select JPEG file");
                File jumbf = fg3.getFile("Select JUMBF file");
                
                try {
                    utils.getJUMBFUtils().mergeJUMBF(image.getAbsolutePath(), jumbf.getAbsolutePath());
                } catch (Exception ex) {
                    Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                fg2.dispose();
                fg3.dispose();
                break;
            case "Build XML": 
                new XMLBuilder();
        }
    }
}