trending-stream
===============

Get email notifications about latest GitHub trending repositories.

Build
-----

JDK >= 1.8 required. Build with

    $ ./mvnw clean package

An executable jar `trending-stream.jar` will be created in `target` directory.

Configuration
-------------

Application configuration is stored in `application.yml` file. 
You can use `application.sample.yml` as an example.

You should provide configuration for email server and message from, to and subject fields.

Run
---

Run built executable jar:

    $ ./trending-stream.jar

Or with `java`:

    $ java -Xmx32m -jar trending-stream.jar  

License
-------

BSD, see `LICENSE`.
