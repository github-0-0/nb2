package box.ascension.app.nb2.physics.collision.shapes;

public class Shapes {
    public interface IShape {}
    public record Circle(double radius) implements IShape {}
    public record Rectangle(double width, double height) implements IShape {}
}
