import java.io.*;

/***
*   This class writes the data from the program execution
*   into the output tape.
*   @author     Adrián Álvarez
*   @version    1.0
*/

public class WritingHead{

    private BufferedWriter writer;

    /**
    *   This is the default constructor of the class.
    *   @param outputTape   path of the output file where we're
    *                       going to write the data.
    *   @see   loadTape
    */
    public WritingHead(String outputTape) throws Exception{
        loadTape(outputTape);
    }

    /**
    *   It creates a buffer stream for writing into the output file.
    *   @param  outputTape   path of the output file where we're
    *                       going to write the data.
    *   @throws IllegalArgumentException - If sz is <= 0
    */
    public void loadTape(String outputTape) throws Exception{
        writer = new BufferedWriter(new FileWriter(outputTape));
    }

    /**
    *   It converts an Integer into a String and write it
    *   into the output file.
    *   @param  symbol       Integer to be written in the output file.
    *   @throws IOException  If an I/O error occurs
    */
    public void writeSymbol(Integer symbol) throws Exception{
        writer.write(symbol.toString());
    }

    /**
    * It closes the stream after flushing it.
    * @throws  IOException  If an I/O error occurs
    */
    public void closeTape() throws Exception{
        writer.close();
    }

    public static void main(String args[]) throws Exception{
        WritingHead wh = new WritingHead("outputTape.txt");
        wh.writeSymbol(new Integer(20));
        wh.closeTape();
    }


}
