import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class TestStockCsvParser {
    public static void main(String[] args) throws Exception {
                String relativePath = new File("").getAbsolutePath();
                File file = new File(relativePath + "\\CSV_Files\\CLDR.csv"); //Generates the working file for unit testing

                testWorkingFile(file, "CLDR"); // This Unit Test should return empty when called

                testNpeTicker(file); // This unit test should throw a null pointer exception for the ticker

                testNpeFile(); // This should throw a null pointer exception for the file

                testFileNotFound();

                testBrokenFile(); // This takes in the CLDR.broken file and should throw an exception

                testUnparseableFile(); // In the custom created CLDR.Unparse there are multiple lines that should not be parsed



    }

    public static void testWorkingFile(File file, String ticker) {
        try {
            StockCsvParser CsvParser = new StockCsvParser(file, ticker);
            float max = 0;
            float min = 9.34f;
            int volume = 0;

            while (CsvParser.hasNext()){
                StockQuote sq = CsvParser.next();
                max = max(max, sq.getHigh());
                min = min(min, sq.getLow());
                volume += sq.getVolume();
            }

            if (min != 9.34f) {
                System.out.println("The lowest the stock went was 9.34");
                System.out.println(min);
            }

            if (max != 19.35f) {
                System.out.println("The highest the stock went was 19.35");
                System.out.println(max);
            }

            volume = volume / 252;
            if (volume != 5275629) {
                System.out.println("The average volume over the period was 5275629");
                System.out.println(volume);
            }

            CsvParser.close();
        }

        catch (FileNotFoundException e) {
            System.out.println("Error in searching for file");
        } catch (StockCsvParsingException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.print("Unknown error: " + e.getMessage());
        }

    }

    public static void testNpeTicker(File file) {
        try{
            StockCsvParser CsvParser = new StockCsvParser(file, null);
            System.out.println("This should not pass through the parser constructor");
            CsvParser.close();
        } catch (NullPointerException | StockCsvParsingException | FileNotFoundException e) {
            // ignore
        } catch (Exception e) {
            System.out.println("Unexpected Error");
        }
    }

    public static void testNpeFile() {
        try {
            StockCsvParser CsvParser = new StockCsvParser(null, "CLDR");
            System.out.println("This should not pass through the parser constructor");
            CsvParser.close();
        } catch (StockCsvParsingException | FileNotFoundException | NullPointerException e) {
            // ignore
        } catch (Exception e) {
            System.out.println("Unexpected Error");
        }
    }

    public static void testFileNotFound() {
        String path = new File("").getAbsolutePath();
        File testFile = new File(path + "\\CSV_Files\\CLSR.csv");
        try {
            StockCsvParser CsvParser = new StockCsvParser(testFile, "CLDR");
            System.out.println("This file is incorrect and should not pass");
            CsvParser.close();
        } catch (FileNotFoundException e) {
            // ignore
        } catch (Exception e) {
            System.out.println("Unexpected Error");
        }
    }

    public static void testBrokenFile() {
        String path = new File("").getAbsolutePath();
        File brokenFile = new File(path + "\\CSV_Files\\CLDR.broken.csv");

        try {
            StockCsvParser CsvParser = new StockCsvParser(brokenFile, "CLDR");
            while (CsvParser.hasNext()) {
                StockQuote sq = CsvParser.next();
            }
            System.out.println("This file is broken and should not have been accepted");
            CsvParser.close();

        } catch (CsvParsingException e) {
            // ignore
        } catch (FileNotFoundException e) {
            System.out.println("Unable to find designated file: " + e.getCause());
        } catch (Exception e) {
            System.out.println("Threw unexpected error");
        }
    }

    public static void testUnparseableFile() {
        String path = new File("").getAbsolutePath();
        File brokenFile = new File(path + "\\CSV_Files\\CLDR.Unparse.csv");

        try {
            StockCsvParser CsvParser = new StockCsvParser(brokenFile, "CLDR");
            List<StockQuote> stockQuoteList = new ArrayList<>();
            while (CsvParser.hasNext()) {
                stockQuoteList.add(CsvParser.next());
            }
            System.out.println("This contains elements that should not be parsed and should not have been accepted");
            CsvParser.close();

        } catch (CsvParsingException e) {
            // ignore
        } catch (FileNotFoundException e) {
            System.out.println("Unable to find designated file: " + e.getCause());
        } catch (Exception e) {
            System.out.println("Threw unexpected error");
        }
    }
}
