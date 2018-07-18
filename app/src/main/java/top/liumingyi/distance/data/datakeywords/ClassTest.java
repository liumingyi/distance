package top.liumingyi.distance.data.datakeywords;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ClassTest {

  public static void main(String args[])
      throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
      InvocationTargetException, InstantiationException {
    //Class<?> clazz = Class.forName("top.liumingyi.distance.data.datakeywords.A");
    //Constructor<?> constructor = clazz.getConstructor(String.class);
    //A a = (A) constructor.newInstance(new Object[] { "Mingyi" });
    //System.out.println(a.name);
    A a = new A();
    List<String> ls = a.getLs();

    for (String s : ls) {
      System.out.println(s);
    }

    a.remove(1);
    System.out.println();

    for (String s : ls) {
      System.out.println(s);
    }
  }

  static class A {
    List<String> ls = new ArrayList<>();

    public A() {
      ls.add("Liu");
      ls.add("Ming");
      ls.add("yi");
    }

    public List<String> getLs() {
      return ls;
    }

    void remove(int i) {
      ls.remove(i);
    }
  }
}
