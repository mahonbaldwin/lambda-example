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
1. Click "Coonfigure Test" at the top of the Lambda's config page.
    - Create a new test with the following body:
        ```
        {
            "name": "hello"
        }
        ```
    - Save.
1. Click "Test".

It should run successfully,