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

    public Integer readHead() throws Exception{
      String inputSymbol = readingHead.readSymbol();
      return Integer.parseInt(inputSymbol);
    }

    public void writeHead(Integer symbol) throws Exception{
      writingHead.writeSymbol(symbol);
    }

    public void load(String operand, Integer value){
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

    public void store(String operand, Integer direction){
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

    public void mul(String operand, Integer value){
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

    public void div(String operand, Integer value){
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

    public void add(String operand, Integer value){
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

    public void sub(String operand, Integer value){
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

    public void read(String operand, Integer direction) throws Exception{

       if (operand.equals(" ")){
         if(direction != 0){
           dataMemory.setData(direction, readHead());
        }else{
           throw new IllegalArgumentException("Accumulator can't store input tape numbers by read instruction");
        }
       }else if (operand.equals("*")){
         Integer newDirection = dataMemory.getData(direction);
         dataMemory.setData(newDirection, readHead());
       }else{
         throw new IllegalArgumentException("No valid operand");
       }

    }

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

    public void jump(String operand, Integer value){
      if (operand.equals(" ")){
        instrPointer = value;
      }else{
        throw new IllegalArgumentException("No valid operand");
      }
    }

    public void jzero(String operand, Integer value){
      if (operand.equals(" ")){
        if(dataMemory.getData(0) == 0){
          instrPointer = value;
        }
      }else{
        throw new IllegalArgumentException("No valid operand");
      }
    }

    public void jgtz(String operand, Integer value){
      if (operand.equals(" ")){
        if(dataMemory.getData(0) > 0){
          instrPointer = value;
        }
      }else{
        throw new IllegalArgumentException("No valid operand");
      }
    }

    public void halt(String operand, Integer value){
      instrPointer = -1;
      System.out.println("Exit by Halt instruction\n");
    }

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
          }
          instrPointer = -1;
         }
      }
      if(debugMode == 1)
      machineTrace();
      stop();
    }

    public void step() throws Exception{
      Instruction currentInstruction = programMemory.getInstruction(instrPointer);
      instrPointer++;
      Integer value = Integer.parseInt(currentInstruction.getValue());
      try{
        llamarFuncion(currentInstruction.getBody(), currentInstruction.getOperand(), value);
      }catch(IllegalArgumentException e){
         throw e;
     }
    }

    public void stop() throws Exception{
      writingHead.closeTape();
      readingHead.closeTape();
    }

    public void llamarFuncion(String nombreFuncion, String symb, Integer in) throws Exception{

       boolean running = false;
       java.lang.reflect.Method method = null;
       try{
           method = this.getClass().getMethod(nombreFuncion.toLowerCase(), symb.getClass(), in.getClass());
       }catch(SecurityException e){
           System.out.println("Excepción de Seguridad");
       }catch(NoSuchMethodException e){
           System.out.println("El método no existe");
       }

       try{
           method.invoke(this, symb, in);
       }catch (IllegalArgumentException e){
           System.out.println("Argumento ilegal");
       }catch (IllegalAccessException e){
           System.out.println("Acceso ilegal");
       }
   }

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

        alphie.machineTrace();
    }

}
