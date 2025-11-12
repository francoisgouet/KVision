package com.example.demo.domain.dom.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.demo.domain.dom.daos.AbstractDomDAO;
import com.example.demo.domain.dom.daos.MainDomDAO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class MainDomDTO extends AbstractDomDTO {

	@JsonProperty("name")
	private String name;

	@JsonProperty("children")
	private List<String> children = new ArrayList();

	@JsonIgnore
	private String otherParam = "otherParam";

	public JsonNode toD3format(List<? extends AbstractDomDAO> listMainDomDAO) {
		// doit coresspondre aux D3
		// {name:"":children:[]}
		// mapper
		ObjectMapper mapper = new ObjectMapper();
		String mainDomAsJsonString = null;
		JsonNode jsonNodeDomArr = mapper.createArrayNode();

		// root of the list of domain
		MainDomDTO rootDom = new MainDomDTO();
		rootDom.setName("root");

		// rootNode Json
		JsonNode rootNode = mapper.valueToTree(rootDom);
		Object propertyRootNode = ((ObjectNode) rootNode).properties().toArray()[1];

		// childrenPropRootNodeKey=children ( pour eviter le code en dur )
		String childrenPropRootNodeKey = ((Map.Entry<?, ?>) propertyRootNode).getKey().toString();

		for (AbstractDomDAO abstractDomDAO : (List<MainDomDAO>)listMainDomDAO) {
			MainDomDTO domDTO = new MainDomDTO();
			domDTO.setName(abstractDomDAO.getLib());
			try {
				mainDomAsJsonString = mapper.writeValueAsString(domDTO);
				((ArrayNode) jsonNodeDomArr).add(mapper.readTree(mainDomAsJsonString));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		((ObjectNode) rootNode).set(childrenPropRootNodeKey, jsonNodeDomArr);

		return rootNode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getChildren() {
		return children;
	}

	public void setChildren(List<String> children) {
		this.children = children;
	}
}
