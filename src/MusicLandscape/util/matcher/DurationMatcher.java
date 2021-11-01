package MusicLandscape.util.matcher;

import MusicLandscape.entities.Track;
import MusicLandscape.util.MyMatcher;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Encapsulates the concept of matching a track based on its duration.
 * This class is used to test whether given a given track's duration lies in a certain range, the range being the pattern of this matcher.
 * The pattern is a simple string consisting of the (white-space separated) lower and upper bounds (inclusive) of the range of duration s (in seconds) accepted by this matcher.
 * More precisely, a valid pattern is a String that can be interpreted as
 * either a single integer number (leading and trailing whitespace are ignored, if present) which then represents the lower bound
 * or two integer numbers, separated by (any number of) whitespace, which then represent lower and upper bound.
 * The bounds are understood to be inclusive.
 */
public class DurationMatcher extends MyMatcher<Track> {

    /**
     * the lower bound of the accepted range.
     */
    private int lower;

    /**
     * the upper bound of the accepted range.
     */
    private int upper;

    /**
     * Creates a default duration matcher.
     * By default a matcher matches any duration, including unknown duration.
     */
    public DurationMatcher() {

        super("0 " + Integer.MAX_VALUE); // the regex pattern matches a zero
    }

    /**
     * Creates a Matcher object with a specified pattern.<br>
     *
     * @param pat the pattern of this matcher
     */
    public DurationMatcher(String pat) {
        super(pat);
    }

    /**
     * Sets the pattern of this matcher.
     * Interprets the argument as described in the class documentation.
     * First sets the lower, then the upper bound.
     * The bounds specified are set if and only if at the time of setting they are actually lower
     * (for the lower bound) or higher (for the upper bound) than the other or at least equal to
     * the other.
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

            if (!group_upper.equals("") && lower <= Integer.parseInt(group_upper)) {
                // two integers are provided
                int upper = Integer.parseInt(group_upper);

                if (lower == upper && upper == 0) {
                    this.lower = lower;
                    this.upper = upper;
                } else if (lower == upper && upper > 0) {
                    this.lower = lower;
                    this.upper = upper;
                } else if (lower < upper) {
                    this.lower = lower;
                    this.upper = upper;
                }
//                else if (lower > upper) {
//                    // to deal with different order of pattern. smaller number is lower.
//                    this.lower = upper;
//                    this.upper = lower;
//                }
//            else {
//                    // lower and upper are equal
//                    this.lower = lower;
//                    this.upper = upper;
//                }
            } else {
                // one integer is provided, set only lower, upper remains 0
                this.lower = lower;
            }
        } else {
            // there are no matches
            System.out.println("Invalid pattern provided in setPattern() in DurationMatcher!");
        }
    }

    /**
     * Gets the pattern of this matcher.<br>
     * <p>
     * the valid pattern is
     * LOWER UPPER
     * separated by whitespace.
     *
     * @return the pattern
     */
    @Override
    public String getPattern() {
        return lower + " " + upper;
    }

    /**
     * A track matches if its duration is in the range accepted by this matcher.
     *
     * @param track the object to match
     * @return whether t matches the pattern of this matcher.
     */
    @Override
    public boolean matches(Track track) {
        int duration = track.getDuration();
        boolean is_match = false;

        if (this.lower >= 0 && this.upper == Integer.MAX_VALUE) {
            // only one bound was provided or set, the lower
            if (this.lower <= duration) {
                is_match = true;
            }
        } else if (0 <= this.lower && this.lower <= this.upper) {
            // two bounds were provided
            if (this.lower <= duration && duration <= this.upper) {
                is_match = true;
            }
        }

        return is_match;
    }

    /**
     * the string representation is duration in range (RANGE)
     * with range as described in getPattern
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("duration in range (%d %d)", this.lower, this.upper);
    }
}
