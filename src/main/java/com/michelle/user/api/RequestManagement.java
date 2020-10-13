package com.michelle.user.api;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

/** The Class RequestManagement. */
@Slf4j
public class RequestManagement {

  /**
   * Log payload.
   *
   * @param message the log message
   * @param payload the payload to be logged
   */
  public void logPayload(String message, Object payload) {
    log.info(message + " : " + new Gson().toJson(payload));
  }

  /**
   * Circuit breaker error management.
   *
   * @param throwable the throwable
   * @return the object based on the error management
   */
  public Object errorManagement(Throwable throwable) {
    log.error("Error : " + throwable.getMessage());
    return null;
  }
}
