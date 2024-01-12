package com.bugflix.weblog.page;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class PageController {
    private final PageServiceImpl pageService;
    @GetMapping("/page")
    public Page getPage(@RequestParam(name = "url") String url) throws Exception{

        return pageService.getPage(url);
    }
}
