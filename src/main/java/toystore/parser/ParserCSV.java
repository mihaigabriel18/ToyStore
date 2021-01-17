package toystore.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.EnumUtils;

import java.io.*;
import java.util.*;

/**
 * Includes 2 public methods for writing and reading to/from a csv file, storing the csv
 * as a {@link List} of lists of {@link String} objects. The parser uses information from
 * {@link FieldsOfInterest} for sorting the fields so that it respects the order in the enum,
 * removing all fields which headers are not in the enumeration.
 * The class has 2 exceptions associated to it: {@link CsvReadingException} and {@link CsvWritingException}
 * used as unchecked wrapper exception, so that we don't have to propagate multiple checked exceptions
 * throughout the whole package, they are easy to read by end-users.
 */

public class ParserCSV {

    /**
     * This is a constant of the class, used in many methods, it is defined as the ordinal of <em>price</em>
     * in {@link FieldsOfInterest}
     */
    private static final int PRICE_ORDINAL = EnumUtils.getEnum(FieldsOfInterest.class, "price").ordinal();

    /**
     * Opens the file with name of the parameter and parses it into a usable format,
     * keeping only the information that we want from the file. Fields that are being kept
     * are listed in the enum {@link FieldsOfInterest}.
     * @return {@linkplain List} of lines of csv file (a line is a {@linkplain List} of Strings)
     * @throws CsvReadingException If any errors appear while reading to the csv file
     */
    public List<List<String>> readCSV(String fileName) {
        // opening the stream
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new CsvReadingException("Error while reading", e);
        }
        // building the matrix based on the csv file and isolating the error if there is to be one
        List<List<String>> listCSV;
        try {
            listCSV = csvToList(reader);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CsvReadingException("Error while reading", e);
        }
        keepOnlyRequestedColumns(listCSV);  // parse the matrix and keep only the necessary fields
        rearrangeInOrder(listCSV);          // respect order of enum
        // next instruction reformat the file making it the way it is intended
        parseNumberInStock(listCSV.get(FieldsOfInterest.number_available_in_stock.ordinal()));
        List<List<String>> finalList = transposeLinesAndColumns(listCSV);
        removeHeaderOfCsv(finalList);
        return finalList;  // return it as a list of lines, not columns
    }

    /**
     * Parse a stream input that has <em>csv</em> format into a matrix,
     * represented into a {@linkplain List} of lists of strings, strings being
     * fields of csv file. For every line read, keep the id in a hashmap, if the
     * new uniq_id is already in the map, skip this line because we have already read
     * this object.
     * @param reader Stream for reading the <em>csv</em> file
     * @return A list of columns, each column being a list of strings,
     * list of a certain field of a <em>csv</em> file
     * @throws IOException Error while reading
     */
    private List<List<String>> csvToList(Reader reader) throws IOException {
        List<List<String>> listCSV = new ArrayList<>();  // csv file is kept on a list of lists of strings
        CSVParser records = new CSVParser(reader, CSVFormat.DEFAULT
                .withIgnoreHeaderCase());
        for (CSVRecord record : records) {
            List<String> lineList = new ArrayList<>();
            for (int i = 0; i < FieldsOfInterest.values().length; i++)
                lineList.add(record.get(i));
            // skip line that have the price field empty
            if ("".equals(lineList.get(PRICE_ORDINAL)))
                continue;
            // reformat csv line to satisfy our needs
            removeCommaFromPrice(lineList);
            replaceEmptyWithZero(lineList);
            deleteAfterWhiteSpace(lineList);
            addNotAvailableToManufacturer(lineList);
            listCSV.add(lineList);
        }
        reader.close();
        return transposeLinesAndColumns(listCSV);
    }

    /**
     * Remove commas from the price field so that the parsing will not throw {@link NumberFormatException}
     * @param line line of csv file
     */
    private void removeCommaFromPrice(List<String> line) {
        int ordinal = PRICE_ORDINAL;
        line.set(ordinal, line.get(ordinal).replace(",", ""));
    }

    /**
     * Replace empty String in field that holds <em>number_available_in_stock</em>
     * with <em>0 new</em>
     * @param line line of csv file with a product characteristics
     */
    private void replaceEmptyWithZero(List<String> line) {
        int ordinal = EnumUtils.getEnum(FieldsOfInterest.class, "number_available_in_stock").ordinal();
        if ("".equals(line.get(ordinal)))
            line.set(ordinal, "0 new");
    }

    /**
     * For prices that don't respect the format <em>symbol</em> + <em>value</em>,
     * delete the surplus of characters after a space or non-breaking space (in the field for <em>price</em>)
     * @param line line of csv file with a product characteristics
     */
    private void deleteAfterWhiteSpace(List<String> line) {
        int ordinal = PRICE_ORDINAL;
        line.set(ordinal, line.get(ordinal).split("[ \u00A0]")[0]);
    }

    /**
     * For products that do not have a manufacturer specified write <em>Not Available</em>
     * in that place.
     * @param line line of csv file with a product characteristics
     */
    private void addNotAvailableToManufacturer(List<String> line) {
        int ordinal = EnumUtils.getEnum(FieldsOfInterest.class, "manufacturer").ordinal();
        if ("".equals(line.get(ordinal)))
            line.set(ordinal, "Not Available");
    }

    /**
     * Removes the trailing "new" string after the number of new products, or whatever
     * string is appended to the to the word. The only condition is that the number of
     * new products is the first one in the line and separated by either a space or a
     * non-breaking space from the rest of the words.
     * @param numberInStock column with all of the numbers of new products
     */
    private void parseNumberInStock(List<String> numberInStock) {
        for (int index = 1; index < numberInStock.size(); index++) {
            if (numberInStock.get(index).isEmpty())
                numberInStock.set(index, "0");
            else
                // match either a space or a non-breaking space
                numberInStock.set(index, numberInStock.get(index).split("[\u00A0 ]")[0]);
        }
    }

    /**
     * If first line of csv list has header format, delete it
     * format checking is matching first element with values of {@link FieldsOfInterest} enum
     * @param listCsv list of lists of csv values
     */
    private void removeHeaderOfCsv(List<List<String>> listCsv) {
        if (Arrays.stream(FieldsOfInterest.values()).anyMatch(field -> listCsv.get(0).get(0).equals(field.name()))) {
            listCsv.remove(0);
        }
    }

    /**
     * Remove all columns that are not a member of {@link FieldsOfInterest}
     * enum, to make a field visible in the in final list, add a new entry in the
     * specified enum
     * @param list {@linkplain List} of columns (which are lists of strings)
     */
    private void keepOnlyRequestedColumns(List<List<String>> list) {
        // remove if header string is not found in the enum
        list.removeIf(strings -> !EnumUtils.isValidEnum(FieldsOfInterest.class, strings.get(0)));
    }

    /**
     * Swaps the columns around so that the order of them respect the order of enum items
     * in the {@link FieldsOfInterest} enumeration. To get the columns in a specific order
     * modify the order of entries in the enum linked above. First enumeration value will
     * represent the first column of the matrix
     * @param list list of columns
     */
    private void rearrangeInOrder(List<List<String>> list) {
        List<FieldsOfInterest> enumValues = EnumUtils.getEnumList(FieldsOfInterest.class);
        for (int indexValues = 0; indexValues < enumValues.size(); indexValues++)
            for (int indexList = indexValues; indexList < list.size(); indexList++)
                if (list.get(indexList).get(0).equals(enumValues.get(indexValues).name())) {
                    // swap the lists
                    List<String> aux = list.get(indexValues);
                    list.set(indexValues, list.get(indexList));
                    list.set(indexList, aux);
                    break;
                }
    }

    /**
     * Writes the formatted list to a csv file, with a default header, with the specific order
     * of {@link FieldsOfInterest} enumeration, separated by commas
     * @param listCSV list of lists of fields for a csv file
     * @param fileName name of csv file we writing to
     * @throws CsvWritingException If any errors appear while writing to the csv file
     */
    public void writeCSV(List<List<String>> listCSV, String fileName) {
        try (   // opening the streams and reader with predefined header
                CSVPrinter csvPrinter = new CSVPrinter( new FileWriter(fileName), CSVFormat.DEFAULT
                        .withHeader(Arrays.stream(FieldsOfInterest.values()).map(Enum::name).toArray(String[]::new)))

        ) {
            // print every line
            for (List<String> line : listCSV) {
                csvPrinter.printRecord(line.toArray());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new CsvWritingException("Error while writing", e);
        }
    }

    /**
     * Considering the input a matrix, this function transposes the matrix. In other
     * words if the input is a list of columns, the function returns a list of lines;
     * and for a list of lines, it returns a list of columns
     * @param initial {@linkplain List} of lists of strings in a matrix format,
     *                either a list of lines, or a list of columns
     * @return {@linkplain List} of lists of strings in a matrix format
     */
    private List<List<String>> transposeLinesAndColumns(List<List<String>> initial) {
        int secondarySize = initial.get(0).size();  // number of columns in "initial"
        List<List<String>> transposed = new ArrayList<>(secondarySize);
        // iterate for every column
        for (int indexTransposed = 0; indexTransposed < secondarySize; indexTransposed++) {
            // line in the new transposed matrix
            List<String> lineTransposed = new ArrayList<>(initial.size());
            for (List<String> lineInitial : initial) {  // for every line in the initial matrix
                // append to transposed matrix the respective element from the initial matrix
                lineTransposed.add(lineInitial.get(indexTransposed));
            }
            transposed.add(lineTransposed);
        }
        return transposed;
    }
}
