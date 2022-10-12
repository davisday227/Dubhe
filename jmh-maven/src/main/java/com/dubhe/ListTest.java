package com.dubhe;

import java.beans.BeanProperty;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 5)
@Threads(4)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ListTest {
  @Param({"10000", "100000", "1000000", "10000000", "10000000"})
  private int size;

  @Benchmark
  public void arrayListDefaultSize() {
    List<Integer> a = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      a.add(1);
    }
    System.out.println(a.size());
  }

  @Benchmark
  public void arrayListSize() {
    List<Integer> a = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      a.add(1);
    }
    System.out.println(a.size());
  }

  @Benchmark
  public void linkedList() {
    List<Integer> a = new LinkedList<>();
    for (int i = 0; i < size; i++) {
      a.add(1);
    }
    System.out.println(a.size());
  }

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder().include(ListTest.class.getSimpleName())
        .forks(1)
        .resultFormat(ResultFormatType.JSON)
        .build();

    new Runner(opt).run();
  }
}
