package org.javafreedom.kairosdb;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class MultiTenantMapDatapointModule extends AbstractModule {

    @Override
    public void configure() {
        bind(MultiTenantMapDatapointFactory.class).in(Singleton.class);
    }

}
