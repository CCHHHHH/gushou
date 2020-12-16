package com.goodsogood.response;

import lombok.Data;

@Data
public class BaseResponse {
	private static Integer SUCCESS_CODE = 0;
	private Object data;
    private Integer statusCode;
	private String repMessage;
	private Integer errorCode;

    public static BaseResponse initErrorBaseResponse(String baseMessage,Integer code){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(code);
        baseResponse.setRepMessage(baseMessage);
        baseResponse.setData(null);
        return baseResponse;
    }

	public static BaseResponse initErrorBaseResponse(String baseMessage){
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(9999);
		baseResponse.setRepMessage(baseMessage);
		baseResponse.setData(null);
		return baseResponse;
	}

	public static BaseResponse initSuccessBaseResponse(Object data){
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setRepMessage("操作成功");
        baseResponse.setStatusCode(SUCCESS_CODE);
		baseResponse.setData(data);
		return baseResponse;
	}

	public static BaseResponse initSuccessBaseResponse(Object data,String repMessage){
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setRepMessage(repMessage);
        baseResponse.setStatusCode(SUCCESS_CODE);
		baseResponse.setData(data);
		return baseResponse;
	}

}
