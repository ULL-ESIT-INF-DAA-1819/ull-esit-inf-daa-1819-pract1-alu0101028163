import java.io.*;

public class OutputTest{


    private BufferedWriter bw;

    public void loadFile(String outputFile) throws Exception{
        bw = new BufferedWriter(new FileWriter(outputFile));
    }

    public void writeFile(String message) throws Exception{
        bw.write(message);
    }

    public void close() throws Exception{
        bw.close();
    }

    public static void main(String[] args) throws Exception{

        OutputTest myOutput = new O utputTest();
        myOutput.loadFile(args[0]);
        myOutput.writeFile("Hello\n");
        myOutput.writeFile(" World\n");
        myOutput.close();
    }

}
