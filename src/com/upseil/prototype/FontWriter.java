package com.upseil.prototype;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.PixmapPacker.SkylineStrategy;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.tools.bmfont.BitmapFontWriter;
import com.badlogic.gdx.tools.bmfont.BitmapFontWriter.FontInfo;
import com.badlogic.gdx.tools.bmfont.BitmapFontWriter.Padding;

public class FontWriter extends ApplicationAdapter {
    
    private static final String fontName = "lucida-console";
    private static final int fontSize = 18;
    
    @Override
    public void create() {
        FontInfo info = new FontInfo();
        info.padding = new Padding(1, 1, 1, 1);
        
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = fontSize;
        parameter.packer = new PixmapPacker(512, 512, Format.RGBA8888, 2, false, new SkylineStrategy());
        
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/" + fontName + ".ttf"));
        FreeTypeBitmapFontData data = generator.generateData(parameter);
        generator.dispose();
        
        BitmapFontWriter.writeFont(data, new String[] {fontName + "-" + fontSize + ".png"},
                Gdx.files.absolute("font/fnt-" + fontSize + "/" + fontName + "-" + fontSize + ".fnt"), info, 512, 512);
        BitmapFontWriter.writePixmaps(parameter.packer.getPages(), Gdx.files.absolute("font/fnt-" + fontSize + "/"),  fontName + "-" + fontSize);
        
        Gdx.app.exit();
    }
    
    public static void main(String[] args) {
        new LwjglApplication(new FontWriter());
    }
    
}
