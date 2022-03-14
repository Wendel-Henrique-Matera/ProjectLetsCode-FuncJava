package br.com.MyProjects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reader {

    private final List<Actor> listMale;
    private final List<Actor> listFemale;

    Reader(){
        listMale = null;
        listFemale = null;
    }

    Reader(Path pathMale, Path pathFemale) {
        listMale = readArchive(pathMale);
        listFemale = readArchive(pathFemale);
    }

    private List<Actor> readArchive(Path path){

        try(Stream<String> fileLines = Files.lines(path)){
                return fileLines
                        .skip(1)
                        .map(Actor::fromLine)
                        .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    public List<Actor> getListMale() {
        return listMale;
    }

    public List<Actor> getListFemale() {
        return listFemale;
    }
}
