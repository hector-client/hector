package me.prettyprint.cassandra.service.manager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;

public class CassandraManagerFactoryBean implements FactoryBean<CassandraManager> {

    private CassandraManagerFactory factory;

    @Override
    public CassandraManager getObject() throws Exception {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args)
                    throws Throwable {
                return method.invoke(factory.currentManager(), args);
            }
        };
        return (CassandraManager) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {CassandraManager.class}, handler);
    }

    @Override
    public Class<CassandraManager> getObjectType() {
        return CassandraManager.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public CassandraManagerFactory getFactory() {
        return factory;
    }

    public void setFactory(CassandraManagerFactory factory) {
        this.factory = factory;
    }
}
