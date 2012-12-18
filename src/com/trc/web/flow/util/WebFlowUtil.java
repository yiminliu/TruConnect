package com.trc.web.flow.util;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageResolver;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.RequestContextHolder;

public final class WebFlowUtil {

	protected static RequestContext getRequestContext() {
		return RequestContextHolder.getRequestContext();
	}

	protected static void addError(MessageContext messageContext, MessageResolver message) {
		messageContext.addMessage(message);
	}

	protected static MessageResolver buildMessage(String source, String code, String defaultText) {
		MessageBuilder message = new MessageBuilder().error().source(source).code(code);
		if (source != null)
			message.source(source);
		if (code != null)
			message.code(code);
		if (defaultText != null)
			message.defaultText(defaultText);
		return message.build();
	}

	public static void addError(String source, String code, String message) {
		addError(getRequestContext().getMessageContext(), buildMessage(source, code, message));
	}

	public static void addError(String code, String message) {
		addError(null, code, message);
	}

	public static void addError(String message) {
		addError(null, null, message);
	}

}
