package com.dubhe;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@Threads(2)
public class ArrayTest {

  @Benchmark
  public long shift() {
    long t = 455565655225562L;
    long a = 0;
    for (int i = 0; i < 1000; i++) {
      a = t >> 30;
    }
    return a;
  }

  @Benchmark
  public long div() {
    long t = 455565655225562L;
    long a = 0;
    for (int i = 0; i < 1000; i++) {
      a = t / 1024 / 1024 / 1024;
    }
    return a;
  }

  public static void main(String[] args) throws Exception {
    Options opts = new OptionsBuilder()
        .include(ArrayTest.class.getSimpleName())
        .resultFormat(ResultFormatType.JSON)
        .build();
    new Runner(opts).run();
  }
}
