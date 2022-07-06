import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.*;


public class Main {

    static final Flight.Type TYPE_FLIGHT_SEARCH = Flight.Type.DEPARTURE;

    public static void main(String[] args) {

        findPlanesLeavingInTheNextTwoHours(Airport.getInstance()).forEach(System.out::println);

    }

    public static List<Flight> findPlanesLeavingInTheNextTwoHours(Airport airport) {


        //получаем стрим терминалов, в каждом терминале стрим полетов, разворачиваем эти стримы в переменную flight

        var flight = airport.getTerminals().stream()
                .flatMap(t -> t.getFlights()
                        .stream()
                );


        flight = flight.filter(f -> f.getType().equals(TYPE_FLIGHT_SEARCH)); //фильтруем по типу departure

        //1. фильтр по дате не более чем через 2 часа от сейчас в departure
        //2. фильтр по дате от сейчас в списке departure и  времени +2 часа
        //(диапозон от сейчас до сейчас+2)
        flight = flight.filter(f -> getLocalHour(f).isBefore(LocalDateTime.now().plusHours(2)));
        flight = flight.filter(f -> getLocalHour(f).isAfter(LocalDateTime.now()));

        //Вовращаем отфильтрованные значение превращая стрим в коллекцию

        return flight.sorted(Comparator.comparing(Flight::getDate)).collect(Collectors.toList());

    }

    public static LocalDateTime getLocalHour(Flight flight) {
        return flight.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }


}