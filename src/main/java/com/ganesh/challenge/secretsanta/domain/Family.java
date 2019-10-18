package com.ganesh.challenge.secretsanta.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 * Domain Object to hold the Family and member data along with constraints
 *
 * @Date    18/10/2019
 * @author  ganesh mohan
 */
public class Family {

    // Name of the family
    @NotEmpty
    private String name;
    // list of family members cannot be null
    @NotNull
    private List<FamilyMember> familyMemberList;
    // constraints to be applied while selecting the secret santa
    private List<RestrictedPair> constraints;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FamilyMember> getFamilyMemberList() {
        return familyMemberList;
    }

    public void setFamilyMemberList(List<FamilyMember> familyMemberList) {
        this.familyMemberList = familyMemberList;
    }

    public List<RestrictedPair> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<RestrictedPair> constraints) {
        this.constraints = constraints;
    }


}
