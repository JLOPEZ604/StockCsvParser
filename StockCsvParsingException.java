public class StockCsvParsingException extends CsvParsingException {

    public StockCsvParsingException(String ticker) {
        super(ticker + ": Error parsing information from CSV file");
        }

    public StockCsvParsingException(String ticker, String message) {
         super(ticker + message);
    }
}
