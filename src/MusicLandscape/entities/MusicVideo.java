package MusicLandscape.entities;

/**
 * This class represents a music video as a concrete release of a specific artist.
 * A music video is a video presentation for a single track and has the same duration as the track.
 */
public class MusicVideo extends Release {

    /**
     * The track of this music video.  A null track represents an unknown track.
     */
    private Track track;

    public MusicVideo() {
        this.track = null;
    }

    /**
     * Gets the track of this music video.
     *
     * @return the track
     */
    public Track getTrack() {
        return this.track;
    }

    /**
     * Sets the track of this music video.
     *
     * @param track the track to set
     */
    public void setTrack(Track track) {
        this.track = track;
    }

    /**
     * Get the total time of this music video.
     *
     * @return the total running time in seconds.
     */
    @Override
    public int totalTime() {
        if(this.track == null)
            return new Track().getDuration();

        return this.track.getDuration();
    }

    /**
     * Gets a String representation of this music video.
     * The String representation of a music video simply adds "-(Video)" (without quotes) to
     * the String representation of a general release.
     *
     * @return string representation of this music video
     */
    @Override
    public String toString() {
        return super.toString() + "-(Video)";
    }
}
