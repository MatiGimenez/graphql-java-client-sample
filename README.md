# graphql-java-client-sample
This is a simple application sample of how to make requests to a GraphQL API using American Express Nodes library.


### Technologies
- Spring Boot
- Java 8
- Nodes (GraphQL library)

### Setting dependencies

```
<dependency>
  <groupId>io.aexp.nodes.graphql</groupId>
  <artifactId>nodes</artifactId>
  <version>0.5.0</version>
</dependency>
    
<repositories>
  <repository>
    <id>bintray-americanexpress-maven</id>
    <url>https://dl.bintray.com/americanexpress/maven</url>
  </repository>
</repositories>
```

### Creating Models
There is two ways of making queries/mutations using this library. First one is requesting an entire Model class and the second one is making the query/mutation using a raw string.
To make queries/mutations using a whole model class, you need to define a Model (with its attributes and constructor) and use these annotations:

**@GraphQLProperty(name = "methodName", arguments = @GraphQLArgument(name = "argumentName"))**

```java
@GraphQLProperty(name = "getThreads", arguments = @GraphQLArgument(name = "input"))
public class ThreadResult {
  ...
```

This annotation is going to build this query

```graphql
query {
  getThreads(input: ) {
    ...
```

Once we have created models, then we can build our queries.

### Building queries/mutations
To make requests to a graphQL API, we need to use GraphQLTemplate interface. It provides us with both the ```query()``` method and the ```mutate()``` method.
Also, to build the query itself, you can use the GraphQLRequestEntity builder.

```java
GraphQLTemplate graphQLTemplate = new GraphQLTemplate();
```

#### Using models

```java
GraphQLRequestEntity requestEntity = GraphQLRequestEntity.Builder()
       .url(URL)
       .scalars(Date.class)
       .arguments(new Arguments("getThreads", new Argument<>("input", "argument")))
       .request(ThreadResult.class)
       .build();
```

- url(): We need to provide an URL
- scalars(): the custom Scalars we are going to use (because GraphQL has Integer, String and Float scalars only), in this case I set Date class beacuse Thread model has a Date type attribute
- arguments(): the arguments we have to send in the request, first we specify the method name an then we can san as any Argument object as we need, we can send an Input Object, a String or a number
- request(): here we can set a class model or a raw string with the specific attributes we need from response
- build(): generates the request to send to GQL API

Also we have a headers() method in which we can set a Map<String, String> value.

#### Using raw string
```java
GraphQLRequestEntity requestEntity = GraphQLRequestEntity.Builder()
       .url(URL)
       .scalars(Date.class)
       .headers(headers)
       .request("query { getThreadById(t_id: " + id + " ) { message } }")
       .build();
```
We can build the query via a raw string, just appending the arguments and declaring what specifics attributes we want to receive.

Finally, we send the request through the GraphQLTemplate using query() or mutate() methods. So we can make query o mutation requests using the same request we build before, you just need to specify corresponding request method.

To get the response we have GraphQLResponseEntity that receives the answer from query() or mutate() methods. After that we can access to the response attributes and the data itself.

```java
GraphQLResponseEntity<ThreadResult> responseEntity = graphQLTemplate.query(requestEntity, ThreadResult.class);
result = responseEntity.getResponse().getThreads();
```

```java
GraphQLResponseEntity<AuthData> responseEntity = graphQLTemplate.mutate(requestEntity, AuthData.class);
```

### Further information
You can check [American Express Nodes][nodes_link] for further information.

[nodes_link]: https://github.com/americanexpress/nodes
