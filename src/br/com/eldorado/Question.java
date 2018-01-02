package br.com.eldorado;

public class Question {

	private Integer id;
	private Integer answer;
	private AnswerType answerType;
	
	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Question(Integer id, Integer answer, AnswerType answerType) {
		super();
		this.id = id;
		this.answer = answer;
		this.answerType = answerType;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAnswer() {
		return answer;
	}

	public void setAnswer(Integer answer) {
		this.answer = answer;
	}

	public AnswerType getAnswerType() {
		return answerType;
	}

	public void setAnswerType(AnswerType answerType) {
		this.answerType = answerType;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", answer=" + answer + ", answerType=" + answerType + "]";
	}
	
	
}
