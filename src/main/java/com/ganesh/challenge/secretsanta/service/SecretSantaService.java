package com.ganesh.challenge.secretsanta.service;

import com.ganesh.challenge.secretsanta.domain.Family;
import com.ganesh.challenge.secretsanta.exception.EmptyFamilyMemberListException;
import com.ganesh.challenge.secretsanta.exception.InvalidInputException;

/**
 *
 * Provides the services to generate the secret santas for the entire family
 *
 * @Date    18/10/2019
 * @author  ganesh mohan
 */
public interface SecretSantaService {

    /**
     * To populate the graph and compute the secret santa with its members for the entire family
     * sets the computed result to the Cache
     * @param family
     */
    void populateSecretSanta(Family family) throws InvalidInputException, EmptyFamilyMemberListException;

    /**
     * Retreives value from the Cache for each of the family member to know for whom they are the secret santa for
     * @param name
     * @return Member of the family
     */
    String getMemberforSecretSanta(String santaName);
}
