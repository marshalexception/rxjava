package com.sitrumm.rxjava.chapter_4;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Merging Operator
 */
public class FlatMapOperator {

    public static void main(String[] args) throws InterruptedException {
        Observable.just(2, 3, 10, 7)
                .flatMap(i -> Observable.interval(i, TimeUnit.SECONDS)
                        .map(l -> i + "s interval: " + ((l + 1) * i) + " seconds elapsed"))
                .subscribe(System.out::println);
        sleep(12000);

        System.out.println("\n----------\n");

        Observable.just("Alpha", "Beta", "Gamma")
                .flatMap(s -> Observable.fromArray(s.split("")))
                .subscribe(System.out::println);

        System.out.println("\n----------\n");

        Observable.just("Alpha", "Beta", "Gamma")
                .flatMap(s -> Observable.fromArray(s.split("")), (key, value) -> key + "-" + value)
                .subscribe(System.out::println);
    }

}
