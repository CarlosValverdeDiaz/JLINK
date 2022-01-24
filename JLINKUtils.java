package JLINKLibrary;

import JPEGXTBox.SuperBox;
import JUMBF.JUMBFDescriptionBox;
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
public class JLINKUtils {
    private final JUMBFUtils JumbfUtils = new JUMBFUtils();
    
    public JLINKSuperBox shapeJLINKSuperBox(byte[] data) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JUMBFSuperBox xml;
        JLINKSuperBox jlink;
         
        int jlinkLength;
        int boxLength;
        int boxType;
        long boxXLength;
        
        String uuid;
        byte toggles;
        StringBuilder label = new StringBuilder();
        
        int id = 0;
        byte[] signature;
        
        int offset = 0;
        
        jlinkLength = this.JumbfUtils.getIntFromBytes(this.JumbfUtils.allocateBytes(data[offset], data[offset + 1], data[offset + 2], data[offset + 3]));
        System.out.println(jlinkLength);
        offset += 4;
        boxType = this.JumbfUtils.getIntFromBytes(this.JumbfUtils.allocateBytes(data[offset], data[offset + 1], data[offset + 2], data[offset + 3]));
        offset += 4;
        
        if (boxType == Common.Values.JUMBF_jumb) {
            System.out.println("Reading JUMBFSuperBox");
        } else {
            throw new Exception("Not JUMBFSuperBox " + boxType);
        }
        
        boxLength = this.JumbfUtils.getIntFromBytes(this.JumbfUtils.allocateBytes(data[offset], data[offset + 1], data[offset + 2], data[offset + 3]));
        offset += 4;
        boxType = this.JumbfUtils.getIntFromBytes(this.JumbfUtils.allocateBytes(data[offset], data[offset + 1], data[offset + 2], data[offset + 3]));
        offset += 4;
                
        if (boxType == Common.Values.JUMBF_jumd) {
            System.out.println("Reading JUMBFDescriptionBox");
        } else {
            throw new Exception("Not JUMBFDescriptionBox " + boxType);
        }
        
        boxType = this.JumbfUtils.getIntFromBytes(this.JumbfUtils.allocateBytes(data[offset], data[offset + 1], data[offset + 2], data[offset + 3]));
        offset += 4;
        
        switch (boxType) {
            case Common.Values.JUMBF_xml:
                uuid = Common.Values.String_TYPE_XMLContentType;
                System.out.println("Reading XML Box");
                break;
            case Common.Values.JUMBF_json:
                uuid = Common.Values.String_TYPE_JSONContentType;
                System.out.println("Reading JSON Box");
                break;
            case Common.Values.JUMBF_jp2c:
                uuid = Common.Values.String_TYPE_ContiguousCodestream;
                System.out.println("Reading JP2C Box");
                break;
            case Common.Values.JUMBF_uuid:
                uuid = Common.Values.String_TYPE_UUIDContentType;
                System.out.println("Reading UUID Box");
                break;
            case Common.Values.JUMBF_bidb:
                uuid = Common.Values.String_TYPE_EmbeddedFile;
                System.out.println("Reading BIDB Box");
                break;
            case Common.Values.JUMBF_link:
                uuid = Common.Values.String_TYPE_JLINK;
                System.out.println("Reading JLINK Box");
                break;
            default:
                throw new Exception("Invalid Type " + boxType);
        }
        offset += 12;
        //TOGGLES        
        toggles = data[offset];
        offset++;
        
        JUMBFDescriptionBox descriptionBox = new JUMBFDescriptionBox(UUID.fromString(uuid), toggles);

        if((toggles | 0b0010) == toggles) {
            //LABEL
            while(data[offset] != '\0') {
                label.append((char) data[offset]);
                offset++;
            }
            offset++;
            descriptionBox.setLabel(label.toString());
        }
        if((toggles | 0b0100) == toggles) {
            //ID
            byte[] num = new byte[4];

            for(int i = 0; i < 4; i++) {
                num[i] = data[offset + i];
            }
            id = this.JumbfUtils.getIntFromBytes(num);
            offset += 4;
            descriptionBox.setId(id);
        }
        if((toggles | 0b1000) == toggles) {
            //Signature
            signature = new byte[32];
            for(int i = 0; i < signature.length; i++) {
                signature[i] = data[offset + i];
            }
            descriptionBox.setSignature(signature);
            offset += 32;
        }
        jlink = new JLINKSuperBox(toggles, label.toString(), id, null);
        //System.out.println("XML Length: " + this.JumbfUtils.getIntFromBytes(this.JumbfUtils.allocateBytes(data[offset], data[offset + 1], data[offset + 2], data[offset + 3])));
        baos.write(data, offset, this.JumbfUtils.getIntFromBytes(this.JumbfUtils.allocateBytes(data[offset], data[offset + 1], data[offset + 2], data[offset + 3])));
        xml = this.JumbfUtils.shapeJUMBFSuperBox(baos.toByteArray());
        jlink.setXMLContentBox(xml);
        offset += xml.getLBox()/2;
        baos.reset();
        //System.out.println("Next Box Length: " + Integer.toHexString(this.JumbfUtils.getIntFromBytes(this.JumbfUtils.allocateBytes(data[offset], data[offset + 1], data[offset + 2], data[offset + 3]))));
        while (offset < jlinkLength - 20) {
            if(this.JumbfUtils.getIntFromBytes(this.JumbfUtils.allocateBytes(data[offset + 16], data[offset + 17], data[offset + 18], data[offset + 19])) == Common.Values.JUMBF_link) {
                baos.write(data, offset, this.JumbfUtils.getIntFromBytes(this.JumbfUtils.allocateBytes(data[offset], data[offset + 1], data[offset + 2], data[offset + 3])));
                offset += this.JumbfUtils.getIntFromBytes(this.JumbfUtils.allocateBytes(data[offset], data[offset + 1], data[offset + 2], data[offset + 3])) - 4;
                jlink.addJLINK(this.shapeJLINKSuperBox(baos.toByteArray()));
            } else if (isJUMBFBox(this.JumbfUtils.getIntFromBytes(this.JumbfUtils.allocateBytes(data[offset + 4], data[offset + 5], data[offset + 6], data[offset + 7])))) {
                //System.out.println(this.JumbfUtils.getIntFromBytes(this.JumbfUtils.allocateBytes(data[offset + 4], data[offset + 5], data[offset + 6], data[offset + 7])));
                //System.out.println(this.JumbfUtils.getIntFromBytes(this.JumbfUtils.allocateBytes(data[offset + 16], data[offset + 17], data[offset + 18], data[offset + 19])));
                baos.write(data, offset, this.JumbfUtils.getIntFromBytes(this.JumbfUtils.allocateBytes(data[offset], data[offset + 1], data[offset + 2], data[offset + 3])));
                offset += this.JumbfUtils.getIntFromBytes(this.JumbfUtils.allocateBytes(data[offset], data[offset + 1], data[offset + 2], data[offset + 3])) - 4;
                jlink.addJUMBFBox(JumbfUtils.shapeJUMBFSuperBox(baos.toByteArray()));
            }
            offset++;
            baos.reset();
        }
        return jlink;
    }
    
    public boolean isJUMBFBox(int type) {
        if (type == Common.Values.JUMBF_jumb ||
            type == Common.Values.JUMBF_jumd) {
            return true;
        } else {
            return false;
        }
    }
    
    public void DisplayJlink(JLINKSuperBox jlink) throws Exception {
        for (JUMBFSuperBox jsb:jlink.getNestedJumbfBoxes().values()) {
            if (jsb.getDescriptionBox().getContentType().toString().toUpperCase().equals(Common.Values.String_TYPE_ContiguousCodestream)) {
                this.JumbfUtils.getImageFromBox(jsb);
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
        
        for(JLINKSuperBox link:superBox.getNestedJlinkBoxes().values()) {
            this.JLINKToBox(link, link.getDescriptionBox().getLabel(), BoxInstance);
            BoxInstance += superBox.getNestedJumbfBoxes().size();
        }
        for(JUMBFSuperBox sb:superBox.getNestedJumbfBoxes().values()) {
            this.JumbfUtils.JUMBFToBox(sb, sb.getDescriptionBox().getLabel(), BoxInstance);
            BoxInstance++;
        }   
        
        SuperBox[] xtBoxes = this.JumbfUtils.getBoxes(superBox, BoxInstance);
        for (SuperBox box:xtBoxes) {
            baos.write(box.getXTBoxData());
        }
        
        Files.write(file.toPath(), baos.toByteArray());
        return BoxInstance;
    }
    
    public SuperBox[] getBoxes(JLINKSuperBox box, short boxInstance) throws Exception {
        long length = box.getXTBoxData().length;
        int localSpan;
        int offset = 0;
        int numBoxes = 0;
        boolean lastBox = false;
                
        SuperBox[] contentBoxes = null;
        ByteArrayOutputStream contentBuilder = new ByteArrayOutputStream();
            
        if (length > Math.pow(2, 32)) {
            //XLBox length
            localSpan = Common.Values.XT_BOX_MAX_DATA - Common.Values.XT_BOX_HEADER_LENGTH - 1 - 8;
            
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
                    contentBoxes[i] = new SuperBox((short) (Common.Values.XT_BOX_MAX_DATA - 1), boxInstance, i, 1, box.getType(), contentBuilder.toByteArray(), length);
                    contentBuilder.reset();
                    offset += localSpan;
                } else {
                    contentBuilder.write(box.getXTBoxData(), offset, (int) box.getXTBoxData().length);
                    contentBoxes[i] = new SuperBox((short) (box.getXTBoxData().length - offset + Common.Values.XT_BOX_HEADER_LENGTH), boxInstance, i, 1, box.getType(), contentBuilder.toByteArray(), length);
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
                    contentBoxes[i] = new SuperBox((short) (Common.Values.XT_BOX_MAX_DATA - 1), boxInstance, i, (int) length + 8, box.getType(), contentBuilder.toByteArray());
                    contentBuilder.reset();
                    offset += localSpan;

                } else {
                    contentBuilder.write(box.getXTBoxData(), offset, (int) box.getXTBoxData().length - offset);
                    contentBoxes[i] = new SuperBox((short) (box.getXTBoxData().length - offset + Common.Values.XT_BOX_HEADER_LENGTH), boxInstance, i, (int) length + 8, box.getType(), contentBuilder.toByteArray());
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
        
        this.DisplayJlink(this.shapeJLINKSuperBox(this.JumbfUtils.getBoxesFromFile(f.getAbsolutePath())));
        
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
