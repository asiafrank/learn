package com.asiafrank.dp.observer.example4;

import java.util.Observable;
import java.util.Scanner;

/**
 * Observer Pattern using Observable
 */
public class EventSource extends Observable implements Runnable {
    public void run() {
        while (true) {
            String response = new Scanner(System.in).next();
            setChanged();
            notifyObservers(response);
        }
    }

    public static void main(String[] args) {
        System.out.println("Enter Text");
        EventSource eventSource = new EventSource();

        eventSource.addObserver((Observable obj, Object arg) -> {
            System.out.println("Received Response: " + arg);
        });

        new Thread(eventSource).start();
    }
}
