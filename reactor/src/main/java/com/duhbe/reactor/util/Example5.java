package com.duhbe.reactor.util;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class Example5 {

  public static void main(String[] args) {
    Flux.range(1, 10)
        .doOnRequest(r -> System.out.println("request of " + r))
        .subscribe(new BaseSubscriber<Integer>() {
          @Override
          protected void hookOnSubscribe(Subscription subscription) {
            request(1);
          }

          @Override
          protected void hookOnNext(Integer value) {
            System.out.println("Cancelling after having received " + value);
            cancel();
          }
        });
  }
}
