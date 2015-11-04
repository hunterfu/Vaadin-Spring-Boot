# Vaadin & Spring Boot
Playground for the Vaadin UI &amp; Spring Boot framework. Vaadin for Spring Boot is still in beta so there might be bugs. Nevertheless it is a very neat way to fire a web project with cool UI.

This project is an extended version of this [tutorial](https://vaadin.com/wiki/-/wiki/Spring+Vaadin/I+-+Getting+Started+with+Vaadin+Spring+and+Spring+Boot).

## Preview

![Imgur](http://i.imgur.com/pdUJdNO.gifv)

## Run from cmd line

In the root directory of the project. Run the following commands:

```
$ gradle build
$ java -jar build/libs/mymodule-0.0.1-SNAPSHOT.jar
```

If you don't have gradle installed use the wrapper ```./gradlew build``` instead.

After that navigate your browser to this URL: 

```
localhost:8080
```

## HighChart API integration

The charts are being rendered with the help of [VaadinHighChartsAPI](https://github.com/downdrown/VaadinHighChartsAPI).

## License

MIT
