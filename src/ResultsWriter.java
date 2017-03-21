import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by marcus on 21/03/2017.
 */
public class ResultsWriter {

    private ArrayList<String[]> results;

    ResultsWriter(){
        results = new ArrayList<String[]>();
    }

    public void write(String tag, String value){
        String[] s = new String[2];
        s[0] = tag;
        s[1] = value;
        results.add(s);
    }

    public void write(String tag, double value){
        String[] s = new String[2];
        s[0] = tag;
        s[1] = ""+value;
        results.add(s);
    }

    public void flush(String destinationFile){
        BufferedWriter writer = null;
        try {
            File outFile = new File(destinationFile);

            // This will output the full path where the file will be written to...
            System.out.println(outFile.getCanonicalPath());
            writer = new BufferedWriter(new FileWriter(outFile));
            for(int i =0; i < results.size(); i++){
                String[] s = results.get(i);
                String writing = "Result of " + s[0] + " is: " + s[1] +"\n";
                writer.write(writing);
                System.out.print(writing);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
                results = new ArrayList<String[]>();
            } catch (Exception e) {
            }
        }

    }

}
