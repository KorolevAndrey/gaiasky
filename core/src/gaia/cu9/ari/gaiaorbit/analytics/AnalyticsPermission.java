/*
 * This file is part of Gaia Sky, which is released under the Mozilla Public License 2.0.
 * See the file LICENSE.md in the project root for full license details.
 */

package gaia.cu9.ari.gaiaorbit.analytics;

import gaia.cu9.ari.gaiaorbit.util.GlobalConf;

public class AnalyticsPermission implements IPermission {

    @Override
    public boolean check() {
        return GlobalConf.program != null && GlobalConf.program.ANALYTICS_ENABLED;
    }

}
