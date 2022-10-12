package com.dubhe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.Throughput) // 基准测试类型，吞吐量（单位时间内调用了多少次）
@OutputTimeUnit(TimeUnit.MILLISECONDS) // 基准测试结果的时间类型，毫秒
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS) // 预热，2轮，每轮1秒
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS) // 度量，测试5轮，每轮1秒
@Fork(1) // fork出1个线程来测试
@State(Scope.Thread) // 每个测试线程分配1个实例
public class ArrayAndLinkedJmhTest {
  private List<Object> arrayList;

  private List<Object> linkedList;

  @Param({"1000", "10000", "100000"}) // 分别测试不同个数的添加元素
  private int count;

  @Setup(Level.Trial)
  public void init() {
    arrayList = new ArrayList<>();
    linkedList = new LinkedList<>();
  }

  @Benchmark
  public void arrayListAddTest() {
    for (int i = 0; i < count; i++) {
      arrayList.add("Justin");
    }
  }

  @Benchmark
  public void linkedListAddTest() {
    for (int i = 0; i < count; i++) {
      linkedList.add("Justin");
    }
  }

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(ArrayAndLinkedJmhTest.class.getSimpleName())
        .resultFormat(ResultFormatType.JSON)
        .build();

    new Runner(opt).run();
  }
}
