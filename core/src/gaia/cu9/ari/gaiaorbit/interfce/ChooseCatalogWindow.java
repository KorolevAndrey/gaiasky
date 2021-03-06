/*
 * This file is part of Gaia Sky, which is released under the Mozilla Public License 2.0.
 * See the file LICENSE.md in the project root for full license details.
 */

package gaia.cu9.ari.gaiaorbit.interfce;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import gaia.cu9.ari.gaiaorbit.event.EventManager;
import gaia.cu9.ari.gaiaorbit.event.Events;
import gaia.cu9.ari.gaiaorbit.util.GlobalConf;
import gaia.cu9.ari.gaiaorbit.util.I18n;
import gaia.cu9.ari.gaiaorbit.util.scene2d.OwnLabel;

/**
 * GUI window to choose the catalog to use
 * @author tsagrista
 *
 */
public class ChooseCatalogWindow extends GenericDialog {

    private DatasetsWidget dw;
    private String assetsLoc;
    private String notice;

    public ChooseCatalogWindow(Stage stage, Skin skin){
        this(stage, skin, null);
    }

    public ChooseCatalogWindow(Stage stage, Skin skin, String noticeKey) {
        super(I18n.txt("gui.dschooser.title"), skin, stage);
        this.notice = I18n.txt(noticeKey);
        assetsLoc = GlobalConf.ASSETS_LOC;

        setCancelText(I18n.txt("gui.exit"));
        setAcceptText(I18n.txt("gui.start"));

        // Build
        buildSuper();
    }

    @Override
    protected void build() {
        if(notice != null && !notice.isEmpty()){
            content.add(new OwnLabel(notice, skin)).left().pad(15 * GlobalConf.SCALE_FACTOR).row();
        }

        Cell<Actor> cell = content.add((Actor) null);

        dw = new DatasetsWidget(skin, assetsLoc);
        Array<FileHandle> catalogFiles = dw.buildCatalogFiles();

        cell.clearActor();
        cell.space(3 * GlobalConf.SCALE_FACTOR);
        cell.padTop(10 * GlobalConf.SCALE_FACTOR);
        cell.setActor(dw.buildDatasetsWidget(catalogFiles));
    }

    @Override
    protected void accept() {
        boolean first = true;
        // Update setting
        if (dw != null && dw.cbs != null) {
            GlobalConf.data.CATALOG_JSON_FILES = "";
            for (Button b : dw.cbs) {
                if (b.isChecked()) {
                    // Add all selected to list
                    String candidate = dw.candidates.get(b);
                    if (!first) {
                        GlobalConf.data.CATALOG_JSON_FILES += "," + candidate;
                    } else {
                        GlobalConf.data.CATALOG_JSON_FILES += candidate;
                        first = false;
                    }
                }
            }
        }
        // No change to execute exit event, manually restore cursor to default
        Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
        // Data load can start
        EventManager.instance.post(Events.LOAD_DATA_CMD);
    }

    @Override
    protected void cancel() {
        Gdx.app.exit();
    }

}
