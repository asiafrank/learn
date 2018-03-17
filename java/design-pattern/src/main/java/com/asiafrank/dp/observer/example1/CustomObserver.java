package com.asiafrank.dp.observer.example1;

public class CustomObserver {
    // start
    public static void main(String[] args) {
        Initiater initiater = new Initiater();
        Responder responder = new Responder();
        initiater.addListener(responder);
        initiater.notifyAllListeners();
    }
}
