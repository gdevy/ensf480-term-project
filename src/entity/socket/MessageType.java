package entity.socket;

public enum MessageType
{
	LOGIN_ATTEMPT, // LoginInfo
	LOGIN_RESULT, // User type

	CREATE_NEW_PROPERTY, // Property
	EDIT_CURRENT_PROPERTY, // Property

	PROPERTY_SEARCH_REQUEST, // PropertySearchCriteria
	PROPERT_SEARCH_RESULT, // ArrayList<PropertySearchCriteria>

	VIEW_SAVED_SEARCHES_REQUEST, // null
	VIEW_SAVED_SEARCHES_RESULT, // ArrayList<PropertySearchCriteria>

	VIEW_LANDLORD_PROPERTIES_REQUEST, // null
	VIEW_LANDLORD_PROPERTIES_RESULT, // ArrayList<Properties>

	SEND_EMAIL_TO_LANDLORD,

	VIEW_MANAGER_REPORT_REQUEST,
	VIEW_MANAGER_REPORT_RESULT,

	DELETE_PROPERTY_SEARCH, // PropertySearchCriteria

	NULL_OBJECT
}