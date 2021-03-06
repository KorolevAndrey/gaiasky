/*
 * This file is part of Gaia Sky, which is released under the Mozilla Public License 2.0.
 * See the file LICENSE.md in the project root for full license details.
 */

package gaia.cu9.ari.gaiaorbit.interfce;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import gaia.cu9.ari.gaiaorbit.util.GlobalConf;
import gaia.cu9.ari.gaiaorbit.util.I18n;
import gaia.cu9.ari.gaiaorbit.util.scene2d.Link;
import gaia.cu9.ari.gaiaorbit.util.scene2d.OwnLabel;

/**
 * Popup window that informs the user of new updates.
 */
public class UpdatePopup extends GenericDialog {
    private String tagVersion;

    public UpdatePopup(String tagVersion, Stage ui, Skin skin) {
        super(I18n.txt("gui.newversion.new"), skin, ui);

        this.tagVersion = tagVersion;

        setAcceptText(I18n.txt("gui.close"));
        setCancelText(null);

        buildSuper();
        setModal(false);
    }

    @Override
    protected void build() {
        float padb = 5 * GlobalConf.SCALE_FACTOR;
        content.clear();
        content.pad(10 * GlobalConf.SCALE_FACTOR);
        content.add(new OwnLabel(I18n.txt("gui.newversion.new.current") + ":", skin)).left().padRight(padb).padBottom(padb);
        content.add(new OwnLabel(GlobalConf.version.version, skin)).left().padBottom(padb).row();

        content.add(new OwnLabel(I18n.txt("gui.newversion.new.new") + ":", skin)).left().padRight(padb).padBottom(padb * 2);
        content.add(new OwnLabel(tagVersion, skin, "header")).left().padBottom(padb * 2).row();

        Label.LabelStyle linkStyle = skin.get("link", Label.LabelStyle.class);
        content.add(new Link(I18n.txt("gui.newversion.getit"), linkStyle, GlobalConf.WEBPAGE_DOWNLOADS)).center().colspan(2);
    }

    @Override
    protected void accept() {
        // Do nothing
    }

    @Override
    protected void cancel() {
        // Do nothing
    }
}
