package com.dubhe;

public class GenericDemo2<T> {

  //普通方法使用与类同名泛型,不需要声明
  //传递T类型参数,直接使用
  public void get1(T t) {

  }

  //返回T类型,可以直接使用
  public T get2() {
    return null;
  }
}
