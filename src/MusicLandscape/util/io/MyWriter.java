package MusicLandscape.util.io;

import MusicLandscape.util.MyFormatter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Generic class for writing objects to files in a specific format.
 */
public class MyWriter<T> {

    /**
     * The underlying stream.
     * Cannot be null.
     */
    protected FileWriter out;

    /**
     * The format in which data is written to file.
     * Cannot be null;
     */
    private MyFormatter<T> theFormat;



    /**
     * Constructs a MyWriter with a specific target file and format.
     * In case null objects are passed to this constructor IllegalArgumentException is thrown.
     *
     * @param file      the file to which to save the data.
     * @param theFormat the format in which to store the data.
     */
    public MyWriter(java.io.FileWriter file, MyFormatter<T> theFormat) {
        if (file == null || theFormat == null) {
            if (file == null)
                throw new IllegalArgumentException("expected non-null FileWriter");
            else if (theFormat == null)
                throw new IllegalArgumentException("expected non-null MyFormatter");
        } else {
            this.out = file;
            this.theFormat = theFormat;
        }
    }

    /**
     * Closes the underlying stream.
     * All exceptions are ducked.
     *
     * @throws IOException the exception thrown by closing the underlying stream
     */
    public void close() throws IOException {
        this.out.close();
    }

    /**
     * Writes a single object to the underlying file.
     * The object passed to this method is written to file in the format of this MyWriter.
     * A newline character is appended at the end of data. This method handles all IOExceptions
     * that might occur and returns false in such a case.
     *
     * @param t the object to be written to file
     * @return true if the object was written to file successfully, false otherwise.
     */
    public final boolean put(T t) {
        //TODO maybe t needs to get checked for type?
        try {
//            this.out.write(this.theFormat.header());
//            this.out.write(this.theFormat.topSeparator());
            this.out.write(this.theFormat.format(t));
            this.out.write("\n");

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
