package nodscraper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

//this class is for printing console output to a text file for logging purposes
public class NODPrintStream extends PrintStream{
    private final PrintStream second;

    public NODPrintStream (OutputStream main, PrintStream second){
        super(main);
        this.second = second;
    }//end of NODPrintStream constructor/method/whatever

    /**
     * Closes the main stream. 
     * The second stream is just flushed but <b>not</b> closed.
     * @see java.io.PrintStream#close()
     */
    @Override
    public void close(){
        // just for documentation
        super.close();
    }//end of close method

    @Override
    public void flush(){
        super.flush();
        second.flush();
    }//end of flush method

    @Override
    public void write(byte[] buf, int off, int len){
        super.write(buf, off, len);
        second.write(buf, off, len);
    }//end of write method

    @Override
    public void write(int b){
        super.write(b);
        second.write(b);
    }//end of write method alt

    @Override
    public void write(byte[] b) throws IOException{
        super.write(b);
        second.write(b);
    }//end of write method alt alt
}//end of NODPrintStream class