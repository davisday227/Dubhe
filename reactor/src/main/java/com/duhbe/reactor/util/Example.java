package com.duhbe.reactor.util;

import java.util.concurrent.atomic.AtomicLong;
import reactor.core.publisher.Flux;

public class Example {

  public static void main(String[] args) {
    Flux<String> flux = Flux.generate(
        () -> 0,
        (state, sink) -> {
          sink.next("3 X " + state + " = " + 3 * state);
          if (state == 10) {
            sink.complete();
          }
          return state + 1;
        }
    );
    flux.subscribe(x -> System.out.println(x));

    Flux<String> flux2 = Flux.generate(
      AtomicLong::new,
        (state, sink) -> {
          long i = state.getAndIncrement();
          sink.next("3 X " + i + " = " + 3 * i);
          if (i == 10) {
            sink.complete();
          }
          return state;
        }
    );

    flux2.subscribe(x -> System.out.println(x));
  }
}
