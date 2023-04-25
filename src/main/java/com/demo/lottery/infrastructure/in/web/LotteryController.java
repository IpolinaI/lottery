package com.demo.lottery.infrastructure.in.web;

import com.demo.lottery.domain.service.LotteryService;
import com.demo.lottery.infrastructure.in.web.dto.request.LotteryCreateRequest;
import com.demo.lottery.infrastructure.in.web.dto.response.LotteryResponse;
import com.demo.lottery.infrastructure.in.web.mapper.LotteryDtoMapper;
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

@RestController
@RequestMapping("/api/lotteries")
@RequiredArgsConstructor
public class LotteryController {

    private final LotteryService lotteryService;

    private final LotteryDtoMapper lotteryDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LotteryResponse create(@RequestBody @Valid LotteryCreateRequest lotteryCreateRequest) {
        var createdLottery = lotteryService.create(lotteryDtoMapper.map(lotteryCreateRequest));
        return lotteryDtoMapper.map(createdLottery);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LotteryResponse> getAll(@RequestParam(required = false, defaultValue = "1") int page,
                                        @RequestParam(required = false, defaultValue = "30") int limit) {

        var lotteryPage = lotteryService.getAll(PageRequest.of(page - 1, limit)).map(lotteryDtoMapper::map);

        return lotteryPage.getContent();
    }
}
