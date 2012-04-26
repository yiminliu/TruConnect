package com.trc.user.activation.logger;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.webflow.execution.FlowExecutionException;

import com.trc.dao.impl.ActivationStateDao;
import com.trc.user.User;

@Service
@Scope("session")
public class ActivationLogger {
  @Autowired
  private ActivationStateDao activationStateDao;
  private ActivationMap activationMap = null;
  private ActivationState currentState = null;
  private ActivationState previousState = null;

  public void endState() {
    currentState.setDateOut(new Date());
    activationStateDao.updateActivationState(currentState);
    previousState = currentState;
    currentState = null;
  }

  public void error(ActState state, Errors errors) {
    // TODO log validation errors from webflow context
  }

  public void exception(FlowExecutionException flowException, Exception exception) {
    String error = "CAUSE=" + flowException.getCause() + "\n\nMSG=" + flowException.getMessage() + "\n\n";
    Throwable rootException = exception.getCause();
    while (rootException != null && rootException.getCause() != null) {
      rootException = rootException.getCause();
    }
    StackTraceElement[] stack = rootException != null ? rootException.getStackTrace() : exception.getStackTrace();
    StackTraceElement se;
    for (int i = 0; i < 5; i++) {
      if (i < stack.length) {
        se = stack[i];
        error += se.getClassName() + "." + se.getMethodName() + "(line:" + se.getLineNumber() + ")";
        if (i < 5) {
          error += "\n";
        }
      }
    }
    currentState.setError(error);
    activationStateDao.updateActivationState(currentState);
  }

  public void start(User user) {
    activationMap = new ActivationMap(user);
    activationStateDao.saveActivationMap(activationMap);
    previousState = null;
    currentState = null;
  }

  public void startState(ActState state) {
    currentState = new ActivationState(state, activationMap);
    if (previousState != null) {
      currentState.setParentState(previousState);
      previousState.getChildren().add(currentState);
      activationStateDao.updateActivationState(previousState);
    } else {
      activationStateDao.saveActivationState(currentState);
    }
  }

}