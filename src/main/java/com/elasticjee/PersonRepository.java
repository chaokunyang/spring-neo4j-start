package com.elasticjee;

import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author chaokunyang
 */
public interface PersonRepository extends Neo4jRepository<Person, Long> {
    Person findByName(String name);
}
