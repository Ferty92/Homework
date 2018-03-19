package ru.sberbank.homework.Polushin.hw3_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UrlLoader {
    public void readPage(String inputUrl) throws IOException {
        URL url = new URL(inputUrl);

        try (BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
        }
        System.out.println("End reading");

    }
}