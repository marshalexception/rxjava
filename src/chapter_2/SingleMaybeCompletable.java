package chapter_2;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.ResourceObserver;

import java.util.concurrent.TimeUnit;

public class SingleMaybeCompletable {

    public static void main (String[] args) {
        //--------------------------------- Single ---------------------------------
        Single.just("Hello!")
                .map(String::length)
                .subscribe(System.out::println,
                        e -> System.out.println("Error captured: " + e));

        // Single to Observable: toObservable()
        // Observable to Single: .first()

        Observable<String> stringObservable = Observable.just("Alpha", "Beta");
        stringObservable.first("Nil").subscribe(System.out::println);


        //--------------------------------- Maybe ---------------------------------

        // Maybe = Single +  it allows no emissions to occur
        // emissions
        Maybe<Integer> maybeWithEmissions = Maybe.just(100);
        maybeWithEmissions.subscribe(s -> System.out.println("First Maybe: " + s),
                Throwable::printStackTrace,
                () -> System.out.println("First Maybe: Done!")); // observable would print this, too - but maybe emits 1 or 0 items therefore called implicitly
        // no emissions
        Maybe<Integer> maybeWithoutEmissions = Maybe.empty();
        maybeWithoutEmissions.subscribe(s -> System.out.println("Second Maybe: " + s),
                Throwable::printStackTrace,
                () -> System.out.println("Second Maybe: Done!"));

        // .firstElement() yields a Maybe
        stringObservable.firstElement()
                .subscribe(s -> System.out.println("Received: " + s),
                        Throwable::printStackTrace,
                        () -> System.out.println("Done!"));


        //--------------------------------- Completable ---------------------------------

        // does not receive any emissions
        Completable.fromRunnable(() -> runProcess()).subscribe(() -> System.out.println("Completable done!"));


        //--------------------------------- Disposable ---------------------------------

        // .subscribe() returns Disposable - link between Observable and Observer
        // .dispose() to stop emissions and dispose all resources
        Observable<Long> disposableObservable = Observable.interval(1, TimeUnit.SECONDS);
        Disposable disposable = disposableObservable.subscribe(l -> System.out.println("Received: " + l));
        // sleep 3 seconds
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disposable.dispose();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Disposable isDisposed = " + disposable.isDisposed());

        // Disposable withiin an Observer
        // instead of using .subscribe() use .subscribeWith(observer) - results in default Disposable handling
        Observable<Long> anotherDisposableObservable = Observable.interval(1, TimeUnit.SECONDS);
        ResourceObserver<Long> resourceObserver = new ResourceObserver<Long>() {
            @Override
            public void onNext(@NonNull Long aLong) {
                System.out.println(aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Done!");
            }
        };
        ResourceObserver<Long> longResourceObserver = anotherDisposableObservable.subscribeWith(resourceObserver);
        longResourceObserver.dispose();

        // CompositeDisposable to manage several subscriptions
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Disposable disposable1 = anotherDisposableObservable.subscribe(s -> System.out.println("Observer 1: " + s));
        Disposable disposable2 = anotherDisposableObservable.subscribe(s -> System.out.println("Observer 2: " + s));
        compositeDisposable.addAll(disposable1, disposable2);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        compositeDisposable.dispose();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("CompositeDisposable isDisposed = " + compositeDisposable.isDisposed());

        Observable.create(source -> {

        });
    }

    private static void runProcess() {
        // run process here
    }
}
