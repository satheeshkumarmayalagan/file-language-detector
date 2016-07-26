# file-language-detector

Introduction:
This application is a web application that detects the language of the uploaded file. The web application allows the user to upload a text, doc or a pdf file less than 2MB and detects the language of the file from the first line of the file assuming that the rest of the file content is also of the same language.

Detecting Language:
The application uses the Language Detection API ( https://detectlanguage.com/ ). It uses the JAVA Client API to find the language of the given file.
                <dependency>
                    <groupId>com.detectlanguage</groupId>
                    <artifactId>detectlanguage</artifactId>
                    <version>${detectlanguageversion}
                    </version>
                </dependency>

The application exposes an /detectLanguage as a POST request and takes in a multipart file as
the input. The REST Service uses the above mentioned language detection API to find the
language and provides the language in the response.

AngularJS based HTML page is used for the UI. The AngularJS page allows the user to upload
the file, calls the /detectLanguage REST service of the application and displays the language in
the page

Test Cases:

Unit test cases uses MockMVC to test the REST Service. It send different files english.txt, english.pdf and french.txt files (/test/resources/requests) to the REST API and asserts the language in the response.
