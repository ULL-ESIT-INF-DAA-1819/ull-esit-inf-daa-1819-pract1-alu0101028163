import java.io.*;

class InputTest{

    private BufferedReader br;

    public void loadFile(String inputFile)  throws Exception{
        File file = new File(inputFile);
        br = new BufferedReader(new FileReader(file));
    }

    public String readLine()  throws Exception{
        return br.readLine();
    }

    public static void main(String args[]) throws Exception{

        InputTest myInput = new InputTest();
        myInput.loadFile(args[0]);

        System.out.println(myInput.readLine());
        System.out.println(myInput.readLine());
        System.out.println(myInput.readLine());
        System.out.println(myInput.readLine());


    }



}
