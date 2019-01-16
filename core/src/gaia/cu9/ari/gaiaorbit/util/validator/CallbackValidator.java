package gaia.cu9.ari.gaiaorbit.util.validator;

public abstract class CallbackValidator implements IValidator{

    private Runnable isValidCallback, isInvalidCallback;

    public CallbackValidator(){
        super();
    }

    public CallbackValidator(Runnable correctCallback, Runnable incorrectCallback) {
        super();
        this.isValidCallback = correctCallback;
        this.isInvalidCallback = incorrectCallback;
    }

    public void setIsValidCallback(Runnable isValidCallback) {
        this.isValidCallback = isValidCallback;
    }

    public void setIsInvalidCallback(Runnable isInvalidCallback) {
        this.isInvalidCallback = isInvalidCallback;
    }

    protected void runIsValidCallback() {
        if (isValidCallback != null)
            isValidCallback.run();
    }

    protected void runIsInvalidCallback() {
        if (isInvalidCallback != null)
            isInvalidCallback.run();
    }

    @Override
    public boolean validate(String value){
        boolean valid = validateLocal(value);
        if(valid)
            runIsValidCallback();
        else
            runIsInvalidCallback();
        return valid;
    }

    protected abstract boolean validateLocal(String value);

}
