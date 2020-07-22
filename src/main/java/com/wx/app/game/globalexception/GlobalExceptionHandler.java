package com.wx.app.game.globalexception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	/**
	 * 处理自定义的业务异常
	 * @param req
	 * @param e
	 * @return
	 */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
	public  ResultBody bizExceptionHandler(HttpServletRequest req, BizException e){
    	logger.error("发生业务异常！原因是：{}",e.getMsg());
    	return ResultBody.error(e.getCode(),e.getMsg());
    }

	/**
	 * 处理空指针的异常
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value =NullPointerException.class)
	@ResponseBody
	public ResultBody exceptionHandler(HttpServletRequest req, NullPointerException e){
		logger.error("发生空指针异常！原因是:",e);
		return ResultBody.error(CommonEnum.BODY_NOT_MATCH);
	}


    /**
        * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
	@ResponseBody
	public ResultBody exceptionHandler(HttpServletRequest req, Exception e){
    	logger.error("未知异常！原因是:",e);
		Map<String,Object> data = new HashMap<>();
		if (e instanceof MethodArgumentNotValidException){
			MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException)e;
			Iterator var5 = methodArgumentNotValidException.getBindingResult().getFieldErrors().iterator();
			FieldError fieldError;
			while(var5.hasNext()) {
				fieldError = (FieldError)var5.next();
				data.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
    		return ResultBody.error(-1,"参数校验不通过",data);
		}
    	if (e instanceof BindException){
			BindException bindException =(BindException)e;
			Iterator<FieldError> iterator = bindException.getBindingResult().getFieldErrors().iterator();
			FieldError fieldError;
			while(iterator.hasNext()) {
				fieldError = iterator.next();
				data.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return ResultBody.error(-1,"参数校验不通过",data);
		}
       	return ResultBody.error(CommonEnum.INTERNAL_SERVER_ERROR);
    }
}
