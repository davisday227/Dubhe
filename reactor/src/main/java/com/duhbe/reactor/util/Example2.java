package com.duhbe.reactor.util;

import reactor.core.publisher.Flux;

public class Example2 {

  public static void main(String[] args) {
    Flux<Integer> ints = Flux.range(1, 5)
        .map(i -> {
          if (i <= 3) {
            return i;
          }
          throw new RuntimeException("got to 4");
        });

    ints.subscribe(i -> System.out.println(i),
        error -> System.out.println("Error: " + error)
    );

    ints.subscribe(i -> System.out.println(i),
        error -> System.out.println("Error: " + error),
        () -> System.out.println("Done")
    );
  }
}
