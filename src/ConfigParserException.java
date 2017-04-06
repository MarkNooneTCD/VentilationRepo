
// This is a simple Exception that is specifically thrown
// during the parsing process of the configuration file. This
// is to make the debugging process easier on the programmer.

public class ConfigParserException extends Exception{
    ConfigParserException(String s){
        super(s + " does not meet the requirements. \n Please read documentation for more information");
    }
}
