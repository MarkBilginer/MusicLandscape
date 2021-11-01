package MusicLandscape.entities;

import MusicLandscape.Date;
import MusicLandscape.Venue;

/**
 * represents a generic event of an artist in a certain venue on certain date
 */
public class Event {

    /**
     * the artist who appeared at this event the artist of an event cannot be null
     */
    private Artist artist;

    /**
     * the number of attendees of this event.
     */
    private int attendees;

    /**
     * the date on which this event takes place a null date represents an unknown date
     */
    private Date date;

    /**
     * description of this event default description is an empty String like
     */
    private String description;

    /**
     * the venue at which this event takes place a null venue represents an unknown venue
     */
    private Venue venue;

    /**
     * creates a default event a default event has a default artist and an empty description.
     * all other information is unknown (See docu for fields)
     */
    public Event() {
        this.artist = new Artist();
        this.description = "";
    }

    /**
     * creates a deep copy of an event
     *
     * @param e the event to copy
     */
    public Event(Event e) {
        this.setArtist(new Artist(e.getArtist()));
        this.setAttendees(e.getAttendees());
        this.setDate(new Date(e.getDate()));
        this.setDescription(e.getDescription());
        this.setVenue(new Venue(e.getVenue()));
    }

    /**
     * gets the artist of this event
     *
     * @return the artist
     */
    public Artist getArtist() {
        return this.artist;
    }

    /**
     * sets the artist of this event the artist of an event cannot be null
     *
     * @param artist the artist to set
     */
    public void setArtist(Artist artist) {
        if (artist != null) {
            this.artist = artist;
        }
    }

    /**
     * gets the venue of this event
     *
     * @return the venue
     */
    public Venue getVenue() {
        return this.venue;
    }

    /**
     * sets the venue of this event
     *
     * @param venue the venue to set
     */
    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    /**
     * gets the date of this event this method is defensive in the sense that it returns a copy of the date
     *
     * @return the date
     */
    public Date getDate() {
        return new Date(date);
    }

    /**
     * sets the date of this event an unknown date is represented by a null date.
     * this method is defensive in the sense that this event keep a copy of the original date
     *
     * @param date the date to set
     */
    public void setDate(Date date) {
        if (date != null) {
            this.date = new Date(date);
        }
    }

    /**
     * gets the number of attendees of this event
     *
     * @return the attendees
     */
    public int getAttendees() {
        return this.attendees;
    }

    /**
     * sets the number of attendess of this event the number of attendees must be a non-negative number.
     * When called with invalid arguments this event remains unchaged.
     *
     * @param attendees the attendees to set
     */
    public void setAttendees(int attendees) {
        if (attendees >= 0) {
            this.attendees = attendees;
        }
    }

    /**
     * gets the description of this event
     *
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * sets the description of this event description can not be null
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    /**
     * returns the impact of this event the impact is an estimation of the number of people who took notice of this
     * event for a generic event, the impact is the number of attendees times 2.
     *
     * @return the impact
     */
    public int impact() {
        return this.getAttendees() * 2;
    }

    //TODO make ternary operator for unknown
    /**
     * returns a String representation of this event the string representation of an event is
     * (without quotes, but including line breaks):
     * "artist" @ "venue name" on "date"
     * "description"
     * ("attendees" attending ("impact"))
     * <p>
     * if a value is not available, replace it with "unknown"
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return this.getArtist().getName() + " @ " + this.getVenue().getName() + " on " + this.getDate().toString() + "\n"
                + this.getDescription() + "\n"
                + "(" + this.getAttendees() + " attending " + "(" + this.impact() + ")" + ")";
    }
}
