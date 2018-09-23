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
@Table(name = "SERVICE")
public class ServicesBO implements Serializable {

	private static final long serialVersionUID = 4162946959533912054L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	private Integer serviceId;

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

}
