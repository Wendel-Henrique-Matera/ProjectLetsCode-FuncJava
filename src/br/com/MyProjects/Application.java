package br.com.MyProjects;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Application extends Reader{

    public static void main(String[] args) {

        Application app = new Application();
        Reader reader = new Reader(Path.of("oscar_file_male.csv"), Path.of("oscar_file_female.csv"));

        //Quem foi o ator mais jovem a ganhar um Oscar?
        System.out.println("Quem foi o ator mais jovem a ganhar um Oscar?\n");
        app.youngestActor(reader);

        System.out.println("\n");

        //Quem foi a atriz que mais vezes foi premiada?
        System.out.println("Quem foi a atriz que mais vezes foi premiada?\n");
        app.mostAwardedActress(reader);

        System.out.println("\n");

        //Qual atriz entre 20 e 30 anos que mais vezes foi vencedora?
        System.out.println("Qual atriz entre 20 e 30 anos que mais vezes foi vencedora?\n");
        app.youngestActressMostAwarded(reader);

        System.out.println("\n");

        //Quais atores ou atrizes receberam mais de um Oscar? Elabore uma única estrutura contendo atores e atrizes.
        System.out.println("Quais atores ou atrizes receberam mais de um Oscar? Elabore uma única estrutura contendo atores e atrizes.\n");
        app.actorsWithMoreThanOneOscar(reader);

        System.out.println("\n");

        //Quando informado o nome de um ator ou atriz, dê um resumo de quantos prêmios ele/ela recebeu e liste ano, idade e nome de cada filme pelo qual foi premiado(a).
        System.out.println("Quando informado o nome de um ator ou atriz, dê um resumo de quantos prêmios ele/ela recebeu e liste ano, idade e nome de cada filme pelo qual foi premiado(a).");
        app.actorSearch(reader);



    }

    void youngestActor(Reader reader){
        reader.getListMale().stream()
                .min(Comparator.comparingInt(Actor::getAge))
                .ifPresent(System.out::println);
    }

    void mostAwardedActress(Reader reader){
        Map<String, Long> map = reader.getListFemale().stream()
                .map(Actor::getName)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        map.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .ifPresent(actress -> System.out.println(actress.getKey() + " - " + actress.getValue() + " premiações."));
    }

    void youngestActressMostAwarded(Reader reader){
        List<Actor> youngActress =  reader.getListFemale().stream()
                .filter(actress -> actress.getAge() >= 20 && actress.getAge() <= 30)
                .toList();
        Map<String, Long> map = youngActress.stream()
                .map(Actor::getName)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .filter(actress -> actress.getValue() > 1)
                .forEachOrdered(System.out::println);
    }

    void actorsWithMoreThanOneOscar(Reader reader){

        List<Actor> winnerActors =  reader.getListMale();
        List<Actor> winnerActress =  reader.getListFemale();

        //Mapping Actors
        Map<String, Long> mapMale = winnerActors.stream()
                .map(Actor::getName)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        //Mapping Actress
        Map<String, Long> mapFemale = winnerActress.stream()
                .map(Actor::getName)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        System.out.println("Resultados: ");
        System.out.println("Homens com mais de um Oscar: \n");
        mapMale.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .filter(actress -> actress.getValue() > 1)
                .forEachOrdered(System.out::println);
        System.out.println("\n");
        System.out.println("Mulheres com mais de um Oscar: \n");
        mapFemale.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .filter(actress -> actress.getValue() > 1)
                .forEachOrdered(System.out::println);
    }

    void actorSearch(Reader reader){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Informe um nome de um ator/atriz");
        String searchValue = scanner.nextLine();

        if(reader.getListMale().stream().anyMatch(actor -> actor.getName().contains(searchValue))){
            List<Actor> result = reader.getListMale().stream()
                    .filter(actor -> actor.getName().equals(searchValue))
                    .toList();

            resultOrganizer(result, searchValue);
        } else if (reader.getListFemale().stream().anyMatch(actor -> actor.getName().contains(searchValue))){
                List<Actor> result = reader.getListFemale().stream()
                        .filter(actor -> actor.getName().equals(searchValue))
                        .toList();

                resultOrganizer(result, searchValue);
        }
    }

    void resultOrganizer(List<Actor> result, String searchValue){

        String[] yearsMovies = new String[result.size()];
        String[] ages = new String[result.size()];
        String[] movies = new String[result.size()];

        int i = 0;

        System.out.println(
                "O ator/atriz " + searchValue
                        + " recebeu um total de " + ages.length
                        + " prêmios.");

        while(i < result.size()){

            String line = String.valueOf(result.get(i));
            String[] split = line.split(", (\\w+=)|}");

            yearsMovies[i] = split[2];
            ages[i] = split[3];
            movies[i] = split[1];

            System.out.println("\nFilme " + (i+1) + " : " + movies[i] +
                               "\nAno do Filme " + (i+1) + " : " + yearsMovies[i] +
                               "\nIdade na premiação " + (i+1) + " : " + ages[i] + "\n");
            i++;
        }
    }
}
