package ru.server.spring.models.assistant;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
public class AssistantGraphVertex {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID uuid;

    @ManyToOne
    private AssistantGraphs assistantGraphs;

    private String name;
    private String description;

    @ElementCollection
    @CollectionTable(name = "assistant_graph_edge", joinColumns = { @JoinColumn(name = "child_uuid") } )
    @Column(name = "parent_uuid")
    private Set<UUID> parents;

    @ElementCollection
    @CollectionTable(name = "assistant_graph_edge", joinColumns = { @JoinColumn(name = "parent_uuid") } )
    @Column(name = "child_uuid")
    private Set<UUID> children;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public AssistantGraphs getAssistantGraphs() {
        return assistantGraphs;
    }

    public void setAssistantGraphs(AssistantGraphs assistantGraphs) {
        this.assistantGraphs = assistantGraphs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UUID> getChildren() {
        return children;
    }

    public void setChildren(Set<UUID> children) {
        this.children = children;
    }

    public Set<UUID> getParents() {
        return parents;
    }

    public void setParents(Set<UUID> parents) {
        this.parents = parents;
    }
}
