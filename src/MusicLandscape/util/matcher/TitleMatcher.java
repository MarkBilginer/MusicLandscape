package MusicLandscape.util.matcher;

import MusicLandscape.entities.Track;
import MusicLandscape.util.MyMatcher;

/**
 * Encapsulates the concept of matching a track based on its title.
 * This class is used to test whether a given track's title starts with a certain string, the starting string being the pattern of this matcher.
 * The pattern is a simple string. Null patterns are not accepted.
 */
public class TitleMatcher extends MyMatcher<Track> {

    private String pattern;
    /**
     * Creates a Matcher object with a specified pattern.<br>
     *
     * @param pat the pattern of this matcher
     */
    public TitleMatcher(String pat) {
        super(pat);
    }

    /**
     * Any non-null String is an acceptable pattern.
     *
     * @param pat the pattern to set
     */
    @Override
    public void setPattern(String pat) {
        if(pat != null) {
            this.pattern = pat;
        } else{
            System.out.println("Null pattern provided in TitleMatcher.");
        }
    }

    /**
     * Gets the pattern of this matcher.<br>
     * <p>
     * The pattern is returned in a format that is considered valid in setPAttern.
     *
     * @return the pattern
     */
    @Override
    public String getPattern() {
        return this.pattern;
    }

    /**
     * A track matches if its title starts with the pattern of this matcher.
     *
     * @param track the object to match
     * @return whether t matches the pattern of this matcher.
     */
    @Override
    public boolean matches(Track track) {
        return track.getTitle().startsWith(this.getPattern());
    }

    /**
     * the string representation is title starts with (PATTERN)
     * with range as described in getPAttern
     * @return string representation of TitleMatcher object
     */
    @Override
    public String toString() {
        return "title starts with (" + this.getPattern() + ")";
    }
}
