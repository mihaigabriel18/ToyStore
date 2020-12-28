package toystore;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.lang3.EnumUtils;

import java.io.*;
import java.util.*;

public class ParserCSV {

    /**
     * String representing the name of the <em>csv</em> file
     */
    private final String fileName;

    public ParserCSV(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Parse a stream input that has <em>csv</em> format into a matrix,
     * represented into a {@linkplain List} of lists of strings, strings being
     * fields of csv file. For every line read, keep the id in a hashmap, if the
     * new uniq_id is already in the map, skip this line because we have already read
     * this object.
     * @param reader Stream for reading the <em>csv</em> file
     * @return A list of columns, each column being a list of strings,
     *         list of a certain field of a <em>csv</em> file
     * @throws IOException Error while reading
     * @throws CsvValidationException File does not respect csv format
     */
    private List<List<String>> csvToList(Reader reader) throws IOException, CsvValidationException {
        List<List<String>> listCSV = new ArrayList<>();  // csv file is kept on a list of lists of strings
        CSVReader csvReader = new CSVReader(reader);
        Map<String, Boolean> uniqIds = new HashMap<>();  // map of id's
        List<String> lineList;
        String[] lineString;
        while ((lineString = csvReader.readNext()) != null) {  // read csv line by line
            lineList = Arrays.asList(lineString);
            int ordinal = FieldsOfInterest.uniq_id.ordinal();
            if (uniqIds.containsKey(lineList.get(ordinal)))
                continue;
            uniqIds.put(lineList.get(ordinal), true);
            listCSV.add(lineList);
        }
        reader.close();
        csvReader.close();
        return transposeLinesAndColumns(listCSV);
    }

    /**
     * Considering the input a matrix, this function transposes the matrix. In other
     * words if the input is a list of columns, the function returns a list of lines;
     * and for a list of lines, it returns a list of columns
     * @param initial {@linkplain List} of lists of strings in a matrix format,
     *        either a list of lines, or a list of columns
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

    /**
     * Remove all columns that are not a member of {@link FieldsOfInterest}
     * enum, to make a field visible in the in final list, add a new entry in the
     * specified enum
     * @param list {@linkplain List} of columns (which are lists of strings)
     */
    private void keepOnlyRequestedColumns(List<List<String>> list) {
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
     * Opens the file with name of the parameter and parses it into a usable format,
     * keeping only the information that we want from the file. Fields that are being kept
     * are listed in the enum {@link FieldsOfInterest}.
     * @return {@linkplain List} of lines of csv file (a line is a {@linkplain List} of Strings)
     * @throws CsvReadingException Error while reading from the file
     */
     public List<List<String>> readCSV() throws CsvReadingException {
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
        } catch (CsvValidationException e) {
            throw new CsvReadingException("File may not respect csv format", e);
        }
        keepOnlyRequestedColumns(listCSV);  // parse the matrix and keep only the necessary fields
        rearrangeInOrder(listCSV);
        parseNumberInStock(listCSV.get(FieldsOfInterest.number_available_in_stock.ordinal()));
        return transposeLinesAndColumns(listCSV);  // return it as a list of lines, not columns
    }

}
