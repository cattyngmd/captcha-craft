package dev.cattyn.captchacraft.utils;

import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public final class MapUtils {
    private static final int MAP_SIZE = 128;

    private MapUtils() {
    }

    public static byte[] toPng(MapItemSavedData data) throws IOException {
        BufferedImage image = new BufferedImage(MAP_SIZE, MAP_SIZE, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < data.colors.length; i++) {
            int x = i % MAP_SIZE;
            int y = i / MAP_SIZE;
            int colorIndex = data.colors[i] & 0xFF;
            int argb = MapColor.getColorFromPackedId(colorIndex);
            image.setRGB(x, y, argb);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        boolean success = ImageIO.write(image, "png", baos);
        if (!success) {
            throw new IOException("No ImageIO writer found for PNG format");
        }
        return baos.toByteArray();
    }

    public static String toBase64(MapItemSavedData data) throws IOException {
        return Base64.getEncoder().encodeToString(toPng(data));
    }
}