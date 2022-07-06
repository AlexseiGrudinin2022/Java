import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class ParserUndergroundJSON {

    private static Document doc;

    private static boolean setDoc() {

        // final String WEB_PAGE_URL = "https://www.moscowmap.ru/metro.html#lines";
        //Jsoup.connect(WEB_PAGE_URL).maxBodySize(0).get();

        try {

            ParserUndergroundJSON.doc =
                    Jsoup.parse(Files.readAllLines(Paths.get("src\\main\\resources\\index.html")).toString());

        } catch (IOException ex) {
            return false;
        }

        return (doc != null);


    }

    private static JSONArray getJSONLines() {
        Elements allLines = doc.select("span.js-metro-line");
        JSONArray arrValuesObj = new JSONArray();

        allLines.forEach(
                line ->
                {
                    JSONObject tempObj = new JSONObject();
                    tempObj.put("number", line.attr("data-line"));
                    tempObj.put("name", line.text());
                    arrValuesObj.add(tempObj);
                }
        );


        return arrValuesObj;
    }

    private static Map<String, String> getParseConnection(Elements connections) {
        if (connections.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, String> result = new HashMap<>();

        for (Element oneConnect : connections) {
            String numberLine = oneConnect.attr("class");
            numberLine = numberLine.substring(numberLine.lastIndexOf("ln-") + 3);
            String stationName = oneConnect.attr("title");
            stationName = stationName.substring(stationName.indexOf("«") + 1, stationName.lastIndexOf("»")).trim();
            result.put(numberLine, stationName);
        }
        return result;
    }

    private static List<Stations> getAllStationsAndConnection() {

        Elements allStations = doc.select("div.js-metro-stations");

        List<Stations> stations = new ArrayList<>();

        for (Element stationOneLines : allStations) {
            String numberLine = stationOneLines.attr("data-line");

            for (Element oneStation : stationOneLines.select("span.name")) {

                String nameStation = oneStation.text();
                var connectAdd = getParseConnection(oneStation.parent().select("span[title]"));

                if (!connectAdd.isEmpty()) {
                    connectAdd.put(numberLine, nameStation);
                }

                stations.add(
                        new Stations(
                                numberLine,
                                nameStation,
                                connectAdd
                        )
                );

            }

        }

        return stations;
    }

    private static JSONObject getJSONStations(List<Stations> stationsAndConnections) {

        JSONObject structStation = new JSONObject();
        var stationsMap = stationsAndConnections
                .stream()
                .collect(Collectors.groupingBy(Stations::getNumberLine));


        for (String key : stationsMap.keySet()) {
            JSONArray jsonArray = new JSONArray();
            stationsMap.get(key).forEach(station -> jsonArray.add(station.getName()));
            structStation.put(key, jsonArray);
        }

        return structStation;

    }

    private static JSONArray getJSONConnections(List<Stations> stationsAndConnections) {

        JSONArray globalArr = new JSONArray();

        var getconnectionOneLine = stationsAndConnections.stream().
                map(Stations::getConnection)
                .filter(map->map.containsKey("2")).distinct().collect(Collectors.toList());

        getconnectionOneLine.forEach(System.out::println);



        stationsAndConnections
                .stream()
                .map(Stations::getConnection)
                .filter(map -> !map.isEmpty())
                .distinct()
                .forEach(index ->
                        {
                            JSONArray jsonArray = new JSONArray();
                            index.keySet().forEach(key ->
                                    {
                                        JSONObject jsonObject_ = new JSONObject();
                                        jsonObject_.put("line", key);
                                        jsonObject_.put("station", index.get(key));
                                        jsonArray.add(jsonObject_);
                                    }
                            );

                            globalArr.add(jsonArray);
                        }
                );

        return globalArr;

    }

    public static Path getJSONMoscowMap(String savePath, String nameFileJSON) throws IOException {

        if (setDoc()) {

            savePath = savePath.replaceAll("\\s+", " ").trim();
            nameFileJSON = nameFileJSON.replaceAll("\\s+", " ").trim();

            if (nameFileJSON.isEmpty()) {
                System.out.println("Имя файла не должно быть пустым!");
                return null;
            }

            Path pathFile = Paths.get(savePath + "\\" + nameFileJSON + ".json").toAbsolutePath();

            if (Files.isDirectory(pathFile)) {
                System.out.println("Путь не может оканчиваться дирректорией, введите имя файла!");
                return null;
            } else if (!Files.exists(pathFile.getParent())) {
                System.out.println("Введен несуществующий путь");
                return null;
            }

            JSONObject jsonMoscowUnderground = new JSONObject();

            var allStationAndConnection = getAllStationsAndConnection();

            jsonMoscowUnderground.put("stations", getJSONStations(allStationAndConnection));
            jsonMoscowUnderground.put("connections", getJSONConnections(allStationAndConnection));
            jsonMoscowUnderground.put("lines", getJSONLines());

            FileWriter fileWriter = new FileWriter(pathFile.toString());
            fileWriter.write(jsonMoscowUnderground.toJSONString());
            fileWriter.flush();
            fileWriter.close();

            System.out.println("Страница парсинга:\n\t" + doc.title() + "\n\t\tЗавершено успешно!");
            return pathFile;

        } else {
            System.out.println("Возникла проблема с подключением к сети!");
            return null;

        }


    }

}
