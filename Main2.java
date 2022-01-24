package JLINKBuilder;

import Common.FileToHex;
import JLINKLibrary.JLINKSuperBox;
import JLINKLibrary.JLINKUtils;
import JUMBF.JUMBFContentBox;
import JUMBF.JUMBFSuperBox;
import JUMBF.JUMBFUtils;
import java.io.File;

/**
 *
 * @author carlos
 */
public class Main2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        //mainFrame f = new mainFrame();
        
        JUMBFUtils p = new JUMBFUtils();
        JLINKLibrary.JLINKUtils utils = new JLINKUtils();
        File f = new File("/home/carlos/Escritorio/3456x2304.jpeg");
        JUMBFSuperBox sb = new JUMBFSuperBox(Common.Values.TYPE_ContiguousCodestream, (byte) 15, "Test Label", 12345678);
        sb.addData(f);

        //System.out.println(utils.shapeJLINKSuperBox(f).boxToString());
        //utils.DisplayJlink(utils.shapeJLINKSuperBox(f));
        //System.out.println(fh.convertFileToHex(f.toPath()));
        p.JUMBFToBox(sb, "testJUMBF", (short) 3);
        
        
        //System.out.println(sb.BoxToString());
        //p.getImageFromBox("qwer");
        //p.mergeJUMBF("/home/carlos/NetBeansProjects/JUMBF/src/Test_JUMBF/test.jpeg", "/home/carlos/Testfiles/qwer.jumbf");//TestJlink1
        p.getImageFromBox("testJUMBF");
        
    }
    
}
