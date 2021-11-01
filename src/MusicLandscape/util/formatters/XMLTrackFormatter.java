package MusicLandscape.util.formatters;

import MusicLandscape.entities.Track;
import MusicLandscape.util.MyFormatter;

public class XMLTrackFormatter implements MyFormatter<Track> {
    /**
     * Get the headers for the table as a single string.<br>
     * <p>
     * Contains the elements and their values of the corresponding Track object.
     * <p>
     * XML Format of all elements in the following order:
     * Title
     * Writer
     * Performer
     * duration
     * year
     *
     * <Track>
     * <Title>"title"</Title>
     * <Writer>"Writer.name"</Writer>
     * <Performer>"Performer.name"</Performer>
     * <Duration>"duration"</Duration>
     * <Year>"year"</Year>
     * </Track>
     * No new line is added at the end of the String!.
     *
     * @return the header string.
     * @return the header string.
     */
    @Override
    public String header() {
        String tab = "\t";
        String xml_format = String.format("%1$s <TrackContainer>\n"
                + "%1$s%1$s<Track>\n"
                + "%1$s%1$s%1$s<Title>\"title\"</Title>\n"
                + "%1$s%1$s%1$s<Writer>\"writer.name\"</Writer>\n"
                + "%1$s%1$s%1$s<Performer>\"Performer.name\"</Performer>\n"
                + "%1$s%1$s%1$s<Duration>\"duration\"</Duration>\n"
                + "%1$s%1$s%1$s<Year>\"year\"</Year>\n"
                + "%1$s%1$s</Track>\n"
                + "%1$s</TrackContainer>",
                tab);

        return xml_format;
    }

    /**
     * Creates a String representation for an object in XML format.
     *
     * @param track the object to be formatted
     * @return the XML format string representing the object
     */
    @Override
    public String format(Track track) {
        String xml_formatted = String.format("\t<Track>\n"
                        + "\t\t<Title>%s</Title>\n"
                        + "\t\t<Writer>%s</Writer>\n"
                        + "\t\t<Performer>%s</Performer>\n"
                        + "\t\t<Duration>%d</Duration>\n"
                        + "\t\t<Year>%d</Year>\n"
                        + "\t</Track>",
                track.getTitle(),
                track.getWriter().getName(),
                track.getPerformer().getName(),
                track.getDuration(),
                track.getYear());
        return xml_formatted;
    }

    /**
     * A line of text to be used between header and data.
     *
     * @return the separator.
     */
    @Override
    public String topSeparator() {
        return "";
    }

    /**
     * the string representation of the class as XML format (without quotes)
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return String.format("XML format\n"
                + "%s", this.header());
    }
}
