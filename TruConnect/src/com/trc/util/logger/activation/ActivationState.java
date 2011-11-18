package com.trc.util.logger.activation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "activation_state")
public class ActivationState {
	private ActivationStateId activationStateId = new ActivationStateId();
	private Date dateIn;
	private Date dateOut;
	private ActivationState parent;
	private ActState parentState;
	private Collection<ActivationState> children = new ArrayList<ActivationState>();

	public ActivationState() {
		// do nothing
	}

	public ActivationState(ActState regState, ActivationMap regMap) {
		getActivationStateId().setActivationMap(regMap);
		getActivationStateId().setActState(regState);
		setDateIn(new Date());
	}

	/* **************************************************************
	 * Getters / Setters
	 * ***************************************************************
	 */

	@EmbeddedId
	public ActivationStateId getActivationStateId() {
		return activationStateId;
	}

	public void setActivationStateId(ActivationStateId activationStateId) {
		this.activationStateId = activationStateId;
	}

	@Column(name = "date_in")
	public Date getDateIn() {
		return dateIn;
	}

	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}

	@Column(name = "date_out")
	public Date getDateOut() {
		return dateOut;
	}

	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}

	@Column(name = "parent_state")
	@Enumerated(EnumType.STRING)
	public ActState getParentState() {
		return parentState;
	}

	public void setParentState(ActState parentState) {
		if (parentState != null) {
			this.parentState = parentState;
		} else {
			this.parentState = null;
		}
	}

	public void setParentState(ActivationState parentState) {
		if (parentState != null) {
			this.parentState = parentState.getActivationStateId().getActState();
		} else {
			this.parentState = null;
		}
	}

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@Cascade(value = CascadeType.ALL)
	@JoinColumns({ @JoinColumn(name = "act_id", referencedColumnName = "act_id", insertable = false, updatable = false),
			@JoinColumn(name = "parent_state", referencedColumnName = "state", insertable = false, updatable = false) })
	public ActivationState getParent() {
		return parent;
	}

	public void setParent(ActivationState parent) {
		this.parent = parent;
	}

	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	@Cascade(value = CascadeType.ALL)
	public Collection<ActivationState> getChildren() {
		return children;
	}

	public void setChildren(Collection<ActivationState> children) {
		this.children = children;
	}

	/* **************************************************************
	 * Helper Methods
	 * ***************************************************************
	 */

	@Transient
	public ActState getState() {
		return getActivationStateId().getActState();
	}

	@Transient
	public ActivationMap getActivationMap() {
		return getActivationStateId().getActivationMap();
	}
}
