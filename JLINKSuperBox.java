package JLINKLibrary;

import JUMBF.*;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 *
 * @author carlos
 */
public class JLINKSuperBox extends JUMBFSuperBox{
    private JUMBFSuperBox XMLContentBox; //XML metadata
    private HashMap<String, JLINKSuperBox> NestedJlinkBoxes; //JLINK box Map with String label (scene) key
    private HashMap<String, JUMBFSuperBox> NestedJumbfBoxes; //JLINK box Map with String label (scene) key   
    
    public JLINKSuperBox(byte toggles, String label, int id, Object XMLMetadata) throws Exception {
        super(Common.Values.TYPE_JLINK_Metadata_Elements, toggles, label, id);
        super.setType(Common.Values.JUMBF_jumb);
        XMLContentBox = new JUMBFSuperBox(Common.Values.TYPE_XMLContentType, (byte) 0b1111, "XML Metadata file in JLINK: " + this.getDescriptionBox().getLabel(), 1);
        XMLContentBox.addData(XMLMetadata);
        NestedJlinkBoxes = new HashMap<>();
        NestedJumbfBoxes = new HashMap<>();
        super.setLBox(super.getLBox() + this.XMLContentBox.getLBox());
    }
    
    public JLINKSuperBox() throws Exception {
        super.setType(Common.Values.JUMBF_jumb);
        XMLContentBox = new JUMBFSuperBox();
        NestedJlinkBoxes = new HashMap<>();
        NestedJumbfBoxes = new HashMap<>();
        super.setLBox(8);
    }

    public JUMBF.JUMBFSuperBox getXMLContentBox() {
        return XMLContentBox;
    }

    public void setXMLContentBox(JUMBF.JUMBFSuperBox XMLContentBox) {
        this.XMLContentBox = XMLContentBox;
        super.setLBox(super.getLBox() + XMLContentBox.getLBox());
    }

    public HashMap<String, JLINKSuperBox> getNestedJlinkBoxes() {
        return NestedJlinkBoxes;
    }
    
    public void addJUMBFBox(JUMBF.JUMBFSuperBox jcb) {
        this.getNestedJumbfBoxes().put(jcb.getDescriptionBox().getLabel(), jcb);
        super.setLBox(super.getLBox() + jcb.getLBox());
    }

    public void setNestedJlinkBoxes(HashMap<String, JLINKSuperBox> NestedJlinkBoxes) {
        this.NestedJlinkBoxes = NestedJlinkBoxes;
    } 

    public HashMap<String, JUMBFSuperBox> getNestedJumbfBoxes() {
        return NestedJumbfBoxes;
    }

    public void setNestedJumbfBoxes(HashMap<String, JUMBFSuperBox> NestedJumbfBoxes) {
        this.NestedJumbfBoxes = NestedJumbfBoxes;
    }    
    
    public void addJLINK(JLINKSuperBox JlinkBox){
        NestedJlinkBoxes.put(JlinkBox.getDescriptionBox().getLabel(), JlinkBox);
        super.setLBox(super.getLBox() + JlinkBox.getLBox());
    }
    
    public JUMBFDescriptionBox getDescriptionBox() {
        return super.getDescriptionBox();
    }
    
    public void setDescriptionBox(JUMBFDescriptionBox b) {
        super.setDescriptionBox(b);
        //super.setLBox(super.getLBox() + b.getLBox());
    }
    
    @Override
    public String boxToString() {
        //This method returns a String representing all JLINK content following the format: JUMBF JLINK SUPERBOX -> JUMBF DESCRIPTION BOX, JUMBF (XML) CONTENT BOX,
        //JUMBF JLINK NESTED BOXES, JUMBF CODESTREAM CONTENT BOXES. For each JLINK box in NestedJlinkBoxes and for each Codestream Box in CodestreamContentBox, this
        //or equivalent method is called
        StringBuilder sb = new StringBuilder();
        
        sb.append(String.format("\n_______________________JUMBF JLINK SUPERBOX %S_______________________\n",super.getDescriptionBox().getLabel()));
        sb.append(super.getDescriptionBox().boxToString());
        sb.append(this.XMLContentBox.boxToString() + "\n");
        for(JUMBFSuperBox jsb:this.getNestedJumbfBoxes().values()) {
            sb.append(jsb.boxToString());
            sb.append("\n");
        }
        for(JLINKSuperBox jlinksb:NestedJlinkBoxes.values()) {
            sb.append(jlinksb.boxToString());
            sb.append("\n");
        }
        
        return sb.toString();  
    }
    
    public byte[] getXTBoxData() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //DescriptionBox Type
        baos.write(allocateData(this.getDescriptionBox().getLBox()));
        baos.write(allocateData(this.getDescriptionBox().getType()));
        // UUID code
        baos.write(allocateData(this.getDescriptionBox().getContentType().getMostSignificantBits()));
        baos.write(allocateData(this.getDescriptionBox().getContentType().getLeastSignificantBits()));
        //Toggles code
        baos.write(allocateData(this.getDescriptionBox().getToggles()));
        //Label code
        baos.write(allocateData(this.getDescriptionBox().getLabel().getBytes()));
        baos.write(0);
        //ID code
        baos.write(allocateData(this.getDescriptionBox().getId()));
        //Signature
        baos.write(allocateData(this.getDescriptionBox().getSignature()));
        //XML Box
        baos.write(allocateData(this.getXMLContentBox().getLBox()));
        baos.write(allocateData(this.getXMLContentBox().getType()));
        baos.write(this.getXMLContentBox().getXTBoxData());
        
        for (JUMBFSuperBox sb:this.NestedJumbfBoxes.values()){
            baos.write(allocateData(sb.getLBox()));
            baos.write(allocateData(sb.getType()));
            baos.write(sb.getXTBoxData());
        }
        
        for (JLINKSuperBox jl:this.NestedJlinkBoxes.values()){
            baos.write(allocateData(jl.getLBox()));
            baos.write(allocateData(jl.getType()));
            baos.write(jl.getXTBoxData());
        }
                
        return baos.toByteArray();
    }
}
