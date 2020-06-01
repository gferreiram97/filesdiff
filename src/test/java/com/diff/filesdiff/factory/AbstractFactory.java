package com.diff.filesdiff.factory;

public interface AbstractFactory<T> {

    T createDefault();

    T createEmpty();
}
