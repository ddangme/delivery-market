package com.ddangme.dm.controller.good;

import com.ddangme.dm.service.good.GoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodApiController {

    private final GoodService goodService;

}
