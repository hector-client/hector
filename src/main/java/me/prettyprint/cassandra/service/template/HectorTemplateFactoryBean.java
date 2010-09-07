package me.prettyprint.cassandra.service.template;

import java.lang.reflect.InvocationHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;
/**
 * A spring factory bean that is used for injecting templates.
 * It makes a proxy so that additional functionalities, like multi-method
 * batches can be supported
 *
 * @author Bozhidar Bozhanov
 *
 */
public class HectorTemplateFactoryBean implements FactoryBean<HectorTemplate> {

    private HectorTemplateFactory factory;

    @Override
    public HectorTemplate getObject() throws Exception {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args)
                    throws Throwable {
                return method.invoke(factory.currentTemplate(), args);
            }
        };
        return (HectorTemplate) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {HectorTemplate.class}, handler);
    }

    @Override
    public Class<HectorTemplate> getObjectType() {
        return HectorTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public HectorTemplateFactory getFactory() {
        return factory;
    }

    public void setFactory(HectorTemplateFactory factory) {
        this.factory = factory;
    }
}
