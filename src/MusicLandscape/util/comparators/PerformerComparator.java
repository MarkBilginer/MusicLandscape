package MusicLandscape.util.comparators;

import MusicLandscape.entities.Track;

/**
 * Encapsulates the concept of comparing two tracks by performer.
 */
public class PerformerComparator implements java.util.Comparator<Track> {

    public PerformerComparator() {
    }

    /**
     * Compares two tracks by performer.
     * This comparator assumes non-null arguments.
     *
     * @param o1
     * @param o2
     * @return positive value if o1.getPerformer() > o2.getPerformer(),
     * 0 if o1.getPerformer() == 02.getPerformer(),
     * negative if o1.getPerformer() < o2.getPerformer()
     */
    @Override
    public int compare(Track o1, Track o2) {
        return o1.getPerformer().compareTo(o2.getPerformer());
    }

    /**
     * the string representation is "by performer" (without quotes)
     *
     * @return
     */
    @Override
    public String toString() {
        return "by performer";
    }
}
