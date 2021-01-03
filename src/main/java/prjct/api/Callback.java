package prjct.api;

/**
 * a callback is a function designed to be called in different cases via anonymous definition.
 */
public interface Callback<T> {

    public T call(T c);

}
