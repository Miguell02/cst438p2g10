package org.example.dailytier.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TierList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private User user;

    private String tierName;

    @Column(columnDefinition = "TEXT") // Store JSON data
    private String items;

    public TierList() {}

    public TierList(User user, String tierName, String items) {
        this.user = user;
        this.tierName = tierName;
        this.items = items;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public String getTierName() { return tierName; }
    public String getItems() { return items; }
}
