package com.upseil.prototype;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class AutoScrollingTextAreaFontTest extends ApplicationAdapter {
    Stage stage;
    int lineCounter;

    public void create () {
        stage = new Stage(new ScreenViewport());
        Skin skin = new Skin(Gdx.files.internal("skin/default/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        Table container = new Table();
        stage.addActor(container);
        container.setFillParent(true);
        container.pad(10).defaults().expandX().fillX().space(4);

        final OpenScrollPane scrollPane = new OpenScrollPane(null, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        scrollPane.setScrollingDisabled(true, false);

        final TextArea textArea = new TextArea("Initial line.", skin) {
            public float getPrefHeight () {
                float prefHeight = (getLines() + 1) * getStyle().font.getLineHeight();
                TextFieldStyle style = getStyle();
                if (style.background != null) {
                    prefHeight = Math.max(prefHeight + style.background.getBottomHeight() + style.background.getTopHeight(), style.background.getMinHeight());
                }
                return prefHeight;
            }
        };
        textArea.setDisabled(true);
        textArea.getStyle().font = new BitmapFont(Gdx.files.internal("font/lucida-console-15.fnt"));
        
        scrollPane.setWidget(textArea);

        Button addLineButton = new TextButton("Add new line", skin);
        addLineButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                textArea.appendText("\nLine " + lineCounter++);
                scrollPane.invalidate();
                scrollPane.scheduleScrollToBottom(); // See OpenScrollPane below
            }
        });
        container.add(addLineButton).colspan(2);
        
        container.row().height(350);
        container.add(scrollPane);
        container.debugAll();
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
        new LwjglApplication(new AutoScrollingTextAreaFontTest(), config);
    }
    
    public class OpenScrollPane extends ScrollPane {
        
        private boolean scrollToBottom;

        public OpenScrollPane(Actor widget, Skin skin) {
            super(widget, skin);
        }
        
        public void scheduleScrollToBottom() {
            scrollToBottom = true;
        }

        @Override
        public void layout() {
            super.layout();
            if (scrollToBottom) {
                setScrollY(getMaxY());
            }
        }
        
    }
}