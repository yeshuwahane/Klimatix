package location

/**
 * This enum represents the permissions used in the application.
 * It provides constant values for various permissions related to system services and features.
 */
enum class Permission {

    // Previous permissions

    /**
     * Indicates that the system setting location service is on.
     */
    LOCATION_SERVICE_ON,

    /**
     * App location fine permission.
     */
    LOCATION_FOREGROUND,

    /**
     * App location background permission.
     */
    LOCATION_BACKGROUND,
}