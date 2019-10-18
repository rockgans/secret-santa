package com.ganesh.challenge.secretsanta.service;

import com.ganesh.challenge.secretsanta.cache.SantaCache;
import com.ganesh.challenge.secretsanta.domain.Family;
import com.ganesh.challenge.secretsanta.domain.FamilyMember;
import com.ganesh.challenge.secretsanta.domain.RestrictedPair;
import com.ganesh.challenge.secretsanta.exception.EmptyFamilyMemberListException;
import com.ganesh.challenge.secretsanta.exception.InvalidInputException;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.HamiltonianCycleAlgorithm;
import org.jgrapht.alg.tour.PalmerHamiltonianCycle;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class SecretSantaServiceImpl implements SecretSantaService{

    private final static Logger logger = LoggerFactory.getLogger(SantaCache.class);

    @Override
    public void populateSecretSanta(Family family) throws InvalidInputException, EmptyFamilyMemberListException{

        Graph<FamilyMember, DefaultEdge> familyGraph = new SimpleGraph<FamilyMember, DefaultEdge>(DefaultEdge.class);

        if(family != null) {
            List<FamilyMember> familyMembers = family.getFamilyMemberList();

            // Validates for empty list
            if (CollectionUtils.isEmpty(familyMembers)) {
                throw new EmptyFamilyMemberListException();
            }

            // create vertex with family members
            createVertex(familyGraph, familyMembers);
            // add edges between all of the family members
            addEdges(familyGraph, familyMembers);
            // apply the constraints and delete the edges between family members
            applyConstraints(family, familyGraph);
            // compute the hamiltonian cycle or happy path
            GraphPath path = getGraphPath(familyGraph);
            /// populate the in memory cache
            populateSantaCache(path);
            logger.info("secret Santa list populated for family {}", family.getName());
        }
    }

    @Override
    public String getMemberforSecretSanta(String santaName){

        if(logger.isDebugEnabled()){
            logger.debug("user queried for: ",santaName);
        }
        if(!StringUtils.isEmpty(SantaCache.getMemberForSanta(santaName))){
            return "You are the secret Santa for " + SantaCache.getMemberForSanta(santaName);
        }
        return "No Member available for you!";
    }

    /*
    Method to populate the Santa Cache after computation
     */
    private void populateSantaCache(GraphPath path) {

        if(path != null) {
            logger.info("path is : {}" , path.getEdgeList());
            List<FamilyMember> vertexList = path.getVertexList();
            if(!CollectionUtils.isEmpty(vertexList))
            for (int i = 0; i < vertexList.size() - 1; i++) {
                SantaCache.putSantaAndMember(vertexList.get(i).getName(), vertexList.get(i + 1).getName());
            }
        }
    }

    /*
    Method to compute the hamiltonian cycle which is used of computing the secret santa for each of the family members
     */
    private GraphPath getGraphPath(Graph<FamilyMember, DefaultEdge> familyGraph) throws InvalidInputException{
        GraphPath path = null;
        HamiltonianCycleAlgorithm santaCycle;
        try{
            santaCycle= new PalmerHamiltonianCycle();
            path = santaCycle.getTour(familyGraph);

        }catch (IllegalArgumentException e){
           logger.error("graph doesn't meet the ORE requirement");
           throw new InvalidInputException();
        }

        return path;
    }

    /*
    Method to apply the constraints decided by the family
    removes the edges between the restricted pair as specified in the constraints
     */
    private void applyConstraints(Family family, Graph<FamilyMember, DefaultEdge> familyGraph) {
        List<RestrictedPair> constraints = family.getConstraints();


        if(!CollectionUtils.isEmpty(constraints)) {

            if(logger.isDebugEnabled()){
                logger.debug("constraints are: {}", constraints);
            }

            for (RestrictedPair restrictedPair : constraints) {
                familyGraph.removeEdge(restrictedPair.getMember1(), restrictedPair.getMember2());
            }
        }
    }

    /*
    Adds the edges between all the family members in the graph to help traverse the graph
     */
    private void addEdges(Graph<FamilyMember, DefaultEdge> familyGraph, List<FamilyMember> familyMembers) {
        for (int i = 0; i < familyMembers.size(); i++) {
            for (int j = i+1; j < familyMembers.size(); j++) {
                familyGraph.addEdge(familyMembers.get(i),familyMembers.get(j));
            }
        }
    }

    /*
    Creates the Vertex one for each family member and populates the graph.
     */
    private void createVertex(Graph<FamilyMember, DefaultEdge> familyGraph, List<FamilyMember> familyMembers) {
        if(logger.isDebugEnabled()){
            logger.debug("family member list: {}",familyMembers );
        }
        for (FamilyMember familyMember : familyMembers) {
            familyGraph.addVertex(familyMember);
        }
    }

}
