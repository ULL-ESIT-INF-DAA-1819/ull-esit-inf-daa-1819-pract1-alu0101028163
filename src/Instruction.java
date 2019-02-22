

public class Instruction{


  private boolean jumpInstruction;
  private String label;
  private String body;
  private String operand;
  private String value;

  /**
   * Constructor of the class
   * @param jumpInstruction  boolean that represents if the instruction is a
   *                         jump instruction (true) or not (false)
   * @param label            in case of existing this attribute stores the label
   *                         associated with this instruction, it's null by default.
   * @param body             the instruction per se (LOAD, STORE, SUB, MUL..)
   * @param operand          of one of the three types ( = , *,  )
   * @param value            Integer in case of a number or String in case of a label.
   */
  public Instruction(boolean jumpInstruction, String label, String body, String operand, String value){
    this.jumpInstruction = jumpInstruction;
    this.label = label;
    this.body = body;
    this.operand = operand;
    this.value = value;
  }


  /**
   * Tells if the Instruction is of type "jump instruction"
   * @return true if the instruction is a jump instruction type
   *         which means it's for example JZERO or JUMP.
   */
  public boolean isJumpInstruction(){
    return jumpInstruction;
  }

  /**
   * Gets the instruction label
   * @return the instruction label if it exists or null.
   */
  public String getLabel(){
    return label;
  }

  /**
   * Gets the instruction body
   * @return the instruction body (LOAD, STORE, JUMP ... ).
   */
  public String getBody(){
    return body;
  }

  /**
   * Gets the instruction operand
   * @return the instruction operand (* or = or ).
   */
  public String getOperand(){
    return operand;
  }

  /**
   * Gets the instruction value
   * @return the instruction value which can be a number
   *         or a string in case of a label (jump instruction).
   */
  public String getValue(){
    return value;
  }

  /**
   * Sets the jumpInstruction attribute value
   * @param jumpInstruction
   * @see   getJumpInstruction
   * @see   Instruction
   */
  public void setJumpInstrucionTo(boolean jumpInstruction){
    this.jumpInstruction = jumpInstruction;
  }

  /**
   * Sets the label attribute value
   * @param label
   * @see   getLabel
   * @see   Instruction
   */
  public void setLabel(String label){
    this.label = label;
  }

  /**
   * Sets the body attribute value
   * @param body
   * @see   getBody
   * @see   Instruction
   */
  public void setBody(String body){
    this.body = body;
  }

  /**
   * Sets the operand attribute value
   * @param operand
   * @see   getJumpInstruction
   * @see   Instruction
   */
  public void setOperand(String operand){
    this.operand = operand;
  }

  /**
   * Sets the value attribute value
   * @param value
   * @see   getJumpInstruction
   * @see   Instruction
   */
  public void setValue(String value){
    this.value = value;
  }


  /**
   * toString method for printing the Instruction
   * @return a String representing the Instruction content.
   */
  public String toString(){
    String str = "";
    if (label != null){
      str += label + " ";
    }
    str += body + " ";
    str += operand + " ";
    str += value;
    return str;
  }


  public static void main(String args[]){
  /*  Instruction instr = new Instruction(false, null, "LOAD", "=", 3);
    System.out.println(instr.isJumpInstruction());
    System.out.println(instr.getLabel());
    System.out.println(instr.getBody());
    System.out.println(instr.getOperand());
    System.out.println(instr.getValue());
    System.out.println(instr);*/

    String str = "PEPE:LOAD = 3";
    String[] output = str.split("\\:");
    System.out.println(output[0]);
    System.out.println(output[1]);
  }


}
