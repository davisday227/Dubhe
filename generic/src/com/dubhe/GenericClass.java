package com.dubhe;

public class GenericClass <Q> {

  public Q getGenericValue(Q q) {
    return q;
  }

  public static void main(String[] args) {
    GenericClass strObject = new GenericClass<String>();
    System.out.println(strObject.getGenericValue("test"));

    GenericClass intObject = new GenericClass<Integer>();
    System.out.println(intObject.getGenericValue("sdf"));

    GenericClass<Integer> intObject2 = new GenericClass();
    System.out.println(intObject2.getGenericValue(123));
  }
}
