public class ConfigParserException extends Exception{
    ConfigParserException(String s){
        super(s + "  does not meet the requirements. \n Please read documentation for more information");
    }
}
