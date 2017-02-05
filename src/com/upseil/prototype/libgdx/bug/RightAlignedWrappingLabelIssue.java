package com.upseil.prototype.libgdx.bug;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class RightAlignedWrappingLabelIssue extends ApplicationAdapter {
    Stage stage;
    int lineCounter;

    public void create () {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table container = new Table();
        stage.addActor(container);
        container.setFillParent(true);
        container.defaults().space(10);
        
        BitmapFont font = new BitmapFont(Gdx.files.internal("font/BMF-15/lucida-console-15.fnt"));
        LabelStyle style = new LabelStyle(font, Color.WHITE);
        
        Label label = new Label("Hello World!", style);
        label.setWrap(true);
        label.setAlignment(Align.right);
        
        Label betterLabel = new Label("Hello\nWorld!", style);
        betterLabel.setWrap(true);
        betterLabel.setAlignment(Align.right);

        container.add(label).width(70);
        container.add(betterLabel).width(70);
        container.debug();
    }

    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void resize (int width, int height) {
        // Pass false to not modify the camera position.
        stage.getViewport().update(width, height, true);
    }

    public static void main (String[] args) throws Exception {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1024;
        config.height = 768;
        new LwjglApplication(new RightAlignedWrappingLabelIssue(), config);
    }
}