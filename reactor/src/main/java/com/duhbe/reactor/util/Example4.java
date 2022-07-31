package com.duhbe.reactor.util;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class Example4<T> extends BaseSubscriber<T> {
  public static void main(String[] args) {
    SampleSubscriber<Integer> ss = new SampleSubscriber<>();
    Flux<Integer> ints = Flux.range(1, 5);
    ints.subscribe(ss);
  }

  public static class SampleSubscriber<T> extends BaseSubscriber<T> {

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
      System.out.println("subscribed");
      request(1);
    }

    @Override
    protected void hookOnNext(T value) {
      System.out.println(value);
      request(1);
    }

  }
}
