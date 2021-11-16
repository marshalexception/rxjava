package chapter_4;

import io.reactivex.rxjava3.core.Observable;

public class GroupingOperators {

    public static void main(String[] args) {
        Observable<String> stringObservable = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");

        stringObservable
                .groupBy(String::length)
                .flatMapSingle(Observable::toList)
                .subscribe(System.out::println);

        System.out.println();

        stringObservable
                .groupBy(String::length)
                .flatMapSingle(group -> group.reduce("", (i, s) -> i.equals("") ? s : i + ", " + s)
                        .map(s -> group.getKey() + ": " + s))
                .subscribe(System.out::println);

    }

}
