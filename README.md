Welcome to Tomcat 7 Websocket ClickStart on CloudBees

This is a "ClickStart" that gets you going with a simple Maven WebSocket "seed" project starting point, which will show you how to use WebSocket on CloudBees platform. You can launch it here:

<a href="https://grandcentral.cloudbees.com/?CB_clickstart=https://raw.github.com/fbelzunc/tomcat7-websocket-clickstart/master/clickstart.json"><img src="https://d3ko533tu1ozfq.cloudfront.net/clickstart/deployInstantly.png"/></a>

This will setup a continuous deployment pipeline - a CloudBees Git repository, a Jenkins build compiling and running the test suite (on each commit).
Should the build succeed, this seed app is deployed on a Tomcat 7 container.

# Application Overview

This is a really basic echo WebSocket app running on Tomcat 7 which will help you to do the set up for your own WebSocket server on CloudBees platform.

<img alt="WebSocket - Main Screen" src="https://raw.github.com/fbelzunc/tomcat7-websocket-clickstart/master/src/site/img/websocket-app.png" style="width: 70%;"/>

## Server side

On the web.xml file you usually define the url-pattern where your WebSocket server will be listening to new clients.

```xml
<web-app
        xmlns="http://java.sun.com/xml/ns/javaee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
        id="tomcat7-hibernate-s3-clickstart" version="3.0" metadata-complete="false">

    <servlet>
        <servlet-name>WsChatServlet</servlet-name>
        <servlet-class>localdomain.localhost.domain.WsChatServlet</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>WsChatServlet</servlet-name>
        <url-pattern>/wschat/WsChatServlet</url-pattern>
    </servlet-mapping>

</web-app>
```

You will need also to enable http 1.1. Go to the "Configure your application to use WebSocket" section to know how to enable it through the CloudBees SDK.

## Client side

You will need to create a WebSocket connection specifying:

* The host which listens to the new requests: "document.location.host"
* The servlet which is listening to the new requests: "/wschat/WsChatServlet"
* Regarding the port number used, please, note that you don't need to specify the port number in which it is working on.

```js
var ws = new WebSocket("ws://"+ document.location.host +"/wschat/WsChatServlet");
```

# Create application manually

### Create Tomcat container

```sh
bees app:create -a websocket-app -t tomcat7
```

### Configure your application to use WebSocket

You need a parameter set for your application that tells it to use http 1.1.

```sh
bees app:proxy:update -a websocket-app http_version=1.1
```

### Deploy your application

```sh
bees app:deploy -a  websocket-app -t tomcat7 app.war
```

# CloudBees WebSocket documentation

You should also take a look at our current Websockets documentation on our Wiki for Developers.

https://developer.cloudbees.com/bin/view/RUN/WebSockets