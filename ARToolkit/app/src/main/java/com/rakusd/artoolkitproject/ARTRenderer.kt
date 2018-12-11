package com.rakusd.artoolkitproject

import android.opengl.GLES20

import org.artoolkit.ar6.base.ARToolKit
import org.artoolkit.ar6.base.NativeInterface
import org.artoolkit.ar6.base.rendering.ARRenderer
import org.artoolkit.ar6.base.rendering.shader_impl.Cube
import org.artoolkit.ar6.base.rendering.shader_impl.SimpleFragmentShader
import org.artoolkit.ar6.base.rendering.shader_impl.SimpleShaderProgram
import org.artoolkit.ar6.base.rendering.shader_impl.SimpleVertexShader

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class ARTRenderer : ARRenderer() {


    companion object {
        class Trackable(val name:String,val height:Float)

        val trackables:Array<Trackable> = arrayOf(
            Trackable("Alterra_Ticket_1.jpg", 95.3f),
            Trackable("Alterra_Postcard_2.jpg", 95.3f),
            Trackable("Alterra_Postcard_3.jpg", 127.0f),
            Trackable("Alterra_Postcard_4.jpg", 95.3f))



    }
    lateinit var trackableUIDs:IntArray
    lateinit var cube:Cube

    override fun configureARScene(): Boolean {
        trackableUIDs= IntArray(trackables.size)
        for(i in 0 until trackables.size) {
            trackableUIDs[i]=ARToolKit.getInstance().addMarker("2d;Data/2d/"+trackables[i].name+";"+ trackables[i].height)
            if(trackableUIDs[i]<0)
                return false
        }
        NativeInterface.arwSetTrackerOptionInt(NativeInterface.ARW_TRACKER_OPTION_2D_MAX_IMAGES, trackables.size)
        return true
    }

    override fun onSurfaceCreated(unused: GL10?, config: EGLConfig?) {
        shaderProgram= SimpleShaderProgram(SimpleVertexShader(),SimpleFragmentShader())
        cube=Cube(40.0f,0.0f,0.0f,0.0f)
        cube.setShaderProgram(shaderProgram)
        super.onSurfaceCreated(unused, config)
    }

    override fun draw() {
        super.draw()

        GLES20.glEnable(GLES20.GL_CULL_FACE)
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        GLES20.glEnable(GLES20.GL_CCW)

        for(i in trackableUIDs) {
            if(ARToolKit.getInstance().queryMarkerVisible(i)) {
                var matrix:FloatArray=ARToolKit.getInstance().projectionMatrix
                var modelMatrix:FloatArray=ARToolKit.getInstance().queryMarkerTransformation(i)
                cube.draw(matrix,modelMatrix)
            }
        }
    }
}
