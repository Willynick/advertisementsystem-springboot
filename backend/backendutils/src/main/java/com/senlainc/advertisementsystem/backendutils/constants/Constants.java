package com.senlainc.advertisementsystem.backendutils.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String DATABASE_MESSAGE = "The operation was not performed";
    public static final String ERROR_MESSAGE = "The operation was not performed. Class: %s. Method:%s";
    public static final String NO_RESULT_MESSAGE = "There are no results. Class: %s. Method:%s";
    public static final String METAMODEL_MESSAGE = "Unable to get metamodel attribute. Check out the metamodel.";

    public static final String EXISTING_ENTITY_MESSAGE = "Object of type %s is already exist";
    public static final String OBJECT_IS_NOT_FIND = "Object of type %s is not find";

    public static final String EMPTY_ADVERTISEMENTS_LIST_MESSAGE = "There are no advertisements to purchase";

    public static final String ILLEGAL_ARGUMENT = "Wrong number of parameters passed";
    public static final String RESOURCE_NOT_FOUND = "Resource not found";
    public static final String VALIDATION_ERROR = "Validation error";
    public static final String TYPE_MISMATCH = "Type mismatch";
    public static final String CONSTRAINT_VIOLATIONS = "Constraint violations";
    public static final String MISSING_PARAMETERS = "Missing parameters";
    public static final String UNSUPPORTED_MEDIA_TYPE = "Unsupported media type";
    public static final String METHOD_NOT_FOUND = "Method not found";
    public static final String ERROR_OCCURRED = "Error occurred";
    public static final String ACCESS_DENIED = "Access denied";

    public static final String USERNAME_NOT_FOUND_MESSAGE = "User with username: %s not found";
    public static final String BAD_CREDENTIALS_MESSAGE = "Invalid username or password";

    public static final String ADVERTISEMENT_HIDE_MESSAGE = "This advertisement hide, restore it";
    public static final String NOT_ENOUGH_MONEY_MESSAGE = "There is not enough money on the account to complete the operation";
    public static final String ORDER_ALREADY_CANCELLED = "Order is already cancelled";
    public static final String ORDER_ALREADY_COMPLETED = "Order is already completed";
    public static final String CHOOSE_DELIVERY_TYPE = "Choose delivery type";
    public static final String ADVERTISEMENT_IS_NOT_AVAILABLE = "Advertisement is not available";
    public static final String RATING_VALUE_IS_NOT_POSSIBLE = "This rating value is not possible";
    public static final String ALREADY_RATED_USER = "You have already rated this user";

    public static final String BASE_URL = "http://localhost:8080/";

}
