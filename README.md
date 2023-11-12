# Quiz Generator
Convert Excel files to Multiple Choice Exam PDF

- [Introduction](#introduction)
- [Dependencies](#dependencies)
- [How To Run](#how-to-run)
- [Uploading Excel File](#uploading-excel-file)
- [Downloading Quiz Document](#downloading-quiz-document)

## Introduction
This Spring Boot REST API project enables the conversion of Excel files into multiple-choice exam PDFs. Users can upload Excel files, and the API processes the data to generate a formatted PDF document with multiple-choice questions. It leverages Spring Boot for the backend, offering a straightforward and efficient solution for transforming Excel data into printable quiz/exam paper.

## Dependencies
| Dependency | Purpose |
| ------------- | ------------- |
| [Spring Boot](https://spring.io/projects/spring-boot) | Spring Boot is an open source Java-based framework used to create microservices. It is developed by Pivotal Team and is used to build stand-alone and production-ready applications.
| [Lombok](https://projectlombok.org) | Lombok is a library for Java that can significantly simplify and streamline your Java code by automatically generating common boilerplate code. |
| [Poiji](https://github.com/ozlerhakan/poiji) | Poiji is a tiny thread-safe Java library that provides one way mapping from Excel sheets to Java classes. In a way it lets us convert each row of the specified excel data into Java objects. Poiji uses Apache Poi (the Java API for Microsoft Documents) under the hood to fulfill the mapping process. |
| [OpenPDF](https://github.com/LibrePDF/OpenPDF) | OpenPDF is a Java library for creating and editing PDF files with a LGPL and MPL open source license. OpenPDF is the LGPL/MPL open source successor of iText, and is based on some forks of iText 4 svn tag. |
| [OpenAPI](https://springdoc.org) | The springdoc-openapi Java library helps automating the generation of API documentation using Spring Boot projects. springdoc-openapi works by examining an application at runtime to infer API semantics based on Spring configurations, class structure and various annotations. |

## How To Run
- Clone the repository
- For building and running the project, you need to have [Java 17](https://www.oracle.com/java/technologies/downloads/#java8](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)) and [Maven 3](https://maven.apache.org/download.cgi) installed on your computer
- Build the project by running mvn clean package
```
C:\Your\Directory\quiz-generator>mvn clean package -Dfile-directory=C:/Your/Directory/ExcelFilesFolder
...
...
...
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  10.654 s
[INFO] Finished at: 2023-11-11T22:23:52+08:00
[INFO] ------------------------------------------------------------------------
```
> [!IMPORTANT]  
> You have to a define a valid file directory when building, the application validates the directory on start up.
- Once successfully built, you can run the jar file using one of these commands :
```
java -jar quiz-generator-0.0.1-SNAPSHOT.jar --file.directory=C:/Your/Directory/ExcelFilesFolder
java -jar quiz-generator-0.0.1-SNAPSHOT.jar --file.directory=C:/Your/Directory/ExcelFilesFolder --server.port=YourPort
```
> [!IMPORTANT]  
> You have to a define a valid file directory, this is for storing excel files.

> [!NOTE]  
> The application will run on port 8080 if not defined in the command.

- Once the application runs you should see something like this :
```
2023-11-11T14:33:27.371+08:00  INFO 7736 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2023-11-11T14:33:27.390+08:00  INFO 7736 --- [main] d.m.q.QuizGeneratorApplication           : Started QuizGeneratorApplication in 2.722 seconds (process running for 3.218)
```
- This application defines the following endpoints :
```
http://localhost:8080/upload
http://localhost:8080/download
```
## Uploading Excel File
- Create an excel file of questionnaires following this format
![questions_file](https://github.com/mrkevr/quiz-generator/assets/98044708/10688583-a0ee-421f-bde6-9b3052b8769a)
- Upload
    - URI : http://localhost:8080/upload
    - HTTP method: POST
    - Attach the file on the request body with the parameter name "file".
- If the request is successful, you'll get a response like this :
```
{
    "timestamp": "2023-11-12T16:42:25.6832042",
    "status": 200,
    "message": "File uploaded and ready for download",
    "downloadUrl": "http://localhost:8080/download?id=e814bb12-3580-45fc-89c9-3a554c01d0de"
}
```
## Downloading Quiz Document
- Convert the excel file into PDF document and download using the download URL given by the response above.
```
http://localhost:8080/download?id=e814bb12-3580-45fc-89c9-3a554c01d0de
```
- You can include a title on top of the document by adding it on the request parameter. This is optional.
```
http://localhost:8080/download?id=e814bb12-3580-45fc-89c9-3a554c01d0de&title=JAVA EXAMINATION 2023
```
- If the file follows the correct format, you will get something like this :
![questions_pdf](https://github.com/mrkevr/quiz-generator/assets/98044708/8efabe60-7dac-423a-aede-147235167ab9)
