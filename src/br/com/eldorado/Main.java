package br.com.eldorado;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		if (args.length > 0) {
			
			Main main = new Main();
			List<Company> companies = new ArrayList<>();
			
			for (int i = 0; i < args.length; i++) {
				Company company = main.readCompanyReportFile(args[i]);
				companies.add(company);
			}
			
			main.buildReportByCompany(companies);
			main.buildFavAnswersByQuestion(companies);
			main.buildQtdAnswersByCompany(companies);
		} else {
			System.out.println("Não foram passados arquivos!");
		}
	}
	
	public void buildReportByCompany(List<Company> companies) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("Summary by companies:");
		strBuilder.append(System.lineSeparator());
		strBuilder.append(System.lineSeparator());
		for (Company company : companies) {
			strBuilder.append(company.getName());
			strBuilder.append(System.lineSeparator());
			List<Integer> answers = company.getIdAnswers();
			Collections.sort(answers);
			for (Integer answer : answers) {
				strBuilder.append(answer+": ");
				long porcentagemFav = company.getPorcentagemAnswer(answer, AnswerType.FAV);
				strBuilder.append(porcentagemFav+"% fav, ");
				long porcentagemNeutral = company.getPorcentagemAnswer(answer, AnswerType.NEUTRAL);
				strBuilder.append(porcentagemNeutral+"% neutral, ");
				long porcentagemUnfav = company.getPorcentagemAnswer(answer, AnswerType.UNFAV);
				strBuilder.append(porcentagemUnfav+"% unfav");
				strBuilder.append(System.lineSeparator());
			}
			strBuilder.append(System.lineSeparator());
		}
		System.out.print(strBuilder.toString());
	}
	
	public void buildFavAnswersByQuestion(List<Company> companies) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("Fav answers by questions:");
		strBuilder.append(System.lineSeparator());
		strBuilder.append(System.lineSeparator());
		
		List<Integer> answers = new ArrayList<>();
		for (Company company : companies) {
			for (Integer id : company.getIdAnswers()) {
				if (!answers.contains(id)) {
					answers.add(id);
				}
			}
		}
		
		for (Integer id : answers) {
			strBuilder.append(id+": ");
			String line = "";
			for (Company company : companies) {
				line = line + company.getName()+" "+company.getPorcentagemAnswer(id, AnswerType.FAV)+"% fav, ";
			}
			line = line.substring(0, line.lastIndexOf(","));
			strBuilder.append(line);
			strBuilder.append(System.lineSeparator());
		}
		strBuilder.append(System.lineSeparator());
		System.out.print(strBuilder.toString());
	}
	
	public void buildQtdAnswersByCompany(List<Company> companies) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("Valid answers:");
		strBuilder.append(System.lineSeparator());
		strBuilder.append(System.lineSeparator());
		for (Company company : companies) {
			strBuilder.append(company.getName()+": "+company.getQtdValids());
			strBuilder.append(System.lineSeparator());
		}
		strBuilder.append(System.lineSeparator());
		strBuilder.append("Invalid answers:");
		strBuilder.append(System.lineSeparator());
		strBuilder.append(System.lineSeparator());
		for (Company company : companies) {
			strBuilder.append(company.getName()+": "+company.getQtdInvalids());
			strBuilder.append(System.lineSeparator());
		}
		System.out.print(strBuilder.toString());
	}
	
	public Company readCompanyReportFile(String pathname) {
		File file = new File(pathname);
		if (file.exists()) {
			try {
				FileReader reader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(reader);
				
				Company company = new Company();
				List<Question> questions = new ArrayList<>();
				
				company.setName(bufferedReader.readLine());
				
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					String[] data = line.split(" ");
					Question question = new Question();
					question.setId(Integer.valueOf(data[0]));
					question.setAnswer(Integer.valueOf(data[1]));
					
					if (Integer.valueOf(data[1]).intValue() < 0 || Integer.valueOf(data[1]).intValue() > 4) {
						question.setAnswerType(AnswerType.INVALID);
					} else if (Integer.valueOf(data[1]).intValue() == 0 || Integer.valueOf(data[1]).intValue() == 1) {
						question.setAnswerType(AnswerType.FAV);
					} else if (Integer.valueOf(data[1]).intValue() == 2) {
						question.setAnswerType(AnswerType.NEUTRAL);
					} else if (Integer.valueOf(data[1]).intValue() == 3 || Integer.valueOf(data[1]).intValue() == 4) {
						question.setAnswerType(AnswerType.UNFAV);
					}
					
					questions.add(question);
				}
				
				company.setQuestions(questions);
				
				bufferedReader.close();
				reader.close();
				
				return company;
			} catch (FileNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
		} else {
			System.out.println("Arquivo não encontrado!");
		}
		return null;
	}
}
