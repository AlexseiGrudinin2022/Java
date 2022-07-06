import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;


public class JSONReader {


    Path pathToJSONFile;

    public JSONReader(Path pathToJSONFile) {
        this.pathToJSONFile = pathToJSONFile;

    }

    public void parseJSONFile() {


        try {
            JSONObject jsonData = (JSONObject) (new JSONParser()).parse(readJSONFile());

            JSONObject stationsObject = (JSONObject) jsonData.get("stations");
            var stations = parseStations(stationsObject);

            AtomicLong count = new AtomicLong();
            AtomicReference<String> maxNumberLines = new AtomicReference<>("");


            System.out.println("\nОбщее количество станций: " + stations.keySet().stream().mapToLong(key ->
                    {

                        var counts = (long) stations.get(key).size();
                        long count_ = count.get();


                        if (count_ < counts) {
                            count.set(counts);
                            maxNumberLines.set(key);
                        }
                        ;

                        System.out.println("Линия номер: " + key + " всего станций: " + counts);
                        return counts;
                    }
            ).sum());

            System.out.println("\nМаксимальное число станций: \n\tЛиния номер: " + maxNumberLines.get() +
                    ";\n\tЧисло станций: " + count.get());


            System.out.println("\nВсего переходов: " + parseCountConnections((JSONArray) jsonData.get("connections")));


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private String readJSONFile() {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(pathToJSONFile);
            lines.forEach(builder::append);
        } catch (Exception ex) {

            String message = "Файл map.json не найден в папке \"" + pathToJSONFile + "\"!\nПриложение будет закрыто!";
            System.err.println(message);
            System.exit(0);

        }

        return builder.toString();
    }


    private static String parseCountConnections(JSONArray connectionsArray) {

        long sumConnectionAllStations = connectionsArray.stream().mapToLong(
                connect -> ((JSONArray) connect).size()
        ).sum();


        return "\n\tПереходов по каждой станции в каждой группе: " + sumConnectionAllStations +
                "\n\tГрупп станций с переходами: " + connectionsArray.size();

    }


    private static Map<String, List<String>> parseStations(JSONObject stationsObject) {

        Map<String, List<String>> stations_ = new HashMap<>();

        for (var numberLines : stationsObject.keySet()) {
            JSONArray jsonArray = (JSONArray) stationsObject.get(numberLines);
            List<String> stationArr = new ArrayList<>();
            for (var stations : jsonArray) {
                stationArr.add((String) stations);
            }

            stations_.put((String) numberLines, stationArr);
        }
        return stations_;
    }

    private static Map<String, String> parseLines(JSONArray linesArray) {

        Map<String, String> lines = new HashMap<>();

        linesArray.forEach(lineObject -> {
                    JSONObject lineJsonObject = (JSONObject) lineObject;

                    lines.put((String) lineJsonObject.get("number"),
                            (String) lineJsonObject.get("name"));

                }
        );
        return lines;
    }


}
