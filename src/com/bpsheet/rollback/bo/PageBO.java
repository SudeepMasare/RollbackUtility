package com.bpsheet.rollback.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author SudeepMasare
 * 
 */
@Entity
@Table(name = "PAGE")
public class PageBO implements Serializable {

	private static final long serialVersionUID = 5502282322044903500L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	private Integer pageId;

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

}
