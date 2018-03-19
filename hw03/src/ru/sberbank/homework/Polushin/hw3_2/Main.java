package ru.sberbank.homework.Polushin.hw3_2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Please input URL or quit for exit program.");
        UrlLoader urlLoader = new UrlLoader();
        String inputUrl;

        try (Scanner scanner = new Scanner(System.in)) {

            while (!(inputUrl = scanner.nextLine()).equals("quit")) {
                try {
                    urlLoader.readPage(inputUrl);
                } catch (MalformedURLException incorrectUrl) {
                    System.out.println("You input incorrect URL. Please try again.");
                } catch (IOException e) {
                    System.out.println("Resources are unavailable . Please try again later.");
                }
            }
        }

    }
}
