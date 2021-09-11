# mastering-corda-tododist

chapter6
ContractState, Linearstate: override geParticipants() and getLinearId()
Contract implements FlowLogic must override call method (if Void, must explicitly return null)
DO NOT REFACTOR EXISTING CLASS!!! JUST ADD WHAT YOU NEED!!! @BelongsToContract(TemplateContract.class) need template files!
After the build add into node.conf :
h2Settings {
    address: "localhost:12345"
}
and you will se at the startup
Database connection url is: jdbc:h2:tcp://localhost:12345/node

Why this error???
Syntax error in SQL statement "SET FORBID_CREATION[*] TRUE"; expected "@, AUTOCOMMIT, MVCC, EXCLUSIVE, IGNORECASE, PASSWORD, SALT, MODE, COMPRESS_LOB, DATABASE, COLLATION, BINARY_COLLATION, UUID_COLLATION, CLUSTER, DATABASE_EVENT_LISTENER, ALLOW_LITERALS, DEFAULT_TABLE_TYPE, CREATE, HSQLDB.DEFAULT_TABLE_TYPE, PAGE_STORE, CACHE_TYPE, FILE_LOCK, DB_CLOSE_ON_EXIT, AUTO_SERVER, AUTO_SERVER_PORT, AUTO_RECONNECT, ASSERT, ACCESS_MODE_DATA, OPEN_NEW, JMX, PAGE_SIZE, RECOVER, NAMES, SCOPE_GENERATED_KEYS, SCHEMA, DATESTYLE, SEARCH_PATH, SCHEMA_SEARCH_PATH, JAVA_OBJECT_SERIALIZER, LOGSIZE, FOREIGN_KEY_CHECKS"; SQL statement:
SET FORBID_CREATION TRUE [42001-200] 42001/42001