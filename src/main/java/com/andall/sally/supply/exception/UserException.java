package com.andall.sally.supply.exception;

public class UserException extends RuntimeException implements WithTypeException{
  
  private static final long serialVersionUID = 1L;

  private Type type;
  
  public UserException(String message) {
    super(message);
    this.type = Type.LACK_PARAMTER;
  }

  public UserException(Type type, String message) {
    super(message);
    this.type = type;
  }
  
  public  Type type(){
    return type;
  }

  
  public enum Type{
    WRONG_PAGE_NUM,LACK_PARAMTER,USER_NOT_LOGIN,USER_NOT_FOUND,USER_AUTH_FAIL,TOKEN_CHECK_FAILED,USER_POWER_NOT_ENOUGH;
  }

}
