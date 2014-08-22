package cz.sam.cubix.render.shader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import cz.sam.cubix.util.Vector2f;
import cz.sam.cubix.util.Vector3f;
import cz.sam.cubix.util.Vector4f;

public class ShaderProgram {
	
	public static final int VERTEX_SHADER = GL20.GL_VERTEX_SHADER;
	public static final int FRAGMENT_SHADER = GL20.GL_FRAGMENT_SHADER;
	
	protected int program;
	protected int vert;
	protected int frag;
	protected String vertShaderSource;
	protected String fragShaderSource;
	private String log;
	
	public ShaderProgram(String vertexShaderSource, String fragShaderSource) {
		this.vertShaderSource = vertexShaderSource;
		this.fragShaderSource = fragShaderSource;
	}
	
	public void init() {
		this.vert = this.compileShader(ShaderProgram.VERTEX_SHADER, this.vertShaderSource);
		this.frag = this.compileShader(ShaderProgram.FRAGMENT_SHADER, this.fragShaderSource);
		this.program = GL20.glCreateProgram();
		GL20.glAttachShader(this.program, this.vert);
		GL20.glAttachShader(this.program, this.frag);
		GL20.glLinkProgram(this.program);
		GL20.glValidateProgram(this.program);
	}
	
	public void use() {
		GL20.glUseProgram(this.program);
	}
	
	public void disable() {
		GL20.glUseProgram(0);
	}
	
	public void dispose() {
		GL20.glDetachShader(this.program, this.vert);
		GL20.glDeleteShader(this.vert);
		GL20.glDetachShader(this.program, this.frag);
		GL20.glDeleteShader(this.frag);
		GL20.glDeleteProgram(this.program);
	}
	
	private int compileShader(int type, String source) {
		int shader = GL20.glCreateShader(type);
		GL20.glShaderSource(shader, source);
		GL20.glCompileShader(shader);
		int comp = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS);
		int len = GL20.glGetShaderi(shader, GL20.GL_INFO_LOG_LENGTH);
		String errorShadertype = this.shaderTypeString(type);
		String error = GL20.glGetShaderInfoLog(shader, len);
		
		if(error != null && error.length() != 0) {
			this.log += errorShadertype + " compile log:\n" + error + "\n";
		}
		
		if(comp == GL11.GL_FALSE) {
			try {
				throw new Exception(log.length() != 0 ? this.log : "Could not compile " + this.shaderTypeString(type));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		GL20.glValidateProgram(shader);
		
		return shader;
	}
	
	public void setUniform2f(String name, Vector2f vector) {
		GL20.glUniform2f(GL20.glGetUniformLocation(this.frag, name), vector.getX(), vector.getY());
	}
	
	public void setUniform3f(String name, Vector3f vector) {
		GL20.glUniform3f(GL20.glGetUniformLocation(this.frag, name), vector.getX(), vector.getY(), vector.getZ());
	}
	
	public void setUniform4f(String name, Vector4f vector) {
		GL20.glUniform4f(GL20.glGetUniformLocation(this.frag, name), vector.getX(), vector.getY(), vector.getZ(), vector.getD());
	}
	
	public void setUniformi(String name, int i) {
		GL20.glUniform1i(GL20.glGetUniformLocation(this.frag, name), i);
	}
	
	private String shaderTypeString(int type) {
		if(type == ShaderProgram.FRAGMENT_SHADER) {
			return "FRAGMENT_SHADER";
		} else if(type == ShaderProgram.VERTEX_SHADER) {
			return "VERTEX_SHADER";
		} else if(type == GL32.GL_GEOMETRY_SHADER) {
			return "GEOMETRY_SHADER";
		} else {
			return "shader";
		}
	}
	
	public int getVertexShaderID() {
		return this.vert;
	}
	
	public int getFragmentShaderID() {
		return this.frag;
	}
	
	public int getProgramID() {
		return this.program;
	}
	
}
