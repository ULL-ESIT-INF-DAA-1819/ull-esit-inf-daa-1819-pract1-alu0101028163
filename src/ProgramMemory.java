import java.util.*;
import java.io.*;
import  javafx.util.*;
import java.util.regex.*;

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

    /**
     * Checks if an instrucion belongs to the machine's instruction set.
     * @param  instr string that contains the instruction
     * @return       true if it belongs, false if it doesn't.
     */
    public boolean isInstruction(String instr){
      for (String str : INSTRUCTION_VALUES){
        if (str.equals(instr.toUpperCase()))
           return true;
      }
      return false;
    }

    /**
    *   It parses the input program into tokens and store them
    *   as instructions in memory.
    *   @param instruction  string containing one instruction
    *   @return             an Instruction instance.
    */

    public Instruction parseInstruction(String instruction) throws Exception{
      boolean isJump = false;
      String label = null;
      String body = null;
      String operand = null;
      String value = null;

      String regex1 = "^([a-zA-Z]{1,}:)\\s{0,}([a-zA-Z]{1,})(\\*|\\s|=)([+|-]?[0-9]+)"; // Con etiqueta
      String regex2 = "^(([a-zA-Z]{1,})(\\*|\\s|=)([+|-]?[0-9]+))"; // Sin etiqueta
      String regex3 = "^(([a-zA-Z]{1,})\\s([a-zA-Z]{1,}))"; // Con salto
      String regex4 = "^[H|h][A|a][L|l][T|t]$"; // HALT

      /**
       * This would parse a non-jump insruction with a label.
       */
      Pattern pattern = Pattern.compile(regex1);
      Matcher matcher = pattern.matcher(instruction);

      if (matcher.find()){
        label = matcher.group(1);
        label = label.substring(0, label.length() - 1);
        body = matcher.group(2).toUpperCase();
        operand = matcher.group(3);
        value = matcher.group(4);
        return new Instruction(isJump, label, body, operand, value);
      }

      /**
       * This would parse a non-jump instruction without a label.
       */
      pattern = Pattern.compile(regex2);
      matcher = pattern.matcher(instruction);

      if (matcher.find()){
        body = matcher.group(2).toUpperCase();
        operand = matcher.group(3);
        value = matcher.group(4);
        if(isInstruction(body)){
          return new Instruction(isJump, label, body, operand, value);
        }else{
          throw new AssertionError("Invalid Instruction: " + instruction);
        }
      }

      /**
       * This would parse a jump instruction.
       */


      pattern = Pattern.compile(regex3);
      matcher = pattern.matcher(instruction);

      if (matcher.find()){
        body = matcher.group(2).toUpperCase();
        operand = " ";
        isJump = true;
        value = matcher.group(3);
        if(isInstruction(body)){
          return new Instruction(isJump, label, body, operand, value);
        }else{
          throw new AssertionError("Invalid Instruction: " + instruction);
        }
      }

      /**
       * This would parse a HALT instruction.
       */


      pattern = Pattern.compile(regex4);
      matcher = pattern.matcher(instruction);

      if (matcher.find()){
        body = matcher.group(0).toUpperCase();
        operand = " ";
        isJump = true;
        value = "-1";
        if(isInstruction(body)){
          return new Instruction(isJump, label, body, operand, value);
        }else{
          return null;
        }
      }

      throw new AssertionError("Invalid Instruction: " + instruction);

    }


    /**
    *   This function replaces the Labels strings in the jump
    *   instructions with numbers.
    */

    public void insertLabels(){
      ArrayList< Pair<Integer,String> > myLabels = new ArrayList< Pair<Integer,String> >();

      for (int i = 0; i < prMemory.size(); i++){
        if (prMemory.get(i).getLabel() != null){
          Pair<Integer,String> pair = new Pair<Integer,String>(new Integer(i), prMemory.get(i).getLabel());
          myLabels.add(pair);
        }
      }

      for (int i = 0; i < prMemory.size(); i++){
        if (prMemory.get(i).isJumpInstruction()){
          for (int j = 0; j < myLabels.size(); j++){
            if (prMemory.get(i).getValue().equals(myLabels.get(j).getValue())){
                prMemory.get(i).setValue(Integer.toString(myLabels.get(j).getKey()));
            }
          }
        }
      }

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
        Integer line = new Integer(0);
        while ((programLine = reader.readLine()) != null){
            Instruction instr = parseInstruction(programLine);
            if (instr != null){
              instr.setLine(line);
              prMemory.add(instr);
            }
            line++;
        }
        insertLabels();
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
     * Returns the instruction located in the position of the instrPointer.
     * @param  instrPointer  index of the position in the array from where
     *                       we want to retrieve the instruction.
     * @return               the instruction.
     */
    public Instruction getInstruction(int instrPointer){
      return prMemory.get(instrPointer);
    }

    /**
    *   It returns an instruction accesing to it by its index
    *   in the ArrayList.
    *   @param position  integer that represents the index of the
    *                    instruction in the ArrayList.
    *   @return          String that contains the instrucion with
    *                    that specific label.
    */
    public Instruction readInstruction(int position){
        return prMemory.get(position);
    }

    public String toString(){
      String str = "\n PROGRAM MEMORY\n";
      for (int i = 0; i < prMemory.size(); i++){
          str += "  " + prMemory.get(i).toString() + "\n";
      }
      return str;
    }

    /**
     * Getter of the program memory's size.
     * @return  the size of the array that contains the program instructions.
     */
    public int getSize(){
      return prMemory.size();
    }

    public static void main(String args[]) throws Exception{
        ProgramMemory pm = new ProgramMemory("myProgram.out");
        pm.insertLabels();
        System.out.println(pm);
        //Instruction instr = pm.getInstruction(0);
        //System.out.println(instr);
        //System.out.println(pm.readInstruction(0));
    }

}
