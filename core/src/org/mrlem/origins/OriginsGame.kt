package org.mrlem.origins

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

// TODO - critical - scene: 3D viewport
// TODO - critical - scene: more complex scene
// TODO - medium - more complex ui / theme

class OriginsGame : ApplicationAdapter() {

    lateinit var stage: Stage
    lateinit var table: Table

    override fun create() {
        stage = Stage(ScreenViewport())
        Gdx.input.inputProcessor = stage

        table = Table()
        table.setFillParent(true)
        stage.addActor(table)

        val uiSkin = Skin(Gdx.files.internal("uiskin.json"))
        val button1 = TextButton("Press me!", uiSkin)
        button1.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                println("plop")
            }
        })
        table.add(button1)
    }

    override fun render() {
        val delta = Gdx.graphics.deltaTime

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        stage.dispose()
    }

}
