import java.io.*;

/***
*   This class reads the data from the input tape.
*   @author     Adrián Álvarez
*   @version    1.0
*/

class ReadingHead{

        private BufferedReader reader;

        /**
        *   This is the constructor of the class.
        *   @param inputTape    the name of the file that contains the
        *                       input tape data.
        *   @return             it returns an instance of the class.
        *   @see                loadTape
        */
        public ReadingHead(String inputTape) throws Exception{
            loadTape(inputTape);
        }

        /**
        *   It created a buffer that contains the data from the
        *   input tape (which is a file) and allows us to read it.
        *   @param  inputTape   the name of the file that contains the
        *                       input tape data.
        *   @throws IllegalArgumentException if the size of the buffer is <= 0.
        */

        public void loadTape(String inputTape) throws Exception{
            File tape = new File(inputTape);
            reader = new BufferedReader(new FileReader(tape));
        }

        /**
        *   It reads the next string of the file which is delimited by
        *   by any one of a line feed ('\n'), a carriage return ('\r'),
        *   or a carriage return followed immediately by a linefeed.
        *   @return         the next line of text.
        *   @return         null if the buffer reachs the end of file.
        *   @throws         IOException if and I/O error occurs.
        */

        public String readSymbol() throws Exception {
            return reader.readLine();
        }

        /**
        *   It closes the buffer.
        *   @throws         IOException if and I/O error occurs.
        */

        public void closeTape() throws Exception {
            reader.close();
        }

        public static void main(String args[]) throws Exception{
            ReadingHead rh = new ReadingHead("inputFile.txt");
            for (int i = 0; i < 10; i++){
                System.out.println(rh.readSymbol());
            }
        }

}