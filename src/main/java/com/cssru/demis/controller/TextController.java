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
package com.cssru.demis.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cssru.demis.Utils;
import com.cssru.demis.controller.editor.TrimEditor;
import com.cssru.demis.form.TextSuiteForm;
import com.cssru.demis.service.ResultsService;

@Controller
public class TextController {
	@Autowired
	private ResultsService resultsService;
	
	@InitBinder("form")
	protected void initBinder(WebDataBinder binder) {
		TrimEditor editor = new TrimEditor();
		binder.registerCustomEditor(String.class, "text1", editor);
		binder.registerCustomEditor(String.class, "text2", editor);
		binder.registerCustomEditor(String.class, "text3", editor);
		binder.registerCustomEditor(String.class, "text4", editor);
		binder.registerCustomEditor(String.class, "text5", editor);
	}
	
	@GetMapping("/")
	public String getAnalyzeForm(Model model) {
		model.addAttribute("form", new TextSuiteForm());
		return "index";
	}
	
	@PostMapping("/index")
	public String doAnalyze(@Valid @ModelAttribute("form") TextSuiteForm form, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			return null;
		}
		
		form.setResults(
			Utils.findIntersections(
				form.getText1(),
				form.getText2(),
				form.getText3(),
				form.getText4(),
				form.getText5()
			)
		);
		
		resultsService.store(Utils.createTextSuiteDto(form));
		
		return "index";
	}
}
