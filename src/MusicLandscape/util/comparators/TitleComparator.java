package MusicLandscape.util.comparators;

import MusicLandscape.entities.Track;

/**
 * Encapsulates the concept of comparing two tracks by title.
 */
public class TitleComparator implements java.util.Comparator<Track> {

    public TitleComparator() {
    }

    /**
     * Compares two tracks by title.
     * Comparison is performed lexicographically on the titles of the two tracks.
     * This comparator does not handle null tracks..
     *
     * @param o1
     * @param o2
     * @return positive value if o1.getTitle() > o2.getTitle(),
     *          0 if o1.getTitle() == 02.getTitle(),
     *          negative value if o1.getTitle() < o2.getTitle()
     */
    @Override
    public int compare(Track o1, Track o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }

    /**
     * the string representation is "by title" (without quotes)
     * @return
     */
    @Override
    public String toString() {
        return "by title";
    }
}
