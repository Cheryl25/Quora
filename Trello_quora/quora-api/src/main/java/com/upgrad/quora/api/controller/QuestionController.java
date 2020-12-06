package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.QuestionService;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class QuestionController {

    @Autowired private QuestionService questionService;

    /**
     * Create a question
     * @param questionRequest This object has the content i.e the question.
     * @param accessToken access token to authenticate user.
     * @return UUID of the question created in DB.
     * @throws AuthorizationFailedException In case the access token is invalid.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/question/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionResponse> createQuestion(
            @RequestHeader("authorization") final String accessToken, QuestionRequest questionRequest)
            throws AuthorizationFailedException {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setContent(questionRequest.getContent());
        questionEntity = questionService.createQuestion(questionEntity, accessToken);
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setId(questionEntity.getUuid());
        questionResponse.setStatus("QUESTION CREATED");
        return new ResponseEntity<QuestionResponse>(questionResponse, HttpStatus.CREATED);
    }


}