import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class ScenarioParser {

    private String baseFile;
    private JSONArray scenarioObject;
    private String baseFilePath;
    private boolean isParsed = false;
    private boolean printStats = false;
    private ArrayList<Pollutant> pollutants;

    ScenarioParser(String baseFilePath){
        this.baseFilePath = baseFilePath;
        try {
            System.out.println("Loading Scenario Data .......");
            FileReader reader = new FileReader(baseFilePath);
            JSONParser jsonParser = new JSONParser();
            pollutants = new ArrayList<Pollutant>();

            // Validates the data assuring that all necessary data is present
            // This method will throw an exception if data fails any test.
            scenarioObject = (JSONArray) jsonParser.parse(reader);
            parse(scenarioObject);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch(ScenarioParserException ex) {
            ex.printStackTrace();
        }
    }

    private void parse(JSONArray o) throws ScenarioParserException{
        for(int i =0; i < o.size(); i++){
            JSONObject object = (JSONObject) o.get(i);
            if(object.containsKey("Pollutant") && object.containsKey("Source") && object.containsKey("Time") && object.containsKey("Rate")) {
                Pollutant p = new Pollutant((String) object.get("Pollutant"), (String) object.get("Source"), (String) object.get("Time"), (String) object.get("Rate"));
                pollutants.add(p);
            } else {
                throw new ScenarioParserException("Pollutant at row " + (i+1) + " is bad record.");
            }
        }
    }

    public ArrayList<Pollutant> hasPollutantsAtTime(LocalTime t){
        ArrayList<Pollutant> p = new ArrayList<Pollutant>();
        for(Pollutant pollutant: pollutants){
            if(pollutant.inTime(t)){
                p.add(pollutant);
            }
        }
        return p;
    }
}
