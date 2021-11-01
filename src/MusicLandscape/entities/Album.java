package MusicLandscape.entities;


/**
 * This class represents an album as a concrete release of a specific artist.
 * An album has a list of tracks, which, in this class, is implemented as a (singly) linked lists of tracks.
 */
public class Album extends Release {

    /**
     * The tracks of this album.
     * More specifically this is the head of the linked list of tracks of this album.
     */
    private Album.TrackListItem trackListHead;

    /**
     * Creates a default Album.
     * A default album is a default release with an empty track list.
     */
    public Album() {
        super();
        this.trackListHead = null;
    }

    /**
     * Creates a copy of an album.
     * All release parts of this album are copied as described in the release copy constructor. The track list of this
     * album contains (references to) the same tracks as the original, meaning tracks are not deeply copied.
     *
     * @param orig the album to copy
     */
    public Album(Album orig) {
        super(orig.getTitle(), new Artist(orig.getArtist()), orig.getYear());
        this.trackListHead = orig.trackListHead;
    }

    /**
     * Create an album with a specific title of a specific artist in a specific year.
     *
     * @param title  the title of the new album
     * @param artist the artist of the new album
     * @param year   the year of the new album
     */
    public Album(String title, Artist artist, int year) {
        super(title, artist, year);
    }

    /**
     * Adds a track to the list of tracks.
     * Tracks are added to the end of the list. Null tracks are not accepted.
     * The method returns whether the list was modified.
     * true means success (track was added) false means no success (track was NOT added).
     *
     * @param track the track to add
     * @return whether the list was modified (added successfully) or not
     */
    public boolean addTrack(Track track) {
        if (track == null) {
            return false;
        }

        // Create a new node with given track
        Album.TrackListItem newTrackListItem = new Album.TrackListItem(track);

        // If the Linked List is empty,
        // then make the new node as head
        if (this.trackListHead == null) {
            this.trackListHead = newTrackListItem;
            return true;
        } else {
            // Else traverse till the last node
            // and insert the new_node there
            Album.TrackListItem lastItem = this.trackListHead;
            while (lastItem.next != null) {
                lastItem = lastItem.next;
            }

            // Insert the new_node at last node
            lastItem.next = newTrackListItem;
            return true;
        }

    }

    /**
     * Removes a track from the track from the list of tracks.
     * Removes and returns the track at position n from the list of tracks. Element numbering starts at 0,
     * such that in a list containing a single element the position of that element is 0 (zero).
     * If the requested element does not exist in the list null is returned.
     *
     * @param index the (zero-based) position of the track to be removed.
     * @return the removed track or null
     */
    public Track removeTrack(int index) {
        // store head node
        Album.TrackListItem currNode = this.trackListHead, prevNode = null;
        //Case 1:
        // If index is 0, then head node itself is to be deleted
        if (index == 0 && currNode != null) {
            // change head
            this.trackListHead = currNode.next;

            // return deleted Track
            return currNode.track;
        }

        //Case 2:
        // If the index is greater than 0 but less than the
        // size of LinkedList
        //
        // The counter
        int counter = 0;

        // Count for the index to be deleted,
        // keep track of the previous node
        // as it is needed to change currNode.next
        while (currNode != null) {
            if (counter == index) {
                // Since the currNode is the required
                // position unlink currNode from linked list

                prevNode.next = currNode.next;
                break;
            } else {
                prevNode = currNode;
                currNode = currNode.next;
                counter++;
            }
        }
        // If the position element was found, it should be
        // at currNode Therefore the currNode shall not be null
        //
        // CASE 3: The index is greater than the size of the LinkedList
        //
        // In this case, the currNode should be null
        if (currNode == null) {
            return null;
        }

        return currNode.track;
    }

    /**
     * Gets the number of tracks on this album.
     *
     * @return the number of tracks
     */
    public int nrTracks() {

        int counter = 0;
        Album.TrackListItem currNode = this.trackListHead;

        while (currNode != null) {
            currNode = currNode.next;
            counter++;
        }

        return counter;
    }

    /**
     * Gets the tracks of this album.
     * This method creates an array containing all tracks of this album preserving their current order.
     * If the album has no tracks an array of size zero is returned. The tracks in the returned array are NOT (deep)
     * copies of the tracks currently maintained by this album, meaning that the caller can modify the tracks of this
     * album, however modification of their ordering in the list is not possible from outside. *
     *
     * @return the tracks of this album in order
     */
    public Track[] getTracks() {

        int length = this.nrTracks();

        if (length == 0) {
            return new Track[0];
        }

        Track[] tracks = new Track[length];
        Album.TrackListItem currNode = this.trackListHead;
        int index = 0;

        while (currNode != null) {
            tracks[index] = currNode.track;
            currNode = currNode.next;
            index++;
        }

        return tracks;
    }

    /**
     * Gets the total running time of this album.
     * The running time is the sum of the running times of all tracks in this album. The time is returned in seconds.
     *
     * @return the total running time in seconds.
     */
    @Override
    public int totalTime() {
        int totalDuration = 0;
        Album.TrackListItem currNode = this.trackListHead;

        while (currNode != null) {
            totalDuration += currNode.track.getDuration();
            currNode = currNode.next;
        }

        return totalDuration;
    }

    /**
     * Gets a String representation of this album.
     * The String representation of an album adds the titles of all tracks to the release String representation.
     * The list of track names is enclosed by opening and closing brackets ([,]). Track titles are also enclosed by
     * opening and closing brackets.
     *
     * @return String representation of Album object.
     */
    @Override
    public String toString() {

        String represent = "";
        Track[] allTracks = this.getTracks();
        for (int i = 0; i < allTracks.length; i++) {
            represent += "[" + allTracks[i].getTitle() + "]";
//            if (i != (allTracks.length - 1)) {
//                represent += ", ";
//            }
        }
        return super.toString()+ "\n" + "[" + represent + "]";
    }

    /**
     * A single item of a linked list of tracks.
     * A single list item consists of the primary data, in our case a track, and a reference to its successor,
     * which is another list item.
     */
    private class TrackListItem {

        /**
         * A reference to the next item in the list.
         */
        Album.TrackListItem next;

        /**
         * The primary data of this list item.
         */
        Track track;

        /**
         * Creates a list item from a track.
         * Simply wraps a list item around a track so it can be inserted into a linked list of tracks.
         * This list item does NOT maintain its own copy of the original track. It means a track in the list can be
         * modified from the caller who might still maintain a reference, however, the list structure is protected.
         *
         * @param track the track of this list item
         */
        TrackListItem(Track track) {
            this.track = track;
            this.next = null; //singly linked list if next points to null its the end of the list.
        }
    }
}
