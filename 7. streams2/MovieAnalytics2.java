import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.IOException;
import java.io.BufferedReader;

public class MovieAnalytics2 extends Movie{

    private ArrayList<Movie> movies;

    public MovieAnalytics2()
    {
        super();
        this.movies = new ArrayList<>();
    }

    public static Consumer<Movie> showInfo() {

        return new Movie();

    }

    public void populateWithData(String fileName) throws IOException {

        try(var br = new BufferedReader(new FileReader(fileName)))
        {
            // “title;releaseYear;duration;genre;score;director”.
            List<Movie> temp = br.lines()
                    .map(line -> line.split(";"))
                    .map(line -> new Movie(line[0],
                            Integer.parseInt(line[1]),
                            Integer.parseInt(line[2]),
                            line[3],
                            Double.parseDouble(line[4]),
                            line[5]))
                    .toList(); // could be .collect(Collectors.toList())
            this.movies = new ArrayList<Movie>(temp);
        }
    }

    public void printCountByDirector(int n) {

        this.movies
                .stream()
                .collect(Collectors
                        .groupingBy(Movie::getDirector, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByKey())
                .sorted(Map.Entry.<String, Long>comparingByValue()
                        .reversed())
                .limit(n)
                .forEach(stringLongEntry -> System.out.println(stringLongEntry.getKey()
                        + ": " + stringLongEntry.getValue()
                        + " movies"));
    }

    public void printAverageDurationByGenre()
    {
        this.movies
                .stream()
                .collect(Collectors
                        .groupingBy(Movie::getGenre, Collectors
                                .collectingAndThen(Collectors
                                        .averagingDouble(Movie::getDuration), avgDuration -> avgDuration)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors
                        .toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new))
                .forEach((s, aDouble) -> System.out.println(s + ": " + String.format("%.2f", aDouble)));
    }

    public void printAverageScoreByGenre() {

        this.movies
                .stream()
                .collect(Collectors
                        .groupingBy(Movie::getGenre, Collectors
                                .collectingAndThen(Collectors
                                        .averagingDouble(Movie::getScore), avgScore -> avgScore)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.<String, Double>comparingByValue()
                        .reversed())
                .collect(Collectors
                        .toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new))
                .forEach((s, aDouble) -> System.out.println(s + ": " + String.format("%.2f", aDouble)));


    }

}