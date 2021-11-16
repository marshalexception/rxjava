package com.sitrumm.rxjava.chapter_4;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Concatenation similar to merging, but it fires off the source observables sequentially rather than all at once.
 */
public class ConcatOperators {

    public static void main(String[] args) throws InterruptedException {
        Observable<String> src1 = Observable.interval(1, TimeUnit.SECONDS)
                .take(2)
                .map(i -> i + 1)
                .map(i -> "Source 1: " + i + " seconds");
        Observable<String> src2 = Observable.interval(300, TimeUnit.MILLISECONDS)
                .map(i -> (i + 1) * 300)
                .map(i -> "Source 2: " + i + " milliseconds");

        Observable.concat(src1, src2)
                .subscribe(i -> System.out.println("Received: " + i));
        sleep(5000);
    }
}
