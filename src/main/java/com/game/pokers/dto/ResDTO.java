package com.game.pokers.dto;


public class ResDTO<T> {

    public static <R> ResDTO<R> getErrResDTO(int errorCode, String errMsg) {
        return new ResDTO<>(null, errMsg, true, errorCode);
    }
    public static <R> ResDTO<R> getSuccessResDTO(R data) {
        return new ResDTO<>(data, "", false, 0);
    }
    public T data;
    public String errMsg;

    public int errCode;
    public Boolean isError;

    public ResDTO(T data, String errMsg, Boolean isError, int errCode) {
        this.data = data;
        this.errMsg = errMsg;
        this.isError = isError;
        this.errCode = errCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }
}
