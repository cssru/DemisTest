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
package com.cssru.demis.entity;

import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "text_suite")
public class TextSuite {
	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 1000)
	private String text1;
	
	@Column(nullable = false, length = 1000)
	private String text2;
	
	@Column(nullable = false, length = 1000)
	private String text3;
	
	@Column(nullable = false, length = 1000)
	private String text4;
	
	@Column(nullable = false, length = 1000)
	private String text5;
	
	@OneToMany(
		mappedBy = "textSuite",
		fetch = FetchType.EAGER,
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private List<Result> results;
	
}
