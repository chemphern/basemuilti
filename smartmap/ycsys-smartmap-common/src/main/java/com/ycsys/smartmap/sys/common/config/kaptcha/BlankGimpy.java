package com.ycsys.smartmap.sys.common.config.kaptcha;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.util.Configurable;

import java.awt.image.BufferedImage;

/**
 * Created by chemphern on 2016/12/4.
 */
public class BlankGimpy extends Configurable implements GimpyEngine {
    @Override
    public BufferedImage getDistortedImage(BufferedImage baseImage) {
        return baseImage;
    }
}
