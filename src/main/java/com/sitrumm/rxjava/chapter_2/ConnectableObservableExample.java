package com.sitrumm.rxjava.chapter_2;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

/**
 * form of hot observable, makes cold to hot
 */
public class ConnectableObservableExample {

    public static void main (String[] args) {
        ConnectableObservable<String> source = Observable.just("Alpha", "Beta", "Gamma").publish();
        source.subscribe(s -> System.out.println("Observer 1: " + s));
        source.map(String::length)
                .subscribe(s -> System.out.println("Observer 2: " + s));
        // ConnectableObservable needs connect() to start it, unlike Cold and Hot where subscribe is enough
        source.connect();
    }

}
