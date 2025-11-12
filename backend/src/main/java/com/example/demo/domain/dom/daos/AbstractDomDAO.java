package com.example.demo.domain.dom.daos;

import com.example.demo.domain.dom.AbstractDom;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
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

	//@Column(unique = true)
	protected String lib;
	// Garantit que deux domaines ne peuvent avoir le même lib

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

}
