Spring Neo4j Demo

一、Standing up a Neo4j server
1、install neo4j and start
2、By default, Neo4j has a username/password of neo4j/neo4j. However, it requires that the new account password be changed. To do so, execute the following command:
```shell
$ curl -v -u neo4j:neo4j -X POST localhost:7474/user/neo4j/password -H "Content-type:application/json" -d "{\"password\":\"secret\"}"
```
This changes the password from neo4j to secret (something to NOT DO in production!) With that completed, you should be ready to run this guide.

注意：spring data neo4j 4.0 与neo4j 3.0 以上版本存在部分不兼容，遇到问题可考虑安装 neo4j 2.3 版本

二、spring data neo4j
1、PersonRepository extends the GraphRepository interface and plugs in the type it operates on: Person. Out-of-the-box, this interface comes with many operations, including standard CRUD (create-read-update-delete) operations.

2、But you can define other queries as needed by simply declaring their method signature. In this case, you added findByName, which seeks nodes of type Person and finds the one that matches on name. You also have `findByTeammatesName`, which looks for a Person node, drills into each entry of the teammates field, and matches based on the teammate’s name.

3、
By default, @EnableNeo4jRepositories will scan the current package for any interfaces that extend one of Spring Data’s repository interfaces. Use it’s basePackageClasses=MyRepository.class to safely tell Spring Data Neo4j to scan a different root package by type if your project layout has multiple projects and its not finding your repositories.

4、
In Neo4j, ALL relationships are directed.
However, you can have the notion of undirected edges at query time. Just remove the direction from your MATCH query :

MATCH (p:Person)-[r:FRIEND_WITH]-(b:Person) where p.name = "PersonB" 

5、In this case, you create three local Person s, Greg, Roy, and Craig. Initially, they only exist in memory. It’s also important to note that no one is a teammate of anyone (yet).

At first, you find Greg and indicate that he works with Roy and Craig, then persist him again. Remember, the teammate relationship was marked as UNDIRECTED, that is, bidirectional. That means that Roy and Craig will have been updated as well.
  
  That’s why when you need to update Roy, it’s critical that you fetch that record from Neo4j first. You need the latest status on Roy’s teammates before adding Craig to the list.