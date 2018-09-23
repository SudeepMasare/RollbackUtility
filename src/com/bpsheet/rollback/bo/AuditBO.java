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
@Table(name = "AUDIT")
public class AuditBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2548758635810472061L;

	/**
	 * CREATE TABLE SM_Hibernate_1.AUDIT( ID INT(10) NOT NULL AUTO_INCREMENT,
	 * EXECUTOR VARCHAR(20) NOT NULL,
	 * 
	 * COMP_MIN_ID INT(10),COMP_MAX_ID INT(10), FUNS_MIN_ID INT(10),FUNS_MAX_ID
	 * INT(10), GROUP_MIN_ID INT(10),GROUP_MAX_ID INT(10), MENUS_MIN_ID
	 * INT(10),MENUS_MAX_ID INT(10), PAGE_MIN_ID INT(10),PAGE_MAX_ID INT(10),
	 * PROCES_MIN_ID INT(10),PROCES_MAX_ID INT(10), DO_MIN_ID INT(10),DO_MAX_ID
	 * INT(10), SVC_MIN_ID INT(10),SVC_MAX_ID INT(10), VIEW_MIN_ID
	 * INT(10),VIEW_MAX_ID INT(10),
	 * 
	 * PRIMARY KEY(ID) );
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	private Integer id;

	@Column(name = "EXECUTOR", nullable = false)
	private String executorName;

	@Column(name = "COMP_MIN_ID")
	private Integer compMinId;

	@Column(name = "COMP_MAX_ID")
	private Integer compMaxId;

	@Column(name = "FUNS_MIN_ID")
	private Integer funsMinId;

	@Column(name = "FUNS_MAX_ID")
	private Integer funsMaxId;

	@Column(name = "GROUP_MIN_ID")
	private Integer groupMinId;

	@Column(name = "GROUP_MAX_ID")
	private Integer groupMaxId;

	@Column(name = "MENUS_MIN_ID")
	private Integer menusMinId;

	@Column(name = "MENUS_MAX_ID")
	private Integer menusMaxId;

	@Column(name = "PAGE_MIN_ID")
	private Integer pageMinId;

	@Column(name = "PAGE_MAX_ID")
	private Integer pageMaxId;

	@Column(name = "PROCES_MIN_ID")
	private Integer processMinId;

	@Column(name = "PROCES_MAX_ID")
	private Integer processMaxId;

	@Column(name = "DO_MIN_ID")
	private Integer displayObjectMinId;

	@Column(name = "DO_MAX_ID")
	private Integer displayObjectMaxId;

	@Column(name = "SVC_MIN_ID")
	private Integer serviceMinId;

	@Column(name = "SVC_MAX_ID")
	private Integer serviceMaxId;

	@Column(name = "VIEW_MIN_ID")
	private Integer viewMinId;

	@Column(name = "VIEW_MAX_ID")
	private Integer viewMaxId;

	public String getExecutorName() {
		return executorName;
	}

	public void setExecutorName(String executorName) {
		this.executorName = executorName;
	}

	public Integer getCompMinId() {
		return compMinId;
	}

	public void setCompMinId(Integer compMinId) {
		this.compMinId = compMinId;
	}

	public Integer getCompMaxId() {
		return compMaxId;
	}

	public void setCompMaxId(Integer compMaxId) {
		this.compMaxId = compMaxId;
	}

	public Integer getFunsMinId() {
		return funsMinId;
	}

	public void setFunsMinId(Integer funsMinId) {
		this.funsMinId = funsMinId;
	}

	public Integer getFunsMaxId() {
		return funsMaxId;
	}

	public void setFunsMaxId(Integer funsMaxId) {
		this.funsMaxId = funsMaxId;
	}

	public Integer getGroupMinId() {
		return groupMinId;
	}

	public void setGroupMinId(Integer groupMinId) {
		this.groupMinId = groupMinId;
	}

	public Integer getGroupMaxId() {
		return groupMaxId;
	}

	public void setGroupMaxId(Integer groupMaxId) {
		this.groupMaxId = groupMaxId;
	}

	public Integer getMenusMinId() {
		return menusMinId;
	}

	public void setMenusMinId(Integer menusMinId) {
		this.menusMinId = menusMinId;
	}

	public Integer getMenusMaxId() {
		return menusMaxId;
	}

	public void setMenusMaxId(Integer menusMaxId) {
		this.menusMaxId = menusMaxId;
	}

	public Integer getPageMinId() {
		return pageMinId;
	}

	public void setPageMinId(Integer pageMinId) {
		this.pageMinId = pageMinId;
	}

	public Integer getPageMaxId() {
		return pageMaxId;
	}

	public void setPageMaxId(Integer pageMaxId) {
		this.pageMaxId = pageMaxId;
	}

	public Integer getProcessMinId() {
		return processMinId;
	}

	public void setProcessMinId(Integer processMinId) {
		this.processMinId = processMinId;
	}

	public Integer getProcessMaxId() {
		return processMaxId;
	}

	public void setProcessMaxId(Integer processMaxId) {
		this.processMaxId = processMaxId;
	}

	public Integer getDisplayObjectMinId() {
		return displayObjectMinId;
	}

	public void setDisplayObjectMinId(Integer displayObjectMinId) {
		this.displayObjectMinId = displayObjectMinId;
	}

	public Integer getDisplayObjectMaxId() {
		return displayObjectMaxId;
	}

	public void setDisplayObjectMaxId(Integer displayObjectMaxId) {
		this.displayObjectMaxId = displayObjectMaxId;
	}

	public Integer getServiceMinId() {
		return serviceMinId;
	}

	public void setServiceMinId(Integer serviceMinId) {
		this.serviceMinId = serviceMinId;
	}

	public Integer getServiceMaxId() {
		return serviceMaxId;
	}

	public void setServiceMaxId(Integer serviceMaxId) {
		this.serviceMaxId = serviceMaxId;
	}

	public Integer getViewMinId() {
		return viewMinId;
	}

	public void setViewMinId(Integer viewMinId) {
		this.viewMinId = viewMinId;
	}

	public Integer getViewMaxId() {
		return viewMaxId;
	}

	public void setViewMaxId(Integer viewMaxId) {
		this.viewMaxId = viewMaxId;
	}

	public Integer getId() {
		return id;
	}

}
