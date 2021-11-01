package MusicLandscape.container;

import MusicLandscape.entities.Track;
import MusicLandscape.util.MyMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeSet;


/**
 * Represents a set of tracks and a (possibly empty) subset of those tracks which are selected.
 * This class is a container for unique tracks. It does not accept null tracks, nor can a track which is already contained be added again.
 * Additionally, it supports the notion of selection, meaning that some tracks can be selected. The selection is a
 * subset of all tracks currently held by the container. The selection may at times be empty, and it may at other times contain all tracks.
 * The container provides methods to filter, sort, and retrieve the selection.
 * Tracks can only be removed from this container by removing all currently selected tracks. Removing tracks therefore
 * requires the creation of a proper selection first. The usual process of creating a selection is:
 * <p>
 * select ALL tracks (by resetting the selection)
 * (possibly repeatedly) filter the selection with a matcher. The filter is applied to the current selection!
 * if desired, sort the selection.
 * remove selected tracks from container OR retrieve the selection as an array of tracks.
 */
public class MyTrackContainer {

    /**
     * The selected tracks in this container.
     * Initially empty.
     */
    private java.util.List<Track> selection;

    /**
     * The tracks in this container.
     * Initially empty.
     */
    private java.util.Set<Track> tracks;

    /**
     * Creates a default MyTrackContainer.
     * A default container has no tracks and an empty selection.
     */
    public MyTrackContainer() {
        // create empty selection list
        this.selection = new ArrayList<Track>();
        // create empty set of tracks
        this.tracks = new TreeSet<Track>();
    }

    /**
     * Creates a container from an iterable object of tracks.
     * All tracks of the argument are added to this container. Initially, all tracks are selected.
     *
     * @param t - the iterable object of tracks to be added to this container.
     */
    public MyTrackContainer(Iterable<Track> t) {
        // call default contructor to initialize member variables.
        this();
        // TODO maybe adding a null iterable guard with a ternary operator or an utility method (public static)
        for (Track track : t) {
            if (t != null) {
                this.tracks.add(track);
                this.selection.add(track);
            }
        }
    }

    /**
     * Creates a container from an array of tracks.
     * All tracks of the argument are added to this container. Initially, all tracks are selected.
     *
     * @param t - the array of tracks to be added to this container.
     */
    public MyTrackContainer(Track[] t) {
        this();
        for (Track track : t) {
            if (t != null) {
                this.tracks.add(track);
                this.selection.add(track);
            }
        }
    }

    /**
     * Sorts the selection of tracks of this container.
     * The currently selected tracks are sorted in the sense defined by the first argument.
     * The second argument controls the scheme (ascending/descending order).
     *
     * @param theComp - the comparator defining the sorting order
     * @param asc     - the sorting scheme. true stands for ascending (from smallest to highest element) false for descending.
     */
    public void sort(java.util.Comparator<Track> theComp, boolean asc) {
        // hint: make use of the Collections class
        if (asc) {
            // if asc is true
            Collections.sort(this.selection, theComp);
        }
        if (!asc) {
            //if asc is false
            Collections.sort(this.selection, Collections.reverseOrder(theComp));
        }
    }

    /**
     * Filters the selection.
     * Applies the filter defined by the argument to the selection, keeping only those elements that match. The filter
     * is applied to the selection and the selection only, i.e. the selection cannot grow in size during this operation.
     * If all elements of a selection match the specified filter, the selection remains unchanged.
     *
     * @param matcher - the filter defining which of the tracks of the selection to keep.
     * @return the number of elements removed from the selection during this operation.
     */
    public int filter(MyMatcher<Track> matcher) {
        // Hint: use an iterator to remove elements from the selection
        int counter = 0;
//        gives ConcurrentModificationException
//        for (Track track : this.selection) {
//            if (!matcher.matches(track)) {
//                this.selection.remove(track);
//                counter++;
//            }
//        }
//        Solution:
        Iterator<Track> iter = this.selection.iterator();
        while (iter.hasNext()) {
            Track track = iter.next();
            if (!matcher.matches(track)) {
                iter.remove();
                counter++;
            }
        }
        return counter;
    }

    /**
     * Resets the selection, thereby selecting ALL tracks in this container.
     */
    public void reset() {
        //Todo after edding the for loop the test got skipped, ignored.
        if (this.tracks.size() != 0 && this.selection.containsAll(this.tracks)) {
            return;
        }
        if (this.tracks.size() == 0) {
            this.selection.clear();
            return;
        }

        this.selection.clear();
        for (Track track : this.tracks) {
            if (!this.selection.contains(track)) {
                this.selection.add(track);
            }
        }
    }

    /**
     * Removes the selected tracks from this container.
     * All currently selected tracks are removed from this container. After this operation all remaining tracks are
     * selected (the selection is reset).
     *
     * @return the number of removed tracks
     */
    public int remove() {
        int counter = 0;

        if (this.selection.isEmpty()) {
            return counter;
        }

        for (Track track : this.selection) {
            if (this.tracks.remove(track)) {
                counter++;
            }
        }
        this.reset();

        return counter;
    }

    /**
     * Bulk operation to add tracks.
     * All tracks of the argument are added to this container.
     * Does not accept null tracks.
     *
     * @param t - the tracks to add
     * @return the number of tracks added
     */
    public int addAll(Track[] t) {
        int counter = 0;

        if (t == null) {
            return counter;
        }

        for (Track track : t) {
            if (track != null) {
                if (this.tracks.add(track)) {
                    counter++;
                }
            }
        }

        return counter;
    }

    /**
     * The number of tracks currently held by this container.
     * Note: this is not the size of the selection.
     *
     * @return the number of tracks
     */
    public int size() {
        return this.tracks.size();
    }

    /**
     * Gets the selected tracks.
     * The currently selected tracks of this container are returned as an array of tracks. The tracks are returned in their current order.
     * If the selection is empty an array of size 0 is returned.
     *
     * @return the selected tracks.
     */
    public Track[] selection() {
        //hint: use List's toArray
        if (this.selection.isEmpty()) {
            return new Track[0];
        }
        return this.selection.toArray(new Track[0]);
    }

    /**
     * Add a single track.
     * The argument is attempted to be added to this container. If successfully added, it is NOT added to the selection.
     * Tracks already added cannot be added again. Null tracks cannot be added either.
     *
     * @param t - the track to add
     * @return whether the argument could be added
     */
    public boolean add(Track t) {
        if (t == null || this.tracks.contains(t)) {
            return false;
        }
        return this.tracks.add(t);
    }
}
