package MusicLandscape.util.comparators;

import MusicLandscape.entities.Track;

/**
 * Encapsulates the concept of comparing two tracks by year.
 */
public class YearComparator implements java.util.Comparator<Track> {

    public YearComparator() {
    }

    /**
     * Compares two tracks by year.
     *
     * @param o1
     * @param o2
     * @return 1 if o1.year > o2.year,
     * 0 if o1.year == 02.year,
     * -1 if o1.year < o2.year
     */
    @Override
    public int compare(Track o1, Track o2) {
        return Integer.compare(o1.getYear(), o2.getYear());
    }

    /**
     * the string representation is "by year" (without quotes)
     *
     * @return
     */
    @Override
    public String toString() {
        return "by year";
    }
}
