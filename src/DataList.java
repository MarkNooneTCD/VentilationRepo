import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DataList {

    private int index = 0;
    private ArrayList<Data> data = new ArrayList<>();

    public DataList(){

    }

    public boolean hasNext(){
        return index < data.size();
    }

    public void append(Data d){
        data.add(d);
    }

    public Data getNext(){
        return data.get(index++);
    }

    public Data getDataByIndex(int i){
        return data.get(i);
    }

    public Data getDataByTime(LocalDateTime dateTime){
        return data.get(indexOfDateTime(dateTime));
    }

    public void insertEvent(Event e){
        LocalDateTime dataTime = data.get(0).getDateTime();
        LocalDateTime start = e.getStartTime().atDate(dataTime.toLocalDate());
        LocalDateTime end = e.getEndTime().atDate(dataTime.toLocalDate());

        for(LocalDateTime current = start.minusSeconds(0); !current.isAfter(end); current = current.plusMinutes(1)){
            Data d = data.get(indexOfDateTime(current));
            d.getEvents().add(e);
        }
    }

    public int indexOfDateTime(LocalDateTime dateTime){
        int min = 0;
        int max = data.size() - 1;
        int current;
        while(min <= max){
            current = (min+max)/2;
            if(data.get(current).getDateTime().isBefore(dateTime))
                min = current + 1;

            else if(data.get(current).getDateTime().isAfter(dateTime))
                max = current - 1;

            else
               return current;

        }
        return -1;
    }

}
