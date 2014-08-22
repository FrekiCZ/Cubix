package cz.sam.cubix.util;

public class Glyph {
	
	private float width;
	private float height;
	private char Char;
	
	public Glyph() {
		
	}
	
	public Glyph(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	public Glyph(float width, float height, char Char) {
		this.width = width;
		this.height = height;
		this.Char = Char;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public char getChar() {
		return Char;
	}

	public Glyph setWidth(float width) {
		this.width = width;
		return this;
	}

	public Glyph setHeight(float height) {
		this.height = height;
		return this;
	}

	public Glyph setChar(char c) {
		Char = c;
		return this;
	}
	
}
