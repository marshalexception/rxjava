package chapter_2;

import io.reactivex.rxjava3.core.Observable;

public class ObservableChapter {

    public static void main (String[] args) {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma");
        // use lambda expressions instead of creating own Observer, e.g.:
        // Observer<Integer> myObserver = new Observer<>() {...}
        source.map(String::length)
                .filter(wordLength -> wordLength > 4)
                .subscribe(System.out::println,
                        Throwable::printStackTrace,
                        () -> System.out.println("Finished!"));
    }

}
