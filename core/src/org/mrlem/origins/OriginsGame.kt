package org.mrlem.origins

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.*
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight

// TODO - critical - scene: more complex scene
// TODO - medium - more complex ui / theme

class OriginsGame : ApplicationAdapter() {

    // ui
    lateinit var stage: Stage
    lateinit var table: Table

    // 3D view
    lateinit var camera: PerspectiveCamera
    lateinit var modelBatch: ModelBatch
    lateinit var modelBuilder: ModelBuilder
    lateinit var model: Model
    lateinit var modelInstance: ModelInstance
    lateinit var environment: Environment

    override fun create() {
        // setup 3D view
        // .. camera
        updateCamera()
        // .. model
        modelBatch = ModelBatch()
        modelBuilder = ModelBuilder()
        model = modelBuilder.createBox(
                2f, 2f, 2f,
                Material(ColorAttribute.createDiffuse(Color.BLUE)),
                (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal).toLong()
        )
        modelInstance = ModelInstance(model, 0f, 0f, 0f)
        // .. environment
        environment = Environment()
        environment.set(ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1.0f))
        environment.add(DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f))

        // setup ui
        val uiSkin = Skin(Gdx.files.internal("uiskin.json"))

        stage = Stage(ScreenViewport())
        table = Table()
        table.setFillParent(true)
        stage.addActor(table)
        val button1 = TextButton("Press me!", uiSkin)
        button1.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                println("plop")
            }
        })
        table.add(button1)

        // inputs
        Gdx.input.inputProcessor = stage
    }

    override fun render() {
        val delta = Gdx.graphics.deltaTime

        // clear
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        // render 3D view
//        camera.rotateAround(Vector3.Zero, Vector3(0f, 1f, 0f), 1f)
//        camera.update()
        modelInstance.transform.rotate(0f, 1f, 0f, 20f * delta)
        modelBatch.begin(camera)
        modelBatch.render(modelInstance, environment)
        modelBatch.end()

        // render ui
        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
        updateCamera()
    }

    override fun dispose() {
        stage.dispose()

        modelBatch.dispose()
        model.dispose()
    }

    private fun updateCamera() {
        camera = PerspectiveCamera(75f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()).apply {
            position.set(0f, 0f, 3f)
            lookAt(0f, 0f, 0f)
            near = 0.1f
            far = 300f
        }
        camera.update()
    }

}
