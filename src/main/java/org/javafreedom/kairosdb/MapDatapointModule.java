package org.javafreedom.kairosdb;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class MapDatapointModule extends AbstractModule {

    @Override
    public void configure() {
        bind(MapDatapointFactory.class).in(Singleton.class);
    }

}
