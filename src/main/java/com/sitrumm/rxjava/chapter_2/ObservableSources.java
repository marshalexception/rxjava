package com.sitrumm.rxjava.chapter_2;

import io.reactivex.rxjava3.core.Observable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class ObservableSources {

    private static int start = 1;
    private static int count = 3;

    public static void main(String[] args) {
        String[] emissions = new String[]{"Alpha", "Beta", "Gamma"};

        Observable<String[]> justObservable = Observable.just(emissions);
        Observable<Object> objectObservable = Observable.create(emitter -> { });
        Observable<String> iterableObservable = Observable.fromIterable(Arrays.asList(emissions));

        Observable<Integer> rangeObservable = Observable.range(5, 3); // equivalent rangeLong()
        rangeObservable.subscribe(s -> System.out.println("Current count: " + s),
                Throwable::printStackTrace,
                () -> System.out.println("Finished counting!"));

        Observable<Long> intervalObservable = Observable.interval(1, TimeUnit.SECONDS);
        intervalObservable.subscribe(s -> System.out.println(LocalDateTime.now().getSecond() + " " + s + " " + "Mississippi"));
        // timer runs on a separated thread, other than the main method
        // to keep the application running thread sleeps 3 seconds to see behaviour of observable
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Although event-driven, intervalObservable is a cold observable
        // To make it hot: ConnectableObservable
        intervalObservable.subscribe(l -> System.out.println("Observer 1: " + l));
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intervalObservable.subscribe(l -> System.out.println("Observer 2: " + l));
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Observable<Object> emptyObservable = Observable.empty();
        emptyObservable.subscribe(System.out::println,
                Throwable::printStackTrace,
                () -> System.out.println("Empty Observable Done!"));

        // Primarily used for testing
        Observable<Object> neverObservable = Observable.never();
        neverObservable.subscribe(System.out::println,
                Throwable::printStackTrace,
                () -> System.out.println("Never Observable Done!"));
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Primarily used for testing
        Observable<Object> errorObservable = Observable.error(new Exception("TestException"));
        errorObservable.subscribe(System.out::println,
                throwable -> System.out.println("Error: " + throwable),
                () -> System.out.println("Done!"));

        // Creates state for each observer, observable wont send emissions which are obsolete
        Observable<Integer> deferObservable = Observable.defer(() -> Observable.range(start, count));
        deferObservable.subscribe(s -> System.out.println("Observer 1: " + s));
        count = 5;
        deferObservable.subscribe(s -> System.out.println("Observer 2: " + s));

        // just would generate error before Observable was even created
        // fromCallable emittes the error to the Observer
        Observable<Integer> fromCallableObservable = Observable.fromCallable(() -> 1 / 0);
        fromCallableObservable.subscribe(System.out::println, throwable -> System.out.println("Error captured: " + throwable));
    }
}
