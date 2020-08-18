package graphics.opengl;

import util.vectors.Vec2f;
import util.vectors.Vec4f;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

	private final int ID;

	public Shader(URL vertexShaderSource, URL fragmentShaderSource) throws Exception {
		int vert = glCreateShader(GL_VERTEX_SHADER);
		int frag = glCreateShader(GL_FRAGMENT_SHADER);
		if(vert == 0) {
			throw new Exception("Couldn't create a vertex shader");
		}
		if(frag == 0) {
			throw new Exception("Couldn't create a fragment shader");
		}

		glShaderSource(vert, readFromFile(vertexShaderSource));
		glCompileShader(vert);
		checkCompileErrors(vert, ShaderType.VERT_SHADER);

		glShaderSource(frag, readFromFile(fragmentShaderSource));
		glCompileShader(frag);
		checkCompileErrors(frag, ShaderType.FRAG_SHADER);

		ID = glCreateProgram();
		if(ID == 0) {
			throw new Exception("Couldn't create a shader program");
		}

		glAttachShader(ID, vert);
		glAttachShader(ID, frag);
		glLinkProgram(ID);
		checkCompileErrors(ID, ShaderType.SHADER_PROGRAM);

		glDeleteShader(vert);
		glDeleteShader(frag);
	}

	public Shader(String vert, String frag) throws Exception {
		this(Shader.class.getResource(vert), Shader.class.getResource(frag));
	}

	private static String readFromFile(URL file) throws IOException {
		byte[] chunk = new byte[4096];
		int bytesRead;
		InputStream stream = file.openStream();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		while ((bytesRead = stream.read(chunk)) > 0) {
			outputStream.write(chunk, 0, bytesRead);
		}
		return new String(outputStream.toByteArray());
	}

	public void use() {
		glUseProgram(ID);
	}

	public void setUniform(String name, boolean value) {
		glUniform1i(glGetUniformLocation(ID, name), value ? 1 : 0);
	}

	public void setUniform(String name, int n) {
		glUniform1i(glGetUniformLocation(ID, name), n);
	}
	public void setUniform(String name, int n1, int n2) {
		glUniform2i(glGetUniformLocation(ID, name), n1, n2);
	}
	public void setUniform(String name, int n1, int n2, int n3) {
		glUniform3i(glGetUniformLocation(ID, name), n1, n2, n3);
	}
	public void setUniform(String name, int n1, int n2, int n3, int n4) {
		glUniform4i(glGetUniformLocation(ID, name), n1, n2, n3, n4);
	}

	public void setUniform(String name, float n) {
		glUniform1f(glGetUniformLocation(ID, name), n);
	}
	public void setUniform(String name, float n1, float n2) {
		glUniform2f(glGetUniformLocation(ID, name), n1, n2);
	}
	public void setUniform(String name, float n1, float n2, float n3) {
		glUniform3f(glGetUniformLocation(ID, name), n1, n2, n3);
	}
	public void setUniform(String name, float n1, float n2, float n3, float n4) {
		glUniform4f(glGetUniformLocation(ID, name), n1, n2, n3, n4);
	}
	public void setUniform(String name, Vec2f v) {
		glUniform2f(glGetUniformLocation(ID, name), v.x, v.y);
	}

	public void setUniform(String name, Vec4f v) {
		glUniform4f(glGetUniformLocation(ID, name), v.x, v.y, v.z, v.w);
	}

	private void checkCompileErrors(int id, ShaderType type) throws Exception {
		String log = "";
		if (type == ShaderType.SHADER_PROGRAM) {
			int len = glGetProgrami(id, GL_INFO_LOG_LENGTH);
			String err = glGetProgramInfoLog(id, len);
			if (!err.isEmpty()) {
				log = err + "\n" + log;
			}
			log = log.trim();
			if(glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
				throw new Exception(!log.isEmpty() ? log : "Could not link program");
			}
			return;
		}
		int len = glGetShaderi(id, GL_INFO_LOG_LENGTH);
		String err = glGetShaderInfoLog(id, len);
		if (!err.isEmpty()) {
			log += type.name()+ " compile log:\n" + err + "\n";
		}
		if(glGetShaderi(id, GL_COMPILE_STATUS) != GL_TRUE) {
			throw new Exception(!log.isEmpty() ? log : "Could not compile " + type.name());
		}
	}

	void destroy() {
		glDeleteShader(ID);
	}
}
