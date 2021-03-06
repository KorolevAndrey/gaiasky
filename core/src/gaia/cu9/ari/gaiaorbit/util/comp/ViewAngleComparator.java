/*
 * This file is part of Gaia Sky, which is released under the Mozilla Public License 2.0.
 * See the file LICENSE.md in the project root for full license details.
 */

package gaia.cu9.ari.gaiaorbit.util.comp;

import gaia.cu9.ari.gaiaorbit.scenegraph.IFocus;

import java.util.Comparator;

/**
 * Compares entities. Further entities go first, nearer entities go last.
 * 
 * @author Toni Sagrista
 */
public class ViewAngleComparator<T> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {

        return Double.compare(((IFocus) o1).getCandidateViewAngleApparent(), ((IFocus) o2).getCandidateViewAngleApparent());

    }

}
