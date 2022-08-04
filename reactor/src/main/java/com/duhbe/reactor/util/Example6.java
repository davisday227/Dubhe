package com.duhbe.reactor.util;

import java.util.concurrent.atomic.AtomicInteger;
import reactor.core.publisher.Flux;

public class Example6 {

  public static void main(String[] args) {
    Flux<String> flux = Flux.generate(
        AtomicInteger::new,
        (state, sink) -> {
          long i = state.getAndIncrement();
          sink.next("3 X " + i + " = " + 3 * i);
          if (i == 10) {
            sink.complete();
          }
          return state;
        },
        (state) -> System.out.println("state " + state)
    );
    flux.subscribe();
  }

}
