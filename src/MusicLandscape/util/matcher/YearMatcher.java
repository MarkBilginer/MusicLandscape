package MusicLandscape.util.matcher;

import MusicLandscape.entities.Track;
import MusicLandscape.util.MyMatcher;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YearMatcher extends MyMatcher<Track> {

    private static final int MIN_YEAR = 1900;

    private static final int MAX_YEAR = 2999;

    /**
     * the lower bound of the accepted range.
     */
    private int lower;

    /**
     * the upper bound of the accepted range.
     */
    private int upper;

    /**
     * Creates a default year matcher.
     * By default a matcher matches any year, between 1900 and 2999.
     */
    public YearMatcher() {
        super(MIN_YEAR + " " + MAX_YEAR);
    }

    /**
     * Creates a Matcher object with a specified pattern.<br>
     *
     * @param pat the pattern of this matcher
     */
    public YearMatcher(String pat) {
        super(pat);
    }

    /**
     * Matches a object against the pattern of this matcher.<br>
     *
     * @param track the object to match
     * @return whether t matches the pattern of this matcher.
     */
    @Override
    public boolean matches(Track track) {
        int track_year = track.getYear();
        boolean is_match = false;

        if(this.lower <= track_year && track_year <= this.upper) {
            is_match = true;
        }

        return is_match;
    }

    /**
     * Sets the pattern of this matcher.<br>
     *
     * @param pat the pattern to set
     */
    @Override
    public void setPattern(String pat) {
        final String REGEX = "(\\d+)[ ]*(\\d*)";
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(pat);
        if (matcher.find()) {
            // there are regex matches in the provided pattern, pat.
            MatchResult result = matcher.toMatchResult();
            String group_lower = result.group(1);
            String group_upper = result.group(2);

            int lower = Integer.parseInt((group_lower));

            if (!group_upper.equals("")) {
                // two integers are provided
                int upper = Integer.parseInt(group_upper);

                // year can be only between 1900 and 2999
                if (lower < upper && MIN_YEAR <= lower && upper <= MAX_YEAR) {
                    this.lower = lower;
                    this.upper = upper;
                }
            } else {
                // only one integer is provided
                if(MIN_YEAR <= lower && lower <= MAX_YEAR) {
                    this.lower = lower;
                    this.upper = MAX_YEAR;
                } else {
                    this.lower = MIN_YEAR;
                    this.upper = MAX_YEAR;
                }
            }
            //System.out.println("\t" + String.format("Year filter is set to show all tracks between %d-%d.", this.lower, this.upper));
        } else {
            // there are no matches
            System.out.println( "\t+" + "Invalid pattern provided in setPattern() in DurationMatcher!");
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
        return this.lower + " " + this.upper;
    }

    /**
     * the string representation is year in range (RANGE)
     * with range as described in getPattern
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("year in range (%d %d)", this.lower, this.upper);
    }
}
