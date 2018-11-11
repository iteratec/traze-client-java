package de.iteratec.traze.client.mqtt;

@FunctionalInterface
public interface Callback<T> {

    void run(T content) throws Exception;
}
