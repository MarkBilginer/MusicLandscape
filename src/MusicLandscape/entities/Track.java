package MusicLandscape.entities;

import MusicLandscape.application.Main;
import MusicLandscape.util.ConsoleScanable;

import java.util.Scanner;

/**
 * represents a piece of music that has been released on some kind of media (CD, vinyl, video, ...)
 */
public class Track implements ConsoleScanable, Comparable<Track> {

    /**
     * the duration of this track in seconds the duration is a non-negative number,
     * duration 0 (zero) represents unknown duration
     */
    private int duration;

    /**
     * the artist who performs this track the performer cannot be null
     */
    private Artist performer;

    /**
     * the title of this track. a null title represents an unknown title
     */
    private String title;

    /**
     * the artist who wrote this track the writer cannot be null
     */
    private Artist writer;

    /**
     * the year in which the Track was or will be produced valid years are between 1900- 2999
     */
    private int year;

    /**
     * creates a default track
     * a default track has the following values:
     * unknown title
     * duration 0
     * default writer and performer
     * year 1900
     */
    public Track() {
        this.title = "unknown title";
        this.duration = 0;
        this.writer = new Artist();
        this.performer = new Artist();
    }

    /**
     * creates a deep copy of a Track
     *
     * @param t the track to copy
     */
    public Track(Track t) {
        this.duration = t.getDuration();
        this.performer = new Artist(t.getPerformer());
        this.title = t.getTitle();
        this.writer = new Artist(t.getWriter());
        this.year = t.getYear();
    }

    /**
     * creates a track with a certain title the resulting track has the specified title, all other values are default
     *
     * @param title the title of this track
     */
    public Track(String title) {
        this.title = title;
        this.duration = 0;
        this.performer = new Artist();
        this.writer = new Artist();
        this.year = 1900; //default year value
    }

    /**
     * gets the production year of this track
     *
     * @return the year
     */
    public int getYear() {
        return this.year;
    }

    /**
     * sets the production year of this track valid years are between 1900 and 2999 other values are ignored,
     * the object remains unchanged
     *
     * @param year the year to set
     */
    public void setYear(int year) {
        if (validateYear(year)) {
            this.year = year;
        } else {
            return;
        }
    }

    /**
     * gets the title of this track. if the title is not known (null) "unknown title" is returned (without quotes)
     *
     * @return the title
     */
    public String getTitle() {
        return (this.title == null ? "unknown title" : this.title);
    }

    /**
     * sets the title of this track.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        if (validateTitle(title)) {
            this.title = title;
        } else {

        }
    }

    /**
     * gets the duration of this track
     *
     * @return the duration
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * sets the duration.
     * a negative value is ignored, the object remains unchanged
     *
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        if (validateDuration(duration)) {
            this.duration = duration;
        } else {
        }
    }

    /**
     * returns the writer of this track
     *
     * @return the writer
     */
    public Artist getWriter() {
        return this.writer;
    }

    /**
     * sets the the writer of this track null arguments are ignored
     *
     * @param writer the writer to set
     */
    public void setWriter(Artist writer) {
        if (writer == null) {
            return;
        }
        this.writer = writer;
    }

    /**
     * returns the performer of this track
     *
     * @return the performer
     */
    public Artist getPerformer() {
        return this.performer;
    }

    /**
     * sets the performer of this track null arguments are ignored
     *
     * @param performer the performer to set
     */
    public void setPerformer(Artist performer) {
        if (performer == null) {
            return;
        }
        this.performer = performer;
    }

    /**
     * this getter is used to check if the writer of this Track is known.
     * A writer is considered as known if the name is not equal to null .
     *
     * @return true if the writer of this track is known (and has a name), false otherwise
     */
    public boolean writerIsKnown() {
        if (this.writer == null || this.writer.getName() == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * returns a formatted String containing all information of this track. the String representation is (without quotes):
     * <p>
     * "title by writer performed by performer (min:sec)"
     * <p>
     * where
     * <p>
     * title stands for the title (exactly 10 chars wide) if not set, return unknown
     * writer stands for the writer name (exactly 10 chars wide, right justified)
     * performer stands for the performer name (exactly 10 chars wide, right justified)
     * min is the duration's amount of full minutes (at least two digits, leading zeros)
     * sec is the duration's remaining amount of seconds (at least two digits, leading zeros)
     *
     * @return String representation of this track
     */
    public String getString() {
        String title = "";
        String writer_name = "";
        String performer_name = "";
        int min = this.duration / 60;
        int sec = this.duration % 60;

        if (getTitle().length() >= 10 && getTitle() != "unknown title") {
            title = getTitle().substring(0, 10);
        } else if (getTitle().length() <= 10 && getTitle() != "unknown title") {
            title = getTitle();
        } else {
            title = "unknown title";
        }

        if (getWriter() != null && getWriter().getName() != null && getWriter().getName().length() >= 10) {
            writer_name = getWriter().getName().substring(0, 10);
        } else if (getWriter() != null && getWriter().getName() != null) {
            writer_name = getWriter().getName();
        } else {
            writer_name = "unknown";
        }

        if (getPerformer() != null && getPerformer().getName() != null && getPerformer().getName().length() >= 10) {
            performer_name = getPerformer().getName().substring(0, 10);
        } else if (getPerformer() != null && getPerformer().getName() != null) {
            performer_name = getPerformer().getName();
        } else {
            performer_name = "unknown";
        }

        String represent = String.format("%10s by %10s performed by %10s (%02d:%02d)",
                title, writer_name, performer_name, min, sec);

        return represent;
    }

    /**
     * @return a String representation of this track the string representation of this track is described in getString()
     */
    @Override
    public String toString() {
        return getString();
    }


    /**
     * Guides the user through a process that allows scanning/modifying of this track with a text-based user interface.
     * This method allows modification of the following fields, in the order listed:
     * title
     * duration
     * <p>
     * For each modifiable field the process is the following:
     * field name and current value are displayed
     * new value is read and validated
     * if input is valid, field is set, otherwise a short message is shown and input of this field is repeated.
     * <p>
     * Old values can be kept for all fields by entering an empty string. The operation cannot be cancelled,
     * instead the user must keep all former values by repeatedly entering empty strings.
     *
     * @return whether this object was altered or not
     */
    @Override
    public boolean scan() {
        boolean field_changed = false;
        Scanner sc = new Scanner(System.in);

        System.out.println(String.format("\t" + "Field name is \"title\" and value is %s", this.title));
        System.out.println("\t" + "Enter new value for title (old value can be kept by entering empty string): ");
        try {
            String valid_title;
            while (!validateTitle(valid_title = sc.nextLine())) {
                System.out.println("\t" + "Invalid value for title, please try again: ");
            }
            if (!valid_title.equals("")) {
                setTitle(valid_title);
                field_changed = true;
            }

//        System.out.println(String.format("Field name is \"Writer\" and value is %s", this.getWriter().toString()));
//        System.out.println("Enter new value for Writer (old value can be kept by entering empty string): ");
//        String valid_writer = sc.nextLine();
////            System.out.println("Invalid value for Writer, please try again: ");
//
//        if (!"".equals(valid_writer)) {
//            this.setWriter(new Artist(valid_writer));
//            field_changed = true;
//        }

//        System.out.println(String.format("Field name is \"Performer\" and value is %s", this.getPerformer().toString()));
//        System.out.println("Enter new value for Performer (old value can be kept by entering empty string): ");
//        String valid_performer = sc.nextLine();
////            System.out.println("Invalid value for Performer, please try again: ");
//        if (!"".equals(valid_performer)) {
//            this.setPerformer(new Artist(valid_performer));
//            field_changed = true;
//        }

            System.out.println(String.format("\t" + "Field name is \"duration\" and value is %d", this.getDuration()));
            System.out.println("\t" + "Enter new value for duration (old value can be kept by entering empty string): ");
            String valid_duration;
            while (!"".equals(valid_duration = sc.nextLine()) && !validateDuration(Integer.parseInt(valid_duration))) {
                System.out.println("\t" + "Invalid value for duration, please try again: ");
            }
            if (!"".equals(valid_duration)) {
                setDuration(Integer.parseInt(valid_duration));
                field_changed = true;
            }

//        System.out.println(String.format("Field name is \"year\" and value is %d", this.getYear()));
//        System.out.println("Enter new value for year (old value can be kept by entering empty string): ");
//        String valid_year;
//        while (!"".equals(valid_year = sc.nextLine()) && !validateYear(Integer.parseInt(valid_year))) {
//            System.out.println("Invalid value for year, please try again: ");
//        }
//        if (!"".equals(valid_year)) {
//            setYear(Integer.parseInt(valid_year));
//            field_changed = true;
//        }

//        sc.close(); // scanner stream is closed in main application
        } catch (NumberFormatException e) {
            System.out.println("\t" + "You need to enter a number. " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\t" + e.getMessage());
        }
        return field_changed;
    }

    /**
     * Naturally, tracks are lexicographically compared by title.
     * It is assumed that this track is only compared to non-null tracks.
     *
     * @param o track to which this Track is compared to
     * @return An int value: 0 if the string is equal to the other string.
     * < 0 if the string is lexicographically less than the other string
     * > 0 if the string is lexicographically greater than the other string (more characters)
     */
    @Override
    public int compareTo(Track o) {
        return this.title.compareTo(o.getTitle());
    }

    /**
     * Validates an int as a year.
     *
     * @param year the year to validate
     * @return true if the argument is a valid year, false otherwise
     */
    public static boolean validateYear(int year) {
        if (year <= 2999 && year >= 1900) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validates a String as a title.
     * The purpose of this method is to separate validation of values from the respective setters, since validation
     * might be necessary in several places (e.g. constructors, scan). This validation method is added for clarity of
     * structure only; since all Strings are accepted as title it has no particular practical importance. Note that it
     * is marked final, this way it can be used in constructors and setters without having the possibly unwanted
     * side-effect that subclasses redefine criteria for validity. Note also that it is marked static. The reason is
     * that classes should provide methods that allow validation of arguments before calling setters and even more
     * importantly constructors. This way expensive exception mechanisms at runtime are avoided.
     *
     * @param title String to validate as title
     * @return whether the argument is a valid title or not (here, always true)
     */
    public static final boolean validateTitle(String title) {
        return true;
    }

    /**
     * Validates an int as a duration.
     *
     * @param duration the duration to validate
     * @return true if the argument is valid, false otherwise
     */
    public static boolean validateDuration(int duration) {
        if (duration >= 0) {
            return true;
        } else {
            return false;
        }
    }
}
