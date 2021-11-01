package MusicLandscape.entities;

public class TVShow extends Event {

    /**
     * the name of this TV show
     * The name of a TVShow cannot be empty or composed of whitespace only.
     * A null name represents an unknown name.
     */
    private String name;

    /**
     * the number of viewers of this TVShow must be non-negative
     * 0 means unknown
     */
    private int viewers;

    /**
     * creates a default TVShow
     * a default TVShow is a default event with an unknown name and an unknown number of viewers.
     */
    public TVShow() {
        this.name = null;
        this.viewers = 0;
    }

    /**
     * creates a deep copy of a TVShow
     *
     * @param tvs the TV show to copy
     */
    public TVShow(TVShow tvs) {
        this.name = new String(tvs.getName());
        this.viewers = tvs.getViewers();
    }

    /**
     * creates a TV show from an event this constructor performs some kind of promotion such that it takes a generic
     * event and creates a TV show which is a (deep) copy of the original event. The resulting TV show has unknown name
     * and unknown viewers.
     *
     * @param e the event to copy/promote to TV show
     */
    public TVShow(Event e) {
        super(e);
        this.name = null;
        this.viewers = 0;
    }

    /**
     * gets the number of viewers of this TVShow
     *
     * @return the viewers
     */
    public int getViewers() {
        return this.viewers;
    }

    /**
     * sets the viewers of this TVshow illegal arguments are ignored
     *
     * @param viewers the number of viewers to set
     */
    public void setViewers(int viewers) {
        if (viewers <= 0) {
            return;
        }
        this.viewers = viewers;
    }

    /**
     * gets the name of this TVShow
     *
     * @ the name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of this TVShow illegal arguments are ignored
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns the impact of this event the impact is an estimation of the number of people who took notice of this
     * event for a TV show event, the impact is the audience times 2. audience are all viewers and attendees of a show
     *
     * @return the impact
     */
    public int impact() {
        int audience = this.getViewers() + this.getAttendees();
        return audience * 2;
    }

    /**
     * returns a String representation of this TV show the string representation of a TV show is (without quotes):
     * "artist name" @ "show name" on "date"
     * "description"
     * ("audience" attending ("impact"))
     * audience are all viewers and attendees of a show
     *
     * @ the string representation
     */
    @Override
    public String toString() {
        String unk = "unknown";
        String artist_name = this.getArtist().getName();
        int audience = this.getViewers() + this.getAttendees();

        return (artist_name == null ? unk : artist_name) + " @ " + (this.name == null ? unk : this.name) + " on " + this.getDate() + "\n"
                + this.getDescription() + "\n"
                + "(" + (audience == 0 ? unk : audience) + " attending " + "(" + this.impact() + ")" + ")";
    }
}
