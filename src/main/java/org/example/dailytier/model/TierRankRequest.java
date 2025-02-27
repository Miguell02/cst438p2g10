package org.example.dailytier.model;

public class TierRankRequest {
    private String itemName;
    private int tierRank;

    // Default constructor
    public TierRankRequest() {}

    public TierRankRequest(String itemName, int tierRank) {
        this.itemName = itemName;
        this.tierRank = tierRank;
    }

    // Getters and Setters
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public int getTierRank() { return tierRank; }
    public void setTierRank(int tierRank) { this.tierRank = tierRank; }
}