import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

public class StockCsvParser extends AbstractCsvParser <StockQuote> {

    private String ticker;
    /**
     * Instantiate an instance of CSV Parser.
     *
     * @param file the file to scan
     * @throws FileNotFoundException if source file is not found
     * @throws NullPointerException  if ticker is not supplied (null)
     */
    public StockCsvParser(File file, String ticker) throws FileNotFoundException {
        super(file);
            Objects.requireNonNull(ticker); // requires the ticker to not be null or else throw NullPointerException
            this.ticker = ticker;
            if (!file.canRead()) {
                throw new FileNotFoundException("The file cannot be found.");
            }
    }

//    public StockQuote next() {
//        super.next();
//        return null;
//    }

    @Override
    protected StockQuote handleElements(String[] elements) throws CsvParsingException {

        try {
            if (elements.length != 7){
                throw new StockCsvParsingException(ticker, "Missing elements encountered when parsed");
            } else {
                StockQuote stockQuote = new StockQuote();
                stockQuote.setDate(elements[0]);
                stockQuote.setOpen(Float.parseFloat(elements[1]));
                stockQuote.setHigh(Float.parseFloat(elements[2]));
                stockQuote.setLow(Float.parseFloat(elements[3]));
                stockQuote.setClose(Float.parseFloat(elements[4]));
                stockQuote.setAdjClose(Float.parseFloat(elements[5]));
                stockQuote.setVolume(Integer.parseInt(elements[6]));
                return stockQuote;
            }

        } catch (StockCsvParsingException | NumberFormatException ex) {
            throw new CsvParsingException(ticker, ex);
        }
    }


    public boolean hasNext() {
        return super.hasNext();
    }

    @Override
    public void remove() {
        super.remove();
    }

    public void close() {
        super.close();
    }
}
