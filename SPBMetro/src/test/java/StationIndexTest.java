import core.Line;
import core.Station;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StationIndexTest extends TestCase {

    List<Line> lines = new ArrayList<>();
    List<Station> stationss = new ArrayList<>();


    @Override
    public void setUp() {

        lines.add(new Line(7, "Таганско-краснопресненская"));
        lines.add(new Line(1, "Сокольническая"));
        lines.add(new Line(5, "Кольцевая"));


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
                }
        );

    }

    private StationIndex addStationsAndLinesAndConnectionsToStationIndex(List<Station> stations, List<Line> lines) {
        StationIndex stationIndex = new StationIndex();

        lines.forEach(stationIndex::addLine);

        stations.forEach(stationIndex::addStation);

        stationIndex.addConnection(new ArrayList<>(Arrays.asList(stationIndex.getStation("баррикадная"), stationIndex.getStation("краснопресненская"))));
        stationIndex.addConnection(new ArrayList<>(Arrays.asList(stationIndex.getStation("парк культуры"), stationIndex.getStation("парк культурыС"))));

        return stationIndex;
    }

    @Test
    public void testGetLine() {

        var stationIndex = addStationsAndLinesAndConnectionsToStationIndex(stationss, lines);

        var actual = stationIndex.getLine(1).toString() + ';' +
                stationIndex.getLine(5).toString() + ';' +
                stationIndex.getLine(7).toString();


        try {
            stationIndex.getLine(999);
        } catch (Exception ex) {
            actual += ex.getMessage(); //почему-то нет исключения...
        }

        Assert.assertEquals("Сокольническая - 1;Кольцевая - 5;Таганско-краснопресненская - 7", actual);
    }


    @Test
    public void testGetStation() {
        var stationIndex = addStationsAndLinesAndConnectionsToStationIndex(stationss, lines);

        var actual = stationIndex.getStation("киевская").toString() + ';' +
                stationIndex.getStation("спортивная").toString() + ';' +
                stationIndex.getStation("беговая").toString() + ';';

        try {
            stationIndex.getStation("Сокольническая", 112);


        } catch (NullPointerException ex) {
            actual += ex.getMessage();
        }


        Assert.assertEquals("киевская - Кольцевая;спортивная - Сокольническая;беговая - Таганско-краснопресненская;" +
                "Cannot invoke \"core.Line.compareTo(core.Line)\" because \"this.line\" is null", actual);
    }


    @Test
    public void testGetConnection() {
        var stationIndex = addStationsAndLinesAndConnectionsToStationIndex(stationss, lines);

        String actual = stationIndex.getConnectedStations(stationIndex.getStation("баррикадная")) + "\n" +
                stationIndex.getConnectedStations(stationIndex.getStation("парк культурыС")) + "\n";
        try {

            stationIndex.getConnectedStations(stationIndex.getStation("баррикадна"));

        } catch (NullPointerException ex) {
            actual += ex.getMessage();
        }

        String expected = "[краснопресненская - Кольцевая]\n" +
                "[парк культуры - Кольцевая]\n" +
                "null";

        assertEquals("Тест выполнился не верно", expected, actual);

    }


}
