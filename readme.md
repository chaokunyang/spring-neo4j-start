Spring Neo4j Demo

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