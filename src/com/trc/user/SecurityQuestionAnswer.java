package com.trc.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SecurityQuestionAnswer implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String answer;

	@Column(name = "question_id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "question_answer")
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "SecurityQuestionAnswer [id=" + id + ", answer=" + answer + "]";
	}

}