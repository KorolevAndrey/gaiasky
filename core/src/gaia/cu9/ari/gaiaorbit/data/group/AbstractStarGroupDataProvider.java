package gaia.cu9.ari.gaiaorbit.data.group;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import gaia.cu9.ari.gaiaorbit.scenegraph.StarGroup.StarBean;
import gaia.cu9.ari.gaiaorbit.util.Logger;

public abstract class AbstractStarGroupDataProvider implements IStarGroupDataProvider {

    protected Array<StarBean> list;

    public AbstractStarGroupDataProvider() {
        super();
    }

    /**
     * Initialises the lists and structures given a file by counting the number
     * of lines
     * 
     * @param f
     */
    protected void initLists(FileHandle f) {
        try {
            int lines = countLines(f);
            initLists(lines - 1);
        } catch (Exception e) {
            Logger.error(e);
        }
    }

    /**
     * Initialises the lists and structures given number of elements
     * 
     * @param f
     */
    protected void initLists(int elems) {
        list = new Array<StarBean>(elems);
    }

    protected int countLines(FileHandle f) throws IOException {
        InputStream is = new BufferedInputStream(f.read());
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }

    protected void dumpToDisk(Array<StarBean> data, String filename) {
        List<StarBean> l = new ArrayList<StarBean>(data.size);
        for (StarBean p : data)
            l.add(p);

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(l);
            oos.close();

        } catch (Exception e) {
            Logger.error(e);
        }
    }

}
