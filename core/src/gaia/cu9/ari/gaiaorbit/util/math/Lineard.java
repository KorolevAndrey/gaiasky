package gaia.cu9.ari.gaiaorbit.util.math;

public class Lineard<T extends Vectord<T>> implements Pathd<T> {


    public T[] controlPoints;

    public Lineard (final T[] controlPoints) {
        set(controlPoints);
    }

    public Lineard set (final T[] controlPoints) {
        this.controlPoints = controlPoints;
        return this;
    }

    @Override
    public T derivativeAt(T out, double t) {
        return null;
    }

    @Override
    public T valueAt(T out, double t) {
       int n = controlPoints.length;
       double step = 1d / ((double) n - 1d);
       int i1 = (int) Math.floor(t / step) + 1;
       double alpha = (t / step) - (double) i1;
       int i0 = i1 - 1;
       T p0 = controlPoints[i0];
       T p1 = controlPoints[i1];

       out.set(p0);
       return out.interpolate(p1, alpha, Interpolationd.linear);
    }


    @Override
    public double approximate(T v) {
        return 0;
    }

    @Override
    public double locate(T v) {
        return 0;
    }

    @Override
    public double approxLength(int samples) {
        return 0;
    }
}
