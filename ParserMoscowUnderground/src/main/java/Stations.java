import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Stations {


    private final String name;
    private final String numberLine;
    private final Map<String, String> connection;

    public Stations(String numberLine, String name, Map<String, String> connection) {
        this.name = name;
        this.numberLine = numberLine;
        this.connection = connection;
    }


    public Map<String, String> getConnection() {
        return (connection.isEmpty()) ? new LinkedHashMap<>() :
                connection.entrySet()
                        .stream()
                        .sorted(Map.Entry.<String, String>comparingByValue())
                        .collect(Collectors
                                .toMap(Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (e1, e2) -> e1,
                                        LinkedHashMap::new));
    }

    public String getName() {
        return name;
    }

    public String getNumberLine() {
        return numberLine;
    }

    @Override
    public String toString() {

        StringBuilder connection = new StringBuilder();

        for (String connect : getConnection().keySet()) {
            connection
                    .append("\t")
                    .append("line ")
                    .append(connect)
                    .append(": ")
                    .append("\t")
                    .append(getConnection().get(connect))
                    .append("\n");
        }

        String result = "name: " + getName() + "\n" +
                "line: " + getNumberLine() + "\n";

        if (!connection.isEmpty()) {
            result +=
                    "пересадки: \n" + connection;
        }

        return result;
    }


}
