import java.util.*;
//import java.lang.reflect.Method;

public class ReflectionTest{

    public void metodoPrueba(String symb, Integer in){
            System.out.println(symb);
            System.out.println(in.toString());
    }


    public void llamarFuncion(String nombreFuncion, String symb, Integer in) throws Exception{
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
        Integer num_ = new Integer(20);
        arr.llamarFuncion("metodoPrueba", "*", num_);
    }

}
