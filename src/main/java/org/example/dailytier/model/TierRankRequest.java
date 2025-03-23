package org.example.dailytier.model;

import java.util.List;

public class TierRankRequest {

    private String username;
    private String tierName;
    private List<SportRank> sports;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTierName() {
        return tierName;
    }

    public void setTierName(String tierName) {
        this.tierName = tierName;
    }

    public List<SportRank> getSports() {
        return sports;
    }

    public void setSports(List<SportRank> sports) {
        this.sports = sports;
    }

    // Nested class for sport ranks
    public static class SportRank {
        private Long sportId;
        private int tierRank;

        public Long getSportId() {
            return sportId;
        }

        public void setSportId(Long sportId) {
            this.sportId = sportId;
        }

        public int getTierRank() {
            return tierRank;
        }

        public void setTierRank(int tierRank) {
            this.tierRank = tierRank;
        }
    }
}
