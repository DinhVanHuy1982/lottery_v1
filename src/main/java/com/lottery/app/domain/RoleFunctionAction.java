package com.lottery.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RoleFunctionAction.
 */
@Entity
@Table(name = "role_function_action")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RoleFunctionAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 50)
    @Column(name = "role_function_code", length = 50)
    private String roleFunctionCode;

    @Size(max = 50)
    @Column(name = "action_code", length = 50)
    private String actionCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RoleFunctionAction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleFunctionCode() {
        return this.roleFunctionCode;
    }

    public RoleFunctionAction roleFunctionCode(String roleFunctionCode) {
        this.setRoleFunctionCode(roleFunctionCode);
        return this;
    }

    public void setRoleFunctionCode(String roleFunctionCode) {
        this.roleFunctionCode = roleFunctionCode;
    }

    public String getActionCode() {
        return this.actionCode;
    }

    public RoleFunctionAction actionCode(String actionCode) {
        this.setActionCode(actionCode);
        return this;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleFunctionAction)) {
            return false;
        }
        return getId() != null && getId().equals(((RoleFunctionAction) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleFunctionAction{" +
            "id=" + getId() +
            ", roleFunctionCode='" + getRoleFunctionCode() + "'" +
            ", actionCode='" + getActionCode() + "'" +
            "}";
    }
}
