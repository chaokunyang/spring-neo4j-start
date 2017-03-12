package com.elasticjee;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author chaokunyang
 */
@NodeEntity
public class Person {
    @GraphId private Long id;
    private String name;
    private Person() {
        // Empty constructor required as of Neo4j API 2.0.5
    }
    public Person(String name) {
        this.name = name;
    }
    @Relationship(type = "TEAMMATE", direction = Relationship.UNDIRECTED)
    public Set<Person> teammates;

    public void worksWith(Person person) {
        if(teammates == null)
            teammates = new HashSet<>();
        teammates.add(person);
    }

    @Override
    public String toString() {
        return this.name + "'s teammates => " +
                Optional.ofNullable(teammates)
                        .orElse(Collections.emptySet())
                .stream()
                .map(person -> person.getName()).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Person> getTeammates() {
        return teammates;
    }

    public void setTeammates(Set<Person> teammates) {
        this.teammates = teammates;
    }
}
