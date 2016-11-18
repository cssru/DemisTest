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
package com.cssru.demis.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cssru.demis.Utils;
import com.cssru.demis.dao.ResultsDao;
import com.cssru.demis.dao.dto.TextSuiteDto;
import com.cssru.demis.entity.TextSuite;

@Repository
public class ResultsDaoImpl implements ResultsDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void store(TextSuiteDto textSuiteDto) {
		TextSuite textSuite = Utils.createTextSuite(textSuiteDto);
		sessionFactory
			.getCurrentSession()
			.save(textSuite);
	}
}
