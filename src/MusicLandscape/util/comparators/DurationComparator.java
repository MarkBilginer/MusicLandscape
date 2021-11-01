package MusicLandscape.util.comparators;

import MusicLandscape.entities.Track;

/**
 * Encapsulates the concept of comparing two tracks by duration.
 */
public class DurationComparator implements java.util.Comparator<Track> {

    public DurationComparator() {
    }

    /**
     * Compares two tracks by duration.
     * This comparator assumes non-null arguments.
     *
     * @param o1
     * @param o2
     * @return positive value if o1.getDuration() > o2.getDuration(),
     * 0 if o1.getDuration() == 02.getDuration(),
     * negative if o1.getDuration() < o2.getDuration()
     */
    @Override
    public int compare(Track o1, Track o2) {
        return Integer.compare(o1.getDuration(), o2.getDuration());
    }

    /**
     * the string representation is "by duration" (without quotes)
     *
     * @return
     */
    @Override
    public String toString() {
        return "by duration";
    }
}
