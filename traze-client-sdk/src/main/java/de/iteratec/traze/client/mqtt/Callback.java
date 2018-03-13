package de.iteratec.traze.client.mqtt;

@FunctionalInterface
public interface Callback<T> {

    public void run(T content) throws Exception;
}
