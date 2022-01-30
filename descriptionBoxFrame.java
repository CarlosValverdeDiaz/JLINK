package JLINKBuilder;

import JLINKLibrary.JLINKSuperBox;
import JUMBF.JUMBFDescriptionBox;
import JUMBF.JUMBFContentBox;
import JUMBF.JUMBFSuperBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author carlos
 */
public class descriptionBoxFrame implements ActionListener {
    JFrame frame;
    JFrame frame2;
    JFrame frame3;
    String[] options = {"ContiguousCodestream", "EmbeddedFile", "JSONContentType", "UUIDContentType", "XMLContentType"};
    JComboBox cb = new JComboBox(options);
    JTextField tfLabel = new JTextField();
    JTextField tfID = new JTextField();
    JTextField tfUUID = new JTextField();
    JTextField tfMediaType = new JTextField();
    JTextField tfFileName = new JTextField();
    JLabel labelLabel = new JLabel("Set JUMBF box Label");
    JLabel labelId = new JLabel("Set JUMBF box Id");
    JLabel labelType = new JLabel("Set JUMBF box Type");
    JLabel labelUUID = new JLabel("Set UUID format XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX");
    JLabel labelMediaType = new JLabel("Set Media Type");
    JLabel labelFileName = new JLabel("Set File name");
    JCheckBox Bit1 = new JCheckBox("Is Requestable");
    JCheckBox Bit2 = new JCheckBox("Has Label");
    JCheckBox Bit3 = new JCheckBox("Has ID");
    JCheckBox Bit4 = new JCheckBox("Has Signature");
    
    JUMBFSuperBox jsb;
    JUMBFDescriptionBox box;
    JUMBFContentBox jcb;
    JLINKSuperBox parent;
    private short BoxInstance;
    
    public descriptionBoxFrame(JLINKSuperBox parent, short BoxInstance) throws Exception {
        this.parent =  parent;
        frame = new JFrame();
        setDescriptionFrame();
    }
    
    protected void setDescriptionFrame() throws Exception {
        Common.Values.setMap();
        
        JButton b1 = new JButton("Continue");
        JButton baux = new JButton();
        
        baux.setVisible(false);
        
        tfLabel.setBounds(50, 60, 300, 30);
        tfID.setBounds(50, 140, 50, 30);
        cb.setBounds(50, 220, 200, 30);
        b1.setBounds(250, 300, 100, 40);
        labelLabel.setBounds(50, 20, 300, 30);
        labelId.setBounds(50, 100, 300, 30);
        labelType.setBounds(50, 180, 300, 30);
        Bit1.setBounds(300, 120, 200, 20);
        Bit2.setBounds(300, 140, 200, 20);
        Bit3.setBounds(300, 160, 200, 20);
        Bit4.setBounds(300, 180, 200, 20);
        
        tfLabel.addActionListener(this);
        tfID.addActionListener(this);
        cb.addActionListener(this);
        b1.addActionListener(this);
        Bit1.addActionListener(this);
        Bit2.addActionListener(this);
        Bit3.addActionListener(this);
        Bit4.addActionListener(this);
        
        frame.setSize(500, 400);
        frame.getContentPane().add(tfLabel);
        frame.getContentPane().add(tfID);
        frame.getContentPane().add(cb);
        frame.getContentPane().add(b1);
        frame.getContentPane().add(labelLabel);
        frame.getContentPane().add(labelId);
        frame.getContentPane().add(labelType);
        frame.getContentPane().add(Bit1);
        frame.getContentPane().add(Bit2);
        frame.getContentPane().add(Bit3);
        frame.getContentPane().add(Bit4);
        
        frame.getContentPane().add(baux);
        frame.setVisible(true);
        
    }
    
    private void setUUIDFrame() {
        frame2 = new JFrame();
        
        JButton b2 = new JButton("UUID completed");
        JButton baux = new JButton();
        
        baux.setVisible(false);
        
        tfUUID.setBounds(50, 100, 300, 30);
        b2.setBounds(250, 300, 100, 40);
        labelUUID.setBounds(50, 70, 300, 30);
        
        tfUUID.addActionListener(this);
        b2.addActionListener(this);
        
        frame2.setSize(400, 400);
        frame2.getContentPane().add(tfUUID);
        frame2.getContentPane().add(labelUUID);
        frame2.getContentPane().add(b2);
        frame2.getContentPane().add(baux);
        
        frame2.setVisible(true);
    }
    
    private void setEmbededFrame() {
        frame3 = new JFrame();
        JButton b3 = new JButton("Embeded completed");
        JButton baux = new JButton();
        
        baux.setVisible(false);
        
        tfMediaType.setBounds(50, 100, 300, 30);
        tfFileName.setBounds(50, 180, 300, 30);
        b3.setBounds(250, 300, 100, 40);
        labelMediaType.setBounds(50, 70, 300, 30);
        labelFileName.setBounds(50, 150, 300, 30);
        
        tfMediaType.addActionListener(this);
        tfFileName.addActionListener(this);
        b3.addActionListener(this);
        
        frame3.setSize(400, 400);
        frame3.getContentPane().add(tfMediaType);
        frame3.getContentPane().add(tfFileName);
        frame3.getContentPane().add(labelMediaType);
        frame3.getContentPane().add(labelFileName);
        frame3.getContentPane().add(b3);
        frame3.getContentPane().add(baux);
        
        frame3.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "Continue":
                byte toggle = (byte) 0b0000;
                
                if (Bit1.isSelected()) {
                    toggle |= ((byte) 0b0001);
                }
                if (Bit2.isSelected()) {
                    toggle |= ((byte) 0b0010);
                }
                if (Bit3.isSelected()) {
                    toggle |= ((byte) 0b0100);
                }
                if (Bit4.isSelected()) {
                    toggle |= ((byte) 0b1000);
                }
                
                try {
                    box = new JUMBFDescriptionBox(Common.Values.BoxTypes.get(cb.getSelectedItem()), toggle, tfLabel.getText(), Integer.parseInt(tfID.getText()));    
                } catch (Exception ex) {
                    Logger.getLogger(descriptionBoxFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                jsb = new JUMBFSuperBox();
                jsb.setDescriptionBox(box);
                
                fileGetter fg2 = new fileGetter();
                File f2 = fg2.getFile("Select a CodeStream file");

                if (box.getContentType().toString().equalsIgnoreCase(Common.Values.String_TYPE_UUIDContentType)) {
                    System.out.println("UUID");
                    this.setUUIDFrame();
                } else if (box.getContentType().toString().equalsIgnoreCase(Common.Values.String_TYPE_EmbeddedFile)) {
                    System.out.println("BIDB");
                    this.setEmbededFrame();
                } else {
                    try {
                        jcb = new JUMBFContentBox(f2, box.getContentType());
                        jsb.addContentBox(jcb);
                        parent.addJUMBFBox(jsb);
                    } catch (Exception ex) {
                        Logger.getLogger(descriptionBoxFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                fg2.dispose();
                        
                frame.dispose();
                break;
                
            case "UUID completed":
                fileGetter fg3 = new fileGetter();
                File f3 = fg3.getFile("Select a CodeStream file");
                try {
                    jcb = new JUMBFContentBox(f3, box.getContentType(), this.tfUUID.getText());
                    jsb.addContentBox(jcb);
                    parent.addJUMBFBox(jsb);
                } catch (Exception ex) {
                    Logger.getLogger(descriptionBoxFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                fg3.dispose();
                frame2.dispose();
                break;
                
            case "Embeded completed":
                fileGetter fg4 = new fileGetter();
                File f4 = fg4.getFile("Select a CodeStream file");
                try {
                    jcb = new JUMBFContentBox(f4, box.getContentType(), null, (byte) 0b0011, this.tfMediaType.getText(), this.tfFileName.getText());
                    jsb.addContentBox(jcb);
                    parent.addJUMBFBox(jsb);
                } catch (Exception ex) {
                    Logger.getLogger(descriptionBoxFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                fg4.dispose();
                frame3.dispose();
                break;
        }
    }

}
