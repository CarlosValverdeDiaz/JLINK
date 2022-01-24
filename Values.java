package JLINKBuilder;

import java.util.HashMap;
import java.util.UUID;
/**
 *
 * @author carlos
 */
public class Values {
    //Box properties
    
    //Box values
    protected static final short BOX_MARKER = 0x4A50;
    
    //Defined boxes
    protected static final int JUMBF_jumb = 0x6A756D62;
    protected static final int JUMBF_jumd = 0x6A756D64;
    protected static final int JUMBF_jp2c = 0x6A703263;
    protected static final int JUMBF_xml = 0x786D6C20;
    protected static final int JUMBF_json = 0x6A736F6E;
    protected static final int JUMBF_uuid = 0x75756964;
    protected static final int JUMBF_jlink = 0x4C494E4B;
    protected static final int JUMBF_bidb = 0x62696462;
    
    protected static final String CodestreamContentType = "CodestreamContentType";    
    protected static final String XMLContentType = "XMLContentType";
    protected static final String JSONContentType = "JSONContentType";
    protected static final String UUIDContentType = "UUIDContentType";
    protected static final String ContiguousCodestream = "ContiguousCodestream";
    protected static final String EmbeddedFile = "EmbeddedFile";

    protected static final String String_TYPE_codestreamContentType = "6579D6FB-DBA2-446B-B2AC-1B82FEEB89D1";    
    protected static final String String_TYPE_XMLContentType = "786D6C20-0011-0010-8000-00AA00389B71";
    protected static final String String_TYPE_JSONContentType = "6A736F6E-0011-0010-8000-00AA00389B71";
    protected static final String String_TYPE_UUIDContentType = "75756964-0011-0010-8000-00AA00389B71";
    protected static final String String_TYPE_ContiguousCodestream = "6A703263-0011-0010-8000-00AA00389B71";
    protected static final String String_TYPE_EmbeddedFile = "40CB0C32-BB8A-489D-A70B-2AD6F47F4369";
    //Description Box types
    protected static final UUID TYPE_codestreamContentType = UUID.fromString(String_TYPE_codestreamContentType);    
    protected static final UUID TYPE_XMLContentType = UUID.fromString(String_TYPE_XMLContentType);
    protected static final UUID TYPE_JSONContentType = UUID.fromString(String_TYPE_JSONContentType);
    protected static final UUID TYPE_UUIDContentType = UUID.fromString(String_TYPE_UUIDContentType);
    protected static final UUID TYPE_ContiguousCodestream = UUID.fromString(String_TYPE_ContiguousCodestream);
    protected static final UUID TYPE_EmbeddedFile = UUID.fromString(String_TYPE_EmbeddedFile);
    
    protected static final int XT_BOX_Max_data = 65536;
    
    //JLINK values
    protected static final UUID TYPE_JLINK_Metadata_Elements = UUID.fromString("4C494E4B-0011-0010-8000-00AA00389B71");
    
    //JPEG markers
    protected static final int START_OF_FRAME0 = 0xFFC0; //Basline DCT
    protected static final int START_OF_FRAME1 = 0xFFC1; //Extended Sequential DCT
    protected static final int START_OF_FRAME2 = 0xFFC2; //Progressive DCT
    protected static final int START_OF_FRAME3 = 0xFFC3; //Lossless (sequential)
    protected static final int START_OF_FRAME5 = 0xFFC5; //Differential sequential DCT
    protected static final int START_OF_FRAME6 = 0xFFC6; //Differential progressive DCT
    protected static final int START_OF_FRAME7 = 0xFFC7; //Differential lossless (sequential)
    protected static final int START_OF_FRAME9 = 0xFFC9; //Extended sequential DCT, Arithmetic coding
    protected static final int START_OF_FRAME10 = 0xFFCA; //Progressive DCT, Arithmetic coding
    protected static final int START_OF_FRAME11 = 0xFFCB; //Lossless (sequential), Arithmetic coding
    protected static final int START_OF_FRAME13 = 0xFFCD; //Differential sequential DCT, Arithmetic coding
    protected static final int START_OF_FRAME14 = 0xFFCE; //Differential progressive DCT, Arithmetic coding
    protected static final int START_OF_FRAME15 = 0xFFCF; //Differential lossless (sequential), Arithmetic coding
    
    protected static final int DEFINE_HUFFMAN_TABLE  = 0xFFC4;
    protected static final int JPEG_EXTENSIONS = 0xFFC8;
    protected static final int DEFINE_ARITHMETIC_CODING = 0xFFCC;
            
    protected static final int RESTART_MARKER_0 = 0xFFD0;
    protected static final int RESTART_MARKER_1 = 0xFFD1;
    protected static final int RESTART_MARKER_2 = 0xFFD2;
    protected static final int RESTART_MARKER_3 = 0xFFD3;
    protected static final int RESTART_MARKER_4 = 0xFFD4;
    protected static final int RESTART_MARKER_5 = 0xFFD5;
    protected static final int RESTART_MARKER_6 = 0xFFD6;
    protected static final int RESTART_MARKER_7 = 0xFFD7;

    protected static final int START_OF_IMAGE = 0xFFD8;
    protected static final int END_OF_IMAGE = 0xFFD9;
    protected static final int START_OF_SCAN = 0xFFDA;
            
    protected static final int DEFINE_QUANTIZATION_TABLE = 0xFFDB;
    protected static final int DEFINE_NUMBER_OF_LINES = 0xFFDC;
    protected static final int DEFINE_RESTART_INTERVAL = 0xFFDD;
    protected static final int DEFINE_HIERARCHICAL_PROGRESSION = 0xFFDE;
            
    protected static final int EXPAND_REFERENCE_COMPONENT = 0xFFDF;
            
    protected static final int APPLICATION_SEGMENT_0 = 0xFFE0;
    protected static final int APPLICATION_SEGMENT_1 = 0xFFE1;
    protected static final int APPLICATION_SEGMENT_2 = 0xFFE2;
    protected static final int APPLICATION_SEGMENT_3 = 0xFFE3;
    protected static final int APPLICATION_SEGMENT_4 = 0xFFE4;
    protected static final int APPLICATION_SEGMENT_5 = 0xFFE5;
    protected static final int APPLICATION_SEGMENT_6 = 0xFFE6;
    protected static final int APPLICATION_SEGMENT_7 = 0xFFE7;
    protected static final int APPLICATION_SEGMENT_8 = 0xFFE8;
    protected static final int APPLICATION_SEGMENT_9 = 0xFFE9;
    protected static final int APPLICATION_SEGMENT_10 = 0xFFEA;
    protected static final int APPLICATION_SEGMENT_11 = 0xFFEB;
    protected static final int APPLICATION_SEGMENT_12 = 0xFFEC;
    protected static final int APPLICATION_SEGMENT_13 = 0xFFED;
    protected static final int APPLICATION_SEGMENT_14 = 0xFFEE;
    protected static final int APPLICATION_SEGMENT_15 = 0xFFEF;

    protected static final int JPEG_EXTENSION_0 = 0xFFF0;
    protected static final int JPEG_EXTENSION_1 = 0xFFF1;
    protected static final int JPEG_EXTENSION_2 = 0xFFF2;
    protected static final int JPEG_EXTENSION_3 = 0xFFF3;
    protected static final int JPEG_EXTENSION_4 = 0xFFF4;
    protected static final int JPEG_EXTENSION_5 = 0xFFF5;
    protected static final int JPEG_EXTENSION_6 = 0xFFF6;
    protected static final int JPEG_EXTENSION_7 = 0xFFF7;
    protected static final int JPEG_EXTENSION_8 = 0xFFF8;
    protected static final int JPEG_EXTENSION_9 = 0xFFF9;
    protected static final int JPEG_EXTENSION_10 = 0xFFFA;
    protected static final int JPEG_EXTENSION_11 = 0xFFFB;
    protected static final int JPEG_EXTENSION_12 = 0xFFFC;
    protected static final int JPEG_EXTENSION_13 = 0xFFFD;
    
    protected static final int COMMENT = 0xFFFE;
    
    protected static HashMap<String, UUID> BoxTypes = new HashMap<>();
    
    protected static void setMap() {
        BoxTypes.put(Values.CodestreamContentType, Values.TYPE_codestreamContentType);
        BoxTypes.put(Values.ContiguousCodestream, Values.TYPE_ContiguousCodestream);
        BoxTypes.put(Values.EmbeddedFile, Values.TYPE_EmbeddedFile);
        BoxTypes.put(Values.JSONContentType, Values.TYPE_JSONContentType);
        BoxTypes.put(Values.UUIDContentType, Values.TYPE_UUIDContentType);
        BoxTypes.put(Values.XMLContentType, Values.TYPE_XMLContentType);        
    }
}
