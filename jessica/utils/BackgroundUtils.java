package jessica.utils;

import net.minecraft.client.Minecraft;

import org.apache.commons.lang3.RandomUtils;

public class BackgroundUtils {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final int bgMax = 5;
    private static int backgroundNum1 = RandomUtils.nextInt(0, bgMax);
    private static String background1 = null;
    private static float alpha1 = 1f;
    private static int backgroundNum2;
    private static String background2 = null;
    private static float alpha2 = 0f;
    private static boolean changingBG = false;
    private static final TimerUtils timer = new TimerUtils();

    public static void defineBG() {
        if(backgroundNum1 > bgMax) backgroundNum1 = 0;
        else if(backgroundNum1 < 0)
            backgroundNum1 = bgMax;
        background1 = backgroundNum1 + ".jpg";

        if(changingBG) {
            if(backgroundNum2 > bgMax)
                backgroundNum2 = 0;
            else if(backgroundNum2 < 0)
                backgroundNum2 = bgMax;
            background2 = backgroundNum2 + ".jpg";
        }
        else {
            background2 = null;
        }
    }

    public static void drawBG1() {
        if(background1 != null)
            TextureUtils.bindTexture(background1, Utils.getScaledResolution().getScaledWidth(), Utils.getScaledResolution().getScaledHeight(), alpha1);
    }

    public static void drawBG2() {
        if(background2 != null)
            TextureUtils.bindTexture(background2, Utils.getScaledResolution().getScaledWidth(), Utils.getScaledResolution().getScaledHeight(), alpha2);
    }

    public static void draw(){
        drawBG1();
        boolean reached = timer.hasReached(7500L);
        if(reached || changingBG) {
            changingBG = true;
            if(reached) {
                backgroundNum2 = backgroundNum1 + 1;
                defineBG();
            }
            drawBG2();
            float offset = 0.015f;
            if(!reached) offset = 0.03f;
            if(alpha1 > 0)
                alpha1 -= offset;
            if(alpha2 < 1)
                alpha2 += offset;

            if(alpha1 <= 0 && alpha2 >= 1) {
                changingBG = false;

                alpha1 = 1f;
                alpha2 = 0f;

                backgroundNum1 = backgroundNum2;

                defineBG();

                timer.reset();
            }
        }
    }
}
