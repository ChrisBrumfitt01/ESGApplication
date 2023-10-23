# ESGApplication
Application written as part of the recruitment process for ESG.

We will need a new Java app to read in a CSV file from a directory. The contents will then need to be sent to a REST API endpoint, in JSON format, and saved to a SQL database.

1. Create a console app to read in a CSV file from a directory.

2. Parse the CSV file of which the contents are:

- Customer Ref
- Customer Name
- Address Line 1
- Address Line 2
- Town
- County
- Country
- Postcode

3. Loop through the rows of the CSV file and send each row to a REST API POST endpoint, in JSON format.

4. The REST API should then save the content to a database table. Format can match the CSV file.

5. Create a REST API GET endpoint to retrieve the customer information, passing in a customer ref to filter the data. Contents should be returned in JSON format.

6. Some documentation or Wiki to explain the approach taken.

Where possible, a Test-Driven Development (TDD) approach should be taken.

# Approach taken

I have created this as a Spring boot application.

#### First commit

First, I have been thinking about the CSV reader which will need to parse a CSV file and then send the rows to a POST endpoint. So working backwards, I have created the POST endpoint first with a service and repository behind it. Created an integration test using MockMVC and a H2 database to test the creation of a Customer.


#### Second commit 

Next, I began to implement classes that would parse the CSV and send the HTTP request.

I could have produced a List of CustomerCsvBean after reading in the CSV file, but this would mean looping twice (once to populate the list of Customers, again to send the POST requests) so I looped through and on each iteration, send the request.

I'm considering making this multi-threaded or using Webflux because when sending the POST request and waiting for the response, this program will be at a standstill. It would be more efficient if it could be non-blocking and process more lines whilst waiting for this response. To save time, this wasn't implemented yet.

One problem I noticed whilst writing this: if a line failed to process, an exception would be thrown, the rest of the file would not be processed, whilst those earlier on would already have been processed. And it would not be possible to roll these back without a lot of extra work.
I made a change to this to handle invalid lines. So if the program reaches an invalid line, it will log a message and then continue processing the rest of the file.


#### Third commit 

Fixed errors not spotted earlier, caused by missing configuration for RestTemplate.

#### Fourth commit 

Implemented the GET endpoint for retrieving a customer. Used a global exception handler because I think it is a cleaner way to handle non-2xx errors.