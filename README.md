# Easy Santa

## Preamble
Easy Santa is a web application written for Google App Engine (GAE) using a variety of technologies to facilitate and manage secretive assignments of individuals to one another in relatively small groups.

## Technology Overview
Easy Santa utilizes Java source code, which compiles to standard Java bytecode for execution on the server as well as Javascript by way of crosscompilation using Google Web Toolkit (GWT) for execution on the client.  Application state is persisted in Big Table through JDO framework.

## Build Instructions
Easy Santa can be built using Apache Ant by running it against `build.xml`.  All configuration describing external dependencies and versions can be reviewed and modified within `build.properties`.  The name of the GAE artifact to be deployed can be modified within `htdoc/WEB-INF/appengine-web.xml`.  Javadoc API documentation can be produced with `api` build target within `build.xml`.

## Operating Instructions
When deployed, this application will allow a user to log into a web UI hosted on GAE using GAE-compatible credentials (such as standard Google accepted logins) and will allow said user to create what's known as a user pool.  A user may not join an existing user pool that they are not already a member of.  If a user attempts to create a pool with a name that already exists, this action will be rejected.

Upon successful creation of a user pool, the user will be automatically placed in that pool.  The user will then be able to add other users to the pool by specifying their email addresses.  Specifying an email address that does not fit the standard email pattern will be rejected.  Upon successful addition of users to a user pool, those users gain same administrative privileges over the pool that the pool's creator had.  All users within a pool have symmetrical privileges, which include the following permissible actions:
* adding new users to pool
* removing existing users from pool (including self)
* shuffling a pool

When a user removes themsevles from a pool, they retain administrative privileges over the pool for as long as they remain on the browser page.  This will give the user the opportunity to add themselves back to the pool if the removal action was performed by mistake.

A user pool is always in one of two states: shuffled or not shuffled.  A shuffled pool is one where users have been given assignments (were assigned other users within the pool).  It is technically possible to shuffle a 1 or 2 user pool, though it makes no goddamn sense.  Adding or removing a user from a shuffled pool will make the pool unshuffled.  Adding a user to a shuffled pool and then removing that user from it will take the pool back to its shuffled state, however removing a user and then adding same user back will not.

Upon successful shuffling of a pool, the system will contact each member by email, notifying them of their assignment.  Each member of the pool can subsequently log into the application and request the same reminder be sent to them at any time.  Shuffling an already shuffled pool is permissible and will result in new assignments being sent out.
