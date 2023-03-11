package com.simplicia.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AtmpResponse {
    private Long id;
    private String nns;
    private String siret;
    private Integer nbJoursArret;

    public Long getId() {
        return id;
    }

    public String getNns() {
        return nns;
    }

    public String getSiret() {
        return siret;
    }

    public Integer getNbJoursArret() {
        return nbJoursArret;
    }

    public AtmpResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public AtmpResponse setNns(String nns) {
        this.nns = nns;
        return this;
    }

    public AtmpResponse setSiret(String siret) {
        this.siret = siret;
        return this;
    }

    public AtmpResponse setNbJoursArret(Integer nbJoursArret) {
        this.nbJoursArret = nbJoursArret;
        return this;
    }
}
