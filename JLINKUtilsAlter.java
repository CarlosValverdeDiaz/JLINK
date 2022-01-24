package JLINKLibrary;

import JPEGXTBox.SuperBox;
import JUMBF.JUMBFSuperBox;
import JUMBF.JUMBFUtils;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author carlos
 */
public class JLINKUtilsAlter {
    private JUMBFUtils JumbfUtils = new JUMBFUtils();
    
    public JLINKSuperBox shapeJLINKSuperBox(File f) throws Exception {
        byte[] data = Files.readAllBytes(f.toPath());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int offset = 0;
        
        for (int i = 0; i < data.length - 1; i++) {
            if (Byte.toUnsignedInt(data[i]) == 0xff 
               && Byte.toUnsignedInt(data[i + 1]) == 0xeb) {
                offset += 12;
                break;
            } else {
                offset++;
            }
        } 
        
        baos.write(data, offset, data.length - offset);
        
        JUMBFSuperBox jsb = this.JumbfUtils.shapeJUMBFSuperBox(baos.toByteArray());
        HashMap <String, JUMBFSuperBox> jumbfBoxes = this.FindJUMBFSinJLINK(f);
        HashMap <String, JUMBFSuperBox> aux = new HashMap<>();
        LinkedList<JUMBFSuperBox> list = new LinkedList<>();
        for(JUMBFSuperBox box:jumbfBoxes.values())
            list.add(box);
        for(int i = 1; i < list.size(); i++) {
            aux.put(list.get(i).getDescriptionBox().getLabel(), (list.get(i)));
        }
        JLINKSuperBox jlink = new JLINKSuperBox(jsb.getDescriptionBox().getToggles(), jsb.getDescriptionBox().getLabel(), jsb.getDescriptionBox().getId(), list.getFirst());
        jlink.setNestedJumbfBoxes(aux);
        
        return jlink;
    }
    
    public HashMap<String, JUMBFSuperBox> FindJUMBFSinJLINK(File f) throws Exception {
        int offset = 0;
        int boxLength = 0;
        byte[] data = Files.readAllBytes(f.toPath());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HashMap<String, JUMBFSuperBox> jumbfs = new HashMap<>();
                
        for (int i = 0; i < data.length - 1; i++) {                
            if ((Byte.toUnsignedInt(data[i]) == 0x6a
                    && Byte.toUnsignedInt(data[i + 1]) == 0x70
                    && Byte.toUnsignedInt(data[i + 2]) == 0x32
                    && Byte.toUnsignedInt(data[i + 3]) == 0x63)
                    || (Byte.toUnsignedInt(data[i]) == 0x78
                    && Byte.toUnsignedInt(data[i + 1]) == 0x6d
                    && Byte.toUnsignedInt(data[i + 2]) == 0x6c
                    && Byte.toUnsignedInt(data[i + 3]) == 0x20)
                    || (Byte.toUnsignedInt(data[i]) == 0x6a
                    && Byte.toUnsignedInt(data[i + 1]) == 0x73
                    && Byte.toUnsignedInt(data[i + 2]) == 0x6f
                    && Byte.toUnsignedInt(data[i + 3]) == 0x6e)
                    || (Byte.toUnsignedInt(data[i]) == 0x75
                    && Byte.toUnsignedInt(data[i + 1]) == 0x75
                    && Byte.toUnsignedInt(data[i + 2]) == 0x69
                    && Byte.toUnsignedInt(data[i + 3]) == 0x64)
                    || (Byte.toUnsignedInt(data[i]) == 0x62
                    && Byte.toUnsignedInt(data[i + 1]) == 0x69
                    && Byte.toUnsignedInt(data[i + 2]) == 0x64
                    && Byte.toUnsignedInt(data[i + 3]) == 0x62)) {
                offset = i - 16;
                i += 16;

                byte toggles = data[i];

                if((toggles | 0b0010) == toggles) {
                    //LABEL
                    while(data[i] != '\0') {
                        i++;
                    }
                    i++;
                }
                if((toggles | 0b0100) == toggles) {
                    //ID
                    i += 4;
                }
                if((toggles | 0b1000) == toggles) {
                    //Signature
                    i += 32;
                }
               
                boxLength = this.JumbfUtils.getIntFromBytes(this.JumbfUtils.allocateBytes(data[i], data[i + 1], data[i + 2], data[i + 3]));

                baos.write(data, offset, boxLength - 1);
                
                i += boxLength;
                
                JUMBFSuperBox jsb = this.JumbfUtils.shapeJUMBFSuperBox(baos.toByteArray()); 
                baos.reset();
                jumbfs.put(jsb.getDescriptionBox().getLabel(), jsb);

                offset = i;
            }
        } 
        System.out.println(jumbfs.size());
        return jumbfs;
    }
    
    public void DisplayJlink(JLINKSuperBox jlink) throws Exception {
        for (JUMBFSuperBox jsb:jlink.getNestedJumbfBoxes().values()) {
            if (jsb.getDescriptionBox().getContentType().toString().toUpperCase().equals(Common.Values.String_TYPE_ContiguousCodestream)) {
                this.JumbfUtils.getImageFromBox(jsb.getDescriptionBox().getLabel());
            }
        } 
        for (JLINKSuperBox links:jlink.getNestedJlinkBoxes().values()) {
            this.DisplayJlink(links);
        }
    }
    
    public short JLINKToBox(JLINKSuperBox superBox, String fileName, short BoxInstance) throws Exception {        
        String s = new String("/home/carlos/Testfiles/" + fileName + ".jumbf");
        File file = new File(s);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JUMBFUtils utils = new JUMBFUtils();
        
        for(JLINKSuperBox link:superBox.getNestedJlinkBoxes().values()) {
            this.JLINKToBox(link, link.getDescriptionBox().getLabel(), BoxInstance);
            BoxInstance++;
        }
        for(JUMBFSuperBox sb:superBox.getNestedJumbfBoxes().values()) {
            utils.JUMBFToBox(sb, sb.getDescriptionBox().getLabel(), BoxInstance);
            BoxInstance++;
        }   
        
        SuperBox[] xtBoxes = this.getBoxes(superBox, BoxInstance);
        for (SuperBox box:xtBoxes) {
            baos.write(box.getXTBoxData());
        }
        
        Files.write(file.toPath(), baos.toByteArray());
        return BoxInstance;
    }
    
    private SuperBox[] getBoxes(JLINKSuperBox box, short BoxInstance) throws Exception {
        long length = box.getXTBoxData().length;
        int localSpan;
        int offset = 0;
        int numBoxes = 0;
        boolean lastBox = false;
                
        SuperBox[] contentBoxes = null;
        ByteArrayOutputStream contentBuilder = new ByteArrayOutputStream();
            
        if (length > Math.pow(2, 32)) {
            //XLBox length
            localSpan = Common.Values.XT_BOX_MAX_DATA - Common.Values.XT_BOX_HEADER_LENGTH - 1 + 4;
            
            while (length != 0) {
                numBoxes++;
                if (box.getXTBoxData().length < localSpan) {
                    length = 0;
                } else {
                    length -= localSpan;
                }
            }
            
            contentBoxes = new SuperBox[numBoxes];
            length = box.getXTBoxData().length;
            
            for (int i = 0; i < numBoxes; i++) {
                if (i != numBoxes - 1) {
                    contentBuilder.write(box.getXTBoxData(), offset, localSpan);
                    contentBoxes[i] = new SuperBox((short) (Common.Values.XT_BOX_MAX_DATA - 1), BoxInstance, i, 1, box.getType(), contentBuilder.toByteArray(), length + 8);
                    contentBuilder.reset();
                    offset += localSpan;
                } else {
                    contentBuilder.write(box.getXTBoxData(), offset, (int) box.getXTBoxData().length);
                    contentBoxes[i] = new SuperBox((short) (box.getXTBoxData().length - offset + Common.Values.XT_BOX_HEADER_LENGTH), BoxInstance, i, 1, box.getType(), contentBuilder.toByteArray(), length + 8);
                    contentBuilder.reset();
                }
                
            }
            
        } else {
            //LBox length
            localSpan = Common.Values.XT_BOX_MAX_DATA - Common.Values.XT_BOX_HEADER_LENGTH - 1;
            while (!lastBox) {
                numBoxes++;
                if (length < localSpan) {
                    lastBox = !lastBox;
                } else {
                    length -= localSpan;
                }
            }
            
            contentBoxes = new SuperBox[numBoxes];
            length = box.getXTBoxData().length;
            
            for (int i = 0; i < numBoxes; i++) {
                if (i < numBoxes - 1) {
                    contentBuilder.write(box.getXTBoxData(), offset, localSpan);
                    contentBoxes[i] = new SuperBox((short) (Common.Values.XT_BOX_MAX_DATA - 1), BoxInstance, i, (int) length + 8, box.getType(), contentBuilder.toByteArray());
                    contentBuilder.reset();
                    offset += localSpan;
                } else {
                    contentBuilder.write(box.getXTBoxData(), offset, (int) box.getXTBoxData().length - offset);
                    contentBoxes[i] = new SuperBox((short) (box.getXTBoxData().length - offset + Common.Values.XT_BOX_HEADER_LENGTH), BoxInstance, i, (int) length + 8, box.getType(), contentBuilder.toByteArray());
                    contentBuilder.reset();
                }
            }
        }       
        return contentBoxes;
    }
    
    public void analizeJPEGFile(File f) throws Exception {
        JFrame frame = new JFrame(f.getName());
        JLabel label = new JLabel();
        ImageIcon icon = new ImageIcon(Files.readAllBytes(f.toPath()));
        
        this.DisplayJlink(this.shapeJLINKSuperBox(f));
        
        label.setIcon(icon);
        frame.setVisible(true);
        frame.add(label);
        
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public JUMBFUtils getJUMBFUtils() {
        return this.JumbfUtils;
    }
}
