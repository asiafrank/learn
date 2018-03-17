package com.asiafrank.dp.observer.example1;

class Responder implements EventListener {
    @Override
    public void handle() {
        System.out.println("Hello there! Try to handle something.");
    }
}