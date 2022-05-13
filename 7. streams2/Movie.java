import java.util.*;
import java.util.function.Consumer;

public class Movie implements Consumer<Movie> {

    private String title;
    private int releaseYear;
    private int duration;
    private String genre;
    private double score;
    private String director;

    public Movie(String title, int releaseYear, int duration, String genre, double score, String director) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.duration = duration;
        this.genre = genre;
        this.score = score;
        this.director = director;
    }

    public Movie() {

    }

    public String getTitle() {

        return this.title;
    }

    public int getReleaseYear() {

        return this.releaseYear;
    }

    public int getDuration() {

        return this.duration;
    }

    public String getGenre() {

        return this.genre;
    }

    public double getScore() {

        return this.score;
    }

    public String getDirector() {

        return this.director;
    }


    @Override
    public void accept(Movie t) {
        System.out.println(t.getTitle() + " (By " + t.getDirector() + ", " + t.getReleaseYear() + ")");
    }

    @Override
    public Consumer<Movie> andThen(Consumer<? super Movie> after) {
        return Consumer.super.andThen(after);
    }
}