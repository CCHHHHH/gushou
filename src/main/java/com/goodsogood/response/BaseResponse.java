package com.goodsogood.response;

import lombok.Data;

@Data
public class BaseResponse {
	private static Integer SUCCESS_CODE = 0;
	private Integer code;
	private Object data;
	private String exception;
	private String message;

    public static BaseResponse initErrorBaseResponse(String baseMessage,Integer code){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(code);
        baseResponse.setMessage(baseMessage);
        baseResponse.setData(null);
        return baseResponse;
    }

	public static BaseResponse initErrorBaseResponse(String baseMessage){
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setCode(9999);
		baseResponse.setMessage(baseMessage);
		baseResponse.setData(null);
		return baseResponse;
	}

	public static BaseResponse initSuccessBaseResponse(Object data){
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessage("操作成功");
        baseResponse.setCode(SUCCESS_CODE);
		baseResponse.setData(data);
		return baseResponse;
	}

	public static BaseResponse initSuccessBaseResponse(Object data,String repMessage){
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessage(repMessage);
        baseResponse.setCode(SUCCESS_CODE);
		baseResponse.setData(data);
		return baseResponse;
	}

}
