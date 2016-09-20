package gaia.cu9.ari.gaiaorbit.render;

import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Disposable;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Antialiasing;
import com.bitfire.postprocessing.effects.Bloom;
import com.bitfire.postprocessing.effects.Curvature;
import com.bitfire.postprocessing.effects.Fisheye;
import com.bitfire.postprocessing.effects.LensFlare2;
import com.bitfire.postprocessing.effects.LightGlow;
import com.bitfire.postprocessing.effects.MotionBlur;

public interface IPostProcessor extends Disposable {
    public class PostProcessBean {
        public PostProcessor pp;
        public Bloom bloom;
        public Antialiasing antialiasing;
        public LensFlare2 lens;
        public Curvature curvature;
        public Fisheye fisheye;
        public LightGlow lscatter;
        public MotionBlur motionblur;

        public boolean capture() {
            return pp.capture();
        }

        public boolean captureNoClear() {
            return pp.captureNoClear();
        }

        public void render() {
            pp.render();
        }

        public FrameBuffer captureEnd() {
            return pp.captureEnd();
        }

        public void render(FrameBuffer dest) {
            pp.render(dest);
        }

        public void dispose() {
            if (pp != null)
                pp.dispose();
        }

    }

    public enum RenderType {
        screen(0), screenshot(1), frame(2);

        public int index;

        private RenderType(int index) {
            this.index = index;
        }

    }

    public PostProcessBean getPostProcessBean(RenderType type);

    public void resize(int width, int height);

    public boolean isLightScatterEnabled();
}
