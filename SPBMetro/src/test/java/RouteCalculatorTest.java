import core.Line;
import core.Station;
import junit.framework.TestCase;
import java.util.*;
import java.util.stream.Collectors;

public class RouteCalculatorTest extends TestCase {


    private StationIndex stationIndex = new StationIndex();
    private RouteCalculator calc;

    public StationIndex getStationIndex()
    {
        return stationIndex;
    }

      public void setUp() {

        List<Line> lines = new ArrayList<>();
        lines.add(new Line(7, "Таганско-краснопресненская линия"));
        lines.add(new Line(5, "Кольцевая"));
        lines.add(new Line(1, "Сокольническая"));

        List<Station> stationss = new ArrayList<>();
        //ФИОЛЕТОВАЯ
        stationss.add(new Station("полежаевская", lines.get(0)));
        stationss.add(new Station("беговая", lines.get(0)));
        stationss.add(new Station("улица 1905 года", lines.get(0)));
        stationss.add(new Station("баррикадная", lines.get(0)));  //пересадка на кольцо
        stationss.add(new Station("пушкинская", lines.get(0)));

        //КРАСНАЯ
        stationss.add(new Station("спортивная", lines.get(1)));
        stationss.add(new Station("фрунзенская", lines.get(1)));
        stationss.add(new Station("парк культурыС", lines.get(1))); //пересадка на кольцо
        stationss.add(new Station("кропоткинская", lines.get(1)));

        //КОЛЬЦО
        stationss.add(new Station("новослободская", lines.get(2)));
        stationss.add(new Station("белорусская", lines.get(2)));
        stationss.add(new Station("краснопресненская", lines.get(2))); //пересадка с кольца
        stationss.add(new Station("киевская", lines.get(2)));
        stationss.add(new Station("парк культуры", lines.get(2))); //пересадка с кольца
        stationss.add(new Station("октябрьская", lines.get(2)));

        //добавляем станцию на линию и в station index

        stationss.forEach(
                st ->
                {
                    for (Line line : lines) {
                        if (st.getLine().getNumber() == line.getNumber()) {
                            line.addStation(st);
                            break;
                        }
                    }
                    stationIndex.addStation(st);
                }
        );

        stationIndex.addConnection(new ArrayList<>(Arrays.asList(stationIndex.getStation("баррикадная"), stationIndex.getStation("краснопресненская"))));
        stationIndex.addConnection(new ArrayList<>(Arrays.asList(stationIndex.getStation("парк культуры"), stationIndex.getStation("парк культурыС"))));

        calc = new RouteCalculator(stationIndex);

    }

    private List<String> farmActualList(List<Station> stations) {

        return stations.stream().map(Station::getName).collect(Collectors.toList());
    }

    private List<Station> getShortRoute(String from, String to) {
        return calc.getShortestRoute(stationIndex.getStation(from),
                stationIndex.getStation(to));
    }

    public void testCalculateDurationNoConnections() {
        double actual = RouteCalculator.calculateDuration(getShortRoute("беговая", "пушкинская"));
        double expected = 7.5;
        assertEquals(expected, actual);
    }

    public void testCalculateDurationOneConnection() {

        double actual = RouteCalculator.calculateDuration(getShortRoute("улица 1905 года", "парк культуры"));
        double expected = 11.0;
        assertEquals(expected, actual);
    }

    public void testCalculateDurationTwoConnection() {

        double actual = RouteCalculator.calculateDuration(getShortRoute("беговая", "спортивная"));
        double expected = 22.0;
        assertEquals(expected, actual);
    }

    public void testGetShortestRout() {
        var actualString = farmActualList(getShortRoute("полежаевская", "пушкинская"));
        List<String> expectedString = new ArrayList<>();
        expectedString.add("полежаевская");
        expectedString.add("беговая");
        expectedString.add("улица 1905 года");
        expectedString.add("баррикадная");
        expectedString.add("пушкинская");
        assertEquals(expectedString, actualString);
    }

    public void testGetShortestOneRout() {
        var actualString = farmActualList(getShortRoute("полежаевская", "парк культуры"));
        List<String> expectedString = new ArrayList<>();
        expectedString.add("полежаевская");
        expectedString.add("беговая");
        expectedString.add("улица 1905 года");
        expectedString.add("баррикадная");
        expectedString.add("краснопресненская");
        expectedString.add("киевская");
        expectedString.add("парк культуры"); //кольцевая
        assertEquals(expectedString, actualString);
    }

    public void testGetShortestTwoRout() {
        var actualString = farmActualList(getShortRoute("баррикадная", "фрунзенская"));
        List<String> expectedString = new ArrayList<>();
        expectedString.add("баррикадная");
        expectedString.add("краснопресненская");
        expectedString.add("киевская");
        expectedString.add("парк культуры");
        expectedString.add("парк культурыС"); // С - с кольца на сокольническую
        expectedString.add("фрунзенская");
        assertEquals(expectedString, actualString);
    }
}
