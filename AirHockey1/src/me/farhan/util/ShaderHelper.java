package me.farhan.util;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import android.util.Log;

public class ShaderHelper {
	public static final String TAG = ShaderHelper.class.getName();

	public static  int compileVertexShader(String shaderCode)
	{
		return compileShader(GL_VERTEX_SHADER, shaderCode);
	}

	public static  int compileFragmentShader(String shaderCode)
	{
		return compileShader(GL_FRAGMENT_SHADER, shaderCode);
	}

	private static int compileShader(int type, String shaderCode) 
	{
		final int shaderObjectId = glCreateShader(type);
		if(shaderObjectId == 0)
		{
			if(LoggerConfig.ON)
			{
				Log.w(TAG, "could not create new shader");
			}
			return 0;
		}
		glShaderSource(shaderObjectId, shaderCode);

		final int [] compilerStatus = new int[1];

		glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS,compilerStatus, 0);
		
		if (LoggerConfig.ON) {
			// Print the shader info log to the Android log output.
			Log.v(TAG, "Results of compiling source:" + "\n" + shaderCode + "\n:"
					+ glGetShaderInfoLog(shaderObjectId));
		}
		
		if(LoggerConfig.ON)
		{
			if(compilerStatus[0] == 0)
			{
				glDeleteShader(shaderObjectId);
				Log.w(TAG, "complilation of shader failed");
			}
		}

		return shaderObjectId;
	}
	
	public static int linkProgram(int vertexShaderId,int fragmentShaderId)
	{
		final int programId = glCreateProgram();
		
		if(programId == 0)
		{
			if(LoggerConfig.ON)
			{
				Log.w(TAG, "could not create new program");
			}
			return 0;
		}
		glAttachShader(programId, vertexShaderId);
		glAttachShader(programId, fragmentShaderId);
		
		glLinkProgram(programId);
		
		final int [] linkStatus = new int[1];
		glGetProgramiv(programId, GL_LINK_STATUS, linkStatus,0);
		
		if (LoggerConfig.ON) {
			// Print the shader info log to the Android log output.
			Log.v(TAG, "Results of compiling source:" + "\n" + programId + "\n:"
					+ glGetProgramInfoLog(programId));
		}
		
		if(LoggerConfig.ON)
		{
			if(linkStatus[0] == 0)
			{
				glDeleteProgram(programId);
				Log.w(TAG, "complilation of shader failed");
			}
		}
		return programId;
	}
}
