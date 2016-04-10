package com.eventmap.fluent.controller;

import com.eventmap.fluent.exception.FluentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by huytran on 4/10/16.
 */
@Controller
public class ExceptionController {
    @ExceptionHandler(FluentException.class)
    public ModelAndView myError(Exception exception) {
        System.out.println("----Caught FluentException----");
        ModelAndView mav = new ModelAndView();
        mav.addObject("exc", exception);
        mav.addObject("message", "Troubles");
        mav.setViewName("myerror");
        return mav;
    }
}
