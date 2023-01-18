# http4s-tomcat

Provides an [Apache Tomcat][tomcat] backend for [http4s][http4s].

## Unmaintained

See [http4s-jetty](https://github.com/http4s/http4s-jetty) and [http4s-servlet](https://github.com/http4s/http4s-servlet) for alternative servlet integrations.

## SBT coordinates

```scala
libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-tomcat" % http4sTomcatV
)
```

## Compatibility

| http4s-tomcat | http4s-core | servlet-api | tomcat | Scala 2.12 | Scala 2.13 | Scala 3 | Status      |
|:--------------|:---------------|:------------|--------|------------|------------|---------|:------------|
| 0.23.x        | 0.23.x         | 3.1         | 9.x    | ✅         | ✅         | ✅      | Stable      |
| 0.24.x        | 0.23.x         | 4.0         | 9.x    | ✅         | ✅         | ✅      | Milestone   |
| 0.25.x        | 0.23.x         | 5.0         | 10.0.x | ✅         | ✅         | ✅      | Milestone   |
| 0.26.x        | 0.23.x         | 6.0         | 10.0.x | ✅         | ✅         | ✅      | Development |

[http4s]: https://http4s.org/
[tomcat]: https://tomcat.apache.org/index.html
