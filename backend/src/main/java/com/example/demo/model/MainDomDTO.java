package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class MainDomDTO {

	private String name;
	private List<String> children = new ArrayList();

	public JsonNode toD3format(List<MainDomDAO> listMainDomDAO) {
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

		for (MainDomDAO currentMainDomDAO : listMainDomDAO) {
			MainDomDTO domDTO = new MainDomDTO();
			domDTO.setName(currentMainDomDAO.getLib());
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
