/*
 * This file is part of Gaia Sky, which is released under the Mozilla Public License 2.0.
 * See the file LICENSE.md in the project root for full license details.
 */

package gaia.cu9.ari.gaiaorbit.desktop.concurrent;

import com.badlogic.gdx.utils.Array;
import gaia.cu9.ari.gaiaorbit.scenegraph.FadeNode;
import gaia.cu9.ari.gaiaorbit.scenegraph.Particle;
import gaia.cu9.ari.gaiaorbit.scenegraph.SceneGraphNode;
import gaia.cu9.ari.gaiaorbit.scenegraph.camera.ICamera;
import gaia.cu9.ari.gaiaorbit.util.time.ITimeFrameProvider;

import java.util.concurrent.Callable;

public class UpdaterTask<T extends SceneGraphNode> implements Callable<Void> {

    ICamera camera;
    Array<T> nodes;
    ITimeFrameProvider time;
    int start, step;

    public UpdaterTask(Array<T> nodes, int start, int step) {
        this.nodes = nodes;
        this.start = start;
        this.step = step;
    }

    public UpdaterTask(Array<T> nodes) {
        this(nodes, 0, 1);
    }

    @Override
    public Void call() throws Exception {
        int size = nodes.size;
        for (int i = start; i < size; i += step) {
            SceneGraphNode node = nodes.get(i);
            float opacity = node instanceof Particle && ((Particle) node).octant != null ? ((Particle) node).octant.opacity : (node instanceof FadeNode ? 1f : node.opacity);
            node.update(time, node.parent.translation, camera, opacity);
        }
        return null;
    }

    public void setNodesToProcess(Array<T> nodes) {
        this.nodes = nodes;
    }

    public void addAll(Array<T> list) {
        this.nodes.addAll(list);
    }

    /**
     * This must be called to prepare the updater task for execution
     * 
     * @param camera
     * @param time
     */
    public void setParameters(ICamera camera, ITimeFrameProvider time) {
        this.camera = camera;
        this.time = time;
    }

}