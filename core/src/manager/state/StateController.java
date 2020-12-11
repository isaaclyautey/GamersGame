package manager.state;

import java.util.HashMap;

public class StateController implements StateControllerInterface{
	
	private String currentState;
	private HashMap<String, State> stateMap;
	
	public StateController() {
		stateMap = new HashMap<>();
	}
	
	public void addState(String stateName, State state) {
		stateMap.put(stateName, state);
		
		if(currentState == null) {
			currentState = stateName;
			stateMap.get(stateName).enterState();
		}
	}
	
	
	public void update() {
		stateMap.get(currentState).update();
	}
	
	public void render() {
		stateMap.get(currentState).render();
	}
	
	public void renderText() {
		stateMap.get(currentState).renderText();
	}
	
	public void dispose() {
		stateMap.get(currentState).dispose();
	}
	

	@Override
	public void changeState(String state) {
		stateMap.get(currentState).exitState();
		currentState = state;
		stateMap.get(currentState).enterState();
	}

}
