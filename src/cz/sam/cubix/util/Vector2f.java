package cz.sam.cubix.util;

public class Vector2f {
	
	private float x;
	private float y;
	
	public Vector2f() {
		
	}
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float length() {
		return (float) Math.sqrt(this.x * this.x + this.y * this.y);
	}
	
	public void addVector(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public void addX(float x) {
		this.x += x;
	}
	
	public void addY(float y) {
		this.y += y;
	}
	
	public void minX(float x) {
		this.x -= x;
	}
	
	public void minY(float y) {
		this.y -= y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}
	
}
