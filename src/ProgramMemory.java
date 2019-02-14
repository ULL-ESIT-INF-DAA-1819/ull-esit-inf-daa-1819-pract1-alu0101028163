import java.util.*;
import java.io.*;

/**
*   This class represents the Program Memory for the RAM.
*   @author Adrián Álvarez
*   @version 1.0
*/

class ProgramMemory{

    /**
    *   An ArrayList of Strings where each String represents an instruction.
    */
    private ArrayList<String> prMemory;

    /**
    *   Constructor of the class.
    *   @param  programPath     String that contains the path of the file
    *                           that contains the program.
    */

    public ProgramMemory(String programPath) throws Exception{
        prMemory = new ArrayList<String>();
        loadProgram(programPath);
    }

    /**
    *   It creates a stream buffer from where it reads the program data stored
    *   in a file and then store each individual instruction inside the
    *   ArrayList prMemory.
    *   @param programPath  String that contains the path of the file
    *                           that contains the program.
    *   @throws IllegalArgumentException if the size of the buffer is <= 0.
    */

    public void loadProgram(String programPath) throws Exception{
        /* We open the Stream Buffer.*/
        BufferedReader reader = new BufferedReader(new FileReader(new File(programPath)));
        String programLine = " ";
        /* While we don't reach the EOF,
           we store the data from the file into the ArrayList*/
        while ((programLine = reader.readLine()) != null){
            prMemory.add(programLine);
        }
        /* We close the stream buffer*/
        reader.close();
    }

    /**
    *   It shows the program instructions.
    */

    public void showProgram(){
        for (int i = 0; i < prMemory.size(); i++){
            System.out.println(prMemory.get(i));
        }
    }

    /**
    *   It returns an instruction accesing to it by its index
    *   in the ArrayList.
    *   @param label   integer that represents the index of the
    *                  instruction in the ArrayList.
    *   @return        String that contains the instrucion with
    *                  that specific label.
    */
    public String readInstruction(int label){
        return prMemory.get(label);
    }

    public static void main(String args[]) throws Exception{
        ProgramMemory pm = new ProgramMemory("myProgram.out");
        pm.showProgram();
        System.out.println(pm.readInstruction(0));
    }

}
