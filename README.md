# Quiz Generator
Convert Excel files to Multiple Choice Exam PDF

- [Introduction](#introduction)
- [Dependencies](#dependencies)
- [How To Run](#how-to-run)

## Introduction
This Spring Boot REST API project enables the conversion of Excel files into multiple-choice exam PDFs. Users can upload Excel files, and the API processes the data to generate a formatted PDF document with multiple-choice questions. It leverages Spring Boot for the backend, offering a straightforward and efficient solution for transforming Excel data into printable quiz/exam paper.

## Dependencies
| Dependency | Purpose |
| ------------- | ------------- |
| [Spring Boot](https://spring.io/projects/spring-boot) | Spring Boot forms the backbone of this application, offering a robust and efficient framework for building the server-side components of this e-commerce platform. It simplifies configuration, enhances 
| [Lombok](https://projectlombok.org) | Lombok is a library for Java that can significantly simplify and streamline your Java code by automatically generating common boilerplate code. |
| [Poiji](https://github.com/ozlerhakan/poiji) | Poiji is a tiny thread-safe Java library that provides one way mapping from Excel sheets to Java classes. In a way it lets us convert each row of the specified excel data into Java objects. Poiji uses Apache Poi (the Java API for Microsoft Documents) under the hood to fulfill the mapping process. |
| [OpenPDF](https://github.com/LibrePDF/OpenPDF) | OpenPDF is a Java library for creating and editing PDF files with a LGPL and MPL open source license. OpenPDF is the LGPL/MPL open source successor of iText, and is based on some forks of iText 4 svn tag. |
| [OpenAPI](https://springdoc.org) | The springdoc-openapi Java library helps automating the generation of API documentation using Spring Boot projects. springdoc-openapi works by examining an application at runtime to infer API semantics based on Spring configurations, class structure and various annotations. |

## How To Run
- Clone the repository
- Make sure you are using Java 17 and Maven 3
- You can build the project by running mvn clean package
- Once successfully built, you can run the jar file using one of these commands :
```
java -jar quiz-generator-0.0.1-SNAPSHOT.jar --file.directory=C:/Your/File/Directory
java -jar quiz-generator-0.0.1-SNAPSHOT.jar --file.directory=C:/Your/File/Directory --server.port=YourPort
```
> [!IMPORTANT]  
> You have to a define a valid file directory.

> [!NOTE]  
> The application will run on port 8080 if not defined in the command.

- Once the application runs you should see something like this :
```
2023-11-11T14:33:27.371+08:00  INFO 7736 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2023-11-11T14:33:27.390+08:00  INFO 7736 --- [main] d.m.q.QuizGeneratorApplication           : Started QuizGeneratorApplication in 2.722 seconds (process running for 3.218)
```
