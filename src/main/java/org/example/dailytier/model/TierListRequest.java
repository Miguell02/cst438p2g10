package org.example.dailytier.model;

public class TierListRequest {
    private Long userId;
    private String tierName;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getTierName() { return tierName; }
    public void setTierName(String tierName) { this.tierName = tierName; }
}
