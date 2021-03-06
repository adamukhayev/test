package com.example.demo.service.impl;

import com.example.demo.exeptions.GeneralTestApiException;
import com.example.demo.exeptions.TestApiError;
import com.example.demo.model.Status;
import com.example.demo.model.dto.QuestionDto;
import com.example.demo.model.entity.QuestionEntity;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.service.IQuestionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Transactional
@Slf4j
@Service
public class QuestionImpl implements IQuestionService {

    private final ModelMapper modelMapper;
    private final QuestionRepository questionRepository;

    @Override
    public Long addQuestion(QuestionDto questionDto) {
        return questionRepository
                .save(modelMapper.map(
                        questionDto,
                        QuestionEntity.class))
                .getQuestionId();
    }

    @Override
    public Long removeQuestion(Long questionId) {
        QuestionEntity questionEntity = questionRepository.findByQuestionId(questionId);
        if (Objects.nonNull(questionEntity)) {
            questionEntity.setStatus(Status.DELETED);
            return questionRepository.save(questionEntity).getQuestionId();
        } else {
            throw new GeneralTestApiException(TestApiError.E500_QUESTION_NOT_FOUND);
        }
    }

    @Override
    public Long updateQuestion(QuestionDto questionDto) {

        if (questionRepository.existsByQuestionId(questionDto.getQuestionId())) {
            QuestionEntity questionEntity = modelMapper.map(questionDto, QuestionEntity.class);
            questionEntity.setStatus(Status.ACTIVE);
            return questionRepository.save(questionEntity).getQuestionId();
        } else {
            throw new GeneralTestApiException(TestApiError.E500_QUESTION_NOT_FOUND);
        }
    }

    @Override
    public void addAllQuestionSave(List<QuestionDto> questionDtos) {
        List<QuestionEntity> entities = new ArrayList<>();

        for (QuestionDto d : questionDtos) {
            QuestionEntity questionEntity = modelMapper.map(d, QuestionEntity.class);
            questionEntity.setStatus(Status.ACTIVE);
            entities.add(questionEntity);
        }
        try {
            questionRepository.saveAll(entities);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}