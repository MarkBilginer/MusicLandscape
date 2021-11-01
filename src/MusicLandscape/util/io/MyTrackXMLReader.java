package MusicLandscape.util.io;

import MusicLandscape.entities.Artist;
import MusicLandscape.entities.Track;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class MyTrackXMLReader extends MyReader<Track> {

    private String file_name;
    private ArrayList<Track> tracks;
    private int index;

    /**
     * constructs a MyReader from a Buffered Reader.<br>
     * the underlying stream cannot be null. In case a null object is passed to
     * this constructor an IllegalArgumentException including
     * a custom message "expected non-null ReaderObject" is thrown.
     *
     * @param in the underlying stream
     * @ProgrammingProblem.Aspect throwing standard exceptions
     */
    public MyTrackXMLReader(BufferedReader in, String file_name) {
        super(in);
        this.setFileName(file_name);
        this.tracks = new ArrayList<>();
        this.index = 0;
    }

    /**
     * Gets the next object from the underlying ArrayList<Track>.<br>
     * iterates from the beginning till the end and after
     * reaching the end, starts from 0th index again.
     *
     * @return the next Track as an object from ArrayList<Track> private memmber variable.
     */
    @Override
    public Track get() {
        if (this.tracks.isEmpty() || (this.index >= this.tracks.size())) {
            return null;
        }

        return this.tracks.get(index++);
    }

    public String getFileName() {
        return file_name;
    }

    public void setFileName(String file_name) {
        this.file_name = ((!"".equals(file_name)) ? file_name : "NoFileNameProvided");
    }

    /**
     * Creates an ArrayList<Track> out of the file. The Track objects are created by the data
     * provided in the XML file. The xml file is provided to the constructor
     * of this class.
     */
    public void createXMLTrackArray() {

        try {
            // Defines a factory API that enables
            // applications to obtain a parser that produces
            // DOM object trees from XML documents.
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // setting validating to true checks the xml file with its corresponding dtd file.
            dbf.setValidating(true);

            // we are creating an object of builder to parse
            // the  xml file.
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(this.getFileName());

            /*here normalize method Puts all Text nodes in
            the full depth of the sub-tree underneath this
            Node, including attribute nodes, into a "normal"
            form where only structure separates
            Text nodes, i.e., there are neither adjacent
            Text nodes nor empty Text nodes. */
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            // Here nodeList contains all the nodes with
            // name Track.
            NodeList nodeList = doc.getElementsByTagName("Track");

            //Each Track object has the following properties: Title, Writer, Performer, Duration, Year
            // we will construct a Track object by reading these properties from the earlier read XML file
            for (int i = 0; i < nodeList.getLength(); i++) {
                Track loadedTrack = new Track();
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element tElement = (Element) node;

                    String title = tElement.getElementsByTagName("Title").item(0).getTextContent();
                    loadedTrack.setTitle(title);

                    String writer = tElement.getElementsByTagName("Writer").item(0).getTextContent();
                    loadedTrack.setWriter(new Artist(writer));

                    String performer = tElement.getElementsByTagName("Performer").item(0).getTextContent();
                    loadedTrack.setPerformer(new Artist(performer));

                    String duration = tElement.getElementsByTagName("Duration").item(0).getTextContent();
                    loadedTrack.setDuration(Integer.parseInt(duration));

                    String year = tElement.getElementsByTagName("Year").item(0).getTextContent();
                    loadedTrack.setYear(Integer.parseInt(year));

                    this.tracks.add(loadedTrack);
                }
            }


        } catch (IOException e) {
            System.out.println("An IO error occured: " + e.getMessage());
        } catch (SAXException e) {
            System.out.println("A Parse error occured: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("The String doesn't contain a parseable Integer, possible erroneous fields: Duration and Year. " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("An Illegal Argument was provided: " + e.getMessage());
        } catch (ParserConfigurationException e) {
            System.out.println("A DocumentBuilder could not be created which satisfies the configuration requested: "
                    + e.getMessage());
        } catch (FactoryConfigurationError e) {
            System.out.println("A service configuration error or an implementation is not available or could not be instantiated: "
                    + e.getMessage());
        }
    }

    /**
     * Checks if ArrayList<Track> is empty or not and returns it.
     *
     * @return null if ArrayList<Track> is empty, otherwise ArrayList<Track> member variable.
     */
    public ArrayList<Track> getTracks() {
        if (this.tracks.isEmpty()) {
            return null;
        }

        return this.tracks;
    }

    /**
     * @return the current index which will be used next in this.get()
     */
    public int getIndex() {
        return index;
    }
}
