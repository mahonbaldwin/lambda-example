# LambdaExample

A simplified example of the behavior found [here](https://github.com/cognitect-labs/aws-api/issues/131).

## Build

```
mkdir classes
clj -e "(compile 'dynamo-example.core)"
clj -Auberjar
```

## Upload
Manually upload it by following these instructions.

1. Create Lambda [here](https://console.aws.amazon.com/lambda/home?region=us-east-1#/functions).
    - Call it whatever you want.
    - Set Runtime to `Java 11`.
1. On the function screen upload the file and change the handler (under "Function Code").
    - Upload the jar found in the `target` dir.
    - Handler should be `dynamo-example::main`.
1. Update role's permissions to include DynamoDB.
    - Under "Execution role" click `View the <name of role> on the IAM console.`
    - Click "Attach policies".
    - Search "Dynamo".
    - Attach `AmazonDynamoDBFullAccess` (for test, you can also limit the scope).
1. Create a DynamoDB.
    - From [here](https://console.aws.amazon.com/dynamodb/home?region=us-east-1#create-table:) create a new table called "example-db" (or whatever you want)
    - Uncheck "Use default settings"
    - Select "On-demand"
    - Click "Create".
1. Click "Coonfigure Test" at the top of the Lambda's config page.
    - Create a new test with the following body using the table you created above:
        ```
        {
            "table": "example-db",
            "name": "hello"
        }
        ```
    - Save.
1. Click "Test".

It should run successfully, with the metadata output and describe table output. But if you look in the table there