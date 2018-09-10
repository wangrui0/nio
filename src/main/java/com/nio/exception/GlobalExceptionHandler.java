package com.nio.exception;

/**
 * Created by wangruii 2018/8/29.
 */
//@ControllerAdvice
public class GlobalExceptionHandler {
//    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    @ExceptionHandler(value = Throwable.class)
//    @ResponseBody
//    public Result jsonErrorHandler(Throwable e) {
//        Result baseRes = new Result();
//        //自定义业务异常
//        if (e instanceof BusinessException) {
//            BusinessException ae = (BusinessException) e;
//            baseRes.setStatus(ae.getCode());
//            baseRes.setMessage(ae.getMessage());
//            log.error(e.getMessage());
//        } else {
//            ////未知异常 空指针什么的
//            baseRes.setStatus(IndexErrorMsgEnum.SYSTEM_EXCEPTION.getCode());
//            baseRes.setMessage(e.getMessage());
//            e.printStackTrace();
//            log.error(e.getMessage());
//        }
//        return baseRes;
//    }
}