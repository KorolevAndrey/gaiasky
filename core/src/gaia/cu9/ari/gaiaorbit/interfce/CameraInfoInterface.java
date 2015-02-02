package gaia.cu9.ari.gaiaorbit.interfce;

import gaia.cu9.ari.gaiaorbit.event.EventManager;
import gaia.cu9.ari.gaiaorbit.event.Events;
import gaia.cu9.ari.gaiaorbit.event.IObserver;
import gaia.cu9.ari.gaiaorbit.util.Constants;
import gaia.cu9.ari.gaiaorbit.util.I18n;
import gaia.cu9.ari.gaiaorbit.util.math.Vector3d;
import gaia.cu9.ari.gaiaorbit.util.scene2d.OwnLabel;

import java.text.DecimalFormat;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class CameraInfoInterface extends Table implements IObserver {

    protected OwnLabel camVel, camPos;
    private DecimalFormat format;
    /** Lock object for synchronization **/
    private Object lock;

    public CameraInfoInterface(Skin skin, DecimalFormat sformat, Object lock) {
	super(skin);
	this.format = sformat;
	camVel = new OwnLabel("", skin, "hud");
	camVel.setWidth(100);
	camPos = new OwnLabel("", skin, "hud");
	add(new OwnLabel(I18n.bundle.get("gui.cam.cameravel"), skin, "hud")).left();
	add(camVel).left().padLeft(10);
	row();
	add(camPos).left().colspan(2);
	padBottom(10);
	pack();
	this.lock = lock;
	EventManager.getInstance().subscribe(this, Events.CAMERA_MOTION_UPDATED);
    }

    @Override
    public void notify(Events event, Object... data) {
	synchronized (lock) {
	    switch (event) {
	    case CAMERA_MOTION_UPDATED:
		Vector3d campos = (Vector3d) data[0];
		camPos.setText("X: " + format.format(campos.x * Constants.U_TO_KM) + "\nY: " + format.format(campos.y * Constants.U_TO_KM) + "\nZ: " + format.format(campos.z * Constants.U_TO_KM));
		camVel.setText(format.format((double) data[1]) + " km/h");
		break;
	    }
	}
    }

}
