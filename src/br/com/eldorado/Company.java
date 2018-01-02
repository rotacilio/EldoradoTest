package br.com.eldorado;

import java.util.ArrayList;
import java.util.List;

public class Company {

	private String name;
	private List<Question> questions;
	
	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Company(String name, List<Question> questions) {
		super();
		this.name = name;
		this.questions = questions;
	}
	
	public List<Question> getQuestions() {
		return questions;
	}
	
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Company [name=" + name + ", questions=" + questions + "]";
	}
	
	public int getQtdInvalids() {
		int invalids = 0;
		for (Question q : questions) {
			if (q.getAnswerType() == AnswerType.INVALID) {
				invalids++;
			}
		}
		return invalids;
	}
	
	public int getQtdValids() {
		int valids = 0;
		for (Question q : questions) {
			if (q.getAnswerType() != AnswerType.INVALID) {
				valids++;
			}
		}
		return valids;
	}

	public int getQtdValids(Integer idAnswer) {
		int valids = 0;
		for (Question q : questions) {
			if (q.getId().intValue() == idAnswer.intValue() && q.getAnswerType() != AnswerType.INVALID) {
				valids++;
			}
		}
		return valids;
	}

	public long getPorcentagemAnswer(int idAnswer, AnswerType type) {
		int valids = getQtdValids(idAnswer);
		int qtd = 0;
		for (Question q : questions) {
			if (q.getAnswerType() == type && q.getId() == idAnswer) {
				qtd++;
			}
		}
		long porcentagem = Math.round((qtd / (double) valids) * 100);
		return porcentagem;
	}
	
	public List<Integer> getIdAnswers() {
		List<Integer> answers = new ArrayList<>();
		for (Question q : questions) {
			if (!answers.contains(q.getId())) {
				answers.add(q.getId());
			}
		}
		return answers;
	}
}
