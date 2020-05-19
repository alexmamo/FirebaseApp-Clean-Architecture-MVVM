package ro.alexmamo.firebaseapp.data;

public class DataOrException<T, E extends Exception> {
    public T data;
    public E exception;
}