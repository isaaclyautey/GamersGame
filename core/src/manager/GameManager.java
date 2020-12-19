package manager;

import manager.state.StateController;
import states.TestingState;

public class GameManager {
	
	private StateController stateController;
	
	public void create() {
		stateController = new StateController();
		stateController.addState("testing", new TestingState(stateController));
	}
	
	public void update() {
		stateController.update();
	}
	
	public void render() {
		stateController.render();
		stateController.renderText();
	}

	public void dispose() {
		stateController.dispose();
	}
}
