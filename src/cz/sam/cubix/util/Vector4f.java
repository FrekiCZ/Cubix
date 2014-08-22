package cz.sam.cubix.util;

public class Vector4f {
	
	private float x;
	private float y;
	private float z;
	private float d;
	
	public Vector4f() {
		
	}
	
	public Vector4f(float x, float y, float z, float d) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.d = d;
	}
	
	public float length() {
		return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.d * this.d);
	}
	
	public void addVector(float x, float y, float z, float d) {
		this.x += x;
		this.y += y;
		this.z += z;
		this.d += d;
	}
	
	public Vector4f addVector(Vector4f r) {
		return new Vector4f(this.x + r.getX(), this.y + r.getY(), this.z + r.getZ(), this.d + r.getD());
	}
	
	public Vector3f sub(Vector3f r) {
		return new Vector3f(this.x - r.getX(), this.y - r.getY(), this.z - r.getZ());
	}
	
	public Vector3f sub(float r) {
		return new Vector3f(this.x - r, this.y - r, this.z - r);
	}
	
	public Vector3f cross(Vector3f r) {
		float x_ = this.y * r.getZ() - this.z * r.getY();
		float y_ = this.z * r.getX() - this.x * r.getZ();
		float z_ = this.x * r.getY() - this.y * r.getX();
		return new Vector3f(x_, y_, z_);
	}
	
	public Vector3f normalized() {
		float length = length();
		return new Vector3f(this.x / length, this.y / length, this.z / length);
	}
	
	public void addX(float x) {
		this.x += x;
	}
	
	public void addY(float y) {
		this.y += y;
	}
	
	public void addZ(float z) {
		this.z += z;
	}
	
	public void minX(float x) {
		this.x -= x;
	}
	
	public void minY(float y) {
		this.y -= y;
	}
	
	public void minZ(float z) {
		this.z -= z;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}
	
	public float getD() {
		return this.d;
	}
	
	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	public void setD(float d) {
		this.d = d;
	}
	
}
