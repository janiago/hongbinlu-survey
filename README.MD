# Private usage by Hongbin Lu.

## This web application can manage survey and classify the response data.

## To run and debug the application on local: 
Install Java SDK 11
Install Maven

Clone the repository to local.
Open the folder with VS Code.

VS Code plugins required: 
Debugger for Java
Extension Pack for Java
Maven for Java
Spring Boot Extension Pack

Go to Run and Debug, then run the "Spring Boot-SurveyApplication"

## To create build
Run below commands in terminal: 
```
mvn clean
mvn package -DskipTests
```

After the build, a jar file will be generated to the target folder. 

## To deploy the application
Install Java 11 on the server
Copy the jar file to the server
Start the application with command: 

```
java -jar survey-system-0.0.1-SNAPSHOT.jar
```

## To use the application
### Management page
Say the server public IP address is 11.11.11.11, then open the management page with: 
http://11.11.11.11:8080

Input the IP address "11.11.11.11" to the page, so that the application can use this IP address to generate the QR code for surveys.

Upload each survey with a JSON file with format: 

```JSON
{
    "name": "Sample Survey",
    "questions": [
        {
            "text": "What is your favorite color?",
            "type": "single",
            "required": true,
            "options": ["Red", "Blue", "Green"]
        },
        {
            "text": "What hobbies do you have?",
            "type": "multiple",
            "required": false,
            "options": ["Reading", "Gaming", "Sports"]
        },
        {
            "text": "Please share your feedback",
            "type": "open",
            "required": true
        }
    ]
}
```

When uploaded successfully, the QR code is generated automatically. You can scan the QR code to open then survey page. 
On server side, the uploaded survey is saved to surveys folder.

Survey can be deleted with button.

Response data of each survey can be downloaed with button. The data is downloaded as JSON file.

