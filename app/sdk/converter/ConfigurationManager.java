package sdk.converter;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.util.StringUtils;
import sdk.annotations.*;
import sdk.data.*;
import sdk.list.ListServiceConfiguration;
import sdk.list.ListServiceConfigurationAttribute;
import sdk.models.AttributeType;

import javax.management.relation.Relation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Author: Karis Sponsler
 * Date: 8/8/17
 */
public class ConfigurationManager extends TypeManager {
    /**
     * @param someClass
     * @param <T>
     * @return
     */
    public static <T> ServiceConfiguration generateConfiguration(Class<T> someClass) {
        return new ServiceConfiguration.Builder(someClass.getName()).withAttributes(generateConfigurationAttributes(someClass)).build();
    }

    public static <T> ListServiceConfiguration generateListConfiguration(Class<T> someClass, String configName) {
        ListServiceConfiguration listServiceConfiguration = new ListServiceConfiguration(configName);
        listServiceConfiguration.getAttributes().addAll(generateListConfigurationAttributes(someClass));
        return listServiceConfiguration;
    }

    /**
     * @param someClass
     * @param <T>
     * @return
     */
    public static <T> Collection<ServiceConfigurationAttribute> generateListConfigurationAttributes(Class<T> someClass) {
        Field[] fields = someClass.getDeclaredFields();
        Set<ServiceConfigurationAttribute> attributes = new HashSet<>();
        for (Field field : fields) {
            Attribute attribute = field.getAnnotation(Attribute.class);
            if (attribute != null && !attribute.excludeFromList()) {
                attributes.add(getListServiceConfigurationAttributeFromField(field, attribute));
            }
        }
        return attributes;
    }

    /**
     * @param field
     * @param attribute
     * @return
     */
    protected static ListServiceConfigurationAttribute getListServiceConfigurationAttributeFromField(Field field, Attribute attribute) {
        int index = attribute.index();
        String name = attribute.name();
        if (StringUtils.isEmpty(name)) {
            name = inferName(field.getName());
        }
        ConverterAttributeType converterAttributeType = null;
        AttributeType attributeType = attribute.dataType();
        if (attributeType.equals(AttributeType.None)) {
            converterAttributeType = inferDataType(field.getType().getSimpleName());
        }
        ListServiceConfigurationAttribute listServiceConfigurationAttribute = new ListServiceConfigurationAttribute();
        listServiceConfigurationAttribute.setAttributeIndex(index);
        listServiceConfigurationAttribute.setName(name);
        listServiceConfigurationAttribute.setAttributeType(converterAttributeType.getAttributeType());
        listServiceConfigurationAttribute.setIsListItemConfiguration(true);
        return listServiceConfigurationAttribute;
    }

    /**
     * @param someClass
     * @param <T>
     * @return
     */
    public static <T> Collection<ServiceConfigurationAttribute> generateConfigurationAttributes(Class<T> someClass) {
        Collection<ServiceConfigurationAttribute> attributes = new ArrayList<>();
        for (AttributeProxy proxy : getMethodAndFieldAnnotationsForClass(someClass)) {
            Attribute attribute = proxy.getAttributeAnnotation();
            Relationship relationship = proxy.getRelationshipAnnotation();
            if (attribute != null) {
                attributes.add(getServiceConfigurationAttributeFromMember(new ConfigurationWrapper(proxy), attribute));
            }
            if (relationship != null) {
                attributes.add(getServiceConfigurationAttributeFromMember(new ConfigurationWrapper(proxy), relationship));
            }
        }
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
        if (name.contains("_")) { // snake case
            name = name.replace("_", " ");
        } else { // camel case
            StringBuilder builder = new StringBuilder();
            if (name.matches("^(set|get).*$")) name = name.substring(3); // remove set or get from front
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
        return WordUtils.capitalize(name);
    }

    /**
     * @param index
     * @param clazz
     * @return
     */
    protected static AttributeMeta inferMetaData(int index, Class clazz) {
        return new AttributeMeta(inferDataType(clazz).getAttributeType(), index);
    }
}
