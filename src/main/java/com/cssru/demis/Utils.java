/*
 * Copyright (C) 2016 Sergey Chechenev <cssru@mail.ru>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.cssru.demis;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

import com.cssru.demis.dao.dto.ResultDto;
import com.cssru.demis.dao.dto.TextSuiteDto;
import com.cssru.demis.entity.Result;
import com.cssru.demis.entity.TextSuite;
import com.cssru.demis.form.ResultForm;
import com.cssru.demis.form.TextSuiteForm;

public class Utils {
	private static final String WORD_DELIMITERS = " :[]{}();:,.‒…!?-«»„““”‹›\"'`";
	private static final String WORD_PATTERN = "[A-Za-zА-Яа-я0-9]{3,}";
	private static final Pattern pattern = Pattern.compile(WORD_PATTERN);
	private static final String JOIN_SEPARATOR = "; ";
	
	private Utils() {
		// utilities class
	}
	
	public static TextSuiteDto createTextSuiteDto(TextSuiteForm form) {
		
		return new TextSuiteDto(
			form.getText1(),
			form.getText2(),
			form.getText3(),
			form.getText4(),
			form.getText5(),
			createResultDtoList(form.getResults())
		);
	}
	
	private static List<ResultDto> createResultDtoList(List<ResultForm> resultForms) {
		return resultForms
			.stream()
			.map(Utils::createResultDto)
			.collect(Collectors.toList());
	}
	
	private static ResultDto createResultDto(ResultForm form) {
		return new ResultDto(
			form.getWord(),
			form.getCount(),
			form.getTextNumbers()
		);
	}
	
	public static TextSuite createTextSuite(TextSuiteDto dto) {
		TextSuite result = new TextSuite();
		result.setText1(dto.getText1());
		result.setText2(dto.getText2());
		result.setText3(dto.getText3());
		result.setText4(dto.getText4());
		result.setText5(dto.getText5());
		
		List<Result> resultList = new ArrayList<>(dto.getResults().size());
		for (ResultDto resultDto : dto.getResults()) {
			resultList.add(createResult(result, resultDto));
		}
		
		result.setResults(resultList);
		
		return result;
	}
	
	private static Result createResult(TextSuite textSuite, ResultDto dto) {
		Result result = new Result();
		
		result.setWord(dto.getWord());
		result.setCount(dto.getCount());
		result.setTextNumbers(dto.getTextNumbers());
		result.setTextSuite(textSuite);
		
		return result;
	}
	
	public static List<ResultForm> findIntersections(String... texts) {
		Map<String, WordResult> map = new HashMap<>();
		for (int i = 0; i < texts.length; i++) {
			appendWords(texts[i], i, map);
		}
		
		return map
			.values()
			.stream()
			.filter(w -> w.getCount() >= 2)
			.sorted((w1, w2) ->
				w1.getCount().equals(w2.getCount())
					? w1.getWord().compareTo(w2.getWord())
					: -w1.getCount().compareTo(w2.getCount()))
			.limit(20)
			.map(Utils::toResultForm)
			.collect(Collectors.toList());
	}
	
	private static void appendWords(String text, int textNumber, Map<String, WordResult> map) {
		Arrays
			.stream(StringUtils.split(text, WORD_DELIMITERS))
			.filter(t -> pattern.matcher(t).matches())
			.forEach(w -> {
				String word = w.toLowerCase();
				WordResult result = map.get(word);
				if (result == null) {
					result = new WordResult();
					result.setWord(word);
					result.setCount(1);
				} else if (!result.containsTextNumber(textNumber + 1)) {
					result.incrementCount();
				}
				result.addTextNumber(textNumber + 1);
				map.put(word, result);
			});
	}
	
	private static ResultForm toResultForm(WordResult wordResult) {
		ResultForm result = new ResultForm();
		result.setWord(wordResult.getWord());
		result.setCount(wordResult.getCount());
		
		String[] textNumbersArray = wordResult
			.getTextNumbers()
			.stream()
			.map(String::valueOf)
			.toArray(String[]::new);
		
		result.setTextNumbers(StringUtils.join(textNumbersArray, JOIN_SEPARATOR));
		
		return result;
	}
	
	@Getter
	@Setter
	private static class WordResult {
		private String word;
		private Integer count;
		private Set<Integer> textNumbers;
		
		private WordResult() {
			textNumbers = new HashSet<>();
		}
		
		public void incrementCount() {
			count++;
		}
		
		public void addTextNumber(int textNumber) {
			textNumbers.add(textNumber);
		}
		
		public boolean containsTextNumber(Integer number) {
			return textNumbers.contains(number);
		}
	}
}
