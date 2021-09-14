# mastering-corda-tododist

BUILD <project folder>\gradlew deployNodes
RUN <project folder>\build\nodes runnodes.bat

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

Run powershell as Admin to start h2.bat
SELECT * FROM VAULT_LINEAR_STATES VAULT_LINEAR_STATES
SELECT * FROM VAULT_STATES
SELECT * FROM NODE_TRANSACTIONS

inside workflow-java/build.gradle's deployNodes.nodeDefaults, set the H2 database port by adding the below section:

extraConfig = [
    h2Settings: [
        // A random port will be selected.
        address : 'localhost:0'
    ]
]

or inside project's build.gradle for each node, add h2Port:

node {
        name "O=PartyB,L=New York,C=US"
        p2pPort 10008
        h2Port 5556
        rpcSettings {
            address("localhost:10009")
            adminAddress("localhost:10049")
        }
        rpcUsers = [[ user: "user1", "password": "test", "permissions": ["ALL"]]]


    }

Put guava dependencies (ImmutableList) into build.gradle/allProjects/dependencies