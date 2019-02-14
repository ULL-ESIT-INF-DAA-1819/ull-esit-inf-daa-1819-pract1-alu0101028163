import java.util.*;
//import java.lang.reflect.Method;

public class ReflectionTest{

    public void metodoPrueba(String symb, ArrayList<Integer> in){
            System.out.println(symb);
            for (int i = 0; i < in.size(); i++){
                System.out.println(in.get(i).toString());
            }
    }


    public void llamarFuncion(String nombreFuncion, String symb, ArrayList<Integer> in) throws Exception{
        ReflectionTest arr = new ReflectionTest();
        java.lang.reflect.Method method = null;
        try{
            method = arr.getClass().getMethod(nombreFuncion, symb.getClass(), in.getClass());
        }catch(SecurityException e){
            System.out.println("Excepción de Seguridad");
        }catch(NoSuchMethodException e){
            System.out.println("El método no existe");
        }

        try{
            method.invoke(arr, symb, in);
        }catch (IllegalArgumentException e){
            System.out.println("Argumento ilegal");
        }catch (IllegalAccessException e){
            System.out.println("Acceso ilegal");
        }
    }

    public static void main(String args[]) throws Exception{
        ReflectionTest arr = new ReflectionTest();
        ArrayList<Integer> num_ = new ArrayList<Integer>();
        num_.add(new Integer(20));
        num_.add(new Integer(30));
        arr.llamarFuncion("metodoPrueba", "*", num_);
    }

}
