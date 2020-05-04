Axon Framework - Spring Cloud Extension - GiftCard Sample
=========================================================

This repository contains a sample project showcasing [Axon Framework](https://github.com/AxonFramework/AxonFramework) in conjunction with the [Spring Cloud Extension](https://github.com/AxonFramework/extension-springcloud). 
The main aim is to show how the extension can be used to allow distributed command handling with various implementations of [Spring Cloud](https://spring.io/projects/spring-cloud). 

Application overview
--------------------

This sample project is a multi-module project using Maven profiles, written in Kotlin.
The profiles allow you to select the Spring Cloud implementation of your choice to see what specifics might come in to play when using it.

### Base Domain

The base domain to be used in every profile is the `giftcard` module, which depends on the `axon-spring-boot-starter` and `axon-springcloud-spring-boot-starter` modules.
To interact with the domain it contains both a scheduled client and a REST client.

By default, scheduled command dispatching is turned on. 
This can be disabled with the `dispatching.enabled` property.
For convenience to Intellij IDEA users, the REST client can be interacted with through the `requests.http`

Note that all message-transitions are logged on INFO log level to show which node is handling a given command in a distributed set up. 

### Spring Cloud profiles

The Spring Cloud profiles which are currently present are:

 - **Spring Cloud Netflix / Eureka** - Uses [Spring Cloud Netflix'](https://spring.io/projects/spring-cloud-netflix) discovery mechanism called Eureka. 
 When selecting this profile, the `eureka-client` and `eureka-server` modules will be toggled on.
 **Note** that Eureka as solution currently does not work. This will be fixed with extension release 4.3.1 under issue [#17](https://github.com/AxonFramework/extension-springcloud/issues/17).
 
How-to start up
---------------

First, select the profile you like to test.
Depending on the profile the start up process will differ:

 - **Spring Cloud Netflix / Eureka** - First, start up the `eureka-server`. 
 The provided `application.properties` should be sufficient.
 Then, start two or more `eureka-client` instances.
 When starting these it is required to have different `server.port` numbers, as well as provided the `node.number` property to keep them apart.
 Subsequently, you can toggle whether a node should have scheduled dispatching enabled with the `dispatching.enabled` property.
 
> **Intellij IDEA**
>
> A basic set of Intellij IDEA run configuration is provided in this project.
> They should be registered automatically if you open this repository through Intellij.
> If they aren't, you can find the `.xml` config files in the `~/.idea/runConfiguration` folder.
  
Contributing
------------

Some Spring Cloud implementations out there are not present as profiles within this sample.
Other implementations can always be added to this repository and can be regarded as worthwhile contributions.

When looking to provide a pull request for another such implementation, know that it suffices to provide just a client.
Depending on the `giftcard` module gives you all the necessary Axon bits and pieces to distribute commands.
Regardless, contributions to the `giftcard` module to expand its capabilities are also welcome. 