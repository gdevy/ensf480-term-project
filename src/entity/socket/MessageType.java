package entity.socket;

public enum MessageType
{
	LOGIN_ATTEMPT, // LoginInfo
	LOGIN_RESULT, // User type
	CREATE_NEW_PROPERTY, // Property
	PROPERTY_SEARCH_REQUEST, // PropertySearchCriteria
	PROPERT_SEARCH_RESULT,
	VIEW_SAVED_SEARCHES_REQUEST, // null
	VIEW_SAVED_SEARCHES_RESULT, // ArrayList<PropertySearchCriteria>
	VIEW_LANDLORD_PROPERTIES_REQUEST, // null
	VIEW_LANDLORD_PROPERTIES_RESULT, // ArrayList<Properties>

	NULL_OBJECT
}