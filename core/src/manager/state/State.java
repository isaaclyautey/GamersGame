package manager.state;

public abstract class State {
	
	protected StateControllerInterface stateController;
	
	public State(StateController stateController) {
		this.stateController = stateController;
		init();
	}

	
	public abstract void init();
	
	public abstract void enterState();
	public abstract void exitState();
	
	public abstract void update();
	public abstract void render();
	public abstract void renderText();
	
	public abstract void dispose();

}
