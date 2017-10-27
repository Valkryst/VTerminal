package com.valkryst.VTerminal.shader.blur;

import com.jhlabs.image.MotionBlurOp;
import com.valkryst.VTerminal.shader.Shader;
import lombok.Data;
import lombok.NonNull;

import java.awt.image.BufferedImage;

@Data
public class FastMotionBlurShader implements Shader {
    private float centreX = 0.5f;
    private float centreY = 0.5f;
    private float angle = 0.0f;
    private float distance = 1.0f;
    private float zoom = 0.0f;
    private float rotation = 0.0f;

    @Override
    public BufferedImage run(@NonNull BufferedImage bufferedImage) {
        final MotionBlurOp filter = new MotionBlurOp();
        filter.setCentreX(centreX);
        filter.setCentreY(centreY);
        filter.setAngle(angle);
        filter.setDistance(distance);
        filter.setZoom(zoom);
        filter.setRotation(rotation);
        return filter.filter(bufferedImage, null);
    }
}
