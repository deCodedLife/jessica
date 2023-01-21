package jessica.font;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class FontManager {

	public CFontRenderer ChakraPetchRegular = null;
	public CFontRenderer Tahoma = null;
	public CFontRenderer TrebuchetMS = null;
	public CFontRenderer RotundaBlack = null;

	public void loadFonts() throws FontFormatException, IOException {
		ChakraPetchRegular = new CFontRenderer(loadFonts1(18), true, true);
		Tahoma = new CFontRenderer(loadFonts2(15), true, true);
		TrebuchetMS = new CFontRenderer(loadFonts3(18), true, true);
		RotundaBlack = new CFontRenderer(loadFonts4(27), true, true);
	}
	
	public Font loadFonts1(int size) throws IOException, FontFormatException {
		Font font;
		InputStream is = Minecraft.getMinecraft().getResourceManager()
				.getResource(new ResourceLocation("font/chakrapetch-regular.ttf")).getInputStream();
		font = Font.createFont(0, is);
		font = font.deriveFont(0, size);
		return font;
	}
	
	public Font loadFonts2(int size) throws IOException, FontFormatException {
		Font font;
		InputStream is = Minecraft.getMinecraft().getResourceManager()
				.getResource(new ResourceLocation("font/tahoma.ttf")).getInputStream();
		font = Font.createFont(0, is);
		font = font.deriveFont(0, size);
		return font;
	}
	
	public Font loadFonts3(int size) throws FontFormatException, IOException {
		Font font;
		InputStream is = Minecraft.getMinecraft().getResourceManager()
				.getResource(new ResourceLocation("font/trebuchetms.ttf")).getInputStream();
		font = Font.createFont(0, is);
		font = font.deriveFont(0, size);
		return font;
	}
	
	public Font loadFonts4(int size) throws FontFormatException, IOException {
		Font font;
		InputStream is = Minecraft.getMinecraft().getResourceManager()
				.getResource(new ResourceLocation("font/tahoma.ttf")).getInputStream();
		font = Font.createFont(0, is);
		font = font.deriveFont(0, size);
		return font;
	}
}
