package com.sitrumm.rxjava.chapter_2;

import io.reactivex.rxjava3.core.Observable;

public class ColdObservable {

    /**
     * like a cd - resembles finite datasets
     */
    public static void main (String[] args) {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma");

        // first observer
        source.subscribe(s -> System.out.println("Observer 1: " + s));

        // second observer
        source.map(String::length)
                .filter(wordLength -> wordLength >= 5)
                .subscribe(s -> System.out.println("Observer 2: " + s));
    }

}
