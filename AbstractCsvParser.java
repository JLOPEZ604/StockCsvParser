import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;

/**
 * An abstract class for parsing a CSV file. This class is implemented for you.
 * Do not modify.
 */
public abstract class AbstractCsvParser<E> implements AutoCloseable, Iterator<E> {

    private final Scanner input;

    /**
     * Instantiate an instance of CSV Parser.
     *
     * @param file the file to scan
     * @throws FileNotFoundException if source file is not found
     * @throws NullPointerException  if source file is not supplied (null)
     */
    public AbstractCsvParser(File file) throws FileNotFoundException {
        Objects.requireNonNull(file);
        this.input = new Scanner(file);

        // read and throw away (i.e., skip) the first line (the CSV header)
        this.input.nextLine();
    }

    /**
     * Returns true if this parser has another line of input to process.
     */
    @Override
    public boolean hasNext() {
        return this.input.hasNext();
    }

    /**
     * Read the next domain-specific object from the CSV file (i.e. read a row of
     * data).
     */
    @Override
    public E next() {
        final String line = this.input.nextLine();
        final String[] elements = line.split(",");
        try {
            return handleElements(elements);
        } catch (CsvParsingException pe) {
            throw new CsvParsingException("Unable to parse line: " + line, pe);
        }
    }

    /**
     * Given an array of elements parsed from a single line of a CSV file, convert
     * the elements into a domain-specific object. This method never returns a null
     * value. It either returns a completely populated domain-specific object or it
     * throws a CsvParsingException if it is unable to parse the elements into a
     * domain-specific object.
     *
     * @param elements The items parsed from the line of the CSV file
     * @return A domain-specific object
     * @throws CsvParsingException if the file cannot be parsed for any reason
     */
    protected abstract E handleElements(String[] elements) throws CsvParsingException;

    @Override
    public void close() {
        this.input.close();
    }

}
