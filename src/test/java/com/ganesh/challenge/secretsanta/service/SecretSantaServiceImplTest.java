package com.ganesh.challenge.secretsanta.service;

import com.ganesh.challenge.secretsanta.cache.SantaCache;
import com.ganesh.challenge.secretsanta.domain.Family;
import com.ganesh.challenge.secretsanta.domain.FamilyMember;
import com.ganesh.challenge.secretsanta.domain.RestrictedPair;
import com.ganesh.challenge.secretsanta.exception.EmptyFamilyMemberListException;
import com.ganesh.challenge.secretsanta.exception.InvalidInputException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecretSantaServiceImpl.class)
public class SecretSantaServiceImplTest {

    private Family family=null;
    @Autowired
    private SecretSantaService secretSantaService;

    @Before
    public void init() {

        // Test case 1

        family = new Family();
        family.setName("gans-family");

        FamilyMember ganesh = new FamilyMember("ganesh");
        FamilyMember raji = new FamilyMember("raji");
        FamilyMember dhriti = new FamilyMember("dhriti");
        FamilyMember roger = new FamilyMember("roger");
        FamilyMember prasanna = new FamilyMember("prasanna");

        List<FamilyMember> list = new ArrayList<>();
        list.add(ganesh);
        list.add(raji);
        list.add(dhriti);
        list.add(roger);
        list.add(prasanna);


        family.setFamilyMemberList(list);

        List<RestrictedPair> constraints = new ArrayList<>();
        RestrictedPair rajiganesh = new RestrictedPair(raji, ganesh);
        RestrictedPair rogerGans = new RestrictedPair(roger,ganesh);



        constraints.add(rajiganesh);
        constraints.add(rogerGans);


        family.setConstraints(constraints);

    }

    @Test
    public void shouldSaveFamily() {


        secretSantaService.populateSecretSanta(family);
        Assert.assertTrue(secretSantaService.getMemberforSecretSanta("ganesh").equals("dhriti") || SantaCache.getMemberForSanta("ganesh").equals("prasanna"));
    }

    @Test(expected = InvalidInputException.class)
    public void shouldThrowInvalidInputException(){

        Family family1 = new Family();
        family1.setName("new - family");

        FamilyMember ganesh = new FamilyMember("ganesh");
        FamilyMember raji = new FamilyMember("raji");

        List<FamilyMember> list = new ArrayList<>();
        list.add(ganesh);
        list.add(raji);



        family1.setFamilyMemberList(list);

        secretSantaService.populateSecretSanta(family1);
    }

    @Test(expected = EmptyFamilyMemberListException.class)
    public void shouldThrowEmptyFamilyMemberListException(){
        family.setFamilyMemberList(new ArrayList<>());
        secretSantaService.populateSecretSanta(family);

    }
}
