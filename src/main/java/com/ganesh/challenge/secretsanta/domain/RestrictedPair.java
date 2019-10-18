package com.ganesh.challenge.secretsanta.domain;

/**
 *
 * Domain Object represents the Pair of family members who cannot be made the santa for each other.
 *
 * @Date    18/10/2019
 * @author  ganesh mohan
 */
public class RestrictedPair {

    public RestrictedPair(){}
    public RestrictedPair(FamilyMember member1, FamilyMember member2){

        this.member1 = member1;
        this.member2 = member2;
    }

    private FamilyMember member1;
    private FamilyMember member2;

    public FamilyMember getMember1() {
        return member1;
    }

    public FamilyMember getMember2() {
        return member2;
    }

    public void setMember1(FamilyMember member1) {
        this.member1 = member1;
    }

    public void setMember2(FamilyMember member2) {
        this.member2 = member2;
    }

    @Override
    public String toString(){
        return this.member1.getName() + " : " + this.member2.getName();
    }
}
