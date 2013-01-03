package com.alexbool.oauth.support.properties;

import java.util.Map.Entry;
import java.util.Properties;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.util.PropertyPlaceholderHelper;

public class PropertiesPlaceholderResolverFactoryBean implements FactoryBean<Properties> {

    private final Properties rawProperties;
    private final Properties placeholderSourceProperties;
    private final PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper(
            PlaceholderConfigurerSupport.DEFAULT_PLACEHOLDER_PREFIX,
            PlaceholderConfigurerSupport.DEFAULT_PLACEHOLDER_SUFFIX,
            PlaceholderConfigurerSupport.DEFAULT_VALUE_SEPARATOR, true);

    public PropertiesPlaceholderResolverFactoryBean(Properties rawProperties, Properties placeholderSourceProperties) {
        this.rawProperties = rawProperties;
        this.placeholderSourceProperties = placeholderSourceProperties;
    }

    @Override
    public Properties getObject() throws Exception {
        for (Entry<Object, Object> property : rawProperties.entrySet()) {
            if (property.getValue() instanceof String) {
                rawProperties.put(property.getKey(),
                        helper.replacePlaceholders((String) property.getValue(), placeholderSourceProperties));
            }
        }
        return rawProperties;
    }

    @Override
    public Class<?> getObjectType() {
        return Properties.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
