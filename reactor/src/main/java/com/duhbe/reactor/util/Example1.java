package com.duhbe.reactor.util;

import java.util.Arrays;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Example1 {
  public static void main(String[] args) {
    Flux<String> seq1 = Flux.just("foo", "bar", "foobar");
    seq1.subscribe(System.out::println);

    List<String> iterable = Arrays.asList("foo", "bar", "foobar");
    Flux<String> seq2 = Flux.fromIterable(iterable);
    seq2.subscribe(System.out::println);

    Mono<String> noData = Mono.empty();
    noData.subscribe(x -> {
      System.out.println("get x: " + x);
    });

    Mono<String> data = Mono.just("foo");
    data.subscribe(x -> {
      System.out.println("get x: " + x);
    });

    Flux<Integer> numberFromFiveToSeven = Flux.range(5, 3);
    numberFromFiveToSeven.subscribe(System.out::println);

    Flux<Integer> ints = Flux.range(1, 5)
        .map(i -> {
          if (i <= 3 ) return i;
          throw new RuntimeException("got to 4");
        });

    ints.subscribe(i -> System.out.println(i),
        error -> System.out.println("Error: " + error));
  }
}
