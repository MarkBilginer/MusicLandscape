package MusicLandscape.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * represents a concert of a certain artist with a certain set list as a specific event. As an extension of a generic
 * event a concert provides the possibility to store the setlist. The setlist is the sequence of non-null tracks played
 * at a concert. Class concert provides methods to add tracks to the (end of the) tracklist and to reset the tracklist
 * all together (empty it).
 */
public class Concert extends Event {

    /**
     * next free index
     */
    private int nextTdx;

    /**
     * array holding the tracks of the setlist
     */
    private Track[] setList;

    public Concert() {
        this.setList = new Track[10];
        this.nextTdx = 0;
    }

    /**
     * adds a track to the set list Tracks are added to the end of the list with the first track played at the concert
     * being stored at the beginning of the list. This method returns whether the non-null track was successfully added
     * to the setlist or not. This method does not accept/ignores null tracks.
     *
     * @param t the track to add
     * @return true if the track was added, false otherwise
     */
    public boolean addTrack(Track t) {
        if (t == null) {
            return false;
        }

        //TODO delete duplicate code
//        // check if array is long enough
//        // if not create a new larger array
//        if(this.nextTdx >= this.setList.length) {
//            // create a new temporary ArrayList
//            List<Track> enlarged = new ArrayList<Track>(Arrays.asList(this.setList));
//
//            // add the new element
//            enlarged.add(t);
//
//            // convert the ArrayList to array
//            Track[] track = new Track[enlarged.size()];
//            this.setList = enlarged.toArray(track);
//            this.nextTdx++;
//            return true;
//            // copy all elements
//        }

//        // array still has space left
        this.ensureCapacity(this.setList.length + 1);
        this.setList[this.nextTdx] = t;
        this.nextTdx++;
        return true;
    }

    /**
     * ensures sufficient storage for a specific number of tracks in the setlist
     * If the requested capacity can not be
     * ensured before the call, this method increases storage thereby keeping all existing entries.
     *
     * @param length the maximum number of tracks this concert must be able to keep in the setlist
     */
    private void ensureCapacity(int length) {
        // check if array is long enough
        // if not create a new larger array
        if (length >= this.setList.length) {
            // create a new temporary ArrayList containing existing Track[] elements
            List<Track> enlarged = new ArrayList<Track>(Arrays.asList(this.setList));

            // neccessary type for toArray()
            Track[] track = new Track[length];
            // convert the ArrayList to Track[] and assign to this setList
            this.setList = enlarged.toArray(track);
        }
    }

    /**
     * gets the setlist
     * This method returns a defensive copy, meaning it returns a copy of the setlist, which contains
     * (deep) copies of the tracks in the setlist. The returned array does not contain any null entries. If the setlist
     * is empty an array of length 0 is returned.
     *
     * @return the setlist of this concert
     */
    public Track[] getSetList() {
        if (this.setList.length == 0) {
            return new Track[0];
        }

        Track[] deep_copy = new Track[this.setList.length];
        int idx = 0;

        for (Track track : this.setList) {
            if (track != null) {
                deep_copy[idx] = new Track(track);
                idx++;
            }
        }
        return deep_copy;
    }

    /**
     * sets the setList This method creates a defensive copy, meaning it sets the setlist of this concert to contain
     * (deep copies of) all non-null tracks of the argument (and only those) thereby preserving the relative ordering
     * of entries. Null entries in the argument are ignored and not part of the resulting setlist. A null argument is
     * generally ignored.
     *
     * @param tracks the tracks for the setlist
     */
    public void setSetList(Track[] tracks) {
        if (tracks == null) {
            return;
        }

        Track[] deep_copy = new Track[tracks.length];
        int idx = 0;
        for (Track track : tracks) {
            if (track != null) {
                deep_copy[idx] = new Track(track);
                idx++;
            }
        }

        this.setList = deep_copy;
    }

    /**
     * removes all tracks from the setlist
     */
    public void resetSetList() {
        Track[] reset_array = new Track[this.setList.length];
        this.setList = reset_array;
        this.nextTdx = 0;
    }

    /**
     * get the length of the playlist the length of the playlist is the number of entries in the setlist.
     *
     * @return the number of tracks in the setlist
     */
    public int nrTracks() {
        return this.setList.length;
    }

    /**
     * calculates the total duration (in seconds) of all tracks in the setlist
     * More specifically the method returns an estimation (lower bound) since tracks with unknown duration are treated
     * having duration 0.
     *
     * @return total duration (lower bound) in seconds
     */
    public int duration() {
        int total_duration = 0;

        for (Track track : this.setList) {
            total_duration += track.getDuration();
        }

        return total_duration;
    }

    /**
     * returns the impact of this event
     * the impact is an estimation of the number of people who took notice of this event. For a concert, the impact is
     * calculated from the number of attendees and the length of the concert.
     * The number of attendees is mulitplied by the duration factor, which is initially 1 but increases by one for
     * every started half hour the concert lasts. E.G: 400 people attending the concert. 75 minutes duration; duration
     * factor=3 (two full half hours, plus one started half hour) impact therefore is 400*3.
     *
     * @return the impact
     */
    @Override
    public int impact() {
        // duration factor increases per started half hour
        int increase_factor = 30;
        // total duration of concert in minutes
        int total_duration = this.duration() / 60;

        int duration_factor = (int) Math.ceil(total_duration / increase_factor);

        return this.getAttendees() * duration_factor;
    }

    /**
     * returns a String representation of this concert
     * the string representation of a concert appends the following line to the string representation
     * of a generic event (without quotes):
     * "number of tracks" tracks played, total duration "time".
     * time is displayed in the format hh:mm with leading zeros
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        int hours = this.duration() / (60 * 60);
        int minutes = (this.duration() % (60 * 60)) / 60;

        return super.toString() + "\n"
                + this.nrTracks() + " tracks played, total duration " + String.format("%02d:%02d", hours, minutes);
    }
}
