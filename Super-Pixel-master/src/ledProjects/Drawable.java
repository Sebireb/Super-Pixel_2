package ledProjects;

public interface Drawable {
	void draw();
	void clear();
	void collide();
	Enum getDrawableType();
	double getX();
	double getY();
}