package MusicLandscape.util.io;

import MusicLandscape.entities.Artist;
import MusicLandscape.entities.Track;

import java.io.BufferedReader;
import java.io.IOException;

public class MyTrackCSVReader extends MyReader<Track> {

    /**
     * these static fields define which information is found at which position in a line of the CSV. You may find them
     * useful to target a specific information when creating a new Track
     */
    private static int DURATION = 3;
    private static int PERFORMER = 2;
    private static int TITLE = 0;
    private static int WRITER = 1;
    private static int YEAR = 4;

    public MyTrackCSVReader(BufferedReader in) {
        super(in);
    }

    /**
     * Gets the next object from the underlying stream.<br>
     * <p>
     * Reads the next record and creates an object with the respective values
     * set. This method handles ALL IOExceptions that might occur and returns
     * null objects in such situations.
     * <p>
     * reads the current line of the BufferedReader, displays it as is at the console and returns the contained Track of this line
     * displays "Error reading." in case of an IOException
     * displays "Error parsing." in case of any other exception
     *
     * @return Track in case a new Track was created successfully, null otherwise
     */
    @Override
    public Track get() {
        Track loadedTrack = new Track();
        //todo matches also undesirable line content of " , , ,8,9" - improvement?
        String regex = 	"([ ]*[\\w ]+[ ]*)[,]([ ]*[\\w ]+[ ]*)[,]([ ]*[\\w ]+[ ]*)[,]([ ]*[\\d]+[ ]*)[,]([ ]*[\\d]+[ ]*)";
        try {
            // reads the line of "in" stream, might throw IOException
            String line = this.in.readLine();
            if (line != null && line.matches(regex)) {
                // parsing, creating the Track object
                String[] tokens = line.split(",");

                loadedTrack.setTitle(tokens[TITLE].strip());
                loadedTrack.setWriter(new Artist(tokens[WRITER].strip()));
                loadedTrack.setPerformer(new Artist(tokens[PERFORMER].strip()));
                loadedTrack.setDuration(Integer.parseInt(tokens[DURATION].strip()));
                loadedTrack.setYear(Integer.parseInt(tokens[YEAR].strip()));
                return loadedTrack;
            }
            else {
                if(line != null){
                    System.out.print(",,,,");
                    throw new Exception();
                }
            }

        } catch (IOException ioException) {
            System.out.println("Error reading.");
        } catch (Exception exception) {
            System.out.println("Error parsing.");
        }

        return null;
    }
}
