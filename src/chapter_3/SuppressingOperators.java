package chapter_3;

import io.reactivex.rxjava3.core.Observable;

public class SuppressingOperators {

    public static void main (String[] args) {
        // operators that suppress emissions and not call the onNext() function

        Observable<String> stringObservable = Observable.just("Alpha", "Beta", "Gamma");
        Observable<Integer> numberObservable = Observable.just(1, 1, 1, 2, 2, 3, 3, 2, 1, 1);

        // filter()
        stringObservable.filter(s -> s.length() != 5)
                .subscribe(s -> System.out.println("[FILTER] Received: " + s));

        // take()
        stringObservable.take(2)
                .subscribe(s -> System.out.println("[TAKE] Received: " + s));
        // other overloaded take()-method takes time duration instead of number of emissions as parameter

        // skip()
        stringObservable.skip(2)
                .subscribe(s -> System.out.println("[SKIP] Received: " + s));
        // also has overloaded method that takes time duration

        // distinct()
        stringObservable.map(String::length)
                .distinct()
                .subscribe(s -> System.out.println("[DISTINCT-A] Received: " + s));
        stringObservable.distinct(String::length)
                .subscribe(s -> System.out.println("[DISTINCT-B] Received: " + s));

        // distinctUntilChanged()
        numberObservable.distinctUntilChanged()
                .subscribe(s -> System.out.println("[DISTINCT_UNTIL_CHANGED-A] Received: " + s));

        // elementAt()
        stringObservable.elementAt(2)
                .subscribe(s -> System.out.println("[ELEMENT_AT] Received: " + s));
        // elementAtOrError(), firstElement(), lastElement(), ...
    }
}
