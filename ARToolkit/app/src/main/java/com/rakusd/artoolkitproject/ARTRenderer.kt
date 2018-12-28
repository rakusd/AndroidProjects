package com.rakusd.artoolkitproject

import android.opengl.GLES10
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
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


class ARTRenderer : ARRenderer() {

    lateinit var trackableUIDs:IntArray
    lateinit var cube:Cube
    var angle:Float=0f

    companion object {
        class Trackable(val name:String,val height:Float)

        val trackables:Array<Trackable> = arrayOf(
            Trackable("Alterra_Ticket_1.jpg",95.3f))
//        val trackables:Array<Trackable> = arrayOf(
//            Trackable("Alterra_Ticket_1.jpg", 95.3f),
//            Trackable("Alterra_Postcard_2.jpg", 95.3f),
//            Trackable("Alterra_Postcard_3.jpg", 127.0f),
//            Trackable("Alterra_Postcard_4.jpg", 95.3f))



    }

    override fun configureARScene(): Boolean {
        trackableUIDs= IntArray(trackables.size)
        for(i in 0 until trackables.size) {
            trackableUIDs[i]=ARToolKit.getInstance().addMarker("2d;Data/2d/"+trackables[i].name+";"+ trackables[i].height)
            if(trackableUIDs[i]<0)
                return false
        }
        NativeInterface.arwSetTrackerOptionInt(NativeInterface.ARW_TRACKER_OPTION_2D_MAX_IMAGES, trackableUIDs.size)
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
        angle+=5
        if(angle>360)
            angle-=360
        val sinus=sin(PI*(angle/180f)).toFloat()
        val cosinus=cos(PI*(angle/180f)).toFloat()
        GLES20.glEnable(GLES20.GL_CULL_FACE)
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        GLES20.glEnable(GLES20.GL_CCW)

        for(i in trackableUIDs) {
            if(ARToolKit.getInstance().queryMarkerVisible(i)) {
                var matrix:FloatArray=ARToolKit.getInstance().projectionMatrix
                var modelMatrix:FloatArray=ARToolKit.getInstance().queryMarkerTransformation(i)

                var model = FloatArray(16)
                model[0]=modelMatrix[0]*cosinus+modelMatrix[1]*sinus
                model[1]=modelMatrix[0]*(-sinus)+modelMatrix[1]*cosinus
                model[2]=modelMatrix[2]
                model[3]=modelMatrix[3]
                model[4]=modelMatrix[4]*cosinus+modelMatrix[5]*sinus
                model[5]=modelMatrix[4]*(-sinus)+modelMatrix[5]*cosinus
                model[6]=modelMatrix[6]
                model[7]=modelMatrix[7]
                model[8]=modelMatrix[8]*cosinus+modelMatrix[9]*sinus
                model[9]=modelMatrix[8]*(-sinus)+modelMatrix[9]*cosinus
                model[10]=modelMatrix[10]
                model[11]=modelMatrix[11]
                model[12]=modelMatrix[12] //modelMatrix[12]*cosinus+modelMatrix[13]*sinus
                model[13]=modelMatrix[13]//modelMatrix[12]*(-sinus)+modelMatrix[13]*cosinus
                model[14]=modelMatrix[14]
                model[15]=modelMatrix[15]


                //modelMatrix[0]=cosinus
                //modelMatrix[1]=-sinus
                //modelMatrix[2]=0f
                //modelMatrix[4]=sinus
                //modelMatrix[5]=cosinus
                //modelMatrix[6]=0f

                cube.draw(matrix,model)
            }
        }
    }
}
