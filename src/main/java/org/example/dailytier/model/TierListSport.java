package org.example.dailytier.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tier_list_sport")
public class TierListSport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tier_list_id", nullable = false)
    private TierList tierList;

    @ManyToOne
    @JoinColumn(name = "sport_id", nullable = false)
    private Sport sport;

    @Column(name = "tier_rank", nullable = false)
    private int tierRank;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TierList getTierList() { return tierList; }
    public void setTierList(TierList tierList) { this.tierList = tierList; }

    public Sport getSport() { return sport; }
    public void setSport(Sport sport) { this.sport = sport; }

    public int getTierRank() { return tierRank; }
    public void setTierRank(int tierRank) { this.tierRank = tierRank; }
}
