package com.rodriguesadmar.controlesistema.config.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFoundException(ResourceNotFoundException ex) {
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("mensagem", ex.getMessage());
        mv.setStatus(HttpStatus.NOT_FOUND);
        return mv;
    }

    @ExceptionHandler(BusinessException.class)
    public ModelAndView handleBusinessException(BusinessException ex) {
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("mensagem", ex.getMessage());
        mv.setStatus(HttpStatus.BAD_REQUEST);
        return mv;
    }

    @ExceptionHandler(AcessDeniedException.class)
    public ModelAndView handleAcessDeniedException(AcessDeniedException ex) {
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("mensagem", ex.getMessage());
        mv.setStatus(HttpStatus.BAD_REQUEST);
        return mv;
    }


    @ExceptionHandler(UnexpectedErrorException.class)
    public ModelAndView handleUnexpectedErrorException(UnexpectedErrorException ex) {
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("mensagem", ex.getMessage());
        mv.setStatus(HttpStatus.NOT_FOUND);
        return mv;
    }
}
