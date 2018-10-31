package sdk.converter;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.util.StringUtils;
import sdk.annotations.*;
import sdk.data.*;
import sdk.list.ListServiceConfiguration;
import sdk.list.ListServiceConfigurationAttribute;
import sdk.models.AttributeType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Karis Sponsler
 * Date: 8/8/17
 */
public class ConfigurationManager extends TypeManager {

    static ConfigurationParserContext configurationParserContext;

    // NOTE: I chose not to use a `ConcurrentHashMap` because the read and write and slower
    // and it isn't too big of a deal if we miss and cache entry
    //
    //
    // we are going to put our already inferred names and configurations here
    // we don't have to compute them more than once
    // this Map can live as long as the application
    // each application has a finite and relatively small number of possible configs and names
    static final Map<String, String> inferredNameMap = new HashMap<>();
    // List configuration attribute cache by class and name
    static final Map<UniqueClassConfig, ListServiceConfiguration> listServiceConfig = new HashMap<>();
    // data set configuration cache
    static final Map<Class, Collection<ServiceConfigurationAttribute>> attributeMap = new HashMap<>();

    /**
     * @param someClass
     * @param <T>
     * @return
     */
    public static <T> ServiceConfiguration generateConfiguration(Class<T> someClass) {
        return new ServiceConfiguration.Builder(someClass.getName()).withAttributes(generateConfigurationAttributes(someClass)).build();
    }

    public static <T> ListServiceConfiguration generateListConfiguration(Class<T> someClass, String configName) {
        UniqueClassConfig config = new UniqueClassConfig(someClass, configName);
        if(listServiceConfig.containsKey(config)){
            return listServiceConfig.get(config);
        }
        ListServiceConfiguration listServiceConfiguration = new ListServiceConfiguration(configName);
        listServiceConfiguration.getAttributes().addAll(generateListConfigurationAttributes(someClass));

        // put generated config in map
        listServiceConfig.put(config, listServiceConfiguration);
        return listServiceConfiguration;
    }

    /**
     * @param someClass
     * @param <T>
     * @return
     */
    public static <T> Collection<ServiceConfigurationAttribute> generateListConfigurationAttributes(Class<T> someClass) {
        Set<ServiceConfigurationAttribute> attributes = new HashSet<>();
        for (AttributeProxy attributeProxy : getMethodAndFieldAnnotationsForClass(someClass)) {
            Attribute attribute = attributeProxy.getAttributeAnnotation();
            if (attribute != null && !attribute.excludeFromList()) {
                attributes.add(getListServiceConfigurationAttributeFromField(attributeProxy, attribute));
            }
        }
        return attributes;
    }

    /**
     * @param attribute
     * @return
     */
    protected static ListServiceConfigurationAttribute getListServiceConfigurationAttributeFromField(AttributeProxy proxy, Attribute attribute) {
        int index = attribute.index();
        String name = attribute.name();
        if (StringUtils.isEmpty(name)) {
            name = inferName(proxy.getName());
        }
        ConverterAttributeType converterAttributeType = null;
        AttributeType attributeType = attribute.dataType();
        if (attributeType.equals(AttributeType.None)) {
            converterAttributeType = inferDataType(proxy.getType().getSimpleName());
            attributeType = converterAttributeType.getAttributeType();
        }
        ListServiceConfigurationAttribute listServiceConfigurationAttribute = new ListServiceConfigurationAttribute();
        listServiceConfigurationAttribute.setAttributeIndex(index);
        listServiceConfigurationAttribute.setName(name);
        listServiceConfigurationAttribute.setAttributeType(attributeType);
        listServiceConfigurationAttribute.setIsListItemConfiguration(true);
        return listServiceConfigurationAttribute;
    }

    /**
     * @param someClass
     * @param <T>
     * @return
     */
    public static <T> Collection<ServiceConfigurationAttribute> generateConfigurationAttributes(Class<T> someClass) {
        if(attributeMap.containsKey(someClass)){
            return attributeMap.get(someClass);
        }
        ConfigurationParserContext configurationParserContext = getConfigurationParserContext();
        configurationParserContext.setParentClass(someClass); // setting parent class to combat against circular reference
        Collection<ServiceConfigurationAttribute> attributes = new ArrayList<>();
        for (AttributeProxy proxy : getMethodAndFieldAnnotationsForClass(someClass)) {
            Attribute attribute = proxy.getAttributeAnnotation();
            Relationship relationship = proxy.getRelationshipAnnotation();
            if (attribute != null) {
                attributes.add(getServiceConfigurationAttributeFromMember(new ConfigurationWrapper(proxy), attribute));
            }
            if (relationship != null) {
                if (configurationParserContext.addClass(proxy.getType())) { //if clazz is referenced more than twice stop the circular reference.
                    attributes.add(getServiceConfigurationAttributeFromMember(new ConfigurationWrapper(proxy), relationship));
                }
            }
        }
        configurationParserContext = null;

        // we want to remember these
        attributeMap.put(someClass, attributes);
        return attributes;
    }

    /**
     * @param configurationWrapper
     * @param attribute
     * @return
     */
    protected static ServiceConfigurationAttribute getServiceConfigurationAttributeFromMember(ConfigurationWrapper configurationWrapper, Attribute attribute) {
        int index = attribute.index();
        String name = attribute.name();
        if (StringUtils.isEmpty(name)) {
            name = inferName(configurationWrapper.varName);
        }
        AttributeType attributeType = attribute.dataType();
        ConverterAttributeType converterAttributeType;
        if (attributeType.equals(AttributeType.None)) {
            if (attribute.relationshipClass() == Class.class) {
                converterAttributeType = inferDataType(configurationWrapper.clazz);
                attributeType = converterAttributeType.getAttributeType();
            } else {
                converterAttributeType = inferDataType(configurationWrapper.dataTypeName);
                if (!converterAttributeType.getAttributeType().equals(AttributeType.Relation)) {
                    converterAttributeType = new ConverterAttributeType(AttributeType.SingleRelationship, true);
                    attributeType = converterAttributeType.getAttributeType();
                } else {
                    converterAttributeType = new ConverterAttributeType(AttributeType.Relation, true);
                    attributeType = converterAttributeType.getAttributeType();
                }
            }
        }

        boolean canCreate = attribute.canCreate();
        boolean canCreateAndRequired = attribute.canCreateAndRequired();
        boolean canUpdate = attribute.canUpdate();
        boolean canUpdateAndRequired = attribute.canUpdateAndRequired();
        boolean canSearch = attribute.canSearch();
        boolean canSearchAndRequired = attribute.canSearchAndRequired();


        ServiceConfigurationAttribute serviceConfigurationAttribute = new ServiceConfigurationAttribute();
        serviceConfigurationAttribute.setAttributeIndex(index);
        serviceConfigurationAttribute.setAttributeType(attributeType);
        serviceConfigurationAttribute.setName(name);
        serviceConfigurationAttribute.create = canCreate;
        serviceConfigurationAttribute.createRequired = canCreateAndRequired;
        serviceConfigurationAttribute.update = canUpdate;
        serviceConfigurationAttribute.updateRequired = canUpdateAndRequired;
        serviceConfigurationAttribute.search = canSearch;
        serviceConfigurationAttribute.searchRequired = canSearchAndRequired;
        if (requiresRelatedServiceConfiguration(attributeType)) {
            setRelatedServiceConfiguration(attributeType, serviceConfigurationAttribute, name, configurationWrapper.clazz);
        }
        return serviceConfigurationAttribute;
    }

    protected static ServiceConfigurationAttribute getServiceConfigurationAttributeFromMember(ConfigurationWrapper configurationWrapper, Relationship relationship) {
        int index = relationship.index();
        String name = relationship.name();
        if (StringUtils.isEmpty(name)) {
            name = inferName(configurationWrapper.varName);
        }

        AttributeType attributeType = AttributeType.SingleRelationship;
        if (configurationWrapper.isWrappedClass) { // wrapped class = List<> Set<> Map<>
            attributeType = AttributeType.Relation;
        }

        boolean canCreate = relationship.canCreate();
        boolean canCreateAndRequired = relationship.canCreateAndRequired();
        boolean canUpdate = relationship.canUpdate();
        boolean canUpdateAndRequired = relationship.canUpdateAndRequired();
        boolean canSearch = relationship.canSearch();
        boolean canSearchAndRequired = relationship.canSearchAndRequired();


        ServiceConfigurationAttribute serviceConfigurationAttribute = new ServiceConfigurationAttribute();
        serviceConfigurationAttribute.setAttributeIndex(index);
        serviceConfigurationAttribute.setAttributeType(attributeType);
        serviceConfigurationAttribute.setName(name);
        serviceConfigurationAttribute.setName(name);
        serviceConfigurationAttribute.create = canCreate;
        serviceConfigurationAttribute.createRequired = canCreateAndRequired;
        serviceConfigurationAttribute.update = canUpdate;
        serviceConfigurationAttribute.updateRequired = canUpdateAndRequired;
        serviceConfigurationAttribute.search = canSearch;
        serviceConfigurationAttribute.searchRequired = canSearchAndRequired;
        setRelatedServiceConfiguration(attributeType, serviceConfigurationAttribute, name, configurationWrapper.clazz);
        return serviceConfigurationAttribute;
    }


    /**
     * @param attributeType
     * @return
     */
    protected static boolean requiresRelatedServiceConfiguration(AttributeType attributeType) {
        return (attributeType.equals(AttributeType.ListItem) || attributeType.equals(AttributeType.Relation) || attributeType.equals(AttributeType.SingleRelationship));
    }

    /**
     * @param attributeType
     * @param serviceConfigurationAttribute
     * @param configName
     * @param clazz
     */
    protected static void setRelatedServiceConfiguration(AttributeType attributeType, ServiceConfigurationAttribute serviceConfigurationAttribute, String configName, Class<?> clazz) {
        if (attributeType.equals(AttributeType.ListItem)) {
            serviceConfigurationAttribute.setRelatedListServiceConfiguration(generateListConfiguration(clazz, configName));
        } else if (attributeType.equals(AttributeType.Relation)) {
            serviceConfigurationAttribute.setRelatedService(new RelatedServiceConfiguration(configName, generateConfiguration(clazz).getAttributes()));
        } else {
            serviceConfigurationAttribute.setRelatedService(new RelatedServiceConfiguration(configName, generateConfiguration(clazz).getAttributes()));
        }
    }

    /**
     * @param name
     * @return
     */
    public static String inferName(String name) {
        // if we've inferred this name before, return.
        if(inferredNameMap.containsKey(name)){
            return inferredNameMap.get(name);
        }
        if (name.contains("_")) { // snake case
            name = name.replace("_", " ");
        } else { // camel case
            StringBuilder builder = new StringBuilder();
            if (name.startsWith("set") || name.startsWith("get")) name = name.substring(3); // remove set or get from front
            builder.append(name.charAt(0));
            for (int i = 1; i < name.length(); i++) {
                char c = name.charAt(i);
                char p = name.charAt(i - 1);
                if (Character.isAlphabetic(c) != Character.isAlphabetic(p) ||
                        (Character.isUpperCase(c) && Character.isUpperCase(c) != Character.isUpperCase(p)))
                    builder.append(" ");
                builder.append(name.charAt(i));
            }
            name = builder.toString();
        }
        String inferredName =  WordUtils.capitalize(name);
        // we want to remember this name
        inferredNameMap.put(name, inferredName);
        return inferredName;
    }

    /**
     * @param index
     * @param clazz
     * @return
     */
    protected static AttributeMeta inferMetaData(int index, Class clazz) {
        return new AttributeMeta(inferDataType(clazz).getAttributeType(), index);
    }


    /**
     * @return
     */
    private static ConfigurationParserContext getConfigurationParserContext() {
        if (configurationParserContext == null) {
            configurationParserContext = new ConfigurationParserContext();
        }
        return configurationParserContext;
    }
}
