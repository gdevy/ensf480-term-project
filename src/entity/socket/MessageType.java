package entity.socket;

public enum MessageType
{
	LOGIN_ATTEMPT, // LoginInfo
	LOGIN_RESULT, // User type
	CREATE_NEW_PROPERTY, // Property
	PROPERTY_SEARCH_REQUEST, // PropertySearchCriteria
	VIEW_SAVED_SEARCHES_REQUEST,
	VIEW_SAVED_SEARCHES_RESULT
}