package JLINKBuilder;

import Common.FileToHex;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;

/**
 *
 * @author carlos
 */
public class ImageManager {    
    
    public void manageImage(String source) throws Exception {
        
        //ImageOutputStream ios = new FileImageOutputStream(new File(source));
        File f = new File("/home/carlos/Escritorio/test.jpeg");
        FileWriter fr = new FileWriter(f);
        FileToHex fh = new FileToHex();
        
        fr.write(fh.convertFileToHex(Paths.get(source)));
        
        fr.flush();
        fr.close();
        
         /*       
        try {
            // retrieve image
            BufferedImage bi = ImageIO.read(new File(source));
            File outputfile = new File("/home/carlos/Escritorio/test.jpeg");
            ImageIO.write(bi, "jpeg", outputfile);
        } catch (IOException e) {
            throw new Exception("ERROR");
        }
*/
    }
}
