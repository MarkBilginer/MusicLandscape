package MusicLandscape.util.io;

import MusicLandscape.entities.Track;
import MusicLandscape.util.MyFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class MyXMLWriter extends MyWriter<Track> {

    /**
     * Constructs a MyWriter with a specific target file and format.
     * In case null objects are passed to this constructor IllegalArgumentException is thrown.
     *
     * @param file      the file to which to save the data.
     * @param theFormat the format in which to store the data.
     */
    public MyXMLWriter(FileWriter file, MyFormatter<Track> theFormat) {
        super(file, theFormat);
    }

    /**
     * @param tracks
     * @return int number of tracks written
     */
    public int saveToXML(Track[] tracks) {
        Document dom;
        Element subRootEle = null;

        int counter = 0;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();

            // create the root element
            Element rootEle = dom.createElement("TrackContainer");

            // create data elements and place them under root Element
            for (Track track : tracks) {
                // root element Track encloses all elements providing info about the Track
                subRootEle = dom.createElement("Track");

                // creating subRootEle child tags
                Element trackTitle = dom.createElement("Title");
                Element trackWriter = dom.createElement("Writer");
                Element trackPerformer = dom.createElement("Performer");
                Element trackDuration = dom.createElement("Duration");
                Element trackYear = dom.createElement("Year");

                // filling the child tags with data provided by tracks array
                trackTitle.appendChild(dom.createTextNode(track.getTitle()));
                trackWriter.appendChild(dom.createTextNode((track.getWriter().toString())));
                trackPerformer.appendChild((dom.createTextNode(track.getPerformer().toString())));
                trackDuration.appendChild(dom.createTextNode(Integer.toString(track.getDuration())));
                trackYear.appendChild(dom.createTextNode(Integer.toString(track.getYear())));

                // appending child tags filled with data to subRootEle
                subRootEle.appendChild(trackTitle);
                subRootEle.appendChild(trackWriter);
                subRootEle.appendChild(trackPerformer);
                subRootEle.appendChild(trackDuration);
                subRootEle.appendChild(trackYear);

                // appending subRootEle with its childs to rootEle
                rootEle.appendChild(subRootEle);
                counter++;
            }
            // appending rootEle with its child/s and their childs to the DOM document instance
            dom.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                Properties prop = new Properties();

                prop.setProperty(OutputKeys.INDENT, "yes");
                prop.setProperty(OutputKeys.METHOD, "xml");
                prop.setProperty(OutputKeys.ENCODING, "UTF-8");
                prop.setProperty(OutputKeys.DOCTYPE_SYSTEM, "TrackContainer.dtd");
                prop.setProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                tr.setOutputProperties(prop);

                // send DOM to file
                tr.transform(new DOMSource(dom), new StreamResult(this.out));

            } catch (TransformerConfigurationException e) {
                System.out.println("A Serious configuration error occured, failed to create a transformer: " + e.getMessage());
            } catch (TransformerException e) {
                System.out.println("An unrecoverable error occured during transformation: " + e.getMessage());
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }


        return counter;
    }

    /**
     * Creates the corresponding dtd file for validation checking.
     * The dtd file generated by this function shall validate the
     * xml file created by this class' saveToXML() method.
     *
     */
    public void createDTDFile() {
//        if (file_name.isEmpty() || file_name == null) {
//            System.err.println("Filename cannot be null or empty.");
//            return;
//        }

        String element = "!ELEMENT";
        String pcdata = "#PCDATA";

        String dtd_content = String.format(
                "<%1$s TrackContainer (Track+)>\n" +
                "<%1$s Track (Title,Writer,Performer,Duration,Year)>\n" +
                "<%1$s Title (%2$s)>\n" +
                "<%1$s Writer (%2$s)>\n" +
                "<%1$s Performer (%2$s)>\n" +
                "<%1$s Duration (%2$s)>\n" +
                "<%1$s Year (%2$s)>\n",
                element, pcdata);

//        String dtd_file_name = "";
//        if (file_name.contains(".xml")) {
//            dtd_file_name = file_name.replace(".xml", ".dtd");
//        } else if (file_name.contains(".")) {
//            dtd_file_name = file_name.split(".")[0] + ".dtd";
//        } else {
//            dtd_file_name = file_name + ".dtd";
//        }

        try {
            BufferedWriter b_out = new BufferedWriter(new FileWriter("TrackContainer.dtd"));
            b_out.write(dtd_content);
            b_out.close();
        } catch (IOException e) {
            System.out.println("An IO Exception occurred while writing to the DTD file: "
                    + e.getMessage());
        }
    }
}