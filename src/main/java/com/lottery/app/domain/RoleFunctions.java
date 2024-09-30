package com.lottery.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RoleFunctions.
 */
@Entity
@Table(name = "role_functions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RoleFunctions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 50)
    @Column(name = "code", length = 50)
    private String code;

    @Size(max = 50)
    @Column(name = "role_code", length = 50)
    private String roleCode;

    @Size(max = 50)
    @Column(name = "function_code", length = 50)
    private String functionCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RoleFunctions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public RoleFunctions code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRoleCode() {
        return this.roleCode;
    }

    public RoleFunctions roleCode(String roleCode) {
        this.setRoleCode(roleCode);
        return this;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getFunctionCode() {
        return this.functionCode;
    }

    public RoleFunctions functionCode(String functionCode) {
        this.setFunctionCode(functionCode);
        return this;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleFunctions)) {
            return false;
        }
        return getId() != null && getId().equals(((RoleFunctions) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleFunctions{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", roleCode='" + getRoleCode() + "'" +
            ", functionCode='" + getFunctionCode() + "'" +
            "}";
    }
}
