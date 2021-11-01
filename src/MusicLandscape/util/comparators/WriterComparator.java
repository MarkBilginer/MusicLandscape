package MusicLandscape.util.comparators;

import MusicLandscape.entities.Track;

/**
 * Encapsulates the concept of comparing two tracks by writer.
 */
public class WriterComparator implements java.util.Comparator<Track> {

    public WriterComparator() {
    }

    /**
     * Compares two tracks by writer.
     * The natural ordering of artists is used.
     *
     * @param o1
     * @param o2
     * @return positive value if o1.getWriter() > o2.getWriter(),
     * 0 if o1.getWriter() == 02.getWriter(),
     * negative value if o1.getWriter() < o2.getWriter()
     */
    @Override
    public int compare(Track o1, Track o2) {
        return o1.getWriter().compareTo(o2.getWriter());
    }

    /**
     * the string representation is "by writer" (without quotes)
     *
     * @return
     */
    @Override
    public String toString() {
        return "by writer";
    }
}
