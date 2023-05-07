package org.ldclrcq.marrtrix.domain.radarr.webhook

enum class RadarrEventType {
    Test,
    Grab,
    Download,
    Rename,
    MovieDelete,
    MovieFileDelete,
    Health,
    ApplicationUpdate,
    MovieAdded
}