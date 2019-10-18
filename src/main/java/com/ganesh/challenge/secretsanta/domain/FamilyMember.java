package com.ganesh.challenge.secretsanta.domain;

/**
 *
 * Domain Object represents each member of the family
 *
 * @Date    18/10/2019
 * @author  ganesh mohan
 */
public class FamilyMember {

    public FamilyMember(){}

    public FamilyMember(String name){
     this.name = name;
    }

    //Unique name of the family member
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof FamilyMember)) {
            return false;
        }

        FamilyMember member = (FamilyMember) o;

        return member.getName().equals(this.getName());
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }


}
