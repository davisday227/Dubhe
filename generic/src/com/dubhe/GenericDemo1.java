package com.dubhe;

public class GenericDemo1 {

  //静态方法使用同名泛型,和自定义泛型方法一样,必须声明
  //想要返回T类型或传递T类型的参数,则必须在返回之前重新声明
  public <T, K> void get3(T t, K k) {
    System.out.println(t);
    System.out.println(k);
  }

  //想要返回T类型或传递T类型的参数,则必须在返回之前重新声明
  public <T> T get4() {
    return (T) "null";
  }

  public static void main(String[] args) {
    GenericDemo1 genericObj = new GenericDemo1();
    genericObj.get3("1", "2");

    System.out.println((String) genericObj.get4());
  }
}
