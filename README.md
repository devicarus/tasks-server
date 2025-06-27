<img src="https://capsule-render.vercel.app/api?type=waving&height=300&color=006FEE&text=Tasks%20Server&reversal=false&section=header&fontColor=ECEDEE&fontAlignY=42&descAlignY=50" />

**Tasks Server** is the backend for the [**Tasks**](https://github.com/devicarus/tasks?tab=readme-ov-file) self-hosted task manager.

## Technologies Used
- [Quarkus](https://quarkus.io/)
- [Gradle](https://gradle.org/)
- [Kotlin](https://kotlinlang.org/)
- [Hibernate ORM](https://hibernate.org/orm/)
- [argon2-jvm](https://github.com/phxql/argon2-jvm)
- [jjwt](https://github.com/jwtk/jjwt)
- [Smallrye JWT](https://github.com/smallrye/smallrye-jwt)

## How to Use

> ⚠️ **WARNING**: This project is currently in development and not yet ready for production use.

> 💡 **TIP**: *Docker image* and *Helm Chart* comming in the near future, stay tuned!

To clone the project, run the following command:

```bash
git clone https://github.com/devicarus/tasks-server.git
```

### Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

### Packaging and running the application

The application can be packaged using:

```shell script
./gradlew build
```

It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./gradlew build -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

### Creating a native executable

You can create a native executable using:

```shell script
./gradlew build -Dquarkus.native.enabled=true
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./gradlew build -Dquarkus.native.enabled=true -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/tasks-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/gradle-tooling>.