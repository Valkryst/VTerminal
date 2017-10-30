package com.valkryst.VTerminal.shader.blur;

import com.jhlabs.image.MotionBlurFilter;
import com.valkryst.VTerminal.shader.Shader;
import lombok.Data;
import lombok.NonNull;

import java.awt.image.BufferedImage;

@Data
public class MotionBlurShader implements Shader {
    private float angle = 0.0f;
    private float distance = 1.0f;
    private float zoom = 0.0f;
    private float rotation = 0.0f;
    private boolean wrapEdges = false;

    @Override
    public BufferedImage run(@NonNull BufferedImage bufferedImage) {
        final MotionBlurFilter filter = new MotionBlurFilter();
        filter.setAngle(angle);
        filter.setDistance(distance);
        filter.setZoom(zoom);
        filter.setRotation(rotation);
        filter.setWrapEdges(wrapEdges);
        return filter.filter(bufferedImage, null);
    }

    @Override
    public Shader copy() {
        final MotionBlurShader motionBlurShader = new MotionBlurShader();
        motionBlurShader.setAngle(angle);
        motionBlurShader.setDistance(distance);
        motionBlurShader.setZoom(zoom);
        motionBlurShader.setRotation(rotation);
        return motionBlurShader;
    }
}
