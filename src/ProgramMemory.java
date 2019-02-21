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
    private ArrayList<Instruction> prMemory;
    private static final String[] INSTRUCTION_VALUES = new String[]  {"LOAD", "STORE", "ADD", "SUB", "MUL", "DIV", "READ",
                                                              "WRITE", "JUMP", "JZERO", "JGTZ", "HALT"};
    private static final Set<String> instructionSet = new HashSet<>(Arrays.asList(INSTRUCTION_VALUES));

    /**
    *   Constructor of the class.
    *   @param  programPath     String that contains the path of the file
    *                           that contains the program.
    */

    public ProgramMemory(String programPath) throws Exception{
        prMemory = new ArrayList<Instruction>();
        loadProgram(programPath);
    }

    public boolean isInstruction(String instr){
      for (String str : INSTRUCTION_VALUES){
        if (str.equals(instr))
           return true;
      }
      return false;
    }

    public Instruction parseInstruction(String instruction){
      boolean isJump = false;
      String label = null;
      String body = null;
      String operand = null;
      Integer value = null;

      String[] parsedInstr = instruction.split(":");

      int index = 0;

      if (parsedInstr.length == 2){ // Posee una etiqueta.
        label = parsedInstr[0];
        index++;
      }

      String[] parsedOperand;
      if (parsedInstr[index].split(" ").length == 2){
        parsedOperand = parsedInstr[index].split("\\s+");
        operand = " ";
      }else if (parsedInstr[index].split("*").length == 2){
        operand = "*";
        parsedOperand = parsedInstr[index].split("*");
      }else if (parsedInstr[index].split("=").length == 2){
        operand = "=";
        parsedOperand = parsedInstr[index].split("=");
      }else{
        throw new AssertionError("No symbol or space between instruction and operand");
      }

      if (isInstruction(parsedOperand[0])){
        body = parsedOperand[0];
      }else{
        throw new AssertionError("No instruction");
      }

      if (isInstruction(parsedOperand[1])){
        value = Integer.parseInt(parsedOperand[1]);
      }else{
        throw new AssertionError("No operand");
      }

      if (body.equals("JZERO") || body.equals("JUMP")){
          isJump = true;
      }

      return new Instruction(isJump, label, body, operand, value);



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
            prMemory.add(parseInstruction(programLine));
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
    public Instruction readInstruction(int label){
        return prMemory.get(label);
    }

    public static void main(String args[]) throws Exception{
        ProgramMemory pm = new ProgramMemory("myProgram.out");
        pm.showProgram();
        //System.out.println(pm.readInstruction(0));
    }

}
