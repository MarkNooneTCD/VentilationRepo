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
    private ArrayList<Event> events;

    ScenarioParser(String baseFilePath, DataList dataList){
        this.baseFilePath = baseFilePath;
        try {
            System.out.println("Loading Scenario Data .......");
            FileReader reader = new FileReader(baseFilePath);
            JSONParser jsonParser = new JSONParser();
            events = new ArrayList<Event>();

            // Validates the data assuring that all necessary data is present
            // This method will throw an exception if data fails any test.
            scenarioObject = (JSONArray) jsonParser.parse(reader);
            parse(scenarioObject, dataList);

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

    private void parse(JSONArray o, DataList dataList) throws ScenarioParserException{
        for(int i =0; i < o.size(); i++){
            JSONObject object = (JSONObject) o.get(i);

            if(!(object.containsKey("source") || object.containsKey("events")))
                throw new ScenarioParserException("Event at row " + (i+1) + " is bad record.");

            String eventEmmiter = (String) object.get("source");
            JSONArray events = (JSONArray) object.get("events");

            for(Object event : events) {
                JSONObject e = (JSONObject) event;
                if (ConfigParser.objectHasKeys(e, "time", "target", "rate")){
                    String time = (String) e.get("time");
                    String eventName = (String) e.get("target");
                    String rate = (String) e.get("rate");

                    Event ev = new Event(eventName, eventEmmiter, time, rate);
                    dataList.insertEvent(ev);
                }else{
                    throw new ScenarioParserException("Event at row " + (i+1) + " is bad record.");
                }

            }

        }
    }

}
