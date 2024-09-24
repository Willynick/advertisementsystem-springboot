package com.senlainc.advertisementsystem.backendutils;

import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.reflect.FieldUtils;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@UtilityClass
public class Converter {

    private static SingularAttribute getAttribute(Class metamodelClass, String attribute) {
        try {
            return (SingularAttribute) FieldUtils.readDeclaredStaticField(metamodelClass, attribute);
        } catch (IllegalAccessException e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    public static Sort convertSortParameter(List<ViewSortParameter> parameters) {
        List<Sort.Order> sortParameters = new ArrayList<>();
        parameters.forEach(viewSortParameter -> {
            sortParameters.add(new Sort.Order(viewSortParameter.getSortDirection(), viewSortParameter.getAttribute()));
        });
        return Sort.by(sortParameters);
    }

    public static List<SortParameter> convertSortParameter(Class metamodelClass, List<ViewSortParameter> parameters) {
        List<SortParameter> sortParameters = new ArrayList<>();
        parameters.forEach(viewSortParameter -> {
            SingularAttribute attribute = Converter.getAttribute(metamodelClass, viewSortParameter.getAttribute());
            if (attribute == null) {
                log.warn(Constants.METAMODEL_MESSAGE);
            } else {
                sortParameters.add(new SortParameter(
                        attribute,
                        viewSortParameter.getSortDirection()));
            }
        });
        return sortParameters;
    }

    public static List<UpdateParameter> convertUpdateParameter(Class metamodelClass,
                                                               List<ViewUpdateParameter> viewUpdateParameters) {
        List<UpdateParameter> updateParameters = new ArrayList<>();
        viewUpdateParameters.forEach(viewParameter -> {
            SingularAttribute attribute = Converter.getAttribute(metamodelClass, viewParameter.getAttribute());
            if (attribute == null) {
                log.warn(Constants.METAMODEL_MESSAGE);
            } else {
                updateParameters.add(new UpdateParameter(
                        attribute,
                        viewParameter.getValue()));
            }
        });
        return updateParameters;
    }

    public static List<GetParameter> convertGetParameter(Class metamodelClass, List<ViewGetParameter> getParameters) {
        List<GetParameter> parameters = new ArrayList<>();
        getParameters.forEach(getParameter -> {
            SingularAttribute attribute = Converter.getAttribute(metamodelClass, getParameter.getAttribute());
            if (attribute == null) {
                log.warn(Constants.METAMODEL_MESSAGE);
            } else {
                parameters.add(new GetParameter(
                        attribute,
                        getParameter.getValue(),
                        getParameter.getSortDirection()));
            }
        });
        return parameters;
    }

    public static<T> void prepareSortingQuery(List<SortParameter> sortParameters, Selection<? extends T> selection,
                                              Root<T> root, CriteriaBuilder cb, CriteriaQuery<T> cq) {
        if (sortParameters.size() <= 0) {
            cq.select(selection);
        } else {
            List<Order> orders = new ArrayList<>();
            Order order;
            for (SortParameter sortParameter : sortParameters) {
                SingularAttribute<T, ?> attribute = sortParameter.getAttribute();
                Expression expression = root.get(attribute);;
                if (sortParameter.getSortDirection().equals(Sort.Direction.ASC)) {
                    order = cb.asc(expression);
                } else {
                    order = cb.desc(expression);
                }
                orders.add(order);
            }
            cq.orderBy(orders);
        }
    }

    public static<T> void prepareUpdateQuery(List<UpdateParameter> updateParameters, CriteriaUpdate<T> cu) {
        SingularAttribute singularAttribute;
        for (UpdateParameter updateParameter : updateParameters) {
            singularAttribute = updateParameter.getAttribute();
            Object value = prepareValue(singularAttribute, updateParameter.getValue());
            cu.set(singularAttribute, value);
        }
    }

    public static<T> Predicate preparePredicateWithSelectParameters(CriteriaBuilder cb, CriteriaQuery<T> cq,
                                                                    Root<T> root, List<GetParameter> parameters) {
        List<Order> orders = new ArrayList<>();
        Order order;
        Predicate[] predicates = new Predicate[parameters.size()];
        SingularAttribute<T, ?>  singularAttribute;
        for (int i = 0; i < parameters.size(); i++) {
            singularAttribute = parameters.get(i).getAttribute();
            if (parameters.get(i).getSortDirection().equals(Sort.Direction.ASC)) {
                order = cb.asc(root.get(singularAttribute));
            } else {
                order = cb.desc(root.get(singularAttribute));
            }
            orders.add(order);
            Object value = prepareValue(singularAttribute, parameters.get(i).getValue());
            predicates[i] = cb.equal(root.get(singularAttribute), value);
            cq.orderBy(orders);
        }
        return cb.and(predicates);
    }

    private Object prepareValue(SingularAttribute singularAttribute, Object value) {
        return singularAttribute.getType().getJavaType().isEnum()
                ? Enum.valueOf(singularAttribute.getType().getJavaType(), value.toString())
                : value;
    }
}
