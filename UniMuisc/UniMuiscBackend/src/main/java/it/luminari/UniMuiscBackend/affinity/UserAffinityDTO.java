package it.luminari.UniMuiscBackend.affinity;

import lombok.Data;

// DTO class to represent affinity between two users
@Data
class UserAffinityDTO {
    private Long userId1;
    private Long userId2;
    private double affinityScore;

    private int rank;

    public UserAffinityDTO(Long userId1, Long userId2, double affinityScore) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.affinityScore = affinityScore;
    }
}