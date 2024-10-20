package main;

import GUI.MainFrameClass;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {

      IMDB imdb = IMDB.getInstance();

      imdb.run();

    }
}