

public class Instruction<ValueType>{


  private boolean jumpInstruction;
  private String label;
  private String body;
  private String operand;
  private ValueType value;

  public Instruction(boolean jumpInstruction, String label, String body, String operand, ValueType value){
    this.jumpInstruction = jumpInstruction;
    this.label = label;
    this.body = body;
    this.operand = operand;
    this.value = value;
  }


  public boolean isJumpInstruction(){
    return jumpInstruction;
  }

  public String getLabel(){
    return label;
  }

  public String getBody(){
    return body;
  }

  public String getOperand(){
    return operand;
  }

  public ValueType getValue(){
    return value;
  }

  public void setJumpInstrucionTo(boolean jumpInstruction){
    this.jumpInstruction = jumpInstruction;
  }

  public void setLabel(String label){
    this.label = label;
  }

  public void setBody(String body){
    this.body = body;
  }

  public void setOperand(String operand){
    this.operand = operand;
  }

  public void setValue(ValueType value){
    this.value = value;
  }

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
