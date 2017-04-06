
// This is a simple Exception that is specifically thrown
// during the parsing process of the scenarios file. This
// is to make the debugging process easier on the programmer.

public class ScenarioParserException extends Exception {
    ScenarioParserException(String s){
        super(s);
    }
}
