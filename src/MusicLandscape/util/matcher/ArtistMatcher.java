package MusicLandscape.util.matcher;

import MusicLandscape.entities.Track;
import MusicLandscape.util.MyMatcher;

public class ArtistMatcher extends MyMatcher<Track> {

    private String pattern;

    /**
     * Creates a default Artist matcher.
     * By default a matcher matches any writer.
     */
    public ArtistMatcher() {
        super("");
    }

    /**
     * Creates a Matcher object with a specified pattern.<br>
     *
     * @param pat the pattern of this matcher
     */
    public ArtistMatcher(String pat) {
        super(pat);
    }

    /**
     * Matches a object against the pattern of this matcher.<br>
     * <p>
     * Checks for both writer name and performer name, if either one of them matches then the track matches.
     *
     * @param track the object to match
     * @return whether t matches the pattern of this matcher.
     */
    @Override
    public boolean matches(Track track) {
        return (track.getWriter().getName().startsWith(this.getPattern()) || track.getPerformer().getName().startsWith(this.getPattern()));
    }

    /**
     * Sets the pattern of this matcher.<br>
     *
     * @param pat the pattern to set
     */
    @Override
    public void setPattern(String pat) {
        if (pat != null) {
            this.pattern = pat;
        } else {
            System.out.println("Null pattern provided in ArtistMatcher.");
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
     * the string representation is The name of the Artist(=Writer or Performer) name starts with (PATTERN)
     * with range as described in getPAttern
     *
     * @return string representation of ArtistMatcher object
     */
    @Override
    public String toString() {
        return "The name of the Artist(=Writer or Performer) starts with (" + this.getPattern() + ")";
    }
}
