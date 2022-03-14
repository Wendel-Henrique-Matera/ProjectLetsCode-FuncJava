package br.com.MyProjects;


import static java.lang.Integer.parseInt;

public class Actor {

    private String name;
    private String movie;
    private int yearMovie;
    private Integer Age;

    public Actor(int yearMovie, int age, String name, String movie) {

            this.yearMovie = yearMovie;
            this.Age = age;
            this.name = name;
            this.movie = movie;
    }

    public static Actor fromLine(String line){

        String[] split = line.split("; ");
        return new Actor(
                parseInt(split[1]),
                parseInt(split[2]),
                split[3],
                split[4]
        );

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public int getYearMovie() {
        return yearMovie;
    }

    public void setYearMovie(int yearMovie) {
        this.yearMovie = yearMovie;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "name='" + name + '\'' +
                ", movie='" + movie + '\'' +
                ", yearMovie=" + yearMovie +
                ", Age=" + Age +
                '}';
    }
}
