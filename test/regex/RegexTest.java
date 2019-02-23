import java.util.regex.*;


public class RegexTest{

  public static void main(String args[]){
    String s = "HALT";
    String regex1 = "^([a-zA-Z]{1,}:)\\s{0,}([a-zA-Z]{1,})(\\*|\\s|=)([0-9]+)"; // Con etiqueta
    String regex2 = "^(([a-zA-Z]{1,})(\\*|\\s|=)([0-9]+))"; // Sin etiqueta
    String regex3 = "^(([a-zA-Z]{1,})\\s([a-zA-Z]{1,}))"; // Con salto
    String regex4 = "^HALT$|^halt$";

    Pattern pattern = Pattern.compile(regex1);
    Matcher matcher = pattern.matcher(s);
    if (matcher.find()){
      System.out.println("CON ETIQUETA");
      System.out.println(matcher.group(0));
      System.out.println(matcher.group(1)); // Etiqueta
      System.out.println(matcher.group(2)); // load
      System.out.println(matcher.group(3)); // =
      System.out.println(matcher.group(4)); // 3
    }


    pattern = Pattern.compile(regex2);
    matcher = pattern.matcher(s);
    if (matcher.find()){
      System.out.println("SIN ETIQUETA");
      System.out.println(matcher.group(2)); // load
      System.out.println(matcher.group(3)); // =
      System.out.println(matcher.group(4));   // 3
    }


    pattern = Pattern.compile(regex3);
    matcher = pattern.matcher(s);
    if (matcher.find()){
      System.out.println("SALTO");
      System.out.println(matcher.group(0));
      System.out.println(matcher.group(1));
      System.out.println(matcher.group(2)); // JUMP
      System.out.println(matcher.group(3)); // ETIQUETA
    }

    pattern = Pattern.compile(regex4);
    matcher = pattern.matcher(s);
    if (matcher.find()){
      System.out.println("HALT");
      System.out.println(matcher.group(0));
    }
  }

}
