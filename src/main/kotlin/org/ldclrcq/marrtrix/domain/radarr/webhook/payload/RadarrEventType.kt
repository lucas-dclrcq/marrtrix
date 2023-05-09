package org.ldclrcq.marrtrix.domain.radarr.webhook.payload

enum class RadarrEventType {
    Test,
    Grab,
    Download,
    Rename,
    MovieDelete,
    MovieFileDelete,
    Health,
    ApplicationUpdate,
    MovieAdded,
    NullEventType
}