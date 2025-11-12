package com.example.demo.domain.dom;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;

//@MappedSuperclass
public abstract class AbstractDom {

	@JsonIgnore
	protected AbstractDom domparent; // null si c'est la racine

	@JsonIgnore
	protected List<AbstractDom> subdom = new ArrayList<>();

	@JsonIgnore
	protected String lib;

	@JsonIgnore
	public List<? extends AbstractDom> dom = new ArrayList<>();
	
	public void afficher(String prefixe) {
		System.out.println(prefixe + lib);
        for (AbstractDom enfant : subdom) {
            enfant.afficher(prefixe + "  ");
        };
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof AbstractDom)) return false;
	    AbstractDom dossier = (AbstractDom) o;
	    return lib != null ? lib.equals(dossier.lib) : dossier.lib == null;
	}

	@Override
	public int hashCode() {
	    return lib != null ? lib.hashCode() : 0;
	}

}