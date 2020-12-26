package toystore;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.lang3.EnumUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReaderCSV {

    private String fileName;

    /**
     * Parse a stream input that has <em>csv</em> format into a matrix,
     * represented into a {@linkplain List} of lists of strings, strings being
     * fields of csv file
     * @param reader Stream for reading the <em>csv</em> file
     * @return A list of columns, each column being a list of strings,
     *         list of a certain field of a <em>csv</em> file
     * @throws IOException Error while reading
     * @throws CsvValidationException File does not respect csv format
     */
    private List<List<String>> csvToList(Reader reader) throws Exception {
        List<List<String>> list = new ArrayList<>();  // csv file is kept on a list of lists of strings
        CSVReader csvReader = new CSVReader(reader);
        List<String> lineList;
        String[] lineString;
        while ((lineString = csvReader.readNext()) != null) {  // read csv line by line
            lineList = Arrays.asList(lineString);
            list.add(lineList);
        }
        reader.close();
        csvReader.close();
        return transposeLinesAndColumns(list);
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
     * Opens the file with name of the parameter and parses it into a usable format,
     * keeping only the information that we want from the file. Fields that are being kept
     * are listed in the enum {@link FieldsOfInterest}.
     * @param csvFileName String representing the name of the <em>csv</em> file
     * @return {@linkplain List} of lines of csv file (a line is a {@linkplain List} of Strings)
     * @throws Exception Error while reading from the file
     */
    public List<List<String>> readCSV(String csvFileName) throws Exception {
        // opening the stream
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(csvFileName));
        } catch (FileNotFoundException e) {
            System.err.println("Error while opening csv file");
            throw new IOException(e);
        }
        // building the matrix based on the csv file and isolating the error if there is to be one
        List<List<String>> list;
        try {
            list = csvToList(reader);
        } catch (IOException e) {
            System.err.println("Error while reading form the file");
            throw new Exception(e);
        } catch (CsvValidationException e) {
            System.err.println("File may not me in csv format");
            throw new Exception(e);
        }
        keepOnlyRequestedColumns(list);  // parse the matrix and keep only the necessary fields
        return transposeLinesAndColumns(list);  // return it as a list of lines, not columns
    }

}
