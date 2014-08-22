package cz.sam.cubix.gui;

import java.util.ArrayList;
import java.util.List;

public class GuiComponentSorter {
	
	private List<GuiComponent> components = new ArrayList<GuiComponent>();
	private float startX;
	private float startY;
	private float distance = 5F;
	
	public void addComponent(GuiComponent component) {
		this.components.add(component);
	}
	
	public void sort(Gui gui) {
		int index = 0;
		float y = this.startY;
		for(GuiComponent component : this.components) {
			if(index != 0) {
				y += GuiComponent.default_height + this.distance;
			}
			float newY = index == 0 ? this.startY : y;
			component.setX(this.startX);
			component.setY(newY);
			gui.addComponent(component);
			index++;
		}
	}
	
	public void setStartLocation(float x, float y) {
		this.startX = x;
		this.startY = y;
	}
	
	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	public void clear() {
		this.components.clear();
	}
	
}
