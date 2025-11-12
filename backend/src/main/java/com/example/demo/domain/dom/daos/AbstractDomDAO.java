package com.example.demo.domain.dom.daos;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.domain.dom.AbstractDom;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

//@Data génère equals() et hashCode() basés sur tous les champs, y compris l'@Id. Cela peut causer des problèmes avec les collections JPA (ex: Set).
//Solution : Utiliser @EqualsAndHashCode(exclude = "id") :

@Entity
@Table(name = AbstractDomDAO.tableName)
@Inheritance(strategy = InheritanceType.JOINED) // ou SINGLE_TABLE, TABLE_PER_CLASS
//@DiscriminatorColumn(name = "type")
public abstract class AbstractDomDAO extends AbstractDom {

	public static final String tableName = "dom";

	public AbstractDomDAO() {
		super();
		// pour que si on doit a l'avenir utilisé lib du parent
		this.lib = super.lib;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(name="lib",unique=true)
	protected String lib;
	// Garantit que deux domaines ne peuvent avoir le même lib

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", nullable = true)
	protected AbstractDomDAO domparent;

	
	@OneToMany(mappedBy = "domparent", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<AbstractDomDAO> subdom = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLib() {
		return lib;
	}

	public void setLib(String lib) {
		this.lib = lib;
		super.lib = lib;
	}

	public AbstractDom getParent() {
		return (AbstractDomDAO) this.domparent;
	}

	public void setParent(AbstractDom domparent) {
		if (this instanceof MainDomDAO) {
			
		} else {
			this.domparent = (AbstractDomDAO) domparent;	
		}
	}

	public List<? extends AbstractDom> getEnfants() {
		return subdom;
	}

	public void setEnfants(List<AbstractDomDAO> subDom) {
		this.subdom = subDom;
	}

	public void addSubDom(AbstractDomDAO subDom) {
		subdom.add(subDom);
	}
}
