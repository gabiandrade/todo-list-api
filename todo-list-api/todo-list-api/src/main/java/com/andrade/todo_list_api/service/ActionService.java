package com.andrade.todo_list_api.service;

import com.andrade.todo_list_api.domain.Action;
import com.andrade.todo_list_api.domain.dto.request.ActionCreateRequest;
import com.andrade.todo_list_api.repository.ActionRepository;
import com.andrade.todo_list_api.service.creation.ActionCreationFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ActionService {

    private final ActionRepository actionRepository;
    private final ActionCreationFactory actionCreationFactory;

    public Action create(ActionCreateRequest request) {
        Action action = actionCreationFactory.create(request);
        return actionRepository.save(action);

    }


}
