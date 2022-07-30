package com.duhbe.reactor.util;

import reactor.core.publisher.Flux;

public class Example3 {

  public static void main(String[] args) {
    Flux<Integer> ints = Flux.range(1, 100);
    ints.subscribe(i -> System.out.println(i),
        error -> System.out.println("Error, " + error),
        () -> System.out.println("Done"),
        sub -> sub.request(10));
  }

}
