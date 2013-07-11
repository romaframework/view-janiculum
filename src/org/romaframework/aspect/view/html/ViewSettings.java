package org.romaframework.aspect.view.html;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.romaframework.aspect.view.html.transformer.Transformer;
import org.romaframework.core.Roma;

public class ViewSettings {

	private Map<String, String>				typeRenders;
	private Set<String>								formRenders		= new HashSet<String>();
	private String										pagesPath			= "/dynamic/base/view/";
	private Set<String>								events				= new HashSet<String>();
	private Map<String, Transformer>	transformers	= new HashMap<String, Transformer>();

	public static ViewSettings getInstance() {
		return Roma.autoComponent(ViewSettings.class);
	}

	public ViewSettings() {
		events.add("click");
		events.add("change");
		events.add("blur");
		events.add("dblclick");
		events.add("focus");
		events.add("keydown");
		events.add("keyup");
		events.add("mousedown");
		events.add("mousemove");
		events.add("mouseout");
		events.add("mouseover");
		events.add("mouseup");
		events.add("resize");
	}

	public Map<String, String> getTypeRenders() {
		return typeRenders;
	}

	public void setTypeRenders(Map<String, String> typeRenders) {
		this.typeRenders = typeRenders;
	}

	public Set<String> getFormRenders() {
		return formRenders;
	}

	public void setFormRenders(Set<String> formRenders) {
		this.formRenders = formRenders;
	}

	public String getPagesPath() {
		return pagesPath;
	}

	public void setPagesPath(String pagesPath) {
		this.pagesPath = pagesPath;
	}

	public String getRender(String typeName) {
		if (typeRenders != null) {
			typeRenders.get(typeName);
		}
		return null;
	}

	public Set<String> getEvents() {
		return events;
	}

	public void setEvents(Set<String> events) {
		this.events = events;
	}

	public Map<String, Transformer> getTransformers() {
		return transformers;
	}

	public void setTransformers(Map<String, Transformer> transformers) {
		this.transformers = transformers;
	}

}
