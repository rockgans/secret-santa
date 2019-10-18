package com.ganesh.challenge.secretsanta.controller;

import com.ganesh.challenge.secretsanta.domain.Family;
import com.ganesh.challenge.secretsanta.exception.EmptyFamilyMemberListException;
import com.ganesh.challenge.secretsanta.exception.InvalidInputException;
import com.ganesh.challenge.secretsanta.service.SecretSantaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 *
 * Apis to save  extended family members and provide secret santa with his member
 *
 * @Date    18/10/2019
 * @author  ganesh mohan
 */
@RestController
public class SecretSantaController {

    private final static Logger logger = LoggerFactory.getLogger(com.ganesh.challenge.secretsanta.controller.SecretSantaController.class);

    @Autowired
    private SecretSantaService secretSantaService;

    /**
     * Landing page / Home Url
     * @return
     */
    @RequestMapping("/")
    public String home() {
        return "Secret santa!";
    }

    /**
     * Api to generate Secret Santa for a family
     * @param family
     */
    @RequestMapping(value = "/family", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void assignSantas(@RequestBody Family family) {

        secretSantaService.populateSecretSanta(family);
    }

    /**
     * Api to find the member for whom the person is a santa
     * @param santaName
     * @return
     */
    @RequestMapping(value = "/santa", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String getMemberForSanta(@RequestParam String santaName) {

        return secretSantaService.getMemberforSecretSanta(santaName);
    }

    /**
     * Common Exception handler to handle EmptyFamilyMemberListException
     * @param re
     * @return
     */
    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Empty family member list")
    @ExceptionHandler(EmptyFamilyMemberListException.class)
    public String handleEmptyFamilyMemberListException(EmptyFamilyMemberListException re) {
        return re.getMessage();
    }

    /**
     * Common Exception handler for InvalidInputException
     * @param re
     * @return
     */
    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid combination of constraints, secret santa couldn't be computed.")
    @ExceptionHandler(InvalidInputException.class)
    public String handleInvalidInputException(InvalidInputException re) {
        return re.getMessage();
    }


    /**
     * Common Exception handler for uncaught exception
     * @param re
     * @return
     */
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Something went Wrong !")
    @ExceptionHandler(Throwable.class)
    public void handleAllException(Throwable re) {

        logger.error("server error {}", re.getMessage());
        if(logger.isDebugEnabled()){
            logger.debug(re.getStackTrace().toString());
        }

    }


}
