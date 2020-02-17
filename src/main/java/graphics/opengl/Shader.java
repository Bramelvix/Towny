package graphics.opengl;

import util.vectors.Vec4f;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
	private static int VERT_SHADER = 0;
	private static int FRAG_SHADER = 1;
	private static int SHADER_PROGRAM = 2;

	protected String log = "";

	int ID;
	HashMap<String, Integer> uniforms = new HashMap<>();

	//TODO make a shader/opengl exception type
	public Shader(String vertexShaderSource, String fragmentShaderSource) throws Exception {
		int vert = glCreateShader(GL_VERTEX_SHADER);
		int frag = glCreateShader(GL_FRAGMENT_SHADER);
		if(vert == 0) throw new Exception("Couldn't create a vertex shader");
		if(frag == 0) throw new Exception("Couldn't create a fragment shader");

		glShaderSource(vert, vertexShaderSource);
		glCompileShader(vert);
		checkCompileErrors(vert, VERT_SHADER);

		glShaderSource(frag, fragmentShaderSource);
		glCompileShader(frag);
		checkCompileErrors(frag, FRAG_SHADER);

		ID = glCreateProgram();
		if(ID == 0) throw new Exception("Couldn't create a shader program");

		glAttachShader(ID, vert);
		glAttachShader(ID, frag);
		glLinkProgram(ID);
		checkCompileErrors(ID, SHADER_PROGRAM);

		glDeleteShader(vert);
		glDeleteShader(frag);
	}

	public Shader(Path vert, Path frag) throws Exception {
		this(new String(Files.readAllBytes(vert)), new String(Files.readAllBytes(frag)));
	}

	public void use() {
		glUseProgram(ID);
	}

	public void setUniform(String name, boolean value) {
		glUniform1i(uniforms.get(name), value ? 1 : 0);
	}

	public void setUniform(String name, int n) {
		glUniform1i(uniforms.get(name), n);
	}
	public void setUniform(String name, int n1, int n2) {
		glUniform2i(glGetUniformLocation(ID, name), n1, n2);
	}
	public void setUniform(String name, int n1, int n2, int n3) {
		glUniform3i(uniforms.get(name), n1, n2, n3);
	}
	public void setUniform(String name, int n1, int n2, int n3, int n4) {
		glUniform4i(uniforms.get(name), n1, n2, n3, n4);
	}

	public void setUniform(String name, float n) {
		glUniform1f(uniforms.get(name), n);
	}
	public void setUniform(String name, float n1, float n2) {
		glUniform2f(glGetUniformLocation(ID, name), n1, n2);
	}
	public void setUniform(String name, float n1, float n2, float n3) {
		glUniform3f(uniforms.get(name), n1, n2, n3);
	}
	public void setUniform(String name, float n1, float n2, float n3, float n4) {
		glUniform4f(uniforms.get(name), n1, n2, n3, n4);
	}

	public void setUniform(String name, Vec4f v) {
		glUniform4f(glGetUniformLocation(ID, name), v.x, v.y, v.z, v.w);
	}

	private void checkCompileErrors(int id, int type) throws Exception {
		if(type == SHADER_PROGRAM) {
			int len = glGetProgrami(id, GL_INFO_LOG_LENGTH);
			String err = glGetProgramInfoLog(id, len);
			if (err.length() != 0)
				log = err + "\n" + log;
			if (log != null)
				log = log.trim();
			if(glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
				throw new Exception(log.length()!=0 ? log : "Could not link program");
			}
		} else {
			String s_type = "";
			if(type == VERT_SHADER) s_type = "vertex shader";
			else if(type == FRAG_SHADER) s_type = "fragment shader";

			int len = glGetShaderi(id, GL_INFO_LOG_LENGTH);
			String err = glGetShaderInfoLog(id, len);
			if (err.length() != 0)
				log += s_type + " compile log:\n" + err + "\n";
			if(glGetShaderi(id, GL_COMPILE_STATUS) != GL_TRUE) {
				throw new Exception(log.length()!=0 ? log : "Could not compile "+s_type);
			}
		}
	}
}
