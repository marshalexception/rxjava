package chapter_3;

import io.reactivex.rxjava3.core.Observable;

public class ConditionalOperators {

    public static void main (String[] args) {
        Observable<Integer> rangeObservable = Observable.range(1, 1000);
        Observable<String> stringObservable = Observable.just("Alpha", "Beta");

        // takeWhile
        rangeObservable.takeWhile(i -> i < 10)
                .subscribe(i -> System.out.println("Received: " + i),
                        Throwable::printStackTrace,
                        () -> System.out.println("All emissions done or found one which does not fit " +
                                "takeWhile condition and calls onComplete event"));

        // skipWhile
        rangeObservable.skipWhile(i -> i < 995)
                .subscribe(i -> System.out.println("Received: " + i),
                        Throwable::printStackTrace,
                        () -> System.out.println("All emissions done"));

        // defaultIfEmpty
        stringObservable.filter(s -> s.startsWith("Z"))
                .defaultIfEmpty("None") // only used when no emission occurs
                .subscribe(System.out::println);

        // switchIfEmpty - specifies a different observable if existing is empty
        stringObservable.filter(s -> s.startsWith("Z"))
                .switchIfEmpty(Observable.just("Zeta", "Eta", "Theta"))  // only used when no emission occurs
                .subscribe(System.out::println);
    }

}
