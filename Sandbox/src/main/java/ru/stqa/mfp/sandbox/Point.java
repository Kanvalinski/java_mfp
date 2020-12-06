package ru.stqa.mfp.sandbox;

public class Point {
  public static double x1 = -100;
  public static double y1 = 32;
  public static double x2 = 23;
  public static double y2 = -2;
  public Point(double x1, double y1){
  }

 public static void main(String[] args) {
   //создан объект класса Point -- точка p1
   Point p1 = new Point(x1, y1);
   System.out.println("Координаты точки p1 = (" + x1 + ", " + y1 + ")");
   //создан объект класса Point -- точка p2
   Point p2 = new Point(y1, y2);
   System.out.println("Координаты точки p2 = (" + x2 + ", " + y2 + ")");
   System.out.println("Расстояние между двумя точками на плоскости p1 и p2 = "+distance(p1, p2));
 }
   public static double distance(Point p1, Point p2) {
     double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
     return distance;
   }
}
