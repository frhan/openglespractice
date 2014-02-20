package me.farhan.airhockey1;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import me.farhan.util.ShaderHelper;
import me.farhan.util.TextResourceReader;
import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

public class AirHockeyRenderer  implements Renderer{

	public static final String TAG = AirHockeyRenderer.class.getName();

	public static final int POSITION_COMPONENT_COUNT = 2;
	private static final int BYTES_PER_FLOAT = 4;
	private final FloatBuffer vertexBuffer;

	private final Context context;
	float [] tableVerticesWithTriangle = {
			//Trianle 1
			0f,0f,
			9f,14f,
			0f,14f,
			//Trianle 1
			0f,0f,
			9f,0f,
			9f,14f
	};

	float [] tableCentreLine = {
			//line
			0f,7f,
			9f,7f
	};
	float [] mallets = {
			4.5f,2f,
			4.5f,12f
	};

	public AirHockeyRenderer(Context context) 
	{
		this.context = context;
		vertexBuffer = ByteBuffer.allocateDirect(tableVerticesWithTriangle.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		vertexBuffer.put(tableVerticesWithTriangle);

		final String vertexShaderString = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader);
		final String fragmentShaderString = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader);

		int vertexShader = ShaderHelper.compileVertexShader(vertexShaderString);
		int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderString);

	}

	@Override
	public void onDrawFrame(GL10 gl) 
	{
		glClear(GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) 
	{
		glViewport(0, 0, width, height);

	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{
		glClearColor(1.0f, 1.0f, 0.0f, 0.0f);

	}

}
