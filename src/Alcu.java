import java.util.*;
import java.io.*;
import  javafx.util.*;

public class Alcu{

    private String inputTape;
    private String outputTape;
    private ProgramMemory programMemory;
    private DataMemory dataMemory;
    private ReadingHead readingHead;
    private WritingHead writingHead;
    private int         instrPointer;
    private int         debugMode;

    /**
     * Constructor of the Arithmetic Logic and Control Unit.
     * @param  programPath  the path of the file that contains the
     *                      program instructions
     * @param  inputTape    the path of the file that contains the
     *                      input-tape's content
     * @param  outputTape   the path of the file that contains the
     *                      output-tape's content
     * @param  debugMode    activated in case is 1. Prints the machine
     *                      content during each instruction execution.
     * @throws Exception
     */
    public Alcu(String programPath, String inputTape, String outputTape, int debugMode) throws Exception{
        programMemory = new ProgramMemory(programPath);
        dataMemory = new DataMemory();
        this.inputTape = inputTape;
        this.outputTape = outputTape;
        this.debugMode = debugMode;
        readingHead = new ReadingHead(inputTape);
        writingHead = new WritingHead(outputTape);
        instrPointer = 0;
    }

    /**
     * Reads the next symbol from the input tape.
     * @return the actual integer to be readed.
     * @throws Exception
     */
    public Integer readHead() throws Exception{
      String inputSymbol = readingHead.readSymbol();
      return Integer.parseInt(inputSymbol);
    }

    /**
     * Writes a symbol into the output tape.
     * @param  symbol    to be written into the output tape
     * @throws Exception
     */
    public void writeHead(Integer symbol) throws Exception{
      writingHead.writeSymbol(symbol);
    }

    /**
     * It loads a value into the machine's accumulator.
     * @param  operand   it specifies the type of addressing.
     *                  "=" loads the value directly into the acc.
     *                  " " loads the value contained in the
     *                  register with the number specified by the
     *                  following parameter.
     *                  "*" loads the value contained in the register
     *                  that's contained in the register specified
     *                  by the following parameter.
     * @param  value     integer to hold by the accumulator
     * @throws IllegalArgumentException   if the operand doesn't exist.
     */
    public void load(String operand, Integer value) throws Exception{
        if (operand.equals("=")){
          dataMemory.setData(0, value);
        }else if (operand.equals(" ")){
          Integer newValue = dataMemory.getData(value);
          dataMemory.setData(0, newValue);
        }else if (operand.equals("*")){
          Integer newDirection = dataMemory.getData(value);
          Integer newValue = dataMemory.getData(newDirection);
          dataMemory.setData(0, newValue);
        }else{
          throw new IllegalArgumentException("No valid operand");
        }
    }

    /**
     * Stores the content of the accumulator inside another register.
     * @param  operand   it specifies the type of addressing.
     *                   " " stores the value directly into the
     *                   register specified by the following parameter.
     *                   "*" same as above but with indirect adressing.
     * @param  direction of the register where the value will be placed
     *                   or is contained the register to hold the value.
     * @throws IllegalArgumentException   if the operand doesn't exist.
     */
    public void store(String operand, Integer direction) throws Exception {
      if (operand.equals(" ")){
        Integer value = dataMemory.getData(0);
        dataMemory.setData(direction, value);
      }else if (operand.equals("*")){
        Integer value = dataMemory.getData(0);
        Integer newDirection = dataMemory.getData(direction);
        dataMemory.setData(newDirection, value);
      }else {
        throw new IllegalArgumentException("No valid operand");
      }
    }

    /**
     * Adds the content of the accumulator with another value
     * that can be directly passed as argument or allocated in
     * another register and stores the result in the accumulator.
     * @param  operand   for addressing.
     * @param  value     to be added or register.
     * @throws Exception IllegalArgumentException
     */
    public void add(String operand, Integer value) throws Exception {
      if (operand.equals("=")){
        Integer accVal = dataMemory.getData(0);
        dataMemory.setData(0, accVal + value);
      }else if (operand.equals(" ")){
        Integer regVal = dataMemory.getData(value);
        Integer accVal = dataMemory.getData(0);
        dataMemory.setData(0, accVal + regVal);
      }else if (operand.equals("*")){
        Integer newDirection = dataMemory.getData(value);
        Integer regVal = dataMemory.getData(newDirection);
        Integer accVal = dataMemory.getData(0);
        dataMemory.setData(0, accVal + regVal);
      }else{
        throw new IllegalArgumentException("No valid operand");
      }
    }

    /**
     * Analogous to the add method but substracting.
     * @see add
     */
    public void sub(String operand, Integer value) throws Exception{
      if (operand.equals("=")){
        Integer accVal = dataMemory.getData(0);
        dataMemory.setData(0, accVal - value);
      }else if (operand.equals(" ")){
        Integer regVal = dataMemory.getData(value);
        Integer accVal = dataMemory.getData(0);
        dataMemory.setData(0, accVal - regVal);
      }else if (operand.equals("*")){
        Integer newDirection = dataMemory.getData(value);
        Integer regVal = dataMemory.getData(newDirection);
        Integer accVal = dataMemory.getData(0);
        dataMemory.setData(0, accVal - regVal);
      }else{
        throw new IllegalArgumentException("No valid operand");
      }
    }

    /**
     * Multiplies a value with the value stored in the Accumulator
     * and saves the result back into the accumulator.
     * @param  operand   for addressing.
     * @param  value     to make the product with or register.
     * @throws IllegalArgumentException
     */
    public void mul(String operand, Integer value) throws Exception{
      Integer nTimes = null;
      if (operand.equals("=")){
        nTimes = value - 1;
      }else if (operand.equals(" ")){
        nTimes = dataMemory.getData(value) - 1;
      }else if (operand.equals("*")){
        Integer newDirection = dataMemory.getData(value);
        nTimes = dataMemory.getData(newDirection) - 1;
      }else{
        throw new IllegalArgumentException("No valid operand");
      }

      Integer initialValue = dataMemory.getData(0);
      for (int i = 0; i < nTimes; i++){
        add("=", initialValue);
      }

    }

    /**
     * Divides a value with the value stored in the Accumulator
     * and saves the result back into the accumulator.
     * @param  operand   for addressing.
     * @param  value     to make the division with or register.
     * @throws IllegalArgumentException
     */
    public void div(String operand, Integer value) throws Exception{
      Integer divisor = null;
      if (operand.equals("=")){
        divisor = value;
      }else if (operand.equals(" ")){
        divisor = dataMemory.getData(value);
      }else if (operand.equals("*")){
        Integer newDirection = dataMemory.getData(value);
        divisor = dataMemory.getData(newDirection);
      }else{
        throw new IllegalArgumentException("No valid operand");
      }

        Integer dividend = dataMemory.getData(0);
        Integer sum = 0;
        Integer quotient = 0;
        while (sum < dividend){
          sum += divisor;
          quotient++;
        }

        /*In case that the remainder isn't zero*/
        if (sum > dividend)
            quotient--;

        dataMemory.setData(0, quotient);
    }


    /**
     * Reads a value from the input tape and stores it straight
     * into a register. The register cannot be the accumulator.
     * @param  operand   for addressing.
     * @param  direction to store the readed value.
     * @throws IllegalArgumentException in case you use a non valid
     *                                  argument or the accumulator.
     */
    public void read(String operand, Integer direction) throws Exception{

       if (operand.equals(" ")){
         if(direction != 0){
           dataMemory.setData(direction, readHead());
        }else{
           throw new IllegalArgumentException("Accumulator can't store input tape numbers by read instruction");
        }
       }else if (operand.equals("*")){
         Integer newDirection = dataMemory.getData(direction);
         if(newDirection == 0){
            throw new IllegalArgumentException("Accumulator can't store input tape numbers by read instruction");
         }
         dataMemory.setData(newDirection, readHead());
       }else{
         throw new IllegalArgumentException("No valid operand");
       }

    }

    /**
     * Reads a value into the output tape straight from a register.
     * The register cannot be the accumulator.
     * @param  operand   for addressing.
     * @param  direction to retrieve the value to be written.
     * @throws IllegalArgumentException in case you use a non valid
     *                                  argument or the accumulator.
     */
    public void write(String operand, Integer value) throws Exception{
      if (operand.equals("=")){
        writeHead(value);
      }else if (operand.equals(" ")){
        if(value != 0){
         writeHead(dataMemory.getData(value));
       }else{
         throw new IllegalArgumentException("Accumulator can't write into output tape by write instruction");
       }
      }else if (operand.equals("*")){
        Integer newDirection = dataMemory.getData(value);
        writeHead(dataMemory.getData(newDirection));
      }else{
        throw new IllegalArgumentException("No valid operand");
      }
    }

    /**
     * It jumps into a label, which means it trasfer the program
     * control flow from one point to the labeled point.
     * @param operand  must always be a whitespace.
     * @param value    that represents the position in the array
     *                 of the program memory where the label is.
     */
    public void jump(String operand, Integer value){
      if (operand.equals(" ")){
        instrPointer = value;
      }else{
        throw new IllegalArgumentException("No valid operand");
      }
    }

    /**
     * It jumps into a label, which means it trasfer the program
     * control flow from one point to the labeled point when the
     * accumulator's value is zero.
     * @param operand  must always be a whitespace.
     * @param value    that represents the position in the array
     *                 of the program memory where the label is.
     */
    public void jzero(String operand, Integer value) throws Exception {
      if (operand.equals(" ")){
        if(dataMemory.getData(0) == 0){
          instrPointer = value;
        }
      }else{
        throw new IllegalArgumentException("No valid operand");
      }
    }

    /**
     * It jumps into a label, which means it trasfer the program
     * control flow from one point to the labeled point while the
     * accumulator's value is greater than zero.
     * @param operand  must always be a whitespace.
     * @param value    that represents the position in the array
     *                 of the program memory where the label is.
     */
    public void jgtz(String operand, Integer value) throws Exception {
      if (operand.equals(" ")){
        if(dataMemory.getData(0) > 0){
          instrPointer = value;
        }
      }else{
        throw new IllegalArgumentException("No valid operand");
      }
    }

    /**
     * It stops the execution of the program.
     * @param operand   useless parameter it's automatically set to whitespace
     *                  during its creation.
     * @param value     useless parameter it's automatically set to -1
     *                  during its creation.
     */
    public void halt(String operand, Integer value){
      instrPointer = -1;
      System.out.println("Exit by Halt instruction\n");
    }

    /**
     * Contains a loop that executes each program instruction while it doesn't
     * reach the end of the program memory or encounters a halt instruction.
     * It also handles exceptions, prints the machine trace if the machine is
     * in the debug mode and closes the resources safely in sucessful and unsucessful
     * executions.
     * @throws Exception from the machineTrace() and stop() methods.
     */
    public void run() throws Exception{
      boolean running = false;
      while ((instrPointer != -1) && (instrPointer < programMemory.getSize())){
        int currentInstruction = instrPointer;
        if(debugMode == 1)
        machineTrace();
         try{
          step();
        }catch(Exception e){
          Throwable ee = e.getCause ();
          if(ee instanceof IllegalArgumentException){
             System.out.println("Error in Instruction: ");
             System.out.println(programMemory.getInstruction(currentInstruction));
             System.out.println(ee);
          }else if (ee instanceof IndexOutOfBoundsException){
            System.out.println("Error in Instruction: ");
            System.out.println(programMemory.getInstruction(currentInstruction));
            System.out.println(ee);
          }else{
            System.out.println(e);
          }
          instrPointer = -1;
         }
      }
      if(debugMode == 1)
      machineTrace();
      stop();
    }

    /**
     * Calls the next function to be called.
     * @throws Exception
     */
    public void step() throws Exception{
      Instruction currentInstruction = programMemory.getInstruction(instrPointer);
      instrPointer++;
      Integer value = Integer.parseInt(currentInstruction.getValue());
      try{
        llamarFuncion(currentInstruction.getBody(), currentInstruction.getOperand(), value);
      }catch(IllegalArgumentException e){
         throw e;
      }catch(Exception e){
         throw e;
      }
    }

    /**
     * Free the resources used by the input and output units.
     * @throws Exception
     */
    public void stop() throws Exception{
      writingHead.closeTape();
      readingHead.closeTape();
    }

    /**
     * Calls a function using java reflection in execution time.
     * @param  nombreFuncion  name of the function to be called.
     * @param  symb           in this case the operand of the instruction.
     * @param  in             in this case the value to be passed to the
     *                        instruction ( a register, a number, a label ...)
     * @throws SecurityException
     * @throws NoSuchMethodException if the method doesn't exist
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public void llamarFuncion(String nombreFuncion, String symb, Integer in) throws Exception{

       boolean running = false;
       java.lang.reflect.Method method = null;
       try{
           method = this.getClass().getMethod(nombreFuncion.toLowerCase(), symb.getClass(), in.getClass());
       }catch(SecurityException e){
           System.out.println("Security exception");
       }catch(NoSuchMethodException e){
           System.out.println("Method doesn't exists");
       }

       try{
           method.invoke(this, symb, in);
       }catch (IllegalArgumentException e){
           System.out.println("Argumento ilegal");
       }catch (IllegalAccessException e){
           System.out.println("Acceso ilegal");
       }
   }

   /**
    * Prints the content of the input and output tapes,
    * the program and data memory and current instruction being
    * executed.
    * @throws Exception 
    */
   public void machineTrace() throws Exception{
      System.out.println("-------------------------");
      System.out.println(programMemory);
      System.out.println(dataMemory);

      System.out.print("INPUT TAPE [ ");
      File tape = new File(inputTape);
      BufferedReader reader = new BufferedReader(new FileReader(tape));
      String str = " ";
      while ((str = reader.readLine()) != null){
        System.out.print(str + " ");
      }
      System.out.print("]\n");
      reader.close();
      System.out.print("OUTPUT TAPE [ ");
      tape = new File(outputTape);
      reader = new BufferedReader(new FileReader(tape));
      str = " ";
      while ((str = reader.readLine()) != null){
        System.out.print(str + " ");
      }
      System.out.print("]\n");
   }


    public static void main(String args[]) throws Exception{
        if (args.length != 4){
            throw new RuntimeException("Wrong number of Arguments introduced");
        }

        Alcu alphie = new Alcu(args[0], args[1], args[2], Integer.parseInt(args[3]));
        alphie.run();
        if(args[3].equals("0")){
          alphie.machineTrace();
        }

    }

}
