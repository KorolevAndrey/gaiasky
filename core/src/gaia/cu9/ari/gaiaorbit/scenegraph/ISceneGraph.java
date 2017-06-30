package gaia.cu9.ari.gaiaorbit.scenegraph;

import java.util.HashMap;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntMap;

import gaia.cu9.ari.gaiaorbit.util.time.ITimeFrameProvider;

/**
 * Defines the interface for any scene graph implementation
 * 
 * @author tsagrista
 *
 */
public interface ISceneGraph extends Disposable {
    /**
     * Initializes the scene graph
     * 
     * @param nodes
     *            The list of nodes
     * @param time
     *            The time provider
     * @param hasOctree
     *            Whether the list of nodes contains an octree
     */
    public void initialize(Array<SceneGraphNode> nodes, ITimeFrameProvider time, boolean hasOctree);

    /**
     * Inserts a node
     * 
     * @param node
     *            The node to add
     * @param addToIndex
     *            Whether to add the ids of this node to the index
     */
    public void insert(SceneGraphNode node, boolean addToIndex);

    /**
     * Removes a node
     * 
     * @param node
     *            The node to remove
     * @param removeFromIndex
     *            Whether to remove the ids of this node from the index
     */
    public void remove(SceneGraphNode node, boolean removeFromIndex);

    /**
     * Updates the nodes of this scene graph
     * 
     * @param time
     *            The current time provider
     * @param camera
     *            The current camera
     */
    public void update(ITimeFrameProvider time, ICamera camera);

    /**
     * Whether this scene graphs contains a node with the given name
     * 
     * @param name
     *            The name
     * @return True if this scene graph contains the node, false otherwise
     */
    public boolean containsNode(String name);

    /**
     * Returns the node with the given name, or null if it does not exist.
     * 
     * @param name
     *            The name of the node.
     * @return The node with the name.
     */
    public SceneGraphNode getNode(String name);

    /**
     * Updates the string to node map and the star map if necessary.
     * 
     * @param node
     */
    public void addNodeAuxiliaryInfo(SceneGraphNode node);

    /**
     * Removes the info of the node from the aux lists.
     * 
     * @param node
     */
    public void removeNodeAuxiliaryInfo(SceneGraphNode node);

    public HashMap<String, SceneGraphNode> getStringToNodeMap();

    /**
     * Gets a star map: HIP -&gt; Star It only contains the stars with HIP
     * number
     * 
     * @return The HIP star map
     */
    public IntMap<Star> getStarMap();

    public Array<SceneGraphNode> getNodes();

    public SceneGraphNode getRoot();

    public Array<CelestialBody> getFocusableObjects();

    public Array<ParticleGroup> getParticleGroups();

    public CelestialBody findFocus(String name);

    public int getSize();

}
