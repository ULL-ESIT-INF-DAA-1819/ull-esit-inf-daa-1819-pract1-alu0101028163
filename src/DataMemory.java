import java.util.*;
import java.io.*;

/**
*   This class represents the Data Memory for the RAM
*   @author Adrián Álvarez
*   @version 1.0
*/

public class DataMemory{

    private ArrayList<Integer> dtMemory;

    /**
     *  Class constructor
     */
    public DataMemory(){
      dtMemory = new ArrayList<Integer>();
    }

    /**
     * Sets the data in an specific register
     * @param register  where the data will be stored
     * @param data      to be stored.
     */
    public void setData(int register, Integer data){
      if(register > dtMemory.size()){
        for (int i = dtMemory.size(); i <= register; i++){
          dtMemory.add(i,0);
        }
      }
      if(dtMemory.size() > register){
        dtMemory.set(register,data);
      }else{
        dtMemory.add(register,data);
      }
    }

    /**
     * Gets the data stored in an specific register
     * @param  register  where the data is stored
     * @return           the data stored.
     */
    public Integer getData(int register){
      if (register > dtMemory.size()){
        throw new IndexOutOfBoundsException("Register " + register + " is empty");
      }
      return dtMemory.get(register);
    }

    /**
     * Returns a string that represents the whole
     * data memory registers content.
     * @return  string with the registers and their
     *          content.
     */

    public String toString(){
      String str = "\n DATA MEMORY\n";
      for (int i = 0; i < dtMemory.size(); i++){
        str += " [R" + i + "] -- ";
        str += "[" + dtMemory.get(i) + "]";
        str += "\n";
      }
      return str;
    }

    public static void main(String args[]){
      DataMemory dt = new DataMemory();
      dt.setData(0,1);
      dt.setData(1,2);
      dt.setData(2,3);
      System.out.println(dt.toString());
      dt.setData(0,1);
      dt.setData(1,2);
      dt.setData(2,3);
      System.out.println(dt.toString());
    }

}
