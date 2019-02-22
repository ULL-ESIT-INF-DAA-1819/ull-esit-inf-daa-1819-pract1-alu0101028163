
public class Alcu{

    private ProgramMemory programMemory;
    private DataMemory dataMemory;
    private ReadingHead readingHead;
    private WritingHead writingHead;
    private int         instrPointer;


    public Alcu(String programPath, String inputTape, String outputTape) throws Exception{
        programMemory = new ProgramMemory(programPath);
        dataMemory = new DataMemory();
        readingHead = new ReadingHead(inputTape);
        writingHead = new WritingHead(outputTape);
        instrPointer = 0;
    }

    /*public void testFunction(String str, Integer num){
      System.out.println("Im the test function running with the" +
                         "string:" + str  + " and number " + num);
      instrPointer = -1;
    }*/

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

    public void run() throws Exception{
      boolean running = false;
      while (instrPointer != -1){
        step();
      }
    }

    public void step() throws Exception{
      Instruction currentInstruction = programMemory.getInstruction(instrPointer);
      instrPointer++;
      Integer value = Integer.parseInt(currentInstruction.getValue());
      llamarFuncion(currentInstruction.getBody(), currentInstruction.getOperand(), value);
    }

    public void llamarFuncion(String nombreFuncion, String symb, Integer in) throws Exception{

       boolean running = false;
       java.lang.reflect.Method method = null;
       try{
           method = this.getClass().getMethod(nombreFuncion, symb.getClass(), in.getClass());
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

   public void machineTrace(){
      System.out.println(dataMemory);
   }


    public static void main(String args[]) throws Exception{
        if ((args.length < 3) || (args.length > 4)){
            throw new RuntimeException("Wrong number of Arguments introduced");
        }
        Alcu alphie = new Alcu(args[0], args[1], args[2]);
        alphie.load("*", 10);
        alphie.machineTrace();
    }

}
