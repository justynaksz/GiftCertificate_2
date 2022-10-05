package com.epam.esm.audit;

import com.epam.esm.model.Model;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Configures audit methods on before persist and update operations.
 */
public class AuditListener {

    /**
     * Configures audit method on before persist operation.
     * @param model as model entity
     */
    @PrePersist
    public void onPrePersist(Model model) {
        model.setCreateDate();
    }

    /**
     * Configures audit method on before update operation.
     * @param model as model entity
     */
    @PreUpdate
    public void onPreUpdate(Model model) {
        model.setLastUpdateDate();
    }
}
