package com.demo.lottery.infrastructure.in.web;

import com.demo.lottery.domain.service.BallotService;
import com.demo.lottery.infrastructure.in.web.dto.request.BallotCreateRequest;
import com.demo.lottery.infrastructure.in.web.dto.response.BallotResponse;
import com.demo.lottery.infrastructure.in.web.mapper.BallotDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ballots")
@RequiredArgsConstructor
public class BallotController {

    private final BallotService ballotService;

    private final BallotDtoMapper ballotDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BallotResponse create(@RequestBody @Valid BallotCreateRequest ballotCreateRequest) {
        var createdBallot = ballotService.create(ballotDtoMapper.map(ballotCreateRequest));
        return ballotDtoMapper.map(createdBallot);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BallotResponse> getAllForUser(@RequestParam(required = false, defaultValue = "1") int page,
                                              @RequestParam(required = false, defaultValue = "30") int limit) {
        var ballotPage = ballotService.getAllForUser(PageRequest.of(page - 1, limit)).map(ballotDtoMapper::map);
        return ballotPage.getContent();
    }
}
