import java.util.List;
import java.util.Map;

public class BookMyShow {
}

enum SeatCategory{
    PREMIUM,GOLD;
}
enum City{
    blr,del;
}

class Movie{
    int id;
    String name;
    List<Theatre> theaters;
}

class MovieController{
    Map<City,List<Movie>> cityMovieList;
    List<Movie>allMovies;
}
class Theatre{
List<Show>showsList;
int theatreId;
List<Audi>audis;
City city;
}
class TheatreController{
    Map<City,List<Theatre>>cityTheatreMap;
    List<Theatre>theatreList;
}
class Show{
    int id;
    Movie movie;
    int startTime;
    List<Seat>bookedSeats;
    Theatre theatre;
    Audi audi;

}
class Seat{
    int row;
    int col;
    SeatCategory category;
    int price;
}

class Audi{
    List<Seat>seat;
    int id;
}
class Booking{
    int bid;
    Payment paymentId;
    Seat seat;
    Show show;
}
class Payment{
    int id;
    boolean status;
    String paymentMetod;
}