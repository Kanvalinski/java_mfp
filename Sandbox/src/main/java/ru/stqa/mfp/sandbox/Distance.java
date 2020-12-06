package ru.stqa.mfp.sandbox;

public class Distance {
  public static void main(String[] args) {
    //создан объект класса Point -- точка p1
    Point p1 = new Point(-100, 32);
    System.out.println("Координаты точки p1 = (" + p1.x + ", " + p1.y + ")");
    //создан объект класса Point -- точка p2
    Point p2 = new Point(23, -2);
    System.out.println("Координаты точки p2 = (" + p2.x + ", " + p2.y + ")");
    System.out.println("Расстояние между двумя точками на плоскости p1 и p2 = " + p1.distance(p2));
  }
}
